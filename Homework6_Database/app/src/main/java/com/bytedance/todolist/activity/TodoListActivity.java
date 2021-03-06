package com.bytedance.todolist.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.bytedance.todolist.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {
    private static final String TAG = "TodoListActivity";
    private final static int ADD_TODO = 1;

    private TodoListAdapter mAdapter;
    private FloatingActionButton mFab;

    public void deleteEntity(final TodoListEntity entity) {
        new Thread() {
            @Override
            public void run() {
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                if(entity != null) {
                    dao.deleteTodo(entity);
                    Snackbar.make(mFab, R.string.hint_delete_todo, Snackbar.LENGTH_SHORT).show();
                }
            }
        }.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoListAdapter();
        mAdapter.setOnItemClickListener(new TodoListAdapter.OnItemClickListener() {

            @Override
            public void onItemCLick(View v, final int position, final TodoListEntity data) {
                switch (v.getId()) {
                    case R.id.bt_delete:
                        deleteEntity(data);
                        loadFromDatabase();
                        break;
                    case R.id.cb_done:
                        data.setDone(Boolean.TRUE);
                        TodoListItemHolder holder = (TodoListItemHolder) recyclerView
                                .getChildViewHolder((View) v.getParent());
                        if(holder != null) {
                            holder.setDone();
//                            recyclerView.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mAdapter.moveToEnd(data, position);
//                                }
//                            }, 500);
                            Log.d(TAG, "Update successfully");
                        }
                        else {
                            Log.d(TAG, "Holder is null!");
                        }
                        break;
                }
            }

            @Override
            public void onItemLongCLick(View v, int position, TodoListEntity data) {

            }
        });
        recyclerView.setAdapter(mAdapter);


        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Intent intent = new Intent(TodoListActivity.this, AddTodoActivity.class);
                startActivityForResult(intent, ADD_TODO);
            }
        });

        mFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                        dao.deleteAll();
                        for (int i = 0; i < 20; ++i) {
                            dao.addTodo(new TodoListEntity("This is " + i + " item", new Date(System.currentTimeMillis())));
                        }
                        Snackbar.make(mFab, R.string.hint_insert_complete, Snackbar.LENGTH_SHORT).show();
                    }
                }.start();

                return true;
            }
        });
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        new Thread() {
            @Override
            public void run() {
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                final List<TodoListEntity> entityList = dao.loadAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(entityList);
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "In onActivityResult(); Request Code: " + requestCode + ";Result Code:" + resultCode);
        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == ADD_TODO) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                        dao.addTodo(new TodoListEntity(data.getStringExtra(AddTodoActivity.TODO), new Date(System.currentTimeMillis())));
                        Snackbar.make(mFab, R.string.hint_insert_complete, Snackbar.LENGTH_SHORT).show();
                    }
                }.start();
                loadFromDatabase();
                Log.d(TAG, "Added Successfully");
            }
        }
    }


}
