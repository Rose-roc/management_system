package com.example.management_system.utils;

import com.example.management_system.R;
import com.example.management_system.base.BaseException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

public class ExceptionUtil {
    public static String parseException(Throwable e) {
        BaseException be;
        String errorMsg;
        if (e != null) {
            //自定义异常
            if (e instanceof BaseException) {
                be = (BaseException) e;
            } else {
                if (e instanceof HttpException) {
                    //HTTP错误
                    be = new BaseException(XUtil.getString(R.string.BAD_NETWORK_MSG), e);
                } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    //连接错误
                    be = new BaseException(XUtil.getString(R.string.CONNECT_ERROR_MSG), e);
                } else if (e instanceof InterruptedIOException) {
                    //连接超时
                    be = new BaseException(XUtil.getString(R.string.CONNECT_TIMEOUT_MSG), e);
                } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                    //解析错误
                    be = new BaseException(XUtil.getString(R.string.PARSE_ERROR_MSG), e);
                } else {
                    be = new BaseException(XUtil.getString(R.string.OTHER_MSG), e);
                }
            }
        } else {
            be = new BaseException(XUtil.getString(R.string.OTHER_MSG));
        }
        errorMsg = be.getMessage();
        return errorMsg;
    }

    public static Throwable parseException2(Throwable e) {
        BaseException be;
        if (e != null) {
            //自定义异常
            if (e instanceof BaseException) {
                be = (BaseException) e;
            } else {
                if (e instanceof HttpException) {
                    //HTTP错误
                    be = new BaseException(XUtil.getString(R.string.BAD_NETWORK_MSG), e);
                } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    //连接错误
                    be = new BaseException(XUtil.getString(R.string.CONNECT_ERROR_MSG), e);
                } else if (e instanceof InterruptedIOException) {
                    //连接超时
                    be = new BaseException(XUtil.getString(R.string.CONNECT_TIMEOUT_MSG), e);
                } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                    //解析错误
                    be = new BaseException(XUtil.getString(R.string.PARSE_ERROR_MSG), e);
                } else {
                    be = new BaseException(XUtil.getString(R.string.OTHER_MSG), e);
                }
            }
        } else {
            be = new BaseException(XUtil.getString(R.string.OTHER_MSG));
        }
        return be;
    }
}
