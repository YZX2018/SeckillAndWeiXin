package weixin;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

//访问微信接口工具类
public class AccessTokenUtil {
	/**个 人订阅号*/
	//private static final String APPID = "wxb4340da556df1f58";
	//private static final String APPSECRET = "60090835d6027942810904446d43311f";
	/**微信测试号*/
	public static final String APPID = "wx9d7f2a093f648381";
	public static final String APPSECRET = "5b5e4659d33e41ca1781cbad34a13912";
	
	private static final String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//get请求
	public static JSONObject doGetStr(String url){
		HttpClientBuilder  builder = HttpClientBuilder.create();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = builder.build().execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity!=null){
				String result = EntityUtils.toString(entity,"UTF-8");
//				System.out.println(result);
				jsonObject = JSONObject.fromObject(result);
//				System.out.println(jsonObject.toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	//post请求
	public static JSONObject doPostStr(String url,String outStr){
		HttpClientBuilder  builder = HttpClientBuilder.create();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
		try {
			HttpResponse response = builder.build().execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			System.out.println(result);
			jsonObject = JSONObject.fromObject(result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	//获取access_token 
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			token.setAccessToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token; 
	}
}
