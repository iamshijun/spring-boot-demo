package com.kibou.common.cache.serialize;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.serializer.Serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

public class KryoSerializer implements Serializer<Object> {

	private final static KryoFactory KRYO_FACTORY = new KryoFactory() {
		public Kryo create() {
			Kryo kryo = new Kryo();
			// configure kryo instance, customize settings
			return kryo;
		}
	};
	// Build pool with SoftReferences enabled (optional)
	private final static KryoPool KRYO_POOL = new KryoPool.Builder(KRYO_FACTORY).softReferences().build();

	public static Serializer<Object> create(){
		return new KryoSerializer();
	}
	
	@Override
	public void serialize(Object object, OutputStream outputStream)
			throws IOException {
		Kryo kryo = KRYO_POOL.borrow();
		try{
			Output output = new Output(outputStream);
			kryo.writeClassAndObject(output, object);
			output.flush();
			//outpustream由调用者自己关闭. 这里不是用output.close()
		}finally{
			
			KRYO_POOL.release(kryo);
		}
	}
}
