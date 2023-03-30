package cn.edu.ecust.service.user;

import cn.edu.ecust.common.exception.AppException;
import cn.edu.ecust.dao.mapper.FileMapper;
import cn.edu.ecust.dao.mapper.SysMapper;
import cn.edu.ecust.dao.mapper.UserMapper;
import cn.edu.ecust.domain.entity.Files;
import cn.edu.ecust.domain.entity.Permission;
import cn.edu.ecust.domain.entity.Role;
import cn.edu.ecust.domain.entity.User;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @author: mengyang zhu
 * @Date: Create in 11:40 2022/6/12
 **/

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysMapper sysMapper;

    @Autowired
    private FileMapper fileMapper;

    /**
     * @Description:增加用户（通过user类）
     * @Param:
     * @Return:
     **/
    public void createUser(User user){

        userMapper.createUser(user);
    }
    /**
     * @Description:增加用户（通过userId、password和type）
     * @Return:
     **/
    public void addUser(String userId,String password,int type){
        userMapper.addUser(userId,password,type);
    }

    /**
     * @Description: 获取所有用户
     * @Param: []
     * @Return:
     **/
    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }
    /**
     * @Description:获取指定用户
     * @Return:
     **/
    public User getUserByUserId(String userId){return userMapper.getUserByUserId(userId);}

    /**
     * @Description: 通过type获取用户信息
     * @Return:
     **/
    public List<User> getAllUsersByType(String type){
        return userMapper.getAllUsersByType(type);
    }

    /**
     * @Description:通过id查询type
     * @Return:
     **/
    public int getTypeById(String userId){
        int type = userMapper.getTypeById(userId);
        return type;
    }
    /**
     * @Description:
     * @Return:
     **/
    public Integer checkUserId(String userId){
        return userMapper.findCode(userId);
    }
    /**
     * @Description: 获取密码
     * @Return:
     **/
    public String getPasswordByUserId(String userId){
        String password = userMapper.getPasswordByUserId(userId);
        return  password;
    }

    public String getUserNameByUserId(String userId){
        return userMapper.getUserNameByUserId(userId);
    }
    public String getUserIdByUserName(String userId){
        return userMapper.getUserIdByUserName(userId);
    }


    public Role getRoleByUserId(String userId){
        Integer type = userMapper.getTypeById(userId);
        return userMapper.getRoleByUserId(type);
    }


    public List<Permission> getPermissionListByRoleId(Integer roleId){
        return sysMapper.getPermissionsByRoleId(roleId);
    }

    public List<User> getAllUserByCourseId(int courseId){
        List<User> users = new ArrayList<>();
        //首先获取该课程的所有学生Id
        List<String> studentIds = userMapper.getStudentIdByCourseId(courseId);

        for (String studentId:studentIds) {
            User user = userMapper.getUserByUserId(studentId);
            users.add(user);
        }

        return users;
    }

    public void updateUser(User user){
        userMapper.updateUser(user);

    }
    //修改头像
    public  String uploadUserImg(String userId, MultipartFile multipartFile) throws IOException {
        //附件
        if (multipartFile == null){
            throw new AppException("实验图片为空");
        }
        //文件名
        String fileName = multipartFile.getOriginalFilename();
        //文件大小
        long fileSize = multipartFile.getSize();
        //文件类型
        String fileType = multipartFile.getContentType();

        assert fileType != null; //判断文件类型正确性
        if (!fileType.equals("image/jpeg")&&!fileType.equals("image/png")){
            throw new AppException("请上传.jpg/.jpe/.jpeg/.png格式的文件！");
        }
        String fileNewName = fileName.substring(0, fileName.lastIndexOf(".")) +       //不含后缀的原文件名
                (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date()) +//当前时间
                (new Random().nextInt(100))+//随机数
                fileName.substring(fileName.lastIndexOf("."));
        //System.out.println(fileNewName);
        //获取课程名称
        //String courseName = courseService.getCourseName(courseId);
        String fPath = "/home/img/";

        File upload = new File(fPath+fileNewName);
        //文件夹不存在新建文件夹
        if(!upload.getParentFile().exists()) {
            upload.getParentFile().mkdirs();
        }
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),upload);
        //添加文件信息
        Files files = new Files();
        files.setFileName(fileName);
        files.setFileSize((int) fileSize);
        files.setFileObjectId(fileNewName);
        files.setFileType(fileType);

        //System.out.println(files);
        fileMapper.createFile(files);
        //修改user表
        userMapper.updateUserImg(userId,fPath+fileNewName);

        return fPath+fileNewName;
    }

}
