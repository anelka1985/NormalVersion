package com.egame.beans;

import org.json.JSONObject;

import com.egame.utils.ui.IconBeanImpl;

/**
 * 
 * 类说明：产品列表
 * 
 * @创建时间 2012-2-3
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class ProductListBean extends IconBeanImpl
{
    /** 产品名称 */
    private String productName;
    
    /** 产品ID */
    private String productId;
    
    /** 产品类型 */
    private int productType;
    
    /** 访问地址 */
    private String linkUrl;
    
    public ProductListBean(JSONObject obj)
    {
        super(obj.optString("picture"));
        this.productName = obj.optString("productName");
        this.productId = obj.optString("productId");
        this.productType = obj.optInt("productType");
        this.linkUrl = obj.optString("linkUrl");
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public int getProductType()
    {
        return productType;
    }
    
    public void setProductType(int productType)
    {
        this.productType = productType;
    }
    
    public String getLinkUrl()
    {
        return linkUrl;
    }
    
    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }
    
}
