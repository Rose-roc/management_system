package com.example.management_system.utils.glide.progress;

import androidx.annotation.WorkerThread;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2018/9/17
 */
public interface OnProgressListener {
    /**
     * 加载进度的监听
     *
     * @param progress 加载进度
     */
    @WorkerThread
    void onProgress(float progress);
}
