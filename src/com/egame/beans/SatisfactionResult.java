package com.egame.beans;

/**
 * 客户满意度
 * @author wangxianyun
 * @Date 2011-12-15
 */
public class SatisfactionResult
{
    private String result;
    
    private String reply_content_id;
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public String getReply_content_id()
    {
        return reply_content_id;
    }
    
    public void setReply_content_id(String replyContentId)
    {
        reply_content_id = replyContentId;
    }
}
