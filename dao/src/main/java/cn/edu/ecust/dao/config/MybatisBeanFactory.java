package cn.edu.ecust.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Yesheng Xu
 * @Date: Created in 17:18 2022/6/2
 */
@Component
@MapperScan(basePackages = {"cn.edu.ecust.dao.mapper"})
public class MybatisBeanFactory {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 创建druid数据源
     * @return 数据源 dataSource
     */
    @Bean(name = "dataSource", autowire = Autowire.NO, initMethod = "init")
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "spring.datasource")
    public EcsDataSource dataSource() {

        EcsDataSource ecsDataSource = new EcsDataSource();
        logger.info("构建默认数据源[ EcsDataSource ]数据源成功");
        return ecsDataSource;
    }
}
