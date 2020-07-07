package com.example.recyclerviewhw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        int position =getIntent().getIntExtra("item_number", -1);
        Toast.makeText(ChatActivity.this, "点击了第" + position + "个item", Toast.LENGTH_SHORT).show();
    }
}