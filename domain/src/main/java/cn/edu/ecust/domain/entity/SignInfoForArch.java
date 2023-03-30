package cn.edu.ecust.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 15:08 2022/8/31
 **/
//归档课程的签到信息
@Data
public class SignInfoForArch {
    @ExcelProperty("学号")
    private String userId;
    @ExcelProperty("姓名")
    private String userName;
    @ExcelProperty("实验名称")
    private String experimentName;
    @ExcelProperty("是否签到")
    private int isSignin;
    @ExcelProperty("签到时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date signinTime;
}
