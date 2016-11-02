package com.starsea.im.aggregation.util;

import com.starsea.im.aggregation.po.News;
import com.starsea.im.aggregation.po.NewsMessage;
import com.starsea.im.aggregation.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danny on 16/3/29.
 */
public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";

    /*
     * xml转为map集合
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException,DocumentException {
        Map<String, String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        org.dom4j.Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        java.util.List<Element> list = root.elements();

        for(Element e : list){
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    /*
     * 将文本消息对象转为XML
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /*
     * 将图文消息对象转为XML
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",newsMessage.getClass());
        xstream.alias("item",new News().getClass());
        return xstream.toXML(newsMessage);
    }

    /*
     * 拼接图文消息
     */
    public static String initNewsMessage(String toUserName,String fromUserName) {
        String message = null;
        java.util.List<News> newsList= new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("Introduction of e+");
        news.setDescription("Welcome to e+ !");
        news.setPicUrl("http://ejia.ngrok.cc/static/image/e+logo.jpg");
        news.setUrl("ejia.ngrok.cc");

        News news1 = new News();
        news1.setTitle("Advantage of e+");
        news1.setDescription("e+ is remarkable ! You'll be surprised !");
        news1.setPicUrl("http://ejia.ngrok.cc/static/image/e+logo2.jpg");
        news1.setUrl("ejia.ngrok.cc");

        newsList.add(news);
        newsList.add(news1);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }


    /*
     * 拼接文本消息
     */
    public static String initText(String toUserName,String fromUserName,String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return MessageUtil.textMessageToXml(text);
    }

    /*
     * 主菜单
     */
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("Thank you for your attention.Please follow the menu prompts:\n\n");
        sb.append("1.Introduction\n");
        sb.append("2.Introduction of e+\n");
        sb.append("3.Reply ? recall the menu");

        return sb.toString();
    }

    /*
     * 课程介绍
     */
    public static String firstMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("Thank you for your attention!\n");
        return sb.toString();
    }

    /*
     * 慕课网介绍
     */
    public static String secondMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("Introduction of e+ ! Thank you for your attention !\n");

        return sb.toString();
    }

}
