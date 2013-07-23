package com.egame.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateSofeWareBean
{
    /**状态：0=不需要升级1=可选升级 2=强制升级*/
    public int updateCode;
    
    /**游戏平台自定义手机机型UA*/
    public String defineua;
    
    /**软件升级包下载路径*/
    public String softUrl;
    
    /**描述信息*/
    public String remark;

    /**请求结果信息*/
    public String resultMsg;
    
    /**请求结果码*/
    public int resultCode;
    
    public UpdateSofeWareBean(String json)
    {
        try
        {
            JSONObject obj = new JSONObject(json);
            JSONObject result =  obj.getJSONObject("result");
            resultMsg = result.getString("resultmsg");
            resultCode = result.getInt("resultcode");
            updateCode = obj.getInt("updateCode");
            defineua = obj.getString("defineua");
            softUrl = obj.getString("softUrl");
            remark = obj.getString("remark");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    
}
