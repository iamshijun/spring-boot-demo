package com.kibou.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.kibou.appconfig.ConnectionProperties;

@Component
public class MyBean {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${name}") //try with command line args :  --name="shifeng"
	private String name;
	
	@Autowired
	private ConnectionProperties connectionProperties;
	
    @Autowired
    public MyBean(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
        // if run with "--debug logfile.txt" ==>then debug=true, files=["logfile.txt"]
        logger.info("debug options : {}",debug ? "present" : "absent");
        logger.info("args nonOptionArgs : {}",files);
    }

    
    @PostConstruct
    public void afterPropertiesSet(){
    	logger.info("MyBean name = {}",name);
    	logger.info("connectionProperties remoteAddresses = {}",connectionProperties.getRemoteAddresses());
    }
}