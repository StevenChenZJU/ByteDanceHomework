package com.example.hw7imagevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageActivity extends AppCompatActivity {

    private final static String IMAGE_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg";
    private final static String IMAGE_URL2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963755&di=f4aa87b95c87dc01ff0ca2c9150845c8&imgtype=0&src=http%3A%2F%2Fwww.uimaker.com%2Fuploads%2Fallimg%2F121105%2F1_121105084854_2.jpg";
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mImageView = findViewById(R.id.iv_image);
        loadingImage();
    }

    private void loadingImage() {
        RequestOptions cropOptions = new RequestOptions();
        cropOptions = cropOptions.circleCrop();
        Glide.with(ImageActivity.this)
                .load(IMAGE_URL)
                .apply(cropOptions)
                .placeholder(R.drawable.icon_progress_bar)
                .error(R.drawable.icon_failure)
                .fallback(R.drawable.ic_launcher_background)
                .thumbnail(Glide.with(this).load(IMAGE_URL2))
                .transition(withCrossFade(4000))
                .into(mImageView);
    }
}