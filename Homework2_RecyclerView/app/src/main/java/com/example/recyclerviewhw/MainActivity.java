package com.example.recyclerviewhw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recyclerviewhw.recycler.LinearItemDecoration;
import com.example.recyclerviewhw.recycler.MyAdapter;
import com.example.recyclerviewhw.recycler.MessageData;
import com.example.recyclerviewhw.recycler.TestDataSet;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAdapter.IOnItemClickListener {

    private static final String TAG = "TAG";

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    private List<MessageData> messageList;

    private int viewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "MainActivity onCreate");
        initView();
    }

    private void initView() {
//        findViewById(R.id.btn_framelayout).setOnClickListener(this);
//        findViewById(R.id.btn_linearlayout).setOnClickListener(this);
//        findViewById(R.id.btn_relativelayout).setOnClickListener(this);
//        findViewById(R.id.btn_ui_widget).setOnClickListener(this);
//        findViewById(R.id.btn_recyclerview).setOnClickListener(this);
//        findViewById(R.id.btn_intent).setOnClickListener(this);
//        findViewById(R.id.btn_intent_for_result).setOnClickListener(this);

        // for Recycler View
        recyclerView = findViewById(R.id.recycler_vertical);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        messageList = TestDataSet.getData();

        mAdapter = new MyAdapter(messageList);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(3000);
//        recyclerView.setItemAnimator(animator);

        // counting views
        ViewCounter viewCounter = new ViewCounter();
        viewCount = viewCounter.countView(findViewById(android.R.id.content).getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "MainActivity onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "MainActivity onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MainActivity onDestroy");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Toast.makeText(this, "View Count: " + this.viewCount, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_comment:
                Toast.makeText(this, "Push Comment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_fans:
                Toast.makeText(this, "Push Fans", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_like:
                Toast.makeText(this, "Push Like", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_mine:
                Toast.makeText(this, "Push Mine", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tx_contact:
                Toast.makeText(this, "Push Contact", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tx_frontpage:
                Toast.makeText(this, "Push Frontpage", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tx_message:
                Toast.makeText(this, "Push message", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tx_me:
                Toast.makeText(this, "Push Me", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tx_subscribe:
                Toast.makeText(this, "Push Subscribe", Toast.LENGTH_LONG).show();
                break;
        }
    }


    // For RecyclerView Message Array

    @Override
    public void onItemCLick(int position, MessageData data) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("item_number", position);
        startActivity(intent);
    }

    @Override
    public void onItemLongCLick(int position, MessageData data) {


    }
}