package cn.edu.ecust.web.controller.resource;

import cn.edu.ecust.common.exception.AppException;
import cn.edu.ecust.domain.entity.Resource;
import cn.edu.ecust.domain.entity.ResourceInfo;
import cn.edu.ecust.domain.entity.ResourceInfoTeacher;
import cn.edu.ecust.service.Resource.ResourceService;
import cn.edu.ecust.web.util.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description:
 * @author: Fen Min
 * @Date: Create in 12:18 2022/7/13
 **/
@RestController
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/getResourceInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("学生获取资源信息")
    public List<ResourceInfo> getResourceInfo(@ApiParam(value = "课程Id",required = true) @RequestParam int courseId,
                                                  @ApiParam(value = "学生Id",required = true) @RequestParam String userId
    ){

        return resourceService.getResourceInfo(courseId,userId);
    }

    @RequestMapping(value = "/getResourceInfoTeacher",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("老师获取资源信息")
    public List<ResourceInfoTeacher> getResourceInfoTeacher(@ApiParam(value = "课程Id",required = true) @RequestParam int courseId,
                                                                @ApiParam(value = "教师Id",required = true) @RequestParam String teacherId
    ){

        return resourceService.getResourceInfoTeacher(courseId,teacherId);
    }

//    @RequestMapping(value = "/addResource",method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation("上传资源")
//    public JSONObject addResource(@ApiParam(value = "课程Id",required = true) @RequestParam int courseId,
//                                    @ApiParam(value = "资源名称",required = true) @RequestParam String resourceName,
//                                    @ApiParam(value = "资源Url",required = true) @RequestParam String resourceUrl
//
//    ){
//        //Resource resource = resourceService.addResource(courseId, resourceName, resourceUrl);
//        return ResultUtil.success(resource);
//    }


    @RequestMapping(value = "/downloadResourceFile",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @ApiOperation("学生下载资源")
    public JSONObject downloadResourceFile(HttpServletResponse response, HttpServletRequest request, @RequestParam Integer resourceId) throws UnsupportedEncodingException {
        resourceService.downloadResourceFile(response,request,resourceId);
        return ResultUtil.success("下载成功");

    }

    @RequestMapping(value = "/uploadResourceFile",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("教师上传资源")
    public JSONObject uploadResourceFile(
            @ApiParam(value = "课程Id",required = true) @RequestParam int courseId,
            @RequestParam(required = false) String description,
            @ApiParam(value = "资源",required = true) @RequestParam("file") MultipartFile multipartFile) throws IOException {

        Resource resource = resourceService.uploadResourceFile(courseId, multipartFile,description);
        return ResultUtil.success("上传成功",resource);

    }



//    @RequestMapping(value = "/deleteResource",method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation("删除资源")
//    public JSONObject deleteResource(@ApiParam(value = "资源Id",required = false)  @RequestParam(required=false)  int[] resourceIdGroup
//    ){
//        if(resourceIdGroup!=null){
//            for(int i=0;i<resourceIdGroup.length;i++) resourceService.deleteResource(resourceIdGroup[i]);
//            return ResultUtil.success("删除成功");
//        }
//        else throw new AppException("您还未选择任何资源");
//
//
//
//    }

    @RequestMapping(value = "/deleteResource",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("删除资源")
    public JSONObject deleteResource(@ApiParam(value = "资源Id")  @RequestParam  int resourceId
    ){
//        if(resourceIdGroup!=null){
//            for(int i=0;i<resourceIdGroup.length;i++) resourceService.deleteResource(resourceIdGroup[i]);
//            return ResultUtil.success("删除成功");
//        }
//        else throw new AppException("您还未选择任何资源");
        resourceService.deleteResource(resourceId);
        return ResultUtil.success("删除成功");



    }

}
