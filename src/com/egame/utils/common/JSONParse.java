package com.egame.utils.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.egame.beans.Additional;
import com.egame.beans.Content;
import com.egame.beans.QueryByContentIdResult;
import com.egame.beans.QueryByPhoneResult;
import com.egame.beans.ReplyMessageByContentID;
import com.egame.beans.ReplyMessageByPhone;
import com.egame.beans.SatisfactionResult;
import com.egame.config.Const;

public class JSONParse
{
    private static int total;
    
    private final static String TEL = "[联系方式]:";
    
    /**
     * 客户满意度提交结果
     * 
     * @param json
     * @return
     */
    public static SatisfactionResult getSatisfactionResult(String json)
    {
        if (null == json || "".equals(json))
        {
            return null;
        }
        SatisfactionResult satisfactionResult = new SatisfactionResult();
        
        try
        {
            JSONObject jsonObj = new JSONObject(json);
            String reply_content_id = jsonObj.getString("reply_content_id");
            String result = jsonObj.getString("result");
            satisfactionResult.setResult(result);
            satisfactionResult.setReply_content_id(reply_content_id);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return satisfactionResult;
    }
    
    /**
     * 用户信息查询列表接口返回结果
     * 
     * @param json
     * @return
     */
    public static QueryByPhoneResult getQueryByPhoneResult(String json, String client_imei, String client_uid)
    {
        Log.d("test", "json--->" + json);
        if (null == json || "".equals(json))
        {
            return null;
        }
        QueryByPhoneResult queryByPhoneResult = new QueryByPhoneResult();
        try
        {
            JSONObject jsonObj = new JSONObject(json);
            String result = jsonObj.getString("result");
            queryByPhoneResult.setResult(result);
            
            List<ReplyMessageByPhone> replyMessageList = getReplyMessageList(jsonObj);
            List<ReplyMessageByPhone> moreReply = getMoreQueryByPhoneResult(total, client_imei, client_uid);
            
            for (int i = 0; i < moreReply.size(); i++)
            {
                replyMessageList.add(moreReply.get(i));
            }
            
            queryByPhoneResult.setReplyMessageList(replyMessageList);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return queryByPhoneResult;
    }
    
    /**
     * 当总数超过100条时继续获取数据
     * 
     * @param total
     * @param client_imei
     * @param client_uid
     * @return
     * @throws JSONException
     */
    private static List<ReplyMessageByPhone> getMoreQueryByPhoneResult(int total, String client_imei, String client_uid)
        throws JSONException
    {
        int num = 0;
        List<ReplyMessageByPhone> allReplyMessageList = new ArrayList<ReplyMessageByPhone>();
        if (total % 100 == 0 && total > 100)
        {
            num = total % 100;
        }
        else
        {
            num += total / 100;
        }
        
        String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String sig = MD5.getMD5Str(time + Const.KEY_FOR_GAME).toLowerCase();
        
        for (int a = 0; a < num; a++)
        {
            int page = a + 2;
            String queryByPhoneUrl =
                "http://10000club.189.cn:80/service/queryByPhone.php?" + "application_id=5" + "&app_version="
                    + Const.VERSION_CODE + "&client_imei=" + client_imei + "&client_imsi=&client_mdn=&client_uid="
                    + client_uid + "&sig=" + sig + "&time=" + time + "&page_count=100&page=" + page;
            
            String queryByPhoneJson = null;
            try
            {
                queryByPhoneJson = HttpConnect.getHttpString(queryByPhoneUrl, 20000);
                JSONObject jsonObj = new JSONObject(queryByPhoneJson);
                List<ReplyMessageByPhone> replyMessageList = getReplyMessageList(jsonObj);
                for (int i = 0; i < replyMessageList.size(); i++)
                {
                    allReplyMessageList.add(replyMessageList.get(i));
                }
            }
            catch (Exception e)
            {
                return null;
            }
            
        }
        return allReplyMessageList;
    }
    
    /**
     * 对反馈JSON解析
     * 
     * @param jsonObj
     * @return
     * @throws JSONException
     */
    private static List<ReplyMessageByPhone> getReplyMessageList(JSONObject jsonObj)
        throws JSONException
    {
        List<ReplyMessageByPhone> replyMessageList = new ArrayList<ReplyMessageByPhone>();
        
        JSONArray gourpList = jsonObj.getJSONArray("list_arr");
        for (int i = 0; i < gourpList.length(); i++)
        {
            JSONObject listArr = (JSONObject)gourpList.opt(i);
            ReplyMessageByPhone replyMessage = new ReplyMessageByPhone();
            replyMessage.setApp_version(listArr.getString("app_version"));
            replyMessage.setContent_id(listArr.getString("content_id"));
            replyMessage.setClient_imei(listArr.getString("client_imei"));
            replyMessage.setClient_imsi(listArr.getString("client_imsi"));
            replyMessage.setClient_mdn(listArr.getString("client_mdn"));
            replyMessage.setReply_time(CommonUtil.phpToJava(listArr.getString("reply_time")));
            String user_reply_message = listArr.getString("user_reply_message");
            Log.d("test", "user_reply_message--->" + user_reply_message);
            
            if (user_reply_message.lastIndexOf(TEL) != -1)
            {
                
                replyMessage.setUser_reply_message(CommonUtil.getURLDecoder(user_reply_message.substring(0,
                    user_reply_message.lastIndexOf(TEL))));
            }
            else
            {
                replyMessage.setUser_reply_message(CommonUtil.getURLDecoder(user_reply_message));
            }
            if (!"null".equals(listArr.getString("reply_content")))
            {
                
                String reply_contentList =
                    "{\"reply_content\":" + CommonUtil.getURLDecoder(listArr.getString("reply_content")) + "}";
                
                JSONObject jsonContent = new JSONObject(reply_contentList);
                JSONArray contentList = jsonContent.getJSONArray("reply_content");
                
                List<Content> replyContentList = new ArrayList<Content>();
                for (int j = 0; j < contentList.length(); j++)
                {
                    JSONObject reply_content = (JSONObject)contentList.opt(j);
                    
                    Content content = new Content();
                    content.setContent(reply_content.getString("content"));
                    replyContentList.add(content);
                }
                
                replyMessage.setReply_content(replyContentList);
            }
            replyMessage.setReply_date(CommonUtil.phpToJava(listArr.getString("reply_date")));
            
            // 1.7
            total = listArr.getInt("total");
            replyMessage.setTotal(listArr.getInt("total"));
            replyMessageList.add(replyMessage);
            
        }
        
        return replyMessageList;
    }
    
    /**
     * 单个主题详情
     * 
     * @param str JSON格式字段
     * @return
     */
    public static QueryByContentIdResult getQueryByContentIdResult(String json)
    {
        if (null == json || "".equals(json))
        {
            return null;
        }
        QueryByContentIdResult queryByContentIdResult = null;
        try
        {
            JSONObject jsonObj = new JSONObject(json);
            String result = jsonObj.getString("result");
            
            queryByContentIdResult = new QueryByContentIdResult();
            queryByContentIdResult.setResult(result);
            
            List<ReplyMessageByContentID> replyMessageList = new ArrayList<ReplyMessageByContentID>();
            JSONArray gourpList = jsonObj.getJSONArray("list_arr");
            
            for (int i = 0; i < gourpList.length(); i++)
            {
                JSONObject listArr = (JSONObject)gourpList.opt(i);
                ReplyMessageByContentID replyMessage = new ReplyMessageByContentID();
                replyMessage.setApp_version(listArr.getString("app_version"));
                replyMessage.setContent_id(listArr.getString("content_id"));
                replyMessage.setClient_imei(listArr.getString("client_imei"));
                replyMessage.setClient_imsi(listArr.getString("client_imsi"));
                replyMessage.setClient_mdn(listArr.getString("client_mdn"));
                replyMessage.setReply_time(CommonUtil.phpToJava(listArr.getString("reply_time")));
                String user_reply_message = listArr.getString("user_reply_message");
                Log.d("test", "user_reply_message--->" + user_reply_message);
                
                if (user_reply_message.lastIndexOf(TEL) != -1)
                {
                    
                    replyMessage.setUser_reply_message(CommonUtil.getURLDecoder(user_reply_message.substring(0,
                        user_reply_message.lastIndexOf(TEL))));
                }
                else
                {
                    replyMessage.setUser_reply_message(CommonUtil.getURLDecoder(user_reply_message));
                }
                replyMessage.setReply_id(listArr.getString("reply_id"));
                // 构造reply_content客服回复内容
                if (!"null".equals(listArr.getString("reply_content")))
                {
                    
                    String reply_contentList =
                        "{\"reply_content\":" + CommonUtil.getURLDecoder(listArr.getString("reply_content")) + "}";
                    
                    JSONObject jsonContent = new JSONObject(reply_contentList);
                    JSONArray contentArr = jsonContent.getJSONArray("reply_content");
                    
                    List<Content> replyContentList = new ArrayList<Content>();
                    for (int j = 0; j < contentArr.length(); j++)
                    {
                        JSONObject reply_content = (JSONObject)contentArr.opt(j);
                        Content content = new Content();
                        content.setContent(reply_content.getString("content"));
                        content.setTime(CommonUtil.phpToJava(reply_content.getString("time")));
                        content.setReplyContentId(reply_content.getString("reply_content_id"));
                        content.setUserReplySatisfactory(reply_content.getString("user_reply_satisfactory"));
                        content.setSatisfactoryDate(CommonUtil.phpToJava(reply_content.getString("satisfactory_date")));
                        replyContentList.add(content);
                    }
                    
                    replyMessage.setReply_content(replyContentList);
                }
                replyMessage.setReply_date(CommonUtil.phpToJava(listArr.getString("reply_date")));
                replyMessage.setTotal(listArr.getInt("total"));
                
                // 用户追问反馈
                if (!"null".equals(listArr.getString("additional_content_id")))
                {
                    String additionalJson =
                        "{\"additional_content_id\":"
                            + CommonUtil.getURLDecoder(listArr.getString("additional_content_id")) + "}";
                    
                    JSONObject jsonContent = new JSONObject(additionalJson);
                    JSONArray additionalArr = jsonContent.getJSONArray("additional_content_id");
                    // List<Additional> additionalList = new ArrayList<Additional>();
                    List<Additional> additionalList = new ArrayList<Additional>();
                    for (int j = 0; j < additionalArr.length(); j++)
                    {
                        JSONObject additionalObj = (JSONObject)additionalArr.opt(j);
                        Additional additional = new Additional();
                        additional.setContent_id(additionalObj.getString("content_id"));
                        additional.setUser_reply_message(CommonUtil.getURLDecoder(additionalObj.getString("user_reply_message")));
                        additionalList.add(additional);
                    }
                    replyMessage.setAdditional_content_id(additionalList);
                    
                }
                // 测试
                List<ReplyMessageByContentID> aa = getReplyByContentIDList(replyMessage.getAdditional_content_id());
                
                replyMessageList.add(replyMessage);
                
                if (null != aa)
                {
                    
                    for (int j = 0; j < aa.size(); j++)
                    {
                        replyMessageList.add(aa.get(j));
                    }
                }
            }
            
            queryByContentIdResult.setReplyMessageList(replyMessageList);
        }
        catch (JSONException e)
        {
            return null;
        }
        return queryByContentIdResult;
    }
    
    private static List<ReplyMessageByContentID> getReplyByContentIDList(List<Additional> list)
    {
        List<ReplyMessageByContentID> replyMessageList = new ArrayList<ReplyMessageByContentID>();
        String queryByContentIdJson = null;
        for (int a = 0; a < list.size(); a++)
        {
            
            String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
            String sig = MD5.getMD5Str(time + Const.KEY_FOR_GAME).toLowerCase();
            
            String queryByContentIdUrl =
                "http://10000club.189.cn:80/service/queryByContentId.php?" + "content_id="
                    + list.get(a).getContent_id() + "&time=" + time + "&sig=" + sig + "&page_count=100&page=1";
            
            try
            {
                queryByContentIdJson = HttpConnect.getHttpString(queryByContentIdUrl, 20000);
            }
            catch (Exception e1)
            {
                queryByContentIdJson = "error";
            }
            
            if ("error".equals(queryByContentIdJson))
            {
                return null;
            }
            try
            {
                
                JSONObject jsonObj = new JSONObject(queryByContentIdJson);
                JSONArray gourpList = jsonObj.getJSONArray("list_arr");
                List<Additional> additionalList = new ArrayList<Additional>();
                for (int i = 0; i < gourpList.length(); i++)
                {
                    JSONObject listArr = (JSONObject)gourpList.opt(i);
                    ReplyMessageByContentID replyMessage = new ReplyMessageByContentID();
                    replyMessage.setApp_version(listArr.getString("app_version"));
                    replyMessage.setContent_id(listArr.getString("content_id"));
                    replyMessage.setClient_imei(listArr.getString("client_imei"));
                    replyMessage.setClient_imsi(listArr.getString("client_imsi"));
                    replyMessage.setClient_mdn(listArr.getString("client_mdn"));
                    replyMessage.setReply_time(CommonUtil.phpToJava(listArr.getString("reply_time")));
                    String user_reply_message = listArr.getString("user_reply_message");
                    Log.d("test", "user_reply_message--->" + user_reply_message);
                    
                    if (user_reply_message.lastIndexOf(TEL) != -1)
                    {
                        
                        replyMessage.setUser_reply_message(CommonUtil.getURLDecoder(user_reply_message.substring(0,
                            user_reply_message.lastIndexOf(TEL))));
                    }
                    else
                    {
                        replyMessage.setUser_reply_message(CommonUtil.getURLDecoder(user_reply_message));
                    }
                    replyMessage.setReply_id(listArr.getString("reply_id"));
                    // 构造reply_content客服回复内容
                    if (!"null".equals(listArr.getString("reply_content")))
                    {
                        
                        String reply_contentList =
                            "{\"reply_content\":" + CommonUtil.getURLDecoder(listArr.getString("reply_content")) + "}";
                        
                        JSONObject jsonContent = new JSONObject(reply_contentList);
                        JSONArray contentArr = jsonContent.getJSONArray("reply_content");
                        
                        List<Content> replyContentList = new ArrayList<Content>();
                        for (int j = 0; j < contentArr.length(); j++)
                        {
                            JSONObject reply_content = (JSONObject)contentArr.opt(j);
                            Content content = new Content();
                            content.setContent(reply_content.getString("content"));
                            content.setTime(CommonUtil.phpToJava(reply_content.getString("time")));
                            content.setReplyContentId(reply_content.getString("reply_content_id"));
                            content.setUserReplySatisfactory(reply_content.getString("user_reply_satisfactory"));
                            content.setSatisfactoryDate(CommonUtil.phpToJava(reply_content.getString("satisfactory_date")));
                            replyContentList.add(content);
                        }
                        
                        replyMessage.setReply_content(replyContentList);
                    }
                    replyMessage.setReply_date(CommonUtil.phpToJava(listArr.getString("reply_date")));
                    replyMessage.setTotal(listArr.getInt("total"));
                    
                    // 用户追问反馈
                    if (!"null".equals(listArr.getString("additional_content_id")))
                    {
                        String additionalJson =
                            "{\"additional_content_id\":"
                                + CommonUtil.getURLDecoder(listArr.getString("additional_content_id")) + "}";
                        
                        JSONObject jsonContent = new JSONObject(additionalJson);
                        JSONArray additionalArr = jsonContent.getJSONArray("additional_content_id");
                        for (int j = 0; j < additionalArr.length(); j++)
                        {
                            JSONObject additionalObj = (JSONObject)additionalArr.opt(j);
                            Additional additional = new Additional();
                            additional.setContent_id(additionalObj.getString("content_id"));
                            additional.setUser_reply_message(CommonUtil.getURLDecoder(additionalObj.getString("user_reply_message")));
                            additionalList.add(additional);
                        }
                        replyMessage.setAdditional_content_id(additionalList);
                    }
                    
                    replyMessageList.add(replyMessage);
                    
                    List<ReplyMessageByContentID> replyMessageDetailLst = new ArrayList<ReplyMessageByContentID>();
                    
                    replyMessageDetailLst = getReplyByContentIDList(additionalList);
                    
                    if (null != replyMessageDetailLst)
                    {
                        
                        for (int j = 0; j < replyMessageDetailLst.size(); j++)
                        {
                            replyMessageList.add(replyMessageDetailLst.get(j));
                        }
                    }
                    
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            
        }
        
        return replyMessageList;
    }
    
}
