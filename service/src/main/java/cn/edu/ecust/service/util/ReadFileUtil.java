package cn.edu.ecust.service.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: Jiaming Zheng
 * @Date: Create in 16:36 2022/8/28
 * @Modified By:
 **/
public class ReadFileUtil {

    public Reader readResourceFile(String resourceFileName){
        Resource resource = new ClassPathResource(resourceFileName);
        InputStream is = null; // 文件在jar包中时，需要通过流来获取文件内容
        try {
            is = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader isr = new InputStreamReader(is);
        return isr;
    }
}
