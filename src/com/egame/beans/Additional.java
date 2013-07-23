package com.egame.beans;

/**
 * 问题追问
 * 
 * @author wangxianyun
 * @Date 2011-12-6
 */
public class Additional
{
    /** 主题ID */
    private String content_id;
    
    /** 用户反馈信息内容 */
    private String user_reply_message;
    
    public String getContent_id()
    {
        return content_id;
    }
    
    public void setContent_id(String contentId)
    {
        content_id = contentId;
    }
    
    public String getUser_reply_message()
    {
        return user_reply_message;
    }
    
    public void setUser_reply_message(String userReplyMessage)
    {
        user_reply_message = userReplyMessage;
    }
}
