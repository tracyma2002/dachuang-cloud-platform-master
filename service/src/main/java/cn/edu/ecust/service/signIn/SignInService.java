package cn.edu.ecust.service.signIn;

import cn.edu.ecust.common.exception.AppException;
import cn.edu.ecust.dao.mapper.CourseMapper;
import cn.edu.ecust.dao.mapper.SignInMapper;
import cn.edu.ecust.dao.mapper.UserMapper;
import cn.edu.ecust.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 9:24 2022/7/3
 **/
@Service
public class SignInService {

    @Autowired
    private SignInMapper signInMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;

    public List<SignInfo> GetSignInfo(int courseId, String userId) {
        List<SignInfo> signInfos = signInMapper.GetSignInfo(courseId, userId);
        String userName = userMapper.getUserNameByUserId(userId);
        for (SignInfo signInfo: signInfos) {
            signInfo.setUserName(userName);
            if(signInfo.getIsSignin()==1)
                signInfo.setIsSignin2("已签到");
            else
                signInfo.setIsSignin2("未签到");

        }
        return signInfos;
    }

    public void createSignIn(int experimentId, Date fromTime,Date endTime){
        //判断时间是否正确
        if(endTime.before(fromTime))
            throw new AppException("截止时间要在开始时间之后!");
        //重复发起签到
        if(signInMapper.selectSignIn(experimentId)!=null)
            throw new AppException("已经发起了签到，请勿重复操作！");

        Date date = new Date();
        if(!date.before(endTime))
            throw new AppException("截止时间已过，请勿发起无效签到！");

        signInMapper.createSignIn(experimentId,fromTime,endTime);

    }
    //点开签到按钮
    public SignIn SignIn2(int experimentId) {
        //首先判断教师是否发起了签到
        SignIn signIn = signInMapper.selectSignIn(experimentId);
        if(signIn==null){
            throw new AppException("尚未发起签到！");
        }
        //重复签到
        if(signInMapper.checkIfSignIn(experimentId)==1)
            throw new AppException("已签到，请勿重复签到！");
        return signIn;
    }
    //确认签到
    public void SignIn(int experimentId, String userId){
        SignIn signIn = signInMapper.selectSignIn(experimentId);
        //获取当前时间，判断签到是否过期
        Date dateNow = new Date();
        if(dateNow.after(signIn.getEndTime()))
            throw new AppException("签到已结束！");

        //成功签到，修改user_experiment表中的is_signin字段和sign_time字段
        signInMapper.signSuccess(experimentId,userId,dateNow);
    }

    //教师查询签到统计
    public List<SignInfoTeacher> getSignInfoTeacher(int courseId,String teacherId){
        List<String> studentIds = userMapper.getStudentIdByCourseId(courseId);
        //获取某个课程的所有实验信息
        List<SignInfoTeacher> signInfoTeachers = signInMapper.getSignInfoTeacher(courseId,teacherId);
        //课程总人数
        int totalNum = courseMapper.getTotalNum(courseId);
        for (SignInfoTeacher signInfoTeacher: signInfoTeachers) {
            int signInNum=0;
            //获取已签到人数
            for(String studentId:studentIds){
                signInNum+=signInMapper.getSignInNum(signInfoTeacher.getExperimentId(),studentId);
            }
            signInfoTeacher.setSignNum(signInNum);
            //set总人数
            signInfoTeacher.setTotalNum(totalNum);
        }
        return signInfoTeachers;
    }

    public SigninInfo2 getSignRate(int experimentId){

        SigninInfo2 infos = new SigninInfo2();
        //课程总人数
        int totalNum = signInMapper.getTotalNum(experimentId);

        //获取该实验的已签到人数
        int signNum = signInMapper.getSignNum(experimentId);
        infos.setNumSign(signNum);
        infos.setNumNoSign(totalNum-signNum);
        double rate = signNum/totalNum;
        infos.setSignRate(rate*100);
        infos.setNoSignRate((1-rate)*100);


        return infos;
    }

    public List<SignInfoForExcel> getSignInfoTeacher2(int courseId,int experimentId){
        List<SignInfoForExcel> signInfoForExcels = signInMapper.GetSignInfoByexperimentId(experimentId);
        //首先获取这门课程总的签到次数
        int totalSignNum = signInMapper.getTotalSignNum(courseId);
        for (SignInfoForExcel i:signInfoForExcels) {
            if(i.getIsSignin()==0){
                i.setIsSignin2("未签到");
            }
            else {
                i.setIsSignin2("已签到");
                //设置某个学生对这个课程的签到率
                int studentSignNum = signInMapper.getStudentSignNum(courseId, i.getUserId());
                i.setSignRate(studentSignNum/totalSignNum);
            }

        }
        return signInfoForExcels;
    }


}
