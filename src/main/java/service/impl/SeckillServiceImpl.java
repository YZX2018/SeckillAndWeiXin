package service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import dao.RedisDao;
import dao.SeckillDao;
import dao.SuccessKilledDao;
import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import entity.SuccessKilled;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Autowired
	private RedisDao redisDao;
	// md5��ֵ�ַ��������ڻ���MD5
	private final String slat = "fssfdsaf@R##@$TT$fWEF145721#!";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	// �Ƿ�����ɱ
	public Exposer exportSeckillUrl(long seckillId) {
		//�Ż��㣺�����Ż�
		//1:����redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill==null){
			//2:�������ݿ�
			seckill = seckillDao.queryById(seckillId);
			//������ݿ�Ҳû��
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}else{
				//3:����redis
				redisDao.putSeckill(seckill);
			}
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//ϵͳ��ǰʱ��
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		//ת���ض��ַ����Ĺ��̣�������
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Transactional
	/**
	 * ʹ��ע��������񷽷����ŵ�
	 * 1:�����ŶӴ��һ��Լ��,��ȷ��ע���񷽷��ı�̷��
	 * 2:��֤���񷽷���ִ��ʱ�価���ܶ�,��Ҫ���������������
	 * 3:�������еķ�������Ҫ����,��ֻ��һ���޸Ĳ���,ֻ����������Ҫ�������
	 */
	// ִ����ɱ
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		// TODO Auto-generated method stub
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewirte");
		}
		;
		// ִ����ɱ�߼�������� + ��¼������Ϊ
		Date nowTime = new Date();
		try {
			// �����
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				// û�и��µ���¼����ɱ����
				throw new SeckillCloseException("seckill is closed");
			} else {
				// ��¼������Ϊ
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				// Ψһ��seckillId,userPhone
				if (insertCount <= 0) {
					// �ظ���ɱ
					throw new RepeatKillException("seckill repeated");
				} else {
					// ��ɱ�ɹ�
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			// ���б������쳣��ת��Ϊ�������쳣
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

}
