package cn.edu.ecust.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Description:
 * @author: Mengyang zhu
 * @Date: Create in 11:48 2022/10/10
 **/

@Configuration
public class WebSocketConfig {
    /**
     * 使用springboot内置tomcat需要该bean，打war包则注释掉该bean
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
