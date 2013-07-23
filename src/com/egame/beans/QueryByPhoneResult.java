package com.egame.beans;


import java.util.List;

/**
 * 用户信息查询列表接口返回结果
 * 
 * @author wangxianyun
 * @Date 2011-11-23
 */
public class QueryByPhoneResult
{
    /** 1成功/0失败 */
    private String result;
    
    private List<ReplyMessageByPhone> replyMessageList;
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public List<ReplyMessageByPhone> getReplyMessageList()
    {
        return replyMessageList;
    }
    
    public void setReplyMessageList(List<ReplyMessageByPhone> replyMessageList)
    {
        this.replyMessageList = replyMessageList;
    }
}
