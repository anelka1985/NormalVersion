package com.egame.beans;

import org.json.JSONObject;

public class ResultBean
{
    private String resultCode;
    
    private String resultMessage;
    
    public ResultBean(JSONObject obj)
    {
        this.resultCode = obj.optString("resultcode");
        this.resultMessage = obj.optString("resultmsg");
    }

    public String getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getResultMessage()
    {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage)
    {
        this.resultMessage = resultMessage;
    }
    
}
