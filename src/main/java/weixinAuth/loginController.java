package weixinAuth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import weixin.AccessTokenUtil;

@Controller
public class loginController {
	//openid��
	@RequestMapping("/login")
	public String login(){
		String callBackUrl = "http://sunweixin.free.ngrok.cc/Seckill/callBack";
	
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
					+ "appid="+AccessTokenUtil.APPID
					+ "&redirect_uri="+URLEncoder.encode(callBackUrl)        //��Ȩ���ض���Ļص����ӵ�ַ
					+ "&response_type=code"
					+ "&scope=snsapi_userinfo"
					+ "&state=STATE#wechat_redirect";
			return "redirect:"+url;

	}
}
