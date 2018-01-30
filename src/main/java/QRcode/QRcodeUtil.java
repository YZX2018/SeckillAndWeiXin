package QRcode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;
import weixin.AccessToken;
import weixin.AccessTokenUtil;

public class QRcodeUtil {
	public static void main(String[] args) {
		temporaryCode();
		foreverCode();
	}
	// 临时二维码
	public static void temporaryCode() {
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?"
				+ "access_token="+accessToken.getAccessToken();
//{"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("expire_seconds", 604800);
		jsonObject.put("action_name", "QR_SCENE");
		//scene
		JSONObject scene = new JSONObject();
		JSONObject sceneInfo = new JSONObject();
		sceneInfo.put("scene_id", 2000);
		scene.put("scene",sceneInfo);
		jsonObject.put("action_info", scene);
		System.out.println(jsonObject);
		JSONObject doPostStr = AccessTokenUtil.doPostStr(url, jsonObject.toString());
		System.out.println(doPostStr);
		//获取ticket
		String ticket = doPostStr.getString("ticket");
		String getCodeUrl;
		try {
			getCodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?"
					+ "ticket="+URLEncoder.encode(ticket, "UTF-8");
			System.out.println(getCodeUrl);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	// 永久二维码
	public static void foreverCode() {
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?"
				+ "access_token="+accessToken.getAccessToken();
//{"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action_name", "QR_LIMIT_SCENE");
		//scene
		JSONObject scene = new JSONObject();
		JSONObject sceneInfo = new JSONObject();
		sceneInfo.put("scene_id", 3000);
		scene.put("scene",sceneInfo);
		jsonObject.put("action_info", scene);
		System.out.println(jsonObject);
		JSONObject doPostStr = AccessTokenUtil.doPostStr(url, jsonObject.toString());
		System.out.println(doPostStr);
		//获取ticket
		String ticket = doPostStr.getString("ticket");
		String getCodeUrl;
		try {
			getCodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?"
					+ "ticket="+URLEncoder.encode(ticket, "UTF-8");
			System.out.println(getCodeUrl);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
