package cn.edu.ecust.common.task;

import cn.edu.ecust.common.event.Event;
import cn.edu.ecust.common.event.EventListener;
import cn.edu.ecust.common.event.ShutdownEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *  @Description 任务执行线程池
 *  @author xunzhang
 *  @Date 2020.08.21 22:43
 */

public class TaskThreadPool extends ThreadPoolExecutor implements EventListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 构造方法
	 * @param corePoolSize 核心线程数
	 * @param maximumPoolSize 队列最大深度
	 * @param keepAliveTime 非核心线程的闲置超时时间，超过这个时间就会被回收
	 * @param unit keepAliveTime时间单位
	 * @param workQueue 工作队列
	 * @param handler 异常对调处理
	 */
	public TaskThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}
	
	/**
	 * 提交任务
	 * @param task 任务信息
	 */
	public void execute(Task task) {
		logger.info("提交任务：[ {} ]", task.getName());
		super.execute(task);
	}

	/**
	 * 提交任务
	 * @param task 任务信息
	 */
	public Future<?> submit(Task task) {
		logger.info("提交任务：[ {} ]", task.getName());
		return super.submit(task);
	}

	/**
	 * @see ThreadPoolExecutor#beforeExecute(Thread, Runnable)
	 */
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		if (r instanceof Task) {
			Task task = (Task) r;
			task.beforeTask();
		}
		super.beforeExecute(t, r);
	}

	/**
	 * @see ThreadPoolExecutor#afterExecute(Runnable, Throwable)
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		if (r instanceof Task) {
			Task task = (Task) r;
			task.afterTask();
			
			logger.info(
					"任务[ {} ]执行完成，耗时[ {} ]秒", 
					task.getName(), 
					task.getElapsedTime());
			
		}
		super.afterExecute(r, t);
	}

	/**
	 * @description 等待线程池中任务执行完并退出进程
	 * @author xunzhang
	 * @date 2020.08.30 13:39
	 */
	private void shutdownWait() {
		logger.warn("TaskThreadPool线程池即将关闭 ......");
		super.shutdown();
		long activeCount = 0;
		while ((activeCount = getActiveCount()) > 0) {
			try {
				logger.info("当前活动任务数：[ {} ]", activeCount);
				Thread.sleep(1000 * 2);
			} catch (InterruptedException e) {
				logger.error("等待线程池退出发生异常", e);
			}
		}
		logger.info("当前活动任务数：[ {} ]", activeCount);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof ShutdownEvent) {
			doShutdown((ShutdownEvent) event);
		}
	}
	
	/**
	 * 停机操作
	 * @param e 关闭事件
	 */
	private void doShutdown(ShutdownEvent e) {
		// 事件源
		String source = e.getSource();
		
		logger.warn("监听到系统关闭事件，事件源：[ {} ]，准备停止线程池", source);
		
		this.shutdownWait();
	}
}
