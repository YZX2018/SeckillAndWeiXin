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
	//接入开发者模式
	@RequestMapping(method=RequestMethod.GET)
	public void weixin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String signature = request.getParameter("signature");//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String timestamp = request.getParameter("timestamp");//时间戳
		String nonce = request.getParameter("nonce");//随机数
		String echostr = request.getParameter("echostr");//随机字符串
		PrintWriter out = response.getWriter();
		if(weixinService.check(signature, timestamp, nonce)){
			out.print(echostr);
			System.out.println(request.getParameter("signature"));
		}
	}
	//信息的接收与响应
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
		//用户发送的信息如果是文本text类型
		if(Message.MESSAGE_TEXT.equals(msgType)){
			if("1".equals(content)){
				//关键字回复 如果用户发送1
//				TextMessage text=new TextMessage();
//				text.setFromUserName(toUserName);
//				text.setToUserName(fromUserName);
//				text.setCreateTime(new Date().getTime()+"");
//				text.setMsgType("text");
//				text.setContent(content+"冬至快乐，晓虹阿姨最可爱!!");
//				message = MessageUtil.textMessageToXml(text);
				//封装图文信息
				NewMessage newsMessage = new NewMessage();
				newsMessage.setFromUserName(toUserName);
				newsMessage.setToUserName(fromUserName);
				newsMessage.setCreateTime(new Date().getTime()+"");
				newsMessage.setMsgType(Message.MESSAGE_NEWS);
				newsMessage.setArticleCount(1);
				List<News> list = new ArrayList<News>();
				News news = new News();
				news.setTitle("齐天大圣");
				news.setDescription("花果山水莲洞美猴王");
				news.setPicUrl("http://sunweixin.free.ngrok.cc/Seckill/image/0000.png");
				news.setUrl("http://www.baidu.com");
				list.add(news);
				newsMessage.setArticles(list);
				message = MessageUtil.newsMessageToXml(newsMessage);
			}else if("2".equals(content)){
				//关键字回复 如果用户发送2
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				text.setContent(content+"冬至快乐，晓虹阿姨最可爱!!");
				message = MessageUtil.textMessageToXml(text);
			}else if("3".equals(content)){
				//关键字回复  图片消息
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
				//关键字回复  图片消息
				MusicMessage musicMessage=new MusicMessage();
				musicMessage.setFromUserName(toUserName);
				musicMessage.setToUserName(fromUserName);
				musicMessage.setCreateTime(new Date().getTime()+"");
				musicMessage.setMsgType("music");
				Music music = new Music();
				music.setTitle("有心人");
				music.setDescription("张国荣");
				music.setMusicUrl("http://sunweixin.free.ngrok.cc/Seckill/music/music.mp3");
				music.setHQMusicUrl("http://sunweixin.free.ngrok.cc/Seckill/music/music.mp3");
				music.setThumbMediaId("izzStOAd6VusbPl4lOimAtnyYd1lg3UT2nMMwNXJEVQF_4zn8pq3-VeUJwg9R_mn");
				musicMessage.setMusic(music);
				message = MessageUtil.musicMessageToXml(musicMessage);
			}
			else{
				//自动回复，发送任意
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType(msgType);
				text.setContent("冬至快乐，晓虹阿姨最可爱!!,请发送数字 1");
				message = MessageUtil.textMessageToXml(text);
			}
			
			
			//关注
		}else if(Message.MESSAGE_EVENT.equals(msgType)){
			//event 关注
			String eventType = map.get("Event");
			if(Message.MESSAGE_SUBSCRIBE.equals(eventType)){
				//创建一个文本消息的回复
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				text.setContent("冬至快乐，晓虹阿姨最可爱!!");
				message = MessageUtil.textMessageToXml(text);
			}else if(Message.MESSAGE_CLICK.equals(eventType)){
				//创建一个文本消息的回复
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				text.setContent("元旦快乐，晓虹晓虹最可爱!!");
				message = MessageUtil.textMessageToXml(text);
			}
			//扫描公众号生成的二维码    用户未关注时，进行关注后的事件推送
			else if("subscribe".equals(eventType)){
				//创建一个文本消息的回复
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				if("qrscene_2000".equals(map.get("EventKey"))){
					text.setContent("刚关注公众号,这是临时二维码");
				}else if("qrscene_3000".equals(map.get("EventKey"))){
					text.setContent("刚关注公众号,这是永久二维码");
				}
				message = MessageUtil.textMessageToXml(text);
			}
			//扫描公众号生成的二维码    用户已关注时的事件推送
			else if("SCAN".equals(eventType)){
				//创建一个文本消息的回复
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setCreateTime(new Date().getTime()+"");
				text.setMsgType("text");
				if("2000".equals(map.get("EventKey"))){
					text.setContent("已关注公众号,这是临时二维码");
				}else if("3000".equals(map.get("EventKey"))){
					text.setContent("已关注公众号,这是永久二维码");
				}
				
				message = MessageUtil.textMessageToXml(text);
			}
			/*else if("location_select".equals(eventType)){
				//创建一个文本消息的回复
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
