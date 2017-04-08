package com.example.cibao.cibao.Helpers;

import android.graphics.Bitmap;
import android.graphics.Color;
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
         * @show 缩放图片
         * @param bitmap 位图
         * @param w 宽度
         * @param h 高度
         * @return 新图片
         */
        public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
            Bitmap BitmapOrg = bitmap;
            int width = BitmapOrg.getWidth();
            int height = BitmapOrg.getHeight();
            int newWidth = w;
            int newHeight = h;

            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // if you want to rotate the Bitmap
            // matrix.postRotate(45);
            Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                    height, matrix, true);
            return resizedBitmap;
        }
    }
}
