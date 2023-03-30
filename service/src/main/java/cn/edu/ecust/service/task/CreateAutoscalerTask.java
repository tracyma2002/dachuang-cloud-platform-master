package cn.edu.ecust.service.task;

import cn.edu.ecust.common.task.BaseTask;
import cn.edu.ecust.common.util.BeanUtil;
import cn.edu.ecust.domain.entity.AutoScaler.Autoscaler;
import cn.edu.ecust.domain.model.RequestInfo;

public class CreateAutoscalerTask extends BaseTask {

//    private RequestInfo requestInfo;
    private Autoscaler autoscaler;
    public CreateAutoscalerTask(Autoscaler autoscaler){
//        this.requestInfo = requestInfo;
        this.autoscaler = autoscaler;
        this.setName(String.format("创建user_course_id为'%s'的容器", autoscaler));
    }

    @Override
    public void doTask() {
        try {
            BeanUtil.getBean(TaskProcessor.class).createInstance(autoscaler);
        } catch (Exception e) {
            logger.error(String.format("任务[%s]执行出错", getName()), e);
        }
    }
}
