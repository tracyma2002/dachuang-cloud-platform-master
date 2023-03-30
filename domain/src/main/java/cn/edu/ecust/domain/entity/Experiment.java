package cn.edu.ecust.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 8:55 2022/6/25
 **/
@Data
public class Experiment {
    private int  experimentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date experimentReportEndTime;

    private String experimentName;
    private String experimentGuideBookUrl;
    private int  isUnmodifiable;
    private int orderNumber;
    private int experimentGuideBookUrlId;


    private  String experimentReport;
    private  int experimentReportId;
//    private int


}
