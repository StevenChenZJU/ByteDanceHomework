package com.example.recyclerviewhw.recycler;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewhw.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<MessageData> mDataset = new ArrayList<>();
    private IOnItemClickListener mItemClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRemark;
        private TextView tvMessage;
        private TextView tvTime;
        private TextView tvMsgNumber;
        private View contentView;


        public MyViewHolder(View v) {
            super(v);
            contentView = v;
            tvRemark = v.findViewById(R.id.tv_remark);
            tvMessage = v.findViewById(R.id.tv_msg_brief);
            tvTime = v.findViewById(R.id.tv_time);
            tvMsgNumber = v.findViewById(R.id.tv_msg_number);
        }

        public void onBind(int position, MessageData data) {
            tvRemark.setText(data.remark);
            tvMessage.setText(data.message);
            tvTime.setText(data.time);
            tvMsgNumber.setText(data.msgNumber.toString());
            if (data.msgNumber.equals(Integer.valueOf(0))) {
                tvMsgNumber.setVisibility(View.INVISIBLE);
            }
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


    public MyAdapter(List<MessageData> myDataset) {
        mDataset.addAll(myDataset);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void addData(int position, MessageData data) {
        mDataset.add(position, data);
        notifyItemInserted(position);
        if (position != mDataset.size()) {
            //刷新改变位置item下方的所有Item的位置,避免索引错乱
            notifyItemRangeChanged(position, mDataset.size() - position);
        }
    }

    public void removeData(int position) {
        if (null != mDataset && mDataset.size() > position) {
            mDataset.remove(position);
            notifyItemRemoved(position);
            if (position != mDataset.size()) {
                //刷新改变位置item下方的所有Item的位置,避免索引错乱
                notifyItemRangeChanged(position, mDataset.size() - position);
            }
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
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

        void onItemCLick(int position, MessageData data);

        void onItemLongCLick(int position, MessageData data);
    }
}
