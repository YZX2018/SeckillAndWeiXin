package service.impl;

import java.security.MessageDigest;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import service.WeixinService;

@Service
public class WeixinServiceImpl implements WeixinService{
	private static final String token = "weixin";
	//signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	public boolean check(String signature,String timestamp,String nonce){
		String[] arr = new String[]{token,timestamp,nonce};
		//排序
		Arrays.sort(arr);
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<arr.length;i++){
			sb.append(arr[i]);
		}
		String temp = getSha1(sb.toString());
		
		return temp.equals(signature);
		
	}
	
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
