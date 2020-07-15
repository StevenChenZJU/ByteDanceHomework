package com.example.hw7imagevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mButtonImage;
    private Button mButtonVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonImage = findViewById(R.id.bt_image);
        mButtonVideo = findViewById(R.id.bt_video);
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bt_image:
                Intent intent0 = new Intent(this, ImageActivity.class);
                startActivity(intent0);
                break;
            case R.id.bt_video:
                Intent intent1 = new Intent(this, VideoActivity.class);
                startActivity(intent1);
                break;
        }
    }
}