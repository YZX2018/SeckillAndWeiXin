package service;

import java.util.List;

import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;

public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开始时输出秒杀接口地址，否则输出系统时间和秒杀时间
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckill
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
			throws SeckillException,RepeatKillException,SeckillCloseException;
}
