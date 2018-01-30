package weixinAuth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import net.sf.json.JSONObject;
import service.WeixinUserService;

import weixin.AccessTokenUtil;

@Controller
public class CallBackController {
	@Autowired
	WeixinUserService weixinUserService;
	@RequestMapping(value = "/callBack",method=RequestMethod.GET)
	public String callBack(HttpServletRequest request,HttpServletResponse response,Model model){
		String code = request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid="+AccessTokenUtil.APPID
				+ "&secret="+AccessTokenUtil.APPSECRET
				+ "&code="+code
				+ "&grant_type=authorization_code";
		JSONObject jsonObject=AccessTokenUtil.doGetStr(url);
		String access_token = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token="+access_token
				+ "&openid="+openid
				+ "&lang=zh_CN";
		JSONObject userInfo=AccessTokenUtil.doGetStr(infoUrl);
		model.addAttribute("userInfo",userInfo);
		System.out.println(userInfo);
		//通过openid查询数据库的当前系统的账号是否已经绑定
		
		String nickName=weixinUserService.findByOpenid(openid);
		if(nickName!=null&&!"".equals(nickName)){
			//绑定
			return "/weixinInfo.jsp";
		}else{
			//未绑定，跳到登陆页
			request.setAttribute("openid", openid);
			return "/login.jsp";
		}
	}
	
	@RequestMapping(value = "/callBackBD",method=RequestMethod.POST)
	public String callBackBD(HttpServletRequest request,HttpServletResponse response,Model model){
		String userName = (String) request.getParameter("userName");
		String passward = (String) request.getParameter("passward");
		String openid = (String) request.getParameter("openid");
		int temp = weixinUserService.updateUser(openid, userName, passward);
		if(temp>0){
			System.out.println("绑定成功");
		}else{
			System.out.println("绑定失败");
		}
		return "/weixinInfo.jsp";
	}
}
