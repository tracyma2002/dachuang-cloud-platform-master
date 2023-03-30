package cn.edu.ecust.common.util;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 *  @Description httpClient管理类
 *  @author xunzhang
 *  @Date 2020.08.15 17:22
 */
public class HttpClientFactory {
	
	/** 最大连接数 */
	private static final int MAX_TOTAL = 100;
	
	/** 目标最大连接数 */
	private static final int DEFAULT_MAX_PER_ROUTE = 100;
	
	/** 半关闭连接检测周期 */
	private static final int VALIDATE_AFTER_INACTIVITY = 1000 * 60;

	/** 静态实例 */
	private static HttpClientFactory httpClientFactory = null;
	
	/** 生效连接清理 */
	private IdelConnectionMonitor idelConnectionMonitor = null;

	/** HTTP客户端连接池管理器 */
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private HttpClientFactory() {
		logger.info(
				"初始化HTTP客户端连接池，最大连接数：[ {} ]，目标最大连接数：[ {} ]，半关闭连接检测周期：[ {} ]MS", 
				MAX_TOTAL, DEFAULT_MAX_PER_ROUTE, VALIDATE_AFTER_INACTIVITY);
		
		this.poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		// 最大连接数
		this.poolingHttpClientConnectionManager.setMaxTotal(MAX_TOTAL);
		// 每个目标最大连接数
		this.poolingHttpClientConnectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
		// 半关闭连接检测周期
		this.poolingHttpClientConnectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
		
		this.idelConnectionMonitor = new IdelConnectionMonitor(this.poolingHttpClientConnectionManager);
		this.idelConnectionMonitor.start();
	}
	
	/**
	 * @description 获取HttpClientFactory单例实例
	 * @return HttpClientFactory实例
	 * @author xunzhang
	 * @date 2020.08.15 17:23
	 */
	public static HttpClientFactory getInstance() {
		synchronized (HttpClientFactory.class) {
			if (httpClientFactory == null){
				httpClientFactory = new HttpClientFactory();
			}
		}

		return httpClientFactory;
	}
	
	/**
	 * 构造HTTP客户端
	 * @param timeout 超时时间（毫秒）
	 * @return HTTP客户端
	 */
	public CloseableHttpClient build(long timeout) {
		
		int timeoutInt = new Long(timeout).intValue();

		logger.info("初始化HTTP客户端，超时时间：[ {} ]毫秒", timeoutInt);
		
		Builder requestConfigBuilder = RequestConfig.custom()
				// 网络超时时间
				.setSocketTimeout(timeoutInt)
				// 请求超时时间
				.setConnectionRequestTimeout(timeoutInt)
				// 连接超时时间
				.setConnectTimeout(timeoutInt)
				// 请求消息压缩
				.setContentCompressionEnabled(true);
		
		return HttpClients.custom().setConnectionManager(this.poolingHttpClientConnectionManager)
				// 禁止自动重试
				.disableAutomaticRetries()
				.setDefaultRequestConfig(requestConfigBuilder.build())
				// 不重复请求
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
				// 长连接策略
				.setKeepAliveStrategy((response, context) -> {
					HeaderElementIterator it = new BasicHeaderElementIterator(
							response.headerIterator(HTTP.CONN_KEEP_ALIVE));
					while (it.hasNext()) {
						HeaderElement he = it.nextElement();
						String param = he.getName();
						String value = he.getValue();
						if (value != null && param.equalsIgnoreCase("timeout")) {
							return Long.parseLong(value) * 1000;
						}
					}

					// 如果没有约定，则默认定义时长为60秒
					return VALIDATE_AFTER_INACTIVITY;
				}).build();
	}

	private class IdelConnectionMonitor extends Thread {
		private final PoolingHttpClientConnectionManager connMgr;
		private volatile boolean shutdown = false;

		IdelConnectionMonitor(PoolingHttpClientConnectionManager connMgr) {
			this.connMgr = connMgr;
		}

		/**
		 * @see Thread#run()
		 */
		@Override
		public void run() {
			while (!this.shutdown) {
				synchronized (this) {
					try {
						wait(5000);
						this.connMgr.closeExpiredConnections();
						this.connMgr.closeIdleConnections(VALIDATE_AFTER_INACTIVITY, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
					}
				}
			}
		}

		//关闭连接
		void shutdown() {
			synchronized (this) {
				this.shutdown = true;
				notifyAll();
			}
		}
	}
	/**
	 * 关闭连接
	 */
	public void close() {
		this.logger.warn("准备清理HTTP连接 ...... ");
		this.idelConnectionMonitor.shutdown();
		this.poolingHttpClientConnectionManager.close();
		this.logger.warn("HTTP连接已清理");
	}
}
