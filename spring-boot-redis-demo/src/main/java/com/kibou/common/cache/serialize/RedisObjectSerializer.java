package com.kibou.common.cache.serialize;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.DefaultSerializer;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class RedisObjectSerializer implements RedisSerializer<Object> {
	//可以参考 JdkSerializationRedisSerializer
	private Converter<Object, byte[]> serializerConverter;
	private Converter<byte[],Object> deSerializerConverter;
	
	public RedisObjectSerializer(){
		//use DefaultSerializer ! (java built-in (de)serializer)
		this(new DefaultSerializer(),new DefaultDeserializer());
	}
	
	public RedisObjectSerializer(Serializer<Object> serializer,Deserializer<Object> deserializer){
		serializerConverter = new SerializingConverter(serializer);
		deSerializerConverter = new DeserializingConverter(deserializer);
	}
	
	private RedisObjectSerializer(
			RedisObjectSerializerBuilder builder) {
		this(builder.serializer,builder.deserializer);
	}

	public static class RedisObjectSerializerBuilder{
		Serializer<Object> serializer;
		Deserializer<Object> deserializer;
		
		public static RedisObjectSerializerBuilder create(){
			return new RedisObjectSerializerBuilder();
		}
		
		public RedisObjectSerializerBuilder serializer(Serializer<Object> serializer){
			this.serializer = serializer;
			return this;
		}
		public RedisObjectSerializerBuilder deserializer(Deserializer<Object> deserializer){
			this.deserializer = deserializer;
			return this;
		}
		public RedisObjectSerializer build(){
			return new RedisObjectSerializer(this);
		}
	}

	
	private static final byte[] EMPTY_BYTES = new byte[0];
	
	@Override
	public byte[] serialize(Object t) throws SerializationException {
		if(t == null)
			return EMPTY_BYTES;
		return serializerConverter.convert(t);
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null || bytes.length == 0)
			return null;
		return deSerializerConverter.convert(bytes);
	}

}
