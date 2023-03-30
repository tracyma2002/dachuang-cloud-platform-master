package cn.edu.ecust.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 18:57 2022/6/30
 **/
@Data
public class HomeworkInfoTeacher {
    private int homeworkId;
    private String homeworkName;
    private String FileName;

    private String homeworkQuestionUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date homeworkEndTime;

    private int totalNum;

    private int submittedNum;
}
