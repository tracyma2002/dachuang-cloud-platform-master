package cn.edu.ecust.service.task;

import cn.edu.ecust.common.task.BaseTask;
import cn.edu.ecust.common.util.BeanUtil;

public class CreateAutoscalerTaskSimple extends BaseTask {

    private String userCourseName;
    public CreateAutoscalerTaskSimple(String userCourseName){
//        this.requestInfo = requestInfo;
        this.userCourseName = userCourseName;
        this.setName(String.format("创建user_course_id为'%s'的容器", userCourseName));
    }

    @Override
    public void doTask() {
        try {
//            BeanUtil.getBean(TaskProcessor.class).createInstanceSimple(userCourseName);
        } catch (Exception e) {
            logger.error(String.format("任务[%s]执行出错", getName()), e);
        }
    }
}
