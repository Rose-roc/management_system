package com.example.management_system.utils;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.management_system.R;


public class EmptyViewUtil {
    /**
     * 得到网络错误的界面
     *
     * @param recyclerView rv
     * @return 网络错误的界面
     */
    public static View getErrorView(RecyclerView recyclerView) {
        return getView(R.layout.error_view, recyclerView);
    }

    /**
     * 得到空数据界面
     *
     * @param recyclerView rv
     * @return 空数据界面
     */
    public static View getEmptyDataView(RecyclerView recyclerView) {
        return getView(R.layout.empty_view, recyclerView);
    }


    private static View getView(int layout, RecyclerView recyclerView) {
        return ActivityUtil.getCurrentActivity().getLayoutInflater().inflate(layout, recyclerView, false);
    }
}
