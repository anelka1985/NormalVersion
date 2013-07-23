package com.egame.beans;


import java.util.List;

public class QueryByContentIdResult
{
    private String result;
    
    private List<ReplyMessageByContentID> replyMessageList;
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public List<ReplyMessageByContentID> getReplyMessageList()
    {
        return replyMessageList;
    }
    
    public void setReplyMessageList(List<ReplyMessageByContentID> replyMessageList)
    {
        this.replyMessageList = replyMessageList;
    }
    
}
