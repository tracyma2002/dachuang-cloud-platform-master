package cn.edu.ecust.dao.mapper;

import cn.edu.ecust.domain.entity.Role;
import cn.edu.ecust.domain.entity.StudentInfo;
import cn.edu.ecust.domain.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description:
 * @Author: Mengyang zhu
 * @Date: Created in 12:20 2022/6/12
 */
public interface UserMapper {

    /**
     * @Description: 新增学生(通过user类)
     * @Return:
     **/
    @Insert("insert into user(user_id,user_name,password,gender,college,classer,phone,email,picture，type) \n" +
            "VALUES(#{student_id},#{student_name},#{password},#{gender},#{college},#{classer},#{phone},#{email},#{picture},#{type})")
    void createUser(User user);
    /**
     * @Description:
     * @Return:
     **/
    @Insert("insert into user(user_id,password,type) values(#{userId},#{password},#{type})")
    void addUser(String userId,String password,int type);

    /**
     * @Description: 查询所有用户
     * @Return:
     **/
    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where user_id = #{userId}")
    User getUserByUserId(String userId);

    /**
     * @return
     * @Description: 通过type查询所有用户
     */
    @Select("select * from user where type = #{type}")
    List<User> getAllUsersByType(String type);

    @Select("SELECT count(*) as cnt from user where user_id=#{studentId}")
    int getCntByUserId(String studentId);


    @Select("SELECT count(*) as cnt from student_courses where student_id=#{studentId} and course_id=#{courseId}")
    int getScCntByUserId(String studentId,int courseId);

    /**
     * @Description: 通过id和type查询有无此用户
     * @Return
     * @return*/

    @Select("select type from user where user_id=#{userId}")
    Integer getTypeById(String userId);

    /**
     * 根据学号查询学号数量，用于添加用户排出重复学号password
     * @param userId 学号；
     * @return 参数信息
     */
    @Select("select  count(*) from user where user_id=#{userId} ")
    Integer findCode(String userId);




    @Insert("INSERT INTO student_courses(student_id,course_id) VALUES(#{studentId},#{courseId})")
    void updatesc(int courseId,String studentId);


    @Select("select password from user  where  user_id = #{userId}")
    String getPasswordByUserId(String userId);

    @Select("select user_name from user  where  user_id = #{userId}")
    String getUserNameByUserId(String userId);

    @Select("select user_id from user where user_name=#{userName}")
    String getUserIdByUserName(String userName);

    @Select("select * from role where type = #{type}")
    Role getRoleByUserId(int type);

    @Select("select student_id from student_courses where course_id = #{courseId}")
    List<String> getStudentIdByCourseId(int courseId);

    @Select("select student_id,user_name from student_courses LEFT JOIN user on student_id=user_id JOIN archived_course on archived_course.course_id=student_courses.course_id where student_courses.course_id = #{courseId}")
    List<StudentInfo> getStudentInfoByCourseId(int courseId);

    @Update("update user set user_name=#{userName},password=#{password},gender=#{gender},college=#{college}," +
            "phone=#{phone},email=#{email},picture=#{picture} where user_id=#{userId}")
    void updateUser(User user);

    @Insert("INSERT INTO user(user_name,password,gender,college,phone,email,picture,type,user_id)  VALUES(#{userName},#{password},#{gender},#{college},#{phone},#{email},#{picture},#{type},#{userId})")
    void InsertUser(User user);

    @Update("update user set picture=#{imgPath} where  user_id=#{userId}")
    void updateUserImg(String userId,String imgPath);


}
