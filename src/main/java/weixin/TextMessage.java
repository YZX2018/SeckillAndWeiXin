package weixin;

//封装文本消息 一个xml标签相当于类的属性
/*
<xml>
<ToUserName>![CDATA[toUser] ]</ToUserName>
<FromUserName>![CDATA[fromUser] ]</FromUserName>
<CreateTime>12345678</CreateTime>
<MsgType>![CDATA[text] ]</MsgType>
<Content>![CDATA[你好] ]</Content>
</xml>
*/
public class TextMessage extends BaseMessage{
	private String Content;
	private String MsgId;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
}
