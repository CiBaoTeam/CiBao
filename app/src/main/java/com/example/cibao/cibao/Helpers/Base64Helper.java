package com.example.cibao.cibao.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Base64;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 屈彬
 * 2017/4/8
 * Base64编码转换助手
 */
public class Base64Helper {
    /**
     * @show 图片格式
     */
    public static final Bitmap.CompressFormat ImageFormat = Bitmap.CompressFormat.JPEG;
    /**
     * @show 图片质量
     */
    public static final int ImageQuality = 100;

    /**
     * @show 将图片转为BASE64编码
     * @param bitmap 图片
     * @return BASE64编码
     */
    public static String getBase64CodeFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream out = null;
        try{
            out = new ByteArrayOutputStream();
            bitmap.compress(ImageFormat, ImageQuality, out);
            out.flush();
            out.close();
            return Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);
        }catch (Exception e){
            Log.e("getBase64CodeFromImage", e.toString());
            return null;
        }finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @show 从BASE64编码中解析图片
     * @param base64Code BASE64编码
     * @return 位图
     */
    public static Bitmap getBitmapFromBase64Code(String base64Code){
        byte[] base64Byte = Base64.decode(base64Code, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(base64Byte, 0, base64Byte.length);
    }
}
