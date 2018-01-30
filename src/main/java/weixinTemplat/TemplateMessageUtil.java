package weixinTemplat;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import weixin.AccessToken;
import weixin.AccessTokenUtil;

public class TemplateMessageUtil {
	public static void main(String[] args) {
		//setIndustry();
		//getIndustry();
		//templateList();
		sendTemplate();
	}
	//设置所属行业
	public static void setIndustry(){
		//获取access_token
		AccessToken token = AccessTokenUtil.getAccessToken();
		System.out.println(token.getAccessToken());
		String URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?"
				+ "access_token="+token.getAccessToken();
		//java的'' json转到方法上解析不了
		String params = "{'industry_id1':'1','industry_id2':'4'}";
		JSONObject p = JSONObject.fromObject(params);
		System.out.println(p);
		
		JSONObject j =AccessTokenUtil.doPostStr(URL, p.toString());
		System.out.println(j);
	}
	//获取设置的行业信息
	public static void getIndustry(){
		//获取access_token
		AccessToken token = AccessTokenUtil.getAccessToken();
		System.out.println(token.getAccessToken());
		String URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?"
				+ "access_token="+token.getAccessToken();
		JSONObject j =AccessTokenUtil.doGetStr(URL);
		System.out.println(j);
	}
	//获取模板列表
	public static void templateList(){
		//获取access_token
		AccessToken token = AccessTokenUtil.getAccessToken();
		System.out.println(token.getAccessToken());
		String URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?"
				+ "access_token="+token.getAccessToken();
		JSONObject j =AccessTokenUtil.doGetStr(URL);
		System.out.println(j);
	}
	//发送模板消息
	public static void sendTemplate(){
		String template_id = "kqBs1MdzSYacgooJ4gmk9IvjPr-gevx6Qkx6fUQKwCs";
		String openid="ogenS1XGX49Qibqa7ZZapm6tkJcY";
		//获取access_token
		AccessToken token = AccessTokenUtil.getAccessToken();
		System.out.println(token.getAccessToken());
		String URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?"
				+ "access_token="+token.getAccessToken();
		TemplateMessage message = new TemplateMessage();
		
		Data data =  new Data();
		data.setFirst(new valAndcol("恭喜你购买成功！", "#173177"));
		data.setKeynote1(new valAndcol("巧克力", "#173177"));
		data.setKeynote2(new valAndcol("39.8元", "#173177"));
		SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd日");
		data.setKeynote3(new valAndcol(simple.format(new Date()), "#173177"));
		data.setRemark(new valAndcol("欢迎再次购买！", "#173177"));
		message.setData(data);
		message.setTouser(openid);
		message.setTemplate_id(template_id);
		message.setUrl("http://sunweixin.free.ngrok.cc/Seckill/index.jsp");
		String jsonString = JSONObject.fromObject(message).toString();
		System.out.println(jsonString);
		JSONObject j =AccessTokenUtil.doPostStr(URL, jsonString);
		System.out.println(j);
	}
}
