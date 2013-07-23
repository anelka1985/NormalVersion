package com.egame.app.tasks;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.R;
import com.egame.app.uis.HideSettingActivity;
import com.egame.beans.ResultBean;
import com.egame.utils.common.HttpConnect;

/**
 * 
 * 类说明：修改密码
 * 
 * @创建时间 2012-1-5
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class ModifyPasswdTask extends AsyncTask<String, String, ResultBean>
{
    private HideSettingActivity mActivity;
    
//    private String resultCode = "";
    
    public ModifyPasswdTask(HideSettingActivity mActivity)
    {
        super();
        this.mActivity = mActivity;
    }
    
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mActivity.showProgress(R.string.egame_waitModifyPasswd);
    }
    
    @Override
    protected ResultBean doInBackground(String... params)
    {
        ResultBean resultBean = null;
        try
        {
            String s = HttpConnect.getHttpString(params[0]);
            JSONObject obj = new JSONObject(s);
            JSONObject result = obj.getJSONObject("result");
            resultBean = new ResultBean(result);
//            resultCode = result.optString("resultcode");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return resultBean;
    }
    
    @Override
    protected void onPostExecute(ResultBean resultBean)
    {
        mActivity.hideProgress();
        mActivity.modifyPasswdResult(resultBean);
    }
    
}
