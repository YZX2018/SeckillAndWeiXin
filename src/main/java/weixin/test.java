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
		System.out.println("Ʊ��:"+token.getAccessToken());
		System.out.println("��Чʱ��:"+token.getExpiresIn());
		String path = "D:/�̳�/HTML5/web/image/0000.png";
		//String MediaId = UploadUtil.upload(path, token.getAccessToken(), "image");//ͼƬ��Ϣ��ֻҪMediaId
//		String MediaId = UploadUtil.upload(path, token.getAccessToken(), "thumb");//��ȡ��������ͼ��MediaId
//		System.out.println(MediaId);
		
		/**�����Զ���˵�*/
/*		String menu = JSONObject.fromObject(MenuUtil.initMenu()).toString();//�Զ���˵�
		System.out.println(menu);
		int result = MenuUtil.createMenu(token.getAccessToken(), menu);*/
		/**ɾ���˵�*/
		/*int result = MenuUtil.deleteMenu(token.getAccessToken());
		System.out.println(result);*/
		/**��ѯ�Զ���˵�*/
	/*	JSONObject jsonObject = MenuUtil.queryMenu(token.getAccessToken());
		System.out.println(jsonObject);*/
	}

}
