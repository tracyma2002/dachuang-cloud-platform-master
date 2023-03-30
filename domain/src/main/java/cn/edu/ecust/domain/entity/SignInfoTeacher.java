package cn.edu.ecust.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 11:28 2022/7/15
 **/
@Data
public class SignInfoTeacher {
    private int experimentId;
    private String experimentName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date fromTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private int signNum;
    private int totalNum;


}
