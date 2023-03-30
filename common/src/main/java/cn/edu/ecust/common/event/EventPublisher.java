package cn.edu.ecust.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  @Description 事件发布者
 *  @author xunzhang
 *  @Date 2020.09.05 11:13
 */
@Component
public class EventPublisher {

	private List<EventListener> listeners = new ArrayList<>();
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @description 添加事件监听器
	 * @param eventListener: 事件监听器
	 * @author xunzhang
	 * @date 2020.09.05 11:14
	 */
	public void addEventListener(EventListener eventListener) {
		listeners.add(eventListener);
	}

	/**
	 * 发布事件
	 * @param event 需要处理的事件
	 */
	public void publish(Event event) {
		for (EventListener listener : listeners) {
			if (listener == null) {
				return;
			}
			try {
				listener.onEvent(event);
			} catch (Exception e) {
				logger.error("发布事件异常", e);
			}
		}
	}
}
