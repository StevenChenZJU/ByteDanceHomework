package com.example.chapter3.homework.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;

import com.example.chapter3.homework.R;

import java.util.ArrayList;
import java.util.List;

public class FriendItemAdapter extends RecyclerView.Adapter<FriendItemAdapter.MyViewHolder> {
    private List<FriendData> mDataset = new ArrayList<>();
    private IOnItemClickListener mItemClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPortrait;
        private TextView tvRemark;
        private View contentView;

        public MyViewHolder(View v) {
            super(v);
            contentView = v;
            ivPortrait = v.findViewById(R.id.item_portrait);
            tvRemark = v.findViewById(R.id.remark);
        }

        public void onBind(int position, FriendData data) {
            ivPortrait.setImageResource(R.mipmap.ic_launcher);
            tvRemark.setText(data.getRemark());
        }

        public void setOnClickListener(View.OnClickListener listener) {
            if (listener != null) {
                contentView.setOnClickListener(listener);
            }
        }

        public void setOnLongClickListener(View.OnLongClickListener listener) {
            if (listener != null) {
                contentView.setOnLongClickListener(listener);
            }
        }
    }


    public FriendItemAdapter(List<FriendData> myDataset) {
        mDataset.addAll(myDataset);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }

//    public void addData(int position, MessageData data) {
//        mDataset.add(position, data);
//        notifyItemInserted(position);
//        if (position != mDataset.size()) {
//            //刷新改变位置item下方的所有Item的位置,避免索引错乱
//            notifyItemRangeChanged(position, mDataset.size() - position);
//        }
//    }
//
//    public void removeData(int position) {
//        if (null != mDataset && mDataset.size() > position) {
//            mDataset.remove(position);
//            notifyItemRemoved(position);
//            if (position != mDataset.size()) {
//                //刷新改变位置item下方的所有Item的位置,避免索引错乱
//                notifyItemRangeChanged(position, mDataset.size() - position);
//            }
//        }
//    }

    @Override
    public FriendItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.onBind(position, mDataset.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemCLick(position, mDataset.get(position));
                }
            }
        });
        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemLongCLick(position, mDataset.get(position));
                }
                return false;
            }

        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface IOnItemClickListener {

        void onItemCLick(int position, FriendData data);

        void onItemLongCLick(int position, FriendData data);
    }
}
