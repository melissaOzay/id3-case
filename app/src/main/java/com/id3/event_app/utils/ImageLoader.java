package com.id3.event_app.utils;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public final class ImageLoader {

    public static void load(ImageView imageView, @Nullable String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(imageView);
    }

    public static void loadCircle(ImageView imageView, @Nullable String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .circleCrop()
                .into(imageView);
    }

    public static void loadCircle(ImageView imageView, @Nullable String url, @DrawableRes int placeholder) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(placeholder)
                .circleCrop()
                .into(imageView);
    }
}
