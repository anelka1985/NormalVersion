package com.egame.utils.xml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Xml解析出来的键值对实体类,和map一样,唯一的区别是查不到值的时候返回空字符串而不是null
 * @author zhouzhe
 *
 */
public class ObjBean implements Serializable{
	
	private static final long serialVersionUID = 3206766901766068826L;
	private Map<String,Object> map;
	
	public ObjBean(){
		map = new HashMap<String,Object>();
	}
	
	public ObjBean(Map<String,Object> map){
		this.map = map;
	}
	
	public void setMap(Map<String,Object> map){
		this.map = map;
	}
	
	public void put(String key,String value){
		this.map.put(key, value);
	}
	/**
	 * 根据key获取value
	 * @param 字符串类型的key
	 * @return 字符串类型的value,key不存在时返回空字符串""
	 */
	public String get(String key){
		Object s = this.map.get(key);
		if(s == null){
			return "";
		}else{
			return s.toString();
		}
	}
	
	public void putObj(String key,Object value){
		this.map.put(key, value);
	}
	
	public Object getObj(String key){
		return this.map.get(key);
	}
}
