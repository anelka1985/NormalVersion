package com.egame.app.tasks;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.egame.app.adapters.ProductListAdapter;
import com.egame.app.uis.ProductLitActivity;
import com.egame.beans.ProductListBean;
import com.egame.utils.common.HttpConnect;

/**
 * 
 * 类说明：产品列表
 * @创建时间 2012-2-7
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class ProductListTask extends AsyncTask<String, Integer, List<ProductListBean>>
{
    
    private ProductListAdapter adapter;
    
    private List<ProductListBean> list;
    
    private static final String MORE_PRODUCTLIST = "moreProducts";
    
    private ProductLitActivity mActivity;
    public ProductListTask(ProductListAdapter adapter, List<ProductListBean> list, ProductLitActivity mActivity)
    {
        super();
        this.adapter = adapter;
        this.list = list;
        this.mActivity = mActivity;
    }
    
    @Override
    protected List<ProductListBean> doInBackground(String... arg0)
    {
        try
        {
            String s = HttpConnect.getHttpString(arg0[0]);
            JSONObject obj = new JSONObject(s);
            JSONArray array = obj.getJSONArray(MORE_PRODUCTLIST);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject json = array.getJSONObject(i);
                ProductListBean productListBean = new ProductListBean(json);
                list.add(productListBean);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    
    @Override
    protected void onPostExecute(List<ProductListBean> result)
    {
        super.onPostExecute(result);
        adapter.notifyDataSetChanged();
        if (null != result)
        {
            mActivity.getTaskResult(result);
            new GetListIconAsyncTask<ProductListBean>(result, adapter).execute("");
            
        }
       
    }
    
}
