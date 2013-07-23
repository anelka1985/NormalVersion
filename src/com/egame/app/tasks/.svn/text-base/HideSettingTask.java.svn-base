package com.egame.app.tasks;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.uis.HideSettingActivity;
import com.egame.utils.common.HttpConnect;

/**
 * 
 * 类说明：隐私设置
 * 
 * @创建时间 2012-1-5
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class HideSettingTask extends AsyncTask<String, String, String>
{
    
    private HideSettingActivity mActivity;
    
    private String resultCode ="";
    public HideSettingTask(HideSettingActivity mActivity)
    {
        super();
        this.mActivity = mActivity;
    }
    
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mActivity.showProgress(mActivity.getResources().getIdentifier("egame_waitModifyHide", "string",
            mActivity.getPackageName()));
    }
    
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            String s = HttpConnect.getHttpString(params[0]);
            JSONObject obj = new JSONObject(s);
            JSONObject result =  obj.getJSONObject("result");
            resultCode = result.optString("resultcode");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return resultCode;
    }
    
    @Override
    protected void onPostExecute(String result)
    {
        mActivity.hideProgress();
        mActivity.saveHideResult(result);
    }
}
