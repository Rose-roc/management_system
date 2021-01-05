package com.example.management_system.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

public class ImageUtils {
    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        String fileName = System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            // 首先保存图片;
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/鲜果蔬");
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            try {

                //保存图片后发送广播通知更新数据库
                OutputStream out = contentResolver.openOutputStream(uri);
                boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, out);
                out.flush();
                out.close();
                if (isSuccess) {
                    ToastUtil.showToast("图片已成功保存到 sdcard/Pictures/鲜果蔬/"+fileName);
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }else{
            saveImageToGalleryAndroid9(context,bmp);
            return true;
        }

    }

    //保存文件到指定路径
    public static boolean saveImageToGalleryAndroid9(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_PICTURES+"/鲜果蔬";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            MediaScannerConnection.scanFile(XUtil.getApplication(), new String[]{file.getPath()}, new String[]{"image/jpeg"}, (path, uri) -> {
                ToastUtil.showToast("图片已成功保存到" + path);
            });
//            Uri uri = Uri.fromFile(file);
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //保存图片
    public static void scanFile(File file, Context context, Bitmap bmp) {
        String mimeType = getMimeType(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String fileName = file.getName();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) {
                ToastUtil.showToast("图片保存失败");
                return;
            }
            try {
                OutputStream out = contentResolver.openOutputStream(uri);
                boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, out);
                out.flush();
                out.close();
                if (true)
                    ToastUtil.showToast("图片保存成功");
                else
                    ToastUtil.showToast("图片保存失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MediaScannerConnection.scanFile(XUtil.getApplication(), new String[]{file.getPath()}, new String[]{mimeType}, (path, uri) -> {
                ToastUtil.showToast("图片已成功保存到" + path);
            });
        }
    }


    public static String getMimeType(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(file.getName());
        return type;
    }
}
