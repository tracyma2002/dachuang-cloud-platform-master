package cn.edu.ecust.domain.entity;

import lombok.Data;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 16:34 2022/6/20
 **/
@Data
public class Role extends BaseEntity{
    private Integer roleId;
    private String roleName;
    private String description;
    private Integer type;
}
