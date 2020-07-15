package com.example.hw7imagevideo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TimeUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hw7imagevideo.player.VideoPlayerIJK;
import com.example.hw7imagevideo.player.VideoPlayerListener;

import java.sql.Time;
import java.text.SimpleDateFormat;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class
VideoActivity extends AppCompatActivity {
    private VideoPlayerIJK mIjkPlayer;
    private ImageButton mVideoButton;
    private SeekBar mSeekBar;
    private TextView mTime;
    private long mVideoLength;
    private boolean isPlaying = false;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private TextView mLoadingHint;
    private Handler mHandler;
    private static final String TAG = "VideoActivity";
//    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTitle("ijkPlayer");

        mIjkPlayer = findViewById(R.id.ijk_player);
        mVideoButton = findViewById(R.id.video_button);
        mSeekBar = findViewById(R.id.seek_bar);
        mTime = findViewById(R.id.tv_time);
        mLoadingHint = findViewById(R.id.tv_loading);
        mHandler = new Handler(Looper.getMainLooper());
//        mProgressBar = findViewById(R.id.pb_video);
//        mProgressBar.setVisibility(View.VISIBLE);

        //加载native库
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        mIjkPlayer.setListener(new VideoPlayerListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
//        iMediaPlayer.start();
//                mProgressBar.setVisibility(View.GONE);
                mLoadingHint.setVisibility(View.GONE);
                mVideoLength = mIjkPlayer.getDuration();
                mTime.setText(getTime(getCurrentPosition()));
                mVideoButton.setClickable(true);
                mSeekBar.setClickable(true);
                new Thread() {
                    @Override
                    public void run() {
                        if (mIjkPlayer != null) {
                            mSeekBar.setProgress(getCurrentProgress());
                            mTime.setText(getTime(mIjkPlayer.getCurrentPosition()));
                            Log.d(TAG, "Run Once" + "; Progess" + mSeekBar.getProgress() + ";Time" + getTime(getCurrentPosition()));
                        }
                        mHandler.postDelayed(this, 1000);
                    }
                }.start();
            }
        });
        //ijkPlayer.setVideoResource(R.raw.bytedance);
        mIjkPlayer.setVideoPath(getVideoPath());


        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    mIjkPlayer.start();

                    mVideoButton.setImageResource(R.drawable.stop_button);
                    isPlaying = true;
                }
                else {
                    mIjkPlayer.pause();
                    mVideoButton.setImageResource(R.drawable.start_button);

                    isPlaying = false;
                }

            }
        });
        mVideoButton.setClickable(false);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long cur = getCurrentPosition();
                mIjkPlayer.seekTo(cur);
                mTime.setText(getTime(cur));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mIjkPlayer.pause();
                mVideoButton.setImageResource(R.drawable.start_button);
                isPlaying = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long cur = getCurrentPosition();
                mIjkPlayer.seekTo(cur);
                mSeekBar.setProgress(getCurrentProgress());
                mTime.setText(getTime(getCurrentPosition()));
            }
        });
        mSeekBar.setClickable(false);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
    }

    private String getVideoPath() {
        return "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8";
//        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIjkPlayer.isPlaying()) {
            mIjkPlayer.stop();
        }

        IjkMediaPlayer.native_profileEnd();
    }

    private String getTime(long cur) {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(cur) + "\n/" + simpleDateFormat.format(mVideoLength) ;
    }

    private long getCurrentPosition() {
        long cur = mVideoLength * mSeekBar.getProgress() / mSeekBar.getMax();
        return cur;
    }

    private int getCurrentProgress() {
        int cur = (int) (mIjkPlayer.getCurrentPosition() * mSeekBar.getMax() / mVideoLength );
        return cur;
    }
}