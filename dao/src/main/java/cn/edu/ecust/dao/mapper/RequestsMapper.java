package cn.edu.ecust.dao.mapper;

import cn.edu.ecust.domain.entity.Requests;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RequestsMapper {

    @Insert("insert into requests(user_course_id,key_data,request_type,request_state,created_at) " +
            "values(#{userCourseId}, #{keyData}, #{requestType}, #{requestState} ,#{createdAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void addRequests(Requests requests);

    @Select("select * from requests where key_data = #{keyData} and deleted = 0")
    Requests getByKeyData(String keyData);

    @Select("select * from requests where id = #{id} and deleted = 0")
    Requests getById(int id);

    @Select("select * from requests where user_id = #{userCourseId} and deleted = 0 ")
    List<Requests> getUnFinishedRequests(int userCourseId);

    @Update({"<script>",
            "update requests set updated_at = sysdate() ",
            "<if test=\"requestState != null and requestState != ''\">",
            ",request_state = #{requestState} ",
            "</if>",
            "<if test=\"deletedAt != null\">",
            ",deleted_at = #{deletedAt} ",
            "</if>",
            "<if test=\"deleted != null and deleted != ''\">",
            ",deleted = #{deleted} ",
            "</if>",
            "WHERE ID=#{id} ",
            "</script>"
    })
    void updateRequests(Requests requests);
}
