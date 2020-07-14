package com.bytedance.todolist.activity;

import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListItemHolder extends RecyclerView.ViewHolder {
    private TextView mContent;
    private TextView mTimestamp;
    private CheckBox mCheckBox;
    private Button mDelete;
    private final static float DELETED_ALPHA = 0.3F;
    private final static float NORMAL_ALPHA = 1F;

    public TodoListItemHolder(@NonNull View itemView) {
        super(itemView);
        mContent = itemView.findViewById(R.id.tv_content);
        mTimestamp = itemView.findViewById(R.id.tv_timestamp);
        mCheckBox = itemView.findViewById(R.id.cb_done);
        mDelete = itemView.findViewById(R.id.bt_delete);
    }

    public void bind(final TodoListEntity entity) {
        mContent.setText(entity.getContent());
        mTimestamp.setText(formatDate(entity.getTime()));
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    mContent.setAlpha(DELETED_ALPHA);
                    entity.setDone(Boolean.TRUE);
                    mCheckBox.setClickable(false);
                }
                mContent.invalidate();
            }
        });

    }

    private String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        if (listener != null) {
            mCheckBox.setOnClickListener(listener);
            mDelete.setOnClickListener(listener);
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        if (listener != null) {
            mCheckBox.setOnLongClickListener(listener);
            mDelete.setOnLongClickListener(listener);
        }
    }
}
