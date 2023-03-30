package cn.edu.ecust.service.task;

import cn.edu.ecust.common.task.BaseTask;
import cn.edu.ecust.common.util.BeanUtil;
import cn.edu.ecust.domain.entity.AutoScaler.Autoscaler;

public class DeleteInstaceTaskSimple extends BaseTask {

    private Autoscaler autoscaler;

    public DeleteInstaceTaskSimple(Autoscaler autoscaler){
        this.autoscaler = autoscaler;

        this.setName(String.format("删除userCourseName为'%s'的容器", autoscaler));
    }

    @Override
    public void doTask() {
        try {
//            BeanUtil.getBean(TaskProcessor.class).deleteInstanceSimple(autoscaler);
        } catch (Exception e) {
            logger.error(String.format("任务[%s]执行出错", getName()), e);
        }
    }
}
