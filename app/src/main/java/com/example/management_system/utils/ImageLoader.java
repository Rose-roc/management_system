package com.example.management_system.utils;


import android.widget.ImageView;
import com.example.management_system.R;
import com.example.management_system.utils.glide.GlideHelper;
import org.jetbrains.annotations.NotNull;
import java.io.File;

public class ImageLoader {


    /**
     * 通过 url 加载图片
     *
     * @param imageView imageView
     * @param url       url
     * @param isCache   是否缓存
     */
    public static void image(ImageView imageView, String url, boolean isCache) {
        GlideHelper.with(imageView.getContext())
                .errorHolder(R.drawable.ic_image_holder)
                .placeHolder(R.drawable.ic_image_holder)
                .cache(isCache)
                .load(url)
                .into(imageView);
    }


    /**
     * 通过 url 加载 drawable 图片
     *
     * @param imageView iv
     * @param url       url
     */
    public static void image(ImageView imageView, @NotNull String url) {
        GlideHelper.with(imageView.getContext())
                .errorHolder(R.drawable.ic_image_holder)
                .placeHolder(R.drawable.ic_image_holder)
                .cache(true)
                .load(url)
                .into(imageView);
    }


    /**
     * 通过 url 加载 File
     *
     * @param imageView iv
     * @param file       file
     */
    public static void image(ImageView imageView, File file) {
        GlideHelper.with(imageView.getContext())
                .errorHolder(R.drawable.ic_image_holder)
                .placeHolder(R.drawable.ic_image_holder)
                .cache(true)
                .load(file)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param imageView imageView
     * @param res       资源文件
     */
    public static void loadLocalIcon(ImageView imageView, int res) {
        GlideHelper.with(imageView.getContext())
                .load(res)
                .into(imageView);
    }


    /**
     * 通过 url 加载 gif 图片
     *
     * @param imageView iv
     * @param url       url
     */
    public static void gif(ImageView imageView, String url) {
        GlideHelper.with(imageView.getContext())
                .asGif()
                .errorHolder(R.drawable.ic_image_holder)
                .placeHolder(R.drawable.ic_image_holder)
                .cache(true)
                .load(url)
                .into(imageView);
    }
}
