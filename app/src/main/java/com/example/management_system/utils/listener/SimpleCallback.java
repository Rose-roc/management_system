package com.example.management_system.utils.listener;

public interface SimpleCallback<E> {
    /**
     * 回调结果
     * @param data 回调的数据
     */
    void onResult(E data);
}
