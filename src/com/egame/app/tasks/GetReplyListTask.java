package com.egame.app.tasks;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.egame.R;
import com.egame.app.uis.MyReplyActivity;
import com.egame.beans.QueryByPhoneResult;
import com.egame.beans.ReplyMessageByPhone;
import com.egame.config.Const;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.JSONParse;

public class GetReplyListTask extends AsyncTask<String, String, List<ReplyMessageByPhone>>
{
    
    private MyReplyActivity mActivity;
    
    /** 反馈信息集合 */
    public List<ReplyMessageByPhone> replyMessageList = new ArrayList<ReplyMessageByPhone>();
    
    public GetReplyListTask(MyReplyActivity mActivity)
    {
        super();
        this.mActivity = mActivity;
    }
    
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mActivity.showProgress(R.string.egame_getReplyList);
    }
    
    @Override
    protected List<ReplyMessageByPhone> doInBackground(String... params)
    {
        QueryByPhoneResult queryByPhoneResult = null;
        try
        {
            String json = HttpConnect.getHttpString(params[0], 20000);
            queryByPhoneResult = JSONParse.getQueryByPhoneResult(json, params[1], params[2]);
        }
        catch (Exception e)
        {
            return null;
        }
        if (null != queryByPhoneResult && String.valueOf(Const.MSG_GET_SUCCESS).equals(queryByPhoneResult.getResult()))
        {
            return queryByPhoneResult.getReplyMessageList();
        }
        else
        {
            return null;
        }
    }
    
    @Override
    protected void onPostExecute(List<ReplyMessageByPhone> result)
    {
        mActivity.hideProgress();
        mActivity.getReplyList(result);
        
    }
    
}
