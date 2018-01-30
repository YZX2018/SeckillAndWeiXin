package weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	/** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */ 
    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());//根节点用<xml>
        return xstream.toXML(textMessage);
    }
    //图文消息对象转换成xml 
    public static String newsMessageToXml(NewMessage newMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", newMessage.getClass());//根节点用<xml>
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newMessage);
    }
    
    //图片消息对象转换成xml
    public static String imageMessageToXml(ImageMessage imageMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());//根节点用<xml>
        return xstream.toXML(imageMessage);
    }
    //音乐消息对象转换成xml
    public static String musicMessageToXml(MusicMessage musicMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", musicMessage.getClass());//根节点用<xml>
        return xstream.toXML(musicMessage);
    }
    
    //xml转map
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException{
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        
        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            
            List<Element> list = root.elements();
            
           /* for (Element e : list) {
            	if(e.elements().size()!=0){
            		//扫描事件还有子xml
            		put(e.elements(),map);
            	}else{
            		map.put(e.getName(), e.getText());
            	}
            }*/
            put(list,map);
            
            return map;
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }finally{
            ins.close();
        }
        
        return null;
    }

/**遍历Element*/
public static void put(List<Element> list,Map<String, String> map){
	for (Element e : list) {    	
		if(e.elements().size()!=0){
    		//扫描事件还有子xml
    		put(e.elements(),map);
    	}else{
    		map.put(e.getName(), e.getText());
    	}
    }
}}