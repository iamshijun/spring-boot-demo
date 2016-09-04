package com.kibou.common.cache.serialize;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.serializer.Deserializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

public class KryoDeserializer implements Deserializer<Object> {

	private final static KryoFactory KRYO_FACTORY = new KryoFactory() {
		public Kryo create() {
			Kryo kryo = new Kryo();
			// configure kryo instance, customize settings
			return kryo;
		}
	};
	// Build pool with SoftReferences enabled (optional)
	private final static KryoPool KRYO_POOL = new KryoPool.Builder(KRYO_FACTORY).softReferences().build();

	public static Deserializer<Object> create(){
		return new KryoDeserializer();
	}
	
	@Override
	public Object deserialize(InputStream inputStream) throws IOException {
		Kryo kryo = KRYO_POOL.borrow();
		try{
			return kryo.readClassAndObject(new Input(inputStream));
		}finally{
			KRYO_POOL.release(kryo);
		}
	}

}
