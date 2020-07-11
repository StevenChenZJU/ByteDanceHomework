package com.bytedance.androidcamp.network.lib.util;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bytedance.androidcamp.network.lib.R;

public class ImageHelper {
    public static void displayWebImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.loading_image).into(imageView);
    }
}
