package service;

public interface WeixinService {
	 boolean check(String signature,String timestamp,String nonce);
}
