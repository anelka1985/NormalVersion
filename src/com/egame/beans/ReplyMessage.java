package com.egame.beans;

public class ReplyMessage
{
    /** 用户反馈时间 */
    private String reply_date;
    /** 反馈信息 */
    private String user_reply_message;
    
    /** 主题id */
    private String Content_id;
    
    public String getContent_id()
    {
        return Content_id;
    }

    public void setContent_id(String contentId)
    {
        Content_id = contentId;
    }

    /** 客服ID */
    private String id;
    
    /** 客服名称 */
    private String nickName;
    
    /** 客服回复内容 */
    private String content;
    
    /** 客服回复时间 */
    private String time;
    
    /** 客服回复内容ID */
    private String replyContentId;
    
    /** 客服满意度 */
    private String userReplySatisfactory;
    
    /** 用户满意度回复时间戳 */
    private String satisfactoryDate;

    public String getReply_date()
    {
        return reply_date;
    }

    public void setReply_date(String replyDate)
    {
        reply_date = replyDate;
    }

    public String getUser_reply_message()
    {
        return user_reply_message;
    }

    public void setUser_reply_message(String userReplyMessage)
    {
        user_reply_message = userReplyMessage;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getReplyContentId()
    {
        return replyContentId;
    }

    public void setReplyContentId(String replyContentId)
    {
        this.replyContentId = replyContentId;
    }

    public String getUserReplySatisfactory()
    {
        return userReplySatisfactory;
    }

    public void setUserReplySatisfactory(String userReplySatisfactory)
    {
        this.userReplySatisfactory = userReplySatisfactory;
    }

    public String getSatisfactoryDate()
    {
        return satisfactoryDate;
    }

    public void setSatisfactoryDate(String satisfactoryDate)
    {
        this.satisfactoryDate = satisfactoryDate;
    }
    
    
    
}
