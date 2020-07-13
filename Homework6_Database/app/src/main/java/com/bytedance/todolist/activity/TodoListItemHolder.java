package com.bytedance.todolist.activity;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;

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
    private final static float DELETED_ALPHA = 0.3F;
    private final static float NORMAL_ALPHA = 1F;

    public TodoListItemHolder(@NonNull View itemView) {
        super(itemView);
        mContent = itemView.findViewById(R.id.tv_content);
        mTimestamp = itemView.findViewById(R.id.tv_timestamp);
        mCheckBox = itemView.findViewById(R.id.cb_done);
    }

    public void bind(TodoListEntity entity) {
        mContent.setText(entity.getContent());
        mTimestamp.setText(formatDate(entity.getTime()));
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    mContent.setAlpha(DELETED_ALPHA);
                    mContent.invalidate();
                }
                else {
                    mContent.getPaint().setFlags(0);
                    mContent.setAlpha(NORMAL_ALPHA);
                    mContent.invalidate();
                }
            }
        });
    }

    private String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }


}
