package com.bytedance.todolist.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListItemHolder> {
    private List<TodoListEntity> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public TodoListAdapter() {
        mDatas = new ArrayList<>();
    }
    @NonNull
    @Override
    public TodoListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListItemHolder holder, final int position) {
        holder.bind(mDatas.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemCLick(v, mDatas.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @MainThread
    public void setData(List<TodoListEntity> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemCLick(View v, TodoListEntity data);

        void onItemLongCLick(View v, TodoListEntity data);
    }
}
