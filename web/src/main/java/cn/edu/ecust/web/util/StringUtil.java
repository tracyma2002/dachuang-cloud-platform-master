package cn.edu.ecust.web.util;

import java.util.Locale;

/**
 * @Description:
 * @author: Jiaming Zheng
 * @Date: Create in 17:01 2022/9/17
 * @Modified By:
 **/
public class StringUtil {


    public static String getUserCourseName(String userId, int courseId){

        return  (userId+"-"+courseId).toLowerCase(Locale.ROOT);

    }
}
