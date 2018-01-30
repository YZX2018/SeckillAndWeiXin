package weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mysql.fabric.xmlrpc.base.Data;

import service.WeixinService;
import service.impl.WeixinServiceImpl;

@Controller
@RequestMapping(value="/weixin")
public class WeixinController {
	@Autowired
	WeixinService weixinService;
	//���뿪����ģʽ
	@RequestMapping(method=RequestMethod.GET)
	public void weixin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String signature = request.getParameter("signature");//΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
		String timestamp = request.getParameter("timestamp");//ʱ���
		String nonce = request.getParameter("nonce");//�����
		String echostr = request.getParameter("echostr");//����ַ���
		PrintWriter out = response.getWriter();
		if(weixinService.check(signature, timestamp, nonce)){
			out.print(echostr);
			System.out.println(request.getParameter("signature"));
		}
	}
	//��Ϣ�Ľ�������Ӧ
	@RequestMapping(method=RequestMethod.POST)
	public void weixinMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String,String> map = MessageUtil.xmlToMap(request);
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");
		String msgType = map.get("MsgType");
		String content = map.get("Content");
		String msgId = map.get("MsgId");
		System.out.println(("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType+",msgId is:" + msgId));
		PrintWriter out = response.getWriter();
		String message = null;
		//�û����͵���Ϣ������ı�text����
		if(Message.MESSAGE_TEXT.equals(msgType)){
			if("1".equals(content)){
				//�ؼ��ֻظ� ����û�����1
//				TextMessage text=new TextMessage();
//				text.setFromUserName(toUserName);
//				text.setToUserName(fromUserName);
//				text.setCreateTime(new Date().getTime()+"");
//				text.setMsgType("text");
//				text.setContent(content+"�������֣����簢����ɰ�!!");
//				message = MessageUtil.textMessageToXml(text);
				//��װͼ����Ϣ
				NewMessage newsMessage = new NewMessage();
				newsMessage.setFromUserName(toUserName);
				newsMessage.setToUserName(fromUserName);
				newsMessage.setCreateTime(new Date().getTime()+"");
				newsMessage.setMsgType(Message.MESSAGE_NEWS);
				newsMessage.setArticleCount(1);
				List<News> list = new ArrayList<News>();
				News news = new News();
				news.setTitle("�����ʥ");
				news.setDescription("����ɽˮ����������");
				news.setPicUrl("http://sunweixin.free.ngrok.cc/Seckill/image/0000.png");
				news.setUrl("http://www.baidu.com");
				list.add(news);
				newsMessage.setArticles(list);
				message = MessageUtil.newsMessageToXml(newsMessage);
			}else if("2".equals(content)){
				//�ؼ��ֻظ� ����û�����2
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				text.setContent(content+"�������֣����簢����ɰ�!!");
				message = MessageUtil.textMessageToXml(text);
			}else if("3".equals(content)){
				//�ؼ��ֻظ�  ͼƬ��Ϣ
				ImageMessage imageMessage=new ImageMessage();
				imageMessage.setFromUserName(toUserName);
				imageMessage.setToUserName(fromUserName);
				imageMessage.setCreateTime(new Date().getTime()+"");
				imageMessage.setMsgType(Message.MESSAGE_IMAGE);
				Image image = new Image();
				image.setMediaId("iOZ8vOhNyhZaUK4Hpq_WhCsAI5g9K7B_jXgu0wdQqVEKgrNMM4NbsJgmR4E08sSn");
				imageMessage.setImage(image);
				message = MessageUtil.imageMessageToXml(imageMessage);
			}else if("4".equals(content)){
				//�ؼ��ֻظ�  ͼƬ��Ϣ
				MusicMessage musicMessage=new MusicMessage();
				musicMessage.setFromUserName(toUserName);
				musicMessage.setToUserName(fromUserName);
				musicMessage.setCreateTime(new Date().getTime()+"");
				musicMessage.setMsgType("music");
				Music music = new Music();
				music.setTitle("������");
				music.setDescription("�Ź���");
				music.setMusicUrl("http://sunweixin.free.ngrok.cc/Seckill/music/music.mp3");
				music.setHQMusicUrl("http://sunweixin.free.ngrok.cc/Seckill/music/music.mp3");
				music.setThumbMediaId("izzStOAd6VusbPl4lOimAtnyYd1lg3UT2nMMwNXJEVQF_4zn8pq3-VeUJwg9R_mn");
				musicMessage.setMusic(music);
				message = MessageUtil.musicMessageToXml(musicMessage);
			}
			else{
				//�Զ��ظ�����������
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType(msgType);
				text.setContent("�������֣����簢����ɰ�!!,�뷢������ 1");
				message = MessageUtil.textMessageToXml(text);
			}
			
			
			//��ע
		}else if(Message.MESSAGE_EVENT.equals(msgType)){
			//event ��ע
			String eventType = map.get("Event");
			if(Message.MESSAGE_SUBSCRIBE.equals(eventType)){
				//����һ���ı���Ϣ�Ļظ�
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				text.setContent("�������֣����簢����ɰ�!!");
				message = MessageUtil.textMessageToXml(text);
			}else if(Message.MESSAGE_CLICK.equals(eventType)){
				//����һ���ı���Ϣ�Ļظ�
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				text.setContent("Ԫ�����֣�����������ɰ�!!");
				message = MessageUtil.textMessageToXml(text);
			}
			//ɨ�蹫�ں����ɵĶ�ά��    �û�δ��עʱ�����й�ע����¼�����
			else if("subscribe".equals(eventType)){
				//����һ���ı���Ϣ�Ļظ�
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				if("qrscene_2000".equals(map.get("EventKey"))){
					text.setContent("�չ�ע���ں�,������ʱ��ά��");
				}else if("qrscene_3000".equals(map.get("EventKey"))){
					text.setContent("�չ�ע���ں�,�������ö�ά��");
				}
				message = MessageUtil.textMessageToXml(text);
			}
			//ɨ�蹫�ں����ɵĶ�ά��    �û��ѹ�עʱ���¼�����
			else if("SCAN".equals(eventType)){
				//����һ���ı���Ϣ�Ļظ�
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				if("2000".equals(map.get("EventKey"))){
					text.setContent("�ѹ�ע���ں�,������ʱ��ά��");
				}else if("3000".equals(map.get("EventKey"))){
					text.setContent("�ѹ�ע���ں�,�������ö�ά��");
				}
				
				message = MessageUtil.textMessageToXml(text);
			}
			/*else if("location_select".equals(eventType)){
				//����һ���ı���Ϣ�Ļظ�
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				text.setContent(map.get("Label"));
				message = MessageUtil.textMessageToXml(text);
			}*/
		}
		else if(Message.MESSAGE_LOCATION.equals(msgType)){
			TextMessage text=new TextMessage();
			text.setFromUserName(toUserName);
			text.setToUserName(fromUserName);
			text.setCreateTime(new Date().getTime()+"");
			text.setMsgType("text");
			text.setContent(map.get("Label"));
			message = MessageUtil.textMessageToXml(text);
		}
		
		
		System.out.println(message);
		out.write(message);
	}
}
