package com.egame.utils.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.egame.utils.common.L;

/**
 * @desc
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-1-30
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class XmlUtil {

	private final String TAG = "XmlUtil";
	/**
	 * xml中键值对部分的tag
	 */
	private final String OBJTAG = "head_attr";
	
	String[] listTag;
	
	/**
	 * XmlUtil的构造方法
	 * @param listTag,包含所有列表类型数据的节点名的数组
	 */
	public XmlUtil(String[] listTag){
		this.listTag = listTag;
	}
	
	/**
	 * 解析输入流
	 * @param inputStream
	 * @return 返回一个XmlBean对象.
	 */
	public XmlBean parser(InputStream inputStream){
		long time = System.currentTimeMillis();
		XmlPullParser parser = Xml.newPullParser();
        XmlBean xmlBean = new XmlBean();
        Map<String,List<ObjBean>> listBean = new HashMap<String,List<ObjBean>>();
        for(String s : this.listTag){
        	List<ObjBean> listObjBean = new ArrayList<ObjBean>();
        	listBean.put(s, listObjBean);
        }
        xmlBean.setListBean(listBean);
        try {
			parser.setInput(inputStream, "utf-8");
			int event = parser.getEventType();
			while(event != XmlPullParser.END_DOCUMENT){
				//L.d("TAG",event+"");
				switch(event){
				case XmlPullParser.START_TAG:
					String tagName = parser.getName();
					if(OBJTAG.equals(tagName)){
						xmlBean.setObjBean(parserObjBean(parser, OBJTAG));
					}else if(containList(tagName)){
						listBean.get(tagName).add(parserObjBean(parser, tagName));
					}
					break;
					default:
						break;
				}
				event = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		L.d(TAG,"parser time:"+(System.currentTimeMillis() - time));
		return xmlBean;
	}
	
	/**
	 * 解析输入流
	 * @param inputStream
	 * @return 返回一个XmlBean对象.
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 */
	public XmlBean parser(InputStream inputStream,boolean b) throws XmlPullParserException, IOException{
		long time = System.currentTimeMillis();
		XmlPullParser parser = Xml.newPullParser();
        XmlBean xmlBean = new XmlBean();
        Map<String,List<ObjBean>> listBean = new HashMap<String,List<ObjBean>>();
        for(String s : this.listTag){
        	List<ObjBean> listObjBean = new ArrayList<ObjBean>();
        	listBean.put(s, listObjBean);
        }
        xmlBean.setListBean(listBean);
		parser.setInput(inputStream, "utf-8");
		int event = parser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
//			L.d("TAG",event+"");
			switch(event){
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				if(OBJTAG.equals(tagName)){
					xmlBean.setObjBean(parserObjBean(parser, OBJTAG));
				}else if(containList(tagName)){
					listBean.get(tagName).add(parserObjBean(parser, tagName));
				}
				break;
				default:
					break;
			}
			event = parser.next();
		}
		L.d(TAG,"parser time:"+(System.currentTimeMillis() - time));
		return xmlBean;
	}
	
	
	/**
	 * 解析obj节点,将数据放到ObjBean中返回
	 * @param parser XmlPullParser游标
	 * @param tagName 启始节点名称
	 * @return 返回一个ObjBean
	 * @throws XmlPullParserException 解析错误时抛出异常
	 * @throws IOException
	 */
	public ObjBean parserObjBean(XmlPullParser parser,String tagName) throws XmlPullParserException, IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		int event = parser.next();
		while(event != XmlPullParser.END_TAG || !parser.getName().equals(tagName)){
			if(event == XmlPullParser.START_TAG){
				map.put(parser.getName(), parser.nextText());
			}
			event = parser.next();
		}
		return new ObjBean(map);
	}
	
	/**
	 * 解析为xmlBean
	 * @param parser 解析器
	 * @param tagNames 节点数组
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public XmlBean parserXmlBean(XmlPullParser parser,String[] tagNames,String xmlBeanTag) throws XmlPullParserException, IOException{
		long time = System.currentTimeMillis();
        XmlBean xmlBean = new XmlBean();
        Map<String,List<ObjBean>> listBean = new HashMap<String,List<ObjBean>>();
        for(String s : tagNames){
        	List<ObjBean> listObjBean = new ArrayList<ObjBean>();
        	listBean.put(s, listObjBean);
        }
        xmlBean.setListBean(listBean);
		int event = parser.next();
		Map<String,Object> map = new HashMap<String, Object>();
		while(event != XmlPullParser.END_TAG || !xmlBeanTag.equals(parser.getName())){
			switch(event){
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				if(!"ClientGameProductBean".equals(tagName)){
//					L.d("JK3",tagName);
					map.put(tagName, parser.nextText());
				}else{
					listBean.get(tagName).add(parserObjBean(parser, tagName));
				}
				break;
				default:
					break;
			}
			event = parser.next();
		}
		ObjBean obj = new ObjBean();
		obj.setMap(map);
		xmlBean.setObjBean(obj);
		L.d(TAG,"parser time:"+(System.currentTimeMillis() - time));
		return xmlBean;
	}
	/**
	 * 判断节点是否属于列表的起始节点
	 * @param s
	 * @return
	 */
	private boolean containList(String s){
    	for(String listkey : this.listTag){
    		if(listkey.equals(s)){
    			return true;
    		}
    	}
    	return false;
    }
	
	/**
	 * 解析包含多个xmlbean的列表.
	 * @param inputStream
	 * @param xmlBeanTag
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public List<XmlBean> parserXmlBeanList(InputStream inputStream,String xmlBeanTag) throws XmlPullParserException, IOException{
		long time = System.currentTimeMillis();
		XmlPullParser parser = Xml.newPullParser();
        XmlBeanList xmlBeanList = new XmlBeanList();
        List<XmlBean> list = new ArrayList<XmlBean>();
		parser.setInput(inputStream, "utf-8");
		int event = parser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
//			L.d("TAG",event+"");
			switch(event){
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				if(OBJTAG.equals(tagName)){
					xmlBeanList.setObjBean(parserObjBean(parser, OBJTAG));
				}else if(tagName.equals(xmlBeanTag)){
					list.add(parserXmlBean(parser, listTag, xmlBeanTag));
				}
				break;
				default:
					break;
			}
			event = parser.next();
		}
		L.d(TAG,"parser time:"+(System.currentTimeMillis() - time));
		return list;
	}
	
	/**
	 * DOM方式从xml中获取一个值
	 * 
	 * @param s xml字符串
	 * @param key 键
	 * @return 对应的值,异常情况反馈空字符串
	 * @author zhouzhe@lenovo-cw.com
	 * @time 2011-11-4
	 */
	public static String get(String s,String key){
		L.d("DOM",s+"|" + key);
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder;
		try {
			dombuilder = domfac.newDocumentBuilder();
			Document document = dombuilder.parse(new ByteArrayInputStream(s.getBytes()));
			Element element = document.getDocumentElement();
			NodeList list = element.getElementsByTagName(key);
			String result = list.item(0).getFirstChild().getNodeValue();
			L.d("DOM","" + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}

}
