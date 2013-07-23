package com.egame.app.tasks;

import java.util.List;

import android.os.AsyncTask;

import com.egame.R;
import com.egame.app.uis.MyReplyActivity;
import com.egame.beans.QueryByContentIdResult;
import com.egame.beans.ReplyMessageByContentID;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.JSONParse;

/**
 * 
 * 类说明：单个主题反馈详情
 * @创建时间 2011-12-29
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class ReplyDetailTask extends AsyncTask<String, String, List<ReplyMessageByContentID>>
{
    private MyReplyActivity mActivity;
    
    public ReplyDetailTask(MyReplyActivity mActivity)
    {
        super();
        this.mActivity = mActivity;
    }
    
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mActivity.showProgress(R.string.egame_loadData);
    }
    
    @Override
    protected List<ReplyMessageByContentID> doInBackground(String... params)
    {
        QueryByContentIdResult queryByContentIdResult = null;
        try
        {
            String json = HttpConnect.getHttpString(params[0], 20000);
            queryByContentIdResult = JSONParse.getQueryByContentIdResult(json);
        }
        catch (Exception e)
        {
            return null;
        }
        if (null != queryByContentIdResult && "1".equals(queryByContentIdResult.getResult()))
        {
            return queryByContentIdResult.getReplyMessageList();
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(List<ReplyMessageByContentID> result)
    {
        mActivity.hideProgress();
        mActivity.getReplyDetail(result);
    }
    
}
