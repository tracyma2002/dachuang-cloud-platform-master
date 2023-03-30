package cn.edu.ecust.domain;

import cn.edu.ecust.domain.entity.AutoScaler.AutoscalerFile;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: Jiaming Zheng
 * @Date: Create in 20:59 2022/9/17
 * @Modified By:
 **/
public class testmain {

    public static void main(String[] args) {
        AutoscalerFile dir1 = new AutoscalerFile("home",2);
        AutoscalerFile dir2 = new AutoscalerFile("zjm",2);
        AutoscalerFile dir3 = new AutoscalerFile("zmy",2);
//
        List<AutoscalerFile> fileList2 = new ArrayList<AutoscalerFile>();
        List<AutoscalerFile> fileList3 = new ArrayList<AutoscalerFile>();
        for (int i = 0; i < 10; i++) {
            AutoscalerFile file = new AutoscalerFile("zjmfile"+i,1);
            fileList2.add(file);
        }
        dir2.setFileList(fileList2);

        for (int i = 0; i < 10; i++) {
            AutoscalerFile file = new AutoscalerFile("zmyfile"+i,1);
            fileList3.add(file);
        }
        dir3.setFileList(fileList3);

        List<AutoscalerFile> fileList1 = new ArrayList<AutoscalerFile>();
        fileList1.add(dir2);
        fileList1.add(dir3);
        dir1.setFileList(fileList1);
//
//        System.out.println(dir1.toString());

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(dir1);
        System.out.println(jsonObject);//


    }
}
