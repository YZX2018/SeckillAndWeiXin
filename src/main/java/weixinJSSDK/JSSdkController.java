package weixinJSSDK;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weixin.AccessTokenUtil;

@Controller
public class JSSdkController {
	public static void main(String[] args) {
		
	}
	@RequestMapping("jsSdk")
	//调用JSSdk
	public String jsSdk(Model model,HttpSession session){
		String appId = "wx9d7f2a093f648381";//测试号appId
		long timestamp = new Date().getTime();//时间戳
		String  noncestr= getNoncestr();
		//获取signature签名
		String getJsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?"
				+ "access_token="+AccessTokenUtil.getAccessToken().getAccessToken()
				+ "&type=jsapi";
		
		String ticket = "";
		long ticket_time=0;
		if(session.getAttribute("ticket_time")!=null&&!"".equals(session.getAttribute("ticket_time"))){
			ticket_time = (Long) session.getAttribute("ticket_time")+7000;//过期时间
		}
		if(session.getAttribute("ticket")!=null&&!"".equals(session.getAttribute("ticket"))&&ticket_time>new Date().getTime()){
			ticket = (String) session.getAttribute("ticket");
		}else{
			//获取jsapi_ticket
			JSONObject doGetStr = AccessTokenUtil.doGetStr(getJsapiTicketUrl);
			ticket = doGetStr.getString("ticket");
			ticket_time = new Date().getTime()+7000;
			session.setAttribute("ticket", ticket);
			session.setAttribute("ticket_time", ticket_time);
			System.out.println("ticket:"+ticket);
		}
		String str = "jsapi_ticket="+ticket
				+ "&noncestr="+noncestr
				+ "&timestamp="+timestamp
				+ "&url=http://sunweixin.free.ngrok.cc/Seckill/jsSdk";
		String signature = getSha1(str);
		System.out.println(signature);
		model.addAttribute("appId",appId);
		model.addAttribute("timestamp",timestamp);
		model.addAttribute("nonceStr", noncestr);
		model.addAttribute("signature",signature);
		return "/JSSDK.jsp";
	}
	
	//生成16位随机数
	public static String getNoncestr(){
		String ranString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String noncestr = "";
		Random r = new Random(25);
		for(int i = 0;i<16;i++){
			noncestr = noncestr+ranString.charAt(r.nextInt(ranString.length()));
		}
		System.out.println(noncestr);
		return noncestr;
	}
	//sha1加密
	public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}
