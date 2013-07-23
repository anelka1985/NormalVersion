package com.egame.beans;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 类说明：用户信息反馈上行接口返回结果
 * 
 * @创建时间 2011-12-28
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class UserReqResult
{
    /** 返回的操作结果 1成功/0失败 */
    public String result;
    
    /** 该主题的id */
    public int contentId;
    
    /** 该问题的id */
    public int replyId;
    
    public UserReqResult(String json)
    {
        try
        {
            JSONObject obj = new JSONObject(json);
            result = obj.getString("result");
            contentId = obj.getInt("content_id");
            replyId = obj.getInt("reply_id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
}
