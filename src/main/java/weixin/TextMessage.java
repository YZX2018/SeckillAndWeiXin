package weixin;

//��װ�ı���Ϣ һ��xml��ǩ�൱���������
/*
<xml>
<ToUserName>![CDATA[toUser] ]</ToUserName>
<FromUserName>![CDATA[fromUser] ]</FromUserName>
<CreateTime>12345678</CreateTime>
<MsgType>![CDATA[text] ]</MsgType>
<Content>![CDATA[���] ]</Content>
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
