package com.kibou.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Marker;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;

//参考 :
//1.https://github.com/kofemann/mongo-appender-for-logback/blob/master/src/main/java/org/kofemann/MongoDBAppender.java
//2.https://assylias.wordpress.com/2013/03/22/a-simple-logback-appender-for-mongodb/
/**
 * @author shijun.shi
 */
public class MongoDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent>{
	//由数据来提供数据的同步,所以使用UnsynchronizedAppenderBase作为父类即可
	
	//appender可以认为是目的地 , layout是格式化ILoggerEvent - 将LoggerEvent信息(提取)转成string 按照指定格式，排列
	
	//另外logback自身有一个(A)DBAppenderBase 看其使用!

	//mongodb configuration
	private String host = "localhost";
	private int port = 27017;//default
	private String databaseName = "test";
	private String collectionName = "log";
	
	//unused now
    protected Encoder<ILoggingEvent> encoder;
	
	private MongoClient mongoClient;
	private MongoDatabase logDatabase;
	private MongoCollection<DBObject> logCollection;
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public void setEncoder(Encoder<ILoggingEvent> encoder) {
		this.encoder = encoder;
	}

	@Override
	public void start() {
		try {
            connect();
            super.start();
        } catch (MongoException e) {
            addError("Can't connect to mongo: host=" + host + ", port=" + port, e);
        }
	}
	
	@Override
	public void stop() {
		mongoClient.close();
		super.stop();
	}
	
	private void connect(){
		mongoClient = new MongoClient(host, port);
		logDatabase = mongoClient.getDatabase(databaseName);
	    logCollection = logDatabase.getCollection(collectionName,DBObject.class);
	}
	
	//1.https://github.com/kofemann/mongo-appender-for-logback/
	@Override
	protected void append(ILoggingEvent logginEvent) {
		BasicDBObjectBuilder objectBuilder = BasicDBObjectBuilder.start();
		
		doAppend(logginEvent, objectBuilder);

        secondarySubAppend(logginEvent, objectBuilder);
        
        logCollection.insertOne(objectBuilder.get());
	}

	private void doAppend(ILoggingEvent logginEvent, BasicDBObjectBuilder objectBuilder) {
		objectBuilder.add("timestamp", new Date(logginEvent.getTimeStamp()))
				.add("msg", logginEvent.getFormattedMessage())
                .add("level", logginEvent.getLevel().toString())
                .add("logger", logginEvent.getLoggerName())
                .add("thread", logginEvent.getThreadName())
              ;
		
        if(logginEvent.hasCallerData()) {
            StackTraceElement st = logginEvent.getCallerData()[0];
            //String callerData = String.format("%s.%s:%d", st.getClassName(), st.getMethodName(), st.getLineNumber());
            //objectBuilder.add("caller", callerData);
            objectBuilder.push("caller")
            	.add("class", st.getClassName())
        		.add("method", st.getMethodName())
        		.add("line", st.getLineNumber())
        		.add("filename", st.getFileName())
        	.pop()
        	;
        }
	}
	
	private void secondarySubAppend(ILoggingEvent logginEvent, BasicDBObjectBuilder objectBuilder) {
		Map<String, String> propertyMaps = mergePropertyMaps(logginEvent);
        if(propertyMaps.size() > 0){
        	objectBuilder.add("propertyMaps",new BasicDBObject(propertyMaps));
        }
        
        if(logginEvent.getThrowableProxy() != null){
        	doAppendException(logginEvent.getThrowableProxy(), objectBuilder);
        }
	}
	
	private void doAppendException(IThrowableProxy throwableProxy, BasicDBObjectBuilder objectBuilder) {
		if (throwableProxy == null)
			return;
		String tpAsString = ThrowableProxyUtil.asString(throwableProxy); // the stack trace basically
		
		List<String> stackTrace = Arrays.asList(tpAsString.replace("\t", "").split(CoreConstants.LINE_SEPARATOR));
		if (stackTrace.size() > 0) {
			objectBuilder.add("exception", stackTrace.get(0));
		}
		if (stackTrace.size() > 1) {
			objectBuilder.add("stacktrace", stackTrace.subList(1, stackTrace.size()));
		}
	}
	
	//from DBAppenderBase
	Map<String, String> mergePropertyMaps(ILoggingEvent event) {
        Map<String, String> mergedMap = new HashMap<String, String>();
        // we add the context properties first, then the event properties, since
        // we consider that event-specific properties should have priority over
        // context-wide properties.
        Map<String, String> loggerContextMap = event.getLoggerContextVO().getPropertyMap();
        Map<String, String> mdcMap = event.getMDCPropertyMap();
        if (loggerContextMap != null) {
            mergedMap.putAll(loggerContextMap);
        }
        if (mdcMap != null) {
            mergedMap.putAll(mdcMap);
        }

        return mergedMap;
    }

	
	
	//2. https://assylias.wordpress.com/.....
	protected void append0(ILoggingEvent evt) {
        if (evt == null) return; //just in case
 
        DBObject logObject = getBasicLog(evt);
        try {
            logException(evt.getThrowableProxy(), logObject);
            logCollection.insertOne(logObject);
        } catch (Exception e) {
            try {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                logObject.put("logging_error", "Could not log all the event information: " + sw.toString());
                logObject.put("level", "ERROR");
                logCollection.insertOne(logObject);
            } catch (Exception e2) { //really not working
                addError("Could not insert log to mongo: " + evt, e2);
            }
        }
    }
	
	private DBObject getBasicLog(ILoggingEvent evt) {
		BasicDBObject logObject = new BasicDBObject();
        logObject.append("logger", evt.getLoggerName())
        		.append("timestamp", new Date(evt.getTimeStamp()))
        		.append("level", String.valueOf(evt.getLevel())); //in case getLevel returns null
        Marker m = evt.getMarker();
        if (m != null) {
            logObject.append("marker", m.getName());
        }
        logObject.append("thread", evt.getThreadName())
        		.append("message", evt.getFormattedMessage());
        return logObject;
    }
 
    private void logException(IThrowableProxy tp, DBObject log) {
        if (tp == null) return;
        String tpAsString = ThrowableProxyUtil.asString(tp); //the stack trace basically
        List<String> stackTrace = Arrays.asList(tpAsString.replace("\t","").split(CoreConstants.LINE_SEPARATOR));
        if (stackTrace.size() > 0) {
            log.put("exception", stackTrace.get(0));
        }
        if (stackTrace.size() > 1) {
            log.put("stacktrace", stackTrace.subList(1, stackTrace.size()));
        }
    }
}
