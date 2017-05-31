package com.kysfw.www.sfw.utils;

/**
 * Created by manbas on 17-5-31.
 */

public class BaseUtils {
    public static String calculationPrice(float price){
        String priceStr="";
        if(price<10000){
            priceStr=price+"元";
        }else{
            priceStr=(price/10000)+"万元";
        }
        return priceStr;
    }
}
