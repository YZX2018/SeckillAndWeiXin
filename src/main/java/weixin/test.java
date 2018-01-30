package weixin;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

public class test {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		// TODO Auto-generated method stub
		AccessToken token = AccessTokenUtil.getAccessToken();
		System.out.println("票据:"+token.getAccessToken());
		System.out.println("有效时间:"+token.getExpiresIn());
		String path = "D:/教程/HTML5/web/image/0000.png";
		//String MediaId = UploadUtil.upload(path, token.getAccessToken(), "image");//图片消息，只要MediaId
//		String MediaId = UploadUtil.upload(path, token.getAccessToken(), "thumb");//获取音乐缩略图的MediaId
//		System.out.println(MediaId);
		
		/**创建自定义菜单*/
/*		String menu = JSONObject.fromObject(MenuUtil.initMenu()).toString();//自定义菜单
		System.out.println(menu);
		int result = MenuUtil.createMenu(token.getAccessToken(), menu);*/
		/**删除菜单*/
		/*int result = MenuUtil.deleteMenu(token.getAccessToken());
		System.out.println(result);*/
		/**查询自定义菜单*/
	/*	JSONObject jsonObject = MenuUtil.queryMenu(token.getAccessToken());
		System.out.println(jsonObject);*/
	}

}
