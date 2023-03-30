package cn.edu.ecust.web.controller.users;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 13:26 2022/6/24
 **/

import cn.edu.ecust.dao.mapper.UserMapper;
import cn.edu.ecust.domain.entity.Fileinfo;
import cn.edu.ecust.domain.entity.User;
import cn.edu.ecust.service.file.FileService;
import cn.edu.ecust.service.user.UserService;
import cn.edu.ecust.web.util.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;



    @RequestMapping(value = "/getUserByUserId" , method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取用户信息")
    public JSONObject getUserByUserId(@ApiParam(value = "用户Id",required = true) @RequestParam String userId){
        User user = userService.getUserByUserId(userId);
        if( user== null){
            return ResultUtil.fail("查询不到此用户");
        }
        else {

            return ResultUtil.success(user);
        }
    }

    @RequestMapping(value = "/getAllUsersByType" , method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("通过用户类型获取用户信息")
    public JSONObject getAllUsersByType(@ApiParam(value = "用户类型type",required = true) @RequestParam String type){
        List<User> allUsersByType = userService.getAllUsersByType(type);

        return ResultUtil.success(allUsersByType);
    }

    @RequestMapping(value = "/updateUser" , method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改用户信息")
    public JSONObject updateUser(@ApiParam(value = "用户Id",required = true) @RequestBody User user

                                ){
        //通过id修改信息
        //type和userId不能修改
        userService.updateUser(user);
        return ResultUtil.success("修改成功");
    }

    @RequestMapping(value = "/getAllUserByCourseId" , method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取课程的所有用户信息")
    public JSONObject getAllUserByCourseId(@ApiParam(value = "课程Id",required = true) @RequestParam int courseId){
        List<User> allUserByCourseId = userService.getAllUserByCourseId(courseId);

        return ResultUtil.success(allUserByCourseId);

    }

    @RequestMapping(value = "/updateUserImg",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改用户头像")
    public JSONObject updateUserImg(
            @ApiParam(value = "用户Id",required = true) @RequestParam String userId,
            @ApiParam(value = "用户头像",required = false) @RequestParam(name="image",required = false) MultipartFile multipartFile
    ) throws IOException {

        String imgPath = userService.uploadUserImg(userId,multipartFile);

        //userMapper.updateUserImg(userId,imgPath);
        return  ResultUtil.success(imgPath);
    }



}
