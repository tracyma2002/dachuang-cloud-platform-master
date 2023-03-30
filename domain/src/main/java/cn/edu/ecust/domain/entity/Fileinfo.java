package cn.edu.ecust.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fileinfo extends BaseEntity{

    private Integer fileId;

    private String filePath;


}
