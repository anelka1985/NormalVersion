package com.egame.utils.xml;

import java.util.List;
import java.util.Map;

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
public class XmlBean {
	private ObjBean objBean;
	private Map<String,List<ObjBean>> listBean;
	
	public ObjBean getObjBean() {
		return objBean;
	}
	
	public void setObjBean(ObjBean objBean) {
		this.objBean = objBean;
	}
	
	public Map<String, List<ObjBean>> getListBean() {
		return listBean;
	}
	
	public void setListBean(Map<String, List<ObjBean>> listBean) {
		this.listBean = listBean;
	}
}
