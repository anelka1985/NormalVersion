package com.egame.app.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.egame.R;
import com.egame.beans.Content;
import com.egame.beans.ReplyMessageByPhone;

/**
 * 
 * 类说明：反馈列表
 * @创建时间 2012-1-4
 * @创建人： 王先云
 * @邮箱：wangxy@gzylxx.com
 */
public class ReplyListAdapter extends BaseAdapter
{
    private List<ReplyMessageByPhone> listMessage;
    
    private LayoutInflater mInflater;
    
    private Context ctx;
    
    public ReplyListAdapter(List<ReplyMessageByPhone> listMessage, Context ctx)
    {
        super();
        this.ctx = ctx;
        this.listMessage = listMessage;
        mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }
    
    @Override
    public int getCount()
    {
        return listMessage.size();
    }
    
    @Override
    public Object getItem(int arg0)
    {
        return listMessage.get(arg0);
    }
    
    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }
    
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (null == view)
        {
            view = mInflater.inflate(R.layout.egame_myreply_item, null);
            holder = new ViewHolder();
            holder.tvContent = (TextView)view.findViewById(R.id.tv_content);
            holder.tvState = (TextView)view.findViewById(R.id.tv_state);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }
        holder.tvContent.setText(listMessage.get(position).getUser_reply_message());
        List<Content> contentList = listMessage.get(position).getReply_content();
        view.setEnabled(true);
        if (null == contentList)
        {
            holder.tvState.setText(ctx.getResources().getString(R.string.egame_ytj));
            holder.tvState.setTextColor(Color.BLACK);
        }
        else
        {
            holder.tvState.setText(ctx.getResources().getString(R.string.egame_yhf));
            holder.tvState.setTextColor(Color.RED);
        }
        return view;
    }
    
    /**
     * 添加数据列表项
     * 
     * @param replyMessage
     */
    public void addReplyMessage(ReplyMessageByPhone replyMessage)
    {
        listMessage.add(replyMessage);
    }
    
    public static class ViewHolder
    {
        
        public TextView tvContent;
        
        public TextView tvState;
    }
}
