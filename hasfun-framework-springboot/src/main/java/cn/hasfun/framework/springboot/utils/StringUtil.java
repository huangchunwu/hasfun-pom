package cn.hasfun.framework.springboot.utils;

public class StringUtil {

    public static StringUtil instance =  new StringUtil();


    public boolean isBlank(String str){
        if (str == null){
            return  true;
        }
        if (str.length()==0){
            return true;
        }
        return false;
    }
}
