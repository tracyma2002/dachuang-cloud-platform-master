package cn.edu.ecust.service.course;

import cn.edu.ecust.common.exception.AppException;
import cn.edu.ecust.dao.mapper.*;
import cn.edu.ecust.domain.entity.*;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 9:34 2022/6/14
 **/
@Service
public class CourseService {


    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private ExperimentMapper experimentMapper;

    @Autowired
    private HomeworkMapper homeworkMapper;


    /**
     * @Description: 通过teacherId获取course
     * @Return: course类
     **/
    public List<Course> getCourseByTeacherId(String teacherId) {
        return courseMapper.getCourseByTeacherId(teacherId);
    }

    /**
     * @Description: 通过studentId获取course
     * @Return: courseInfo类
     **/

    public List<CourseInfo> getCourseInfoByStudentId(String studentId){
        return courseMapper.getCourseInfoByStudentId(studentId);
    }
    /**
     * @Description:创建课程
     * @Return:
     **/
    public void createCourse(Course course){
        course.setCreatedAt(new Date());
//        System.out.println("ccsercice");
        if (course.getEndTime().before(course.getFromTime())){
            throw new AppException("课程结束时间要在课程开始时间之后");
        }
        courseMapper.createCourse(course);
        int courseId=courseMapper.getCourseId();
        courseMapper.updatetc(courseId);
    }

    /**
     * @Description:删除当前课程中指定学生
     * @Return:
     **/

    public void deleteOneStudentCourse(int courseId,String studentId){
        courseMapper.deleteOneStudentCourse(courseId,studentId);
        // 删除user_experiment表中该课程下的所有实验
        List<Integer> experiment_ids = experimentMapper.getExperimentIdbycourseId(courseId);
        for(Integer experiment_id:experiment_ids){
            experimentMapper.deleteOneUserExperiment(studentId,experiment_id);
        }
        // 删除user_homework表中该课程下的所有作业
        List<Integer> homework_ids = homeworkMapper.getHomeworkIdbycourseId(courseId);
        for (Integer homework_id:homework_ids){
            homeworkMapper.deleteOneUserHomework(studentId,homework_id);
        }


    }
    /**
     * @Description:获取课程id
     * @Return:
     **/

    public int getCourseId(){
        return courseMapper.getCourseId();
    }

    public String getCourseName(int courseId){return courseMapper.getCourseName(courseId);}

    /**
     * @Description:删除课程，在各关联表中删除相关课程记录
     * @Return:
     **/
    public List<Course> deleteCourseAllRecord(int courseId,String teacherId){
        courseMapper.deleteCourse(courseId);
        courseMapper.deleteTeacherCourse(courseId);
        courseMapper.deleteCourseResource(courseId);
        courseMapper.deleteStudentCourse(courseId);
        courseMapper.deleteCourseExperiment(courseId);
        return courseMapper.getCourseByTeacherId(teacherId);
    }

    //归档课程
    @Transactional
    public void archiveCourse(int courseId,String teacherId){
        Course course = courseMapper.getCourseById(courseId);
        //删除course表中的课程
        courseMapper.deleteCourse(courseId);
//        //删除course_recourse中的课程
//        courseMapper.deleteCourseResource(courseId);
//        //删除course_experiment中的课程
//        courseMapper.deleteCourseExperiment(courseId);
//        //存储该课程的学生名单
//
//
//        //删除student_course
//        courseMapper.deleteStudentCourse(courseId);
        //在archived_course中存储归档课程
        ArchivedCourse archivedCourse = new ArchivedCourse();

        Date now = new Date();

        archivedCourse.setArchivedTime(now);
        archivedCourse.setCourseId(courseId);
        archivedCourse.setCourseName(course.getCourseName());
        archivedCourse.setEndTime(course.getEndTime());
        archivedCourse.setFromTime(course.getFromTime());
        archivedCourse.setTeacherId(teacherId);

        courseMapper.createArchivedCourse(archivedCourse);
    }
    //查询归档课程
    public List<ArchivedCourse>selectArchiveCourse(String teacherId){
        List<ArchivedCourse> archivedCourses = courseMapper.selectArchiveCourse(teacherId);
        return archivedCourses;
    }

    //下载归档课程的签到信息
    public void downloadSignInfoForArch(HttpServletResponse response, HttpServletRequest request,int courseId) throws Exception {

        ArchivedCourse archivedCourse = courseMapper.getArchivedCourse(courseId);
        if(archivedCourse==null){
            throw new AppException("暂无此课程");
        }
        //查询签到信息，返回List
        List<String> userId = courseMapper.getAllUserIdByCourseId(courseId);
        //System.out.println(userId);
        List signInfoForArches = new ArrayList<SignInfoForArch>();
        for (String i: userId) {
            List<SignInfoForArch> signInfoForArch = courseMapper.getSignInfoForArch(i);
            //System.out.println(signInfoForArch);
            signInfoForArches.addAll(signInfoForArch);
        }
        if(signInfoForArches.isEmpty()){
            //System.out.println("2");
            throw new AppException("暂无签到信息！");
        }
        String fileName = "签到信息统计"+archivedCourse.getCourseName();
        setExcelRespProp(response,fileName);


        EasyExcel.write(response.getOutputStream()).sheet("统计信息")
                .head(SignInfoForArch.class)
                .needHead(true)
                .doWrite(signInfoForArches);

    }

//    public List<StudentInfo> data() {
//        List<StudentInfo> list = ListUtils.newArrayList();
//        for (int i = 0; i < 10; i++) {
//            StudentInfo data = new StudentInfo();
//            data.setStudentId("yy");
//            data.setUserName("xx");
//            list.add(data);
//        }
//        return list;
//    }


//    public List<StudentInfo> data(int courseId) {
//        List<StudentInfo> studentinfo =userMapper.getStudentInfoByCourseId(courseId);
//        return studentinfo;

//    }
    public void downloadStudentInfo(HttpServletResponse response, int courseId) throws IOException {
        //创建空的excel表格，HSSF普通excel
        Workbook wb = new XSSFWorkbook();
        //创建工作区
        Sheet sheet = wb.createSheet("学生名单");
//        //设置列宽（第一个参数表示：第二列；第二个参数表示：列宽为50，此处注意设置列宽要*256）
//        sheet.setColumnWidth(2,50*256);

        //列名
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("学号");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("姓名");
//        Cell cell2 = row.createCell(2);
//        cell2.setCellValue("签到时间");
//        row.createCell(2).setCellValue("签到时间");
//        row.createCell(3).setCellValue("课程签到率");


        List<StudentInfo> studentinfo =userMapper.getStudentInfoByCourseId(courseId);
        for(int i=0;i<studentinfo.size();i++){
            Row rowi =sheet.createRow(i+1);

            Cell celli0=rowi.createCell(0);
            celli0.setCellValue(studentinfo.get(i).getStudentId());

            Cell celli1=rowi.createCell(1);
            celli1.setCellValue(studentinfo.get(i).getUserName());
            //rowi.createCell(2).setCellValue(studentinfo.get(i).get);


        }
        String fileName=Integer.toString(courseId);
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String newFileName = fileName + currentDateTime + ".xlsx";


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] documentContent = baos.toByteArray();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(newFileName, "utf-8"));

        response.setContentLength(documentContent.length); // response响应头中设置传输文件长度
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bos.write(documentContent);
        wb.close();
        baos.close();
        bos.close();

    }

    public void downloadCourseSignInInfo(HttpServletResponse response, int courseId,int experimentId) throws IOException {
        //创建空的excel表格，HSSF普通excel
        Workbook wb = new XSSFWorkbook();
        String courseName =courseMapper.getCourseName(courseId);
        String experimentName  =experimentMapper.getexperimentNameById(experimentId);
        String sheetName=courseName+experimentName+"签到统计";

        //创建工作区
        Sheet sheet = wb.createSheet(sheetName);
//        //设置列宽（第一个参数表示：第二列；第二个参数表示：列宽为50，此处注意设置列宽要*256）
//        sheet.setColumnWidth(2,50*256);


        //列名
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("学号");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("姓名");
        Cell cell2 = row.createCell(2);
        cell2.setCellValue("签到情况");


//        List<StudentInfo> studentinfo =userMapper.getStudentInfoByCourseId(courseId);
        List<SignInfoForExcel> signInfo= signInMapper.GetSignInfoByexperimentId(experimentId);
        for(int i=0;i<signInfo.size();i++){
            Row rowi =sheet.createRow(i+1);

            Cell celli0=rowi.createCell(0);
            celli0.setCellValue(signInfo.get(i).getUserId());

            Cell celli1=rowi.createCell(1);
            celli1.setCellValue(signInfo.get(i).getUserName());

            Cell celli2=rowi.createCell(2);
            if(signInfo.get(i).getIsSignin()==1) celli2.setCellValue("已签到");
            else celli2.setCellValue("未签到");

            rowi.createCell(3).setCellValue(signInfo.get(i).getSigninTime());
            //首先获取这门课程总的签到次数
            int totalSignNum = signInMapper.getTotalSignNum(courseId);
            int studentSignNum = signInMapper.getStudentSignNum(courseId, signInfo.get(i).getUserId());
            rowi.createCell(4).setCellValue(studentSignNum/totalSignNum);


        }
        String fileName=Integer.toString(courseId);
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String newFileName = sheetName + currentDateTime + ".xlsx";


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] documentContent = baos.toByteArray();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(newFileName, "utf-8"));

        response.setContentLength(documentContent.length); // response响应头中设置传输文件长度
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bos.write(documentContent);
        wb.close();
        baos.close();
        bos.close();

    }




//    public void templateWrite() {
//        String filename="D:\\download\\"+"try.xls";
////        File file=new File(filename);
////        String templateFileName ="demo.xlsx";
////        String fileName = TestFileUtil.getPath() + "templateWrite" + System.currentTimeMillis() + ".xlsx";
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(filename, StudentInfo.class).sheet("try").doWrite(data());
//    }





    public void setExcelRespProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replace("+", "%20");

        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
    }





}
