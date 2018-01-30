package service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import dto.Exposer;
import exception.RepeatKillException;


public interface WeixinUserService {
	String findByOpenid(String openid);
	int updateUser(String openid,String userName,String passward);
}
