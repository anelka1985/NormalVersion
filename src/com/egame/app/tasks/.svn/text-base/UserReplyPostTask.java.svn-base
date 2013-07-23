package com.egame.app.tasks;

import android.os.AsyncTask;

import com.egame.R;
import com.egame.app.uis.MyReplyActivity;
import com.egame.beans.UserReqResult;
import com.egame.config.Const;
import com.egame.utils.common.HttpConnect;

/**
 * 
 * 类说明：提交反馈问题
 * 
 * @创建时间 2011-12-28
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class UserReplyPostTask extends AsyncTask<String, String, String>
{
    
    private MyReplyActivity mActivity;
    
    public UserReplyPostTask(MyReplyActivity mActivity)
    {
        super();
        this.mActivity = mActivity;
    }
    
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mActivity.showProgress(R.string.egame_replyWait);
    }
    
    @Override
    protected String doInBackground(String... params)
    {
        UserReqResult userReqResult;
        try
        {
            String json = HttpConnect.getHttpString(params[0], 20000);
            userReqResult = new UserReqResult(json);
        }
        catch (Exception e)
        {
            return null;
        }
        
        if (!String.valueOf(Const.MSG_GET_SUCCESS).equals(userReqResult.result))
        {
            return null;
        }
        else
        {
            return "OK";
        }
    }
    
    @Override
    protected void onPostExecute(String result)
    {
        mActivity.hideProgress();
        mActivity.postReply(result);
    }
    
}
