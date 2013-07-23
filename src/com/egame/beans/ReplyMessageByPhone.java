package com.egame.beans;

import java.util.List;

/**
 * 用户反馈信息
 * 
 * @author wangxianyun
 * @Date 2011-11-23
 */
public class ReplyMessageByPhone
{
    /** 主题id */
    private String Content_id;
    
    /** 应用版本 */
    private String app_version;
    
    /** IMEI编码 */
    private String client_imei;
    
    /** IMSI编码 */
    private String client_imsi;
    
    /** 时间戳 */
    private String reply_time;
    
    /** 用户反馈时间 */
    private String reply_date;
    
    /** MDN编码 */
    private String client_mdn;
    
    /** 反馈信息 */
    private String user_reply_message;
    
    /** 客服回复内容 */
    private List<Content> reply_content;
    
    /**反馈总数*/
    
    private int total;
    public String getContent_id()
    {
        return Content_id;
    }
    
    public void setContent_id(String contentId)
    {
        Content_id = contentId;
    }
    
    public String getApp_version()
    {
        return app_version;
    }
    
    public void setApp_version(String appVersion)
    {
        app_version = appVersion;
    }
    
    public String getClient_imei()
    {
        return client_imei;
    }
    
    public void setClient_imei(String clientImei)
    {
        client_imei = clientImei;
    }
    
    public String getClient_imsi()
    {
        return client_imsi;
    }
    
    public void setClient_imsi(String clientImsi)
    {
        client_imsi = clientImsi;
    }
    
    public String getClient_mdn()
    {
        return client_mdn;
    }
    
    public void setClient_mdn(String clientMdn)
    {
        client_mdn = clientMdn;
    }
    
    public String getUser_reply_message()
    {
        return user_reply_message;
    }
    
    public void setUser_reply_message(String userReplyMessage)
    {
        user_reply_message = userReplyMessage;
    }
    
    public List<Content> getReply_content()
    {
        return reply_content;
    }
    
    public void setReply_content(List<Content> replyContent)
    {
        reply_content = replyContent;
    }
    
    public String getReply_time()
    {
        return reply_time;
    }
    
    public void setReply_time(String replyTime)
    {
        reply_time = replyTime;
    }
    
    public String getReply_date()
    {
        return reply_date;
    }
    
    public void setReply_date(String replyDate)
    {
        reply_date = replyDate;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }
}
