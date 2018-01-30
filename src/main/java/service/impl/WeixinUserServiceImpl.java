package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.WeixinUserDao;
import entity.WeixinUser;
import service.WeixinUserService;
@Service
public class WeixinUserServiceImpl implements WeixinUserService {
	@Autowired
	private WeixinUserDao weixinUserDao;
	public String findByOpenid(String openid) {
		String nickName = null;
		WeixinUser user = weixinUserDao.findByOpenid(openid);
		if(user!=null){
			nickName=user.getNickName();
		}
		return nickName;
	}

	public int updateUser(String openid, String userName, String passward) {
		int result = weixinUserDao.updateUser(openid, userName, passward);
		return result;
	}

}
