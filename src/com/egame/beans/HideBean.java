package com.egame.beans;

import org.json.JSONObject;

/**
 * 
 * 类说明：隐私设置
 * @创建时间 2012-3-30
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class HideBean
{
    private int homePage = 11;
    
    private int sendMessage = 61;
    
    private int myAge = 91;
    
    private int mySite = 101;

    public HideBean(JSONObject obj)
    {
        this.homePage = obj.optInt("canSeeHomepage");
        this.sendMessage = obj.optInt("canSendMessage");
        this.myAge = obj.optInt("showAge");
        this.mySite = obj.optInt("showLocation");
    }
    
    public int getHomePage()
    {
        return homePage;
    }

    public void setHomePage(int homePage)
    {
        this.homePage = homePage;
    }

    public int getSendMessage()
    {
        return sendMessage;
    }

    public void setSendMessage(int sendMessage)
    {
        this.sendMessage = sendMessage;
    }

    public int getMyAge()
    {
        return myAge;
    }

    public void setMyAge(int myAge)
    {
        this.myAge = myAge;
    }

    public int getMySite()
    {
        return mySite;
    }

    public void setMySite(int mySite)
    {
        this.mySite = mySite;
    }
    
}
