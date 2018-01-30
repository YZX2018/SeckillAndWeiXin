package daoTest;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.SeckillDao;
import entity.Seckill;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-dao.xml")
public class SeckillDaoTest {
	@Resource
	private SeckillDao seckillDao;
	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000L,killTime);
		System.out.println("updateCount="+updateCount);
	}

	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckills = seckillDao.queryAll(0,100);
		for(Seckill s : seckills){
			System.out.println(s);
		}
	}

}
