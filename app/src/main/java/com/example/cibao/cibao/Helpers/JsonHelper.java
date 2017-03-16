package com.example.cibao.cibao.Helpers;

import com.google.gson.Gson;

/**
 * 屈彬
 * 2017/3/16
 * Json报文辅助类
 */
public class JsonHelper {
    /**
     * @show 将对象转化为Json报文
     * @param object 待转换的对象
     * @return Json报文
     */
    public static String getJsonStringFromObject(Object object){
        return new Gson().toJson(object);
    }

    /**
     * @show 将Json报文转换为对象
     * @param serial Json报文
     * @param c 对象类型
     * @return 对象
     */
    public static Object getObjectFromJsonString(String serial, Class c){
        Gson gson=new Gson();
        return  gson.fromJson(serial, c);
    }
}
