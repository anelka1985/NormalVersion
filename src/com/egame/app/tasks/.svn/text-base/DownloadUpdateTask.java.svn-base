package com.egame.app.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;

import com.egame.R;
import com.egame.app.uis.UpdateSoftWareActivity;
import com.egame.config.Const;

public class DownloadUpdateTask extends AsyncTask<String, Integer, Boolean>
{
    /**
     * 下载线程是否退出
     */
    private boolean isAlive = true;
    
    /**
     * 临时文件名称
     */
    private final static String tempfile = "egametmp.apk";
    
    private UpdateSoftWareActivity mActivity;
    
    public DownloadUpdateTask(UpdateSoftWareActivity mActivity)
    {
        super();
        this.mActivity = mActivity;
    }
    
    @Override
    protected Boolean doInBackground(String... params)
    {
        String url = params[0];
        if (!URLUtil.isNetworkUrl(url))
        {
            
        }
        else
        {
            // 取得URL
            URL myURL;
            try
            {
                myURL = new URL(url);
                // 创建连接
                HttpURLConnection conn;
                InputStream is;
                BufferedInputStream bis = null;
                FileOutputStream fos;
                BufferedOutputStream bos = null;
                conn = (HttpURLConnection)myURL.openConnection();
                if (conn != null)
                {
                    conn.setConnectTimeout(1000 * 20);
                    conn.setReadTimeout(1000 * 20);
                    conn.connect();
                    int size = conn.getContentLength();
                    publishProgress(1, size);
                    is = conn.getInputStream();
                    if (is == null)
                    {
                        return false;
                    }
                    else
                    {
                        bis = new BufferedInputStream(is, Const.BUFFER_SIZE);
                        fos = mActivity.openFileOutput(tempfile, Context.MODE_WORLD_READABLE);
                        bos = new BufferedOutputStream(fos, Const.BUFFER_SIZE);
                        byte buf[] = new byte[1024 * 32];
                        do
                        {
                            int numread = bis.read(buf);
                            if (numread <= 0)
                            {
                                break;
                            }
                            bos.write(buf, 0, numread);
                            publishProgress(2, numread);
                        } while (isAlive);
                        bos.flush();
                        try
                        {
                            if (is != null)
                                is.close();
                            if (fos != null)
                                fos.close();
                            if (bos != null)
                                bos.close();
                            if (bis != null)
                                bis.close();
                            if (conn != null)
                                conn.disconnect();
                        }
                        catch (Exception ex)
                        {
                            Log.e("getDataSource", "error: " + ex.getMessage(), ex);
                        }
                    }
                    
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
                return false;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected void onCancelled()
    {
        super.onCancelled();
    }
    
    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        mActivity.hideProgress();
        if (!isAlive)
        {
            return;
        }
        if (!mActivity.isFinishing())
        {
//            mActivity.setUp(result);
        }
        
    }
    
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
        if (values[0] == 1)
        {
            // updateProgressDialog.setMax(values[1]);
        }
        else if (values[0] == 2)
        {
            // updateProgressDialog.setProgress(updateProgressDialog.getProgress() + values[1]);
        }
    }
    
    @Override
    protected void onPreExecute()
    {
        mActivity.showProgress(R.string.egame_waitModifyPasswd);
    }
    
}
