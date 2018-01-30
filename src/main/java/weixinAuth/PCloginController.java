package weixinAuth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import weixin.AccessTokenUtil;

@Controller
public class PCloginController {
	@RequestMapping("/PClogin")
	public String login(){
		String callBackUrl = "http://sunweixin.free.ngrok.cc/Seckill/PCcallBack";
	String url = "https://open.weixin.qq.com/connect/qrconnect?"
			+ "appid="+AccessTokenUtil.APPID
			+ "&redirect_uri="+URLEncoder.encode(callBackUrl)
			+ "&response_type=code"
			+ "&scope=snsapi_login"
			+ "&state=STATE#wechat_redirect";

			return "redirect:"+url;

	}
}
