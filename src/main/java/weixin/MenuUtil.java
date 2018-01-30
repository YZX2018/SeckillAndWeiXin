package weixin;

import net.sf.json.JSONObject;

public class MenuUtil {
	private static final String CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QUERY_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	//�˵�������
	public static Menu initMenu(){
		Menu menu= new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click�˵�");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21 = new ViewButton();
		button21.setName("view�˵�");
		button21.setType("view");
		button21.setUrl("http://www.baidu.com");
		
		ClickButton button31 = new ClickButton();
		button31.setName("ɨ���¼�");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("����λ��");
		button32.setType("location_select");
		button32.setKey("32");
		Button button = new Button();
		button.setName("�˵�");
		button.setSub_button(new Button[]{button31,button32});
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
		
	}
	/**�����Զ���˵�*/
	public static int createMenu(String token,String menu){
		int result = 0;
		String url = CREATE_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = AccessTokenUtil.doPostStr(url, menu);
		if(jsonObject!=null){
			result=jsonObject.getInt("errcode");
		}
		return result;
		
	}
	
	/**��ѯ�˵�*/
	public static JSONObject queryMenu(String token){
		String url = QUERY_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = AccessTokenUtil.doGetStr(url);
		
		return jsonObject;
	}
	
	/**ɾ���˵�*/
	public static int deleteMenu(String token){
		String url = DELETE_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = AccessTokenUtil.doGetStr(url);
		int result = 0;
		if(jsonObject!=null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
