package dao;

import org.apache.ibatis.annotations.Param;

import entity.SuccessKilled;
import entity.WeixinUser;


public interface WeixinUserDao {
	//ͨ��openid
	WeixinUser findByOpenid(String openid);
	
	int updateUser(@Param("openid") String openid,@Param("userName") String userName,@Param("passward") String passward);
}
