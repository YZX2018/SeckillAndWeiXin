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
				String key = "seckill:"+seckillId;//ʹ����Ŀ��seckill+id����key������
				//��û��ʵ���ڲ����л�����
				//get->byte[]->�����л�->Object(Seckill)
				//ʹ��protostuff�������л������ܱ�    ����   implements SerializableҪ��
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes!=null){
					//�ն���
					Seckill seckill = schema.newMessage();
					ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
					//seckill�������л�
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
		//set Object(Seckill)->���л�->byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//��ʱ����
				int timeout = 60*60;//����һСʱ
				//key��value��ʹ���ֽ������洢
				String result=jedis.setex(key.getBytes(),timeout,bytes);
				//result���󷵻ش�����Ϣ���ɹ�����OK
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
