package cn.edu.ecust.web.controller.course;

import cn.edu.ecust.common.exception.AppException;
import cn.edu.ecust.dao.mapper.TemplateMapper;
import cn.edu.ecust.domain.entity.*;
import cn.edu.ecust.service.Experiment.ExperimentService;
import cn.edu.ecust.service.course.CourseService;
import cn.edu.ecust.service.course.ImportExcelService;
import cn.edu.ecust.service.user.UserService;
import cn.edu.ecust.web.controller.template.TemplateController;
import cn.edu.ecust.web.util.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 9:22 2022/6/14
 **/
@RestController
public class CourseController {
    @Autowired
    private ImportExcelService importExcelService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private ExperimentService experimentService;



    @ResponseBody
    @ApiOperation("教师查询课程")
    @RequestMapping(value = "/getCourseByTeacherId",method = RequestMethod.GET)
    public JSONObject getAllCourseByTeacherId(@ApiParam(value = "教师id") @RequestParam String teacherId){
        //查询老师教授的课程
        List<Course> courses = courseService.getCourseByTeacherId(teacherId);
        return ResultUtil.success("查询成功", courses);
    }

    @ResponseBody
    @ApiOperation("学生查询课程信息")
    @RequestMapping(value = "/getCourseInfoStudentId",method = RequestMethod.GET)
    public JSONObject getAllCourseByStudentId(@ApiParam(value = "学生id") @RequestParam String studentId){
        //查询学生上的课程
        List<CourseInfo> courses = courseService.getCourseInfoByStudentId(studentId);
        return ResultUtil.success("查询成功", courses);
    }

    @RequestMapping(value = "/createCourse",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("创建课程")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "创建课程")
    })
    public JSONObject createCourse(@ApiParam(value = "课程信息",required = true)  @RequestBody  Course course
                                    ,@ApiParam(value = "实验id列表",required = false)  @RequestParam(required=false)  int[] experimentIdGroup
    ){
        if (course.getEndTime().before(course.getFromTime())){
            return  ResultUtil.fail("课程结束时间要在课程开始时间之后");
        }
        else {
            System.out.println(course);
            courseService.createCourse(course);
            int courseId=courseService.getCourseId();
            if (experimentIdGroup != null){
                for (int i = 0; i < experimentIdGroup.length; i++) {
                    experimentService.addCourseExperiment(courseId,experimentIdGroup[i]);
                }
            }
            else{
                return ResultUtil.success("课程创建成功！注意您创建的课程没有实验");
            }
            return ResultUtil.success("课程创建成功！");
        }
    }

    @RequestMapping(value = "/deleteCourseAllRecord",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("删除课程")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "删除课程")
    })

    public JSONObject deleteCourseAllRecord(@ApiParam(value = "课程Id列表",required = false) @RequestParam(required=false)  int[] courseIdGroup,
                                            @ApiParam(value = "教师Id",required = true)  @RequestParam  String teacherId
    ){
        if(courseIdGroup!=null){
            for(int i=0;i<courseIdGroup.length;i++) courseService.deleteCourseAllRecord(courseIdGroup[i],teacherId);
            return ResultUtil.success("删除成功");
        }
        else throw new AppException("您还未选择任何课程");
//        else return ResultUtil.success("您还未选择任何课程");
//        return ResultUtil.success("删除成功");


    }





    @RequestMapping(value = "/archiveCourse",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("归档课程")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "归档课程")
    })
    public JSONObject archiveCourse(@ApiParam(value = "课程Id",required = true)  @RequestParam  int courseId,
                                    @ApiParam(value = "教师Id",required = true)  @RequestParam  String teacherId){

        courseService.archiveCourse(courseId,teacherId);
        return ResultUtil.success("归档成功");
    }

    @RequestMapping(value = "/selectArchiveCourse",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询归档课程")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "查询归档课程")
    })
    public JSONObject selectArchiveCourse(@ApiParam(value = "教师Id",required = true)  @RequestParam  String teacherId){
        List<ArchivedCourse> archivedCourses = courseService.selectArchiveCourse(teacherId);
        return ResultUtil.success("查询成功",archivedCourses);
    }

    @RequestMapping(value = "/archiveCourse/downloadSignInInfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("下载归档课程的学生签到信息")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "创建课程")
    })
    public JSONObject downloadSignInInfo(HttpServletResponse response, HttpServletRequest request,
                                         @ApiParam(value = "课程Id",required = true)  @RequestParam  int courseId) throws Exception {

        courseService.downloadSignInfoForArch(response,request,courseId);

        return ResultUtil.success("签到信息下载成功");
    }


    @RequestMapping(value = "/downloadCourseSignInInfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("下载课程的学生签到信息")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "下载课程的学生签到信息")
    })
    public JSONObject downloadCourseSignInInfo(HttpServletResponse response, HttpServletRequest request,
                                               @ApiParam(value = "课程Id",required = true)  @RequestParam  int courseId,
                                               @ApiParam(value = "实验id",required = true)  @RequestParam  int experimentId
    ) throws Exception {

//        courseService.downloadSignInfoForArch(response,request,courseId);
        courseService.downloadCourseSignInInfo(response,courseId,experimentId);

        return ResultUtil.success("签到信息下载成功");
    }


//    @RequestMapping(value = "/Templateimg",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject Template(@ApiParam(value="课程图片") @RequestParam int courseTemplatesId){
//        return ResultUtil.success(templateMapper.selectTemplateImage(courseTemplatesId));
//    }




    @ApiOperation(value = "下载学生名单", notes = "export", produces = "application/octet-stream")

    @RequestMapping(value = "/downloadStudentInfo",method = RequestMethod.GET)
    @ResponseBody
//    @ApiOperation("下载学生名单")
    @ApiResponses({
            @ApiResponse(code = 0000, response = String.class,message = "下载学生名单")
    })
    public  JSONObject downloadStudentInfo(@ApiParam(value = "课程Id",required = true)  @RequestParam  int courseId,
                                           HttpServletResponse response
    ) throws Exception {
        //正则表达式先把第一个'\'当作转义字符，得到"\+"
        //Java字符串又把"\+"中的'\'当作转义字符，得到'+'

//        courseService.setExcelRespProp(response,"fileName");
//        EasyExcel.write(response.getOutputStream(), StudentInfo.class).sheet("学生名单").doWrite(courseService.data());

        courseService.downloadStudentInfo(response,courseId);
        return ResultUtil.success("下载成功");
    }


    @RequestMapping(value = "/updateCourseUser" , method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改用户信息")
    public JSONObject updateUser(@ApiParam(value = "用户Id",required = true) @RequestBody User user){
        //通过id修改信息
        //type和userId不能修改
        userService.updateUser(user);
        return ResultUtil.success("修改成功");
    }

    @RequestMapping(value = "/deleteOneStudentCourse",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("删除当前课程指定学生")
    public JSONObject deleteOneStudentCourse(@ApiParam(value = "课程id",required = true)  @RequestParam(required=true)  int courseId,
                                       @ApiParam(value = "学生id",required = true) @RequestParam(required = true) String studentId){
        courseService.deleteOneStudentCourse(courseId,studentId);
        return ResultUtil.success("删除成功");
    }

    @PostMapping("uploadExcelStudent")
    @ResponseBody
    @ApiOperation("根据本地excel新增学生")
    public JSONObject uploadExcelStudent(MultipartFile file,
                                         @ApiParam(value = "课程id",required = true)  @RequestParam(required=true)  int courseId
    ) throws IOException {

//        EasyExcel.read(file.getInputStream(), UserInfo.class, new UploadDataListener(DemoDAO)).sheet().doRead();

        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = importExcelService.batchImport(fileName, file,courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(a ==true) return ResultUtil.success("新增成功");
        else throw new AppException("新增失败");
    }



}
