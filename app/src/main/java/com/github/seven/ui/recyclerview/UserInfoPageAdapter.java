package com.github.seven.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.seven.ui.R;

import java.util.List;

/**
 * Description: TODO <br/>
 * Date: 2017/1/10 <br/>
 *
 * @author seven.hu@ubtrobot.com
 */

public class UserInfoPageAdapter extends RecyclerView.Adapter<UserInfoPageAdapter.UserViewHolder> {

    private static final String TAG = UserInfoPageAdapter.class.getSimpleName();

    private List<String> mData;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public UserInfoPageAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataSource(List<String> source){
        this.mData = source;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mOnItemLongClickListener = listener;
    }


    private int pageMargin = 20;
    private int spanColumn = 4;
    private int spanRow = 2;
    private int itemWidth = 0;


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (itemWidth <= 0) {
            itemWidth = (parent.getWidth() - pageMargin * 2) / spanColumn;
        }

        View item = LayoutInflater.from(mContext).inflate(R.layout.item_user_info, parent, false);
        UserViewHolder holder = new UserViewHolder(item);

        holder.itemView.measure(0, 0);
        holder.itemView.getLayoutParams().width = itemWidth;
        holder.itemView.getLayoutParams().height = holder.itemView.getMeasuredHeight();

        return holder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {

        //holder.photo.setImageResource(R.drawable.yao);

        if (spanColumn == 1) {
            // 每个Item距离左右两侧各pageMargin
            holder.itemView.getLayoutParams().width = itemWidth + pageMargin * 2;
            holder.itemView.setPadding(pageMargin, 0, pageMargin, 0);
        } else {
            int m = position % (spanRow * spanColumn);
            if (m < spanRow) {
                // 每页左侧的Item距离左边pageMargin
                holder.itemView.getLayoutParams().width = itemWidth + pageMargin;
                holder.itemView.setPadding(pageMargin, 0, 0, 0);
            } else if (m >= spanRow * spanColumn - spanRow) {
                // 每页右侧的Item距离右边pageMargin
                holder.itemView.getLayoutParams().width = itemWidth + pageMargin;
                holder.itemView.setPadding(0, 0, pageMargin, 0);
            } else {
                // 中间的正常显示
                holder.itemView.getLayoutParams().width = itemWidth;
                holder.itemView.setPadding(0, 0, 0, 0);
            }
        }

        holder.name.setText(String.valueOf(position));
        holder.status.setText(String.valueOf(position % 8));

        final View listenView = holder.photo;

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            listenView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(listenView, pos);
                }
            });

            listenView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(listenView, pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView name;
        TextView status;

        public UserViewHolder(View view) {
            super(view);

            photo = (ImageView) view.findViewById(R.id.user_photo_image_view);
            name = (TextView) view.findViewById(R.id.user_name_text_view);
            status = (TextView) view.findViewById(R.id.user_status_text_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

}
