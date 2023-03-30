package cn.edu.ecust.dao.mapper;

import cn.edu.ecust.domain.entity.Resource;
import cn.edu.ecust.domain.entity.ResourceInfo;
import cn.edu.ecust.domain.entity.ResourceInfoTeacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: Fen Min
 * @Date: Create in 12:18 2022/7/13
 **/
@Mapper
public interface ResourceMapper {


    //用作上传资源成功后的检测
    @Select("select * from resource where resource_id = #{resourceId}")
    Resource getResourceById(int resourceId);

    //老师或者学生通过courseID获取的信息一样，但是设置了两个实体类，如果有不一样方便修改
    /**
     * @Description:学生通过课程Id查询资源,
     * @Return:
     **/
    @Select("select r.resource_id,resource_name,resource_upload_time,resource_url from resource r" +
            " left join course_resource cr on cr.resource_id = r.resource_id" +
            " left join student_courses sc on sc.course_id=cr.course_id" +
            " where sc.student_id=#{userId} and cr.course_id=#{courseId}")
    List<ResourceInfo> getResourceInfo(int courseId, String userId);

    /**
     * 教师通过courseId和teacherId查询这门课有哪些资源
     * @param courseId
     * @param teacherId
     * @return
     */
    @Select("SELECT DISTINCT r.resource_id,r.resource_name,r.resource_upload_time,r.resource_url , r.description from resource r " +
            "LEFT JOIN course_resource cr on cr.resource_id=r.resource_id " +
            "LEFT JOIN teacher_courses tc on tc.course_id=cr.course_id " +
            "where tc.teacher_id=#{teacherId} and cr.course_id=#{courseId}")
    List<ResourceInfoTeacher> getResourceInfoTeacher(int courseId, String teacherId);

    /**
     * 新建资源
     * 将表中的主键属性改成自增，这样每插入一条记录id会自动添加
     * @param resourceName
     * @param resourceUrl
     */
//    @Insert("insert into resource(resource_name,resource_url,resource_upload_time,file_id)"+
//    "values(#{resourceName},#{resourceUrl},#{resourceUploadTime},#{fileId})")
//    int addResource(String resourceName, String resourceUrl,Date resourceUploadTime,int fileId);
    @Insert("insert into resource(resource_name,resource_url,resource_upload_time,file_id,description)"+
            "values(#{resourceName},#{resourceUrl},#{resourceUploadTime},#{fileId},#{description})")
    int addResource(String resourceName, String resourceUrl,Date resourceUploadTime,int fileId,String description);
    /**
     * 为什么要获取 ：插入到course_resource表的时候需要resource_id
     * 获取实验Id   PS:如果insert能自动返回自增主键就不需要这样获取了，用mapper。xml应该能实现，但是用注解不好实现
     * @param resourceName
     * @param resourceUrl
     * @return
     */
    @Select("select resource_id from resource where resource_name=#{resourceName} and resource_url=#{resourceUrl}")
    int getResourceId(String resourceName,String resourceUrl);

    /**
     * 检查本课程中 资源名称是否重复
     * @param resourceName
     * @param courseId
     * @return
     */
    @Select("select count(*) from course_resource cr " +
            "left join  resource r on cr.resource_id=r.resource_id " +
            "where course_id=#{courseId} and resource_name=#{resourceName}")
    int checkResourceName(String resourceName,int courseId);

    /**
     * 在course_resource中添加对应关系
     * @param courseId
     * @param resourceId
     */
    @Insert("insert into course_resource(course_id,resource_id) values(#{courseId},#{resourceId})")
    void addResourceInCR(int courseId,int resourceId);

    /**
     * 删除course_resource表中的资源
     * @param resourceId
     */
    @Delete("delete  from course_resource where resource_id = #{resourceId}")
    void deleteCourseResourceById(int resourceId);

    /**
     * 删除resource表中的资源
     * @param resourceId
     */
    @Delete("delete from resource where resource_id = #{resourceId}")
    void deleteResourceById(int resourceId);





}
