package com.example.cibao.cibao.Helpers;

import android.graphics.Bitmap;

import android.graphics.Matrix;

/**
 * 屈彬
 * 2017/4/8
 * 图片处理助手
 */
public class BitmapHelper {
    /**
     * 屈彬
     * 2016/11/6
     * e瞳网作业
     * 位图矩阵
     */
    public static class BitmapMatrix {

        /**
         * @show 单词图片统一尺寸
         */
        public static int BitmapSize = 100;
        /**
         * @show 缩放图片
         * @param bitmap 位图
         * @param w 宽度
         * @param h 高度
         * @return 新图片
         */
        public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            float scaleWidth = ((float) w) / width;
            float scaleHeight = ((float) h) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            return Bitmap.createBitmap(bitmap, 0, 0, width,
                    height, matrix, true);
        }
    }
}
