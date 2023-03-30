package cn.edu.ecust.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 学生查询实验需要的信息
 * @author: Mengyang zhu
 * @Date: Create in 9:16 2022/6/25
 **/
@Data
public class ExperimentInfo {
    private int experimentId;
    private String experimentName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date experimentReportEndTime;
    //实验报告，默认值是 未提交 ，学生提交后为文件路径
    private String experimentReportFileUrl;

//    //imgUrl
//    private String experimentImageUrl;
//    //图片文件名+随机数的String类型
//    private String fileObjectId;
//    //实验报告路径
//    private String experimentGuideBookUrl;
//    //实验报告文件id
//    private int experimentGuidebookId;

}
