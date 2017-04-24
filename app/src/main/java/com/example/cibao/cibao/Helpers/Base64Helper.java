package com.example.cibao.cibao.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
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
    public static int Width = BitmapHelper.BitmapMatrix.BitmapSize;
    public static int Height = BitmapHelper.BitmapMatrix.BitmapSize;
    public static final int ByteLength = 8;

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
    // 位图转存为数组
    public static int[][] getMatrixFromBitmap(Bitmap bitmap){
        int[][] matrix = new int[bitmap.getWidth()][bitmap.getHeight()];
        for(int row = 0;row < bitmap.getHeight();row++){
            for(int col = 0;col < bitmap.getWidth();col++){
                matrix[col][row] = bitmap.getPixel(col, row);
            }
        }
        return matrix;
    }
    // 矩阵转存为位图
    public static Bitmap getBitmapFromMatrix(int[][] matrix){
        if(matrix == null)return null;
        Bitmap bitmap = Bitmap.createBitmap(matrix.length, matrix[0].length, Bitmap.Config.ARGB_8888);
        for(int row = 0;row < bitmap.getHeight();row++){
            for(int col = 0;col < bitmap.getWidth();col++){
                bitmap.setPixel(col, row, matrix[col][row]);
            }
        }
        return bitmap;
    }
    // 哔了狗了， 数据库不能识别二维数组，所以这里要采用降低维度的方法
    // 自定义图片串行编码
    public static String EncodeMatrixToString(int[][] matrix){
        if(matrix == null)return "";
        // 约定每8位字符位一个矩阵元素的信息
        // 约定矩阵宽度为60，高度为60
        String result = "";
        for(int row = 0;row < Height;row++){
            for(int col = 0;col < Width;col++){
                result += Integer.toHexString(matrix[col][row]);// 转成16进制数
            }
        }
        return result;
    }
    // 解码
    public static int[][] DecodeStringToMatrix(String string){
        int[][] matrix = new int[Width][Height];
        int string_index = 0;
        for(int row = 0;row < Height;row++){
            for(int col = 0;col < Width;col++){
                String cache = "#" + string.charAt(string_index) + "";// 截取8位字符
                ++string_index;
                while(string_index % ByteLength != 0){
                    cache += string.charAt(string_index);
                    ++string_index;
                }
                matrix[col][row] =  Color.parseColor(cache);// 再转成10进制整数
            }
        }
        return matrix;
    }
}
