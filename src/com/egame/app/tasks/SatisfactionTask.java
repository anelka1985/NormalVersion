package com.egame.app.tasks;

import android.os.AsyncTask;

import com.egame.R;
import com.egame.app.uis.MyReplyActivity;
import com.egame.beans.SatisfactionResult;
import com.egame.config.Const;
import com.egame.utils.common.HttpConnect;
import com.egame.utils.common.JSONParse;

/**
 * 
 * 类说明：提交满意度
 * 
 * @创建时间 2011-12-29
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class SatisfactionTask extends AsyncTask<String, String, String>
{
    private MyReplyActivity mActivity;
    
    public SatisfactionTask(MyReplyActivity mActivity)
    {
        super();
        this.mActivity = mActivity;
    }
    
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mActivity.showProgress(R.string.egame_satisfaction);
    }
    
    @Override
    protected String doInBackground(String... params)
    {
        SatisfactionResult satisfactionResult = null;
        try
        {
            String json = HttpConnect.getHttpString(params[0], 20000);
            satisfactionResult = JSONParse.getSatisfactionResult(json);
        }
        catch (Exception e)
        {
            return null;
        }
        
        if (!String.valueOf(Const.MSG_GET_SUCCESS).equals(satisfactionResult.getResult()))
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
        super.onPostExecute(result);
        mActivity.hideProgress();
        mActivity.getSatisfaction(result);
    }
    
}
