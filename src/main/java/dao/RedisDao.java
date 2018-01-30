package dao;

import java.io.Serializable;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	private JedisPool jedisPool;
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip,port);
	}
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	public Seckill getSeckill(long seckillId){
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;//使用项目中seckill+id定义key的名称
				//并没有实现内部序列化操作
				//get->byte[]->反序列化->Object(Seckill)
				//使用protostuff进行序列化，性能比    对象   implements Serializable要好
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes!=null){
					//空对象
					Seckill seckill = schema.newMessage();
					ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
					//seckill被反序列化
					return seckill;
				}
				
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
	
	public String putSeckill(Seckill seckill){
		//set Object(Seckill)->序列化->byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int timeout = 60*60;//缓存一小时
				//key和value都使用字节数来存储
				String result=jedis.setex(key.getBytes(),timeout,bytes);
				//result错误返回错误信息，成功返回OK
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
