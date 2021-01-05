package com.example.management_system.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class XUtil {
    private static Application mApplicationContext;
    private static ProgressDialog progressDialog;

    @SuppressLint("StaticFieldLeak")


    public static void initialize(Application app) {
        mApplicationContext = app;
    }

    public static Application getApplication() {
        return mApplicationContext;
    }

    public static void showLoading(String msg) {
        progressDialog = ProgressDialog.show(ActivityUtil.getCurrentActivity(), "", msg, true, true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static String getString(int id) {
        return getApplication().getResources().getString(id);
    }

    public static int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getApplication().getResources().getColor(id, getApplication().getTheme());
        } else {
            //noinspection deprecation
            return getApplication().getResources().getColor(id);
        }

    }

    public static void showLoading(Activity activity, String msg) {
        progressDialog = ProgressDialog.show(activity, "", msg, true, true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void closeSoftKeyboard() {
        InputMethodManager inputManger = (InputMethodManager) ActivityUtil.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManger != null) {
            inputManger.hideSoftInputFromWindow(ActivityUtil.getCurrentActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    /**
     * 实现文本复制功能
     *
     * @param content 复制的文本
     */
    public static void copy(String content) {
        if (!TextUtils.isEmpty(content)) {
            // 得到剪贴板管理器
            ClipboardManager clipboard = (ClipboardManager) ActivityUtil.getCurrentActivity().
                    getSystemService(Context.CLIPBOARD_SERVICE);
            //参数一：标签  参数二：要复制到剪贴板的文本
            ClipData clip = ClipData.newPlainText("copy text", content);
            clipboard.setPrimaryClip(clip);
        }
    }
}
