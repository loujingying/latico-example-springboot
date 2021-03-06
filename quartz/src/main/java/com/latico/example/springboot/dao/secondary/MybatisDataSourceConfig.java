package com.latico.example.springboot.dao.secondary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * <PRE>
 * Mybatis数据源
 * 这里配置成非主数据源，不能使用@Primary注解
 * <p>
 * 多数据源时使用，可以直接拷贝该类配置N个数据源
 * <p>
 * 数据源配置，如果不需要，可以把该类删掉，那么启动程序的时候就不会连接数据库
 * <p>
 * 如果拷贝该类创建一个新数据源，需要修改的地方有如下字段：
 * 把后缀带有secondary的名称全部替换目标字符串
 * </PRE>
 *
 * @Author: latico
 * @Date: 2019-02-26 09:25:32
 * @Version: 1.0
 */
@Configuration
@MapperScan(basePackages = MybatisDataSourceConfig.MAPPER_PACKAGE, sqlSessionTemplateRef = MybatisDataSourceConfig.sqlSessionTemplateBeanName)
public class MybatisDataSourceConfig {
    /**
     * TODO 需要修改的地方
     * mapper的接口类
     * 精确到 master 目录，以便跟其他数据源隔离
     */
    static final String MAPPER_PACKAGE = "com.latico.example.springboot.dao.secondary.mapper";
    /**
     * TODO 需要修改的地方
     * mapper的xml文件的位置
     */
    private static final String MAPPER_LOCATION = "classpath*:mapper/secondary/**/*.xml";

    /**
     * TODO 需要修改的地方
     * 数据源配置前缀，跟application配置文件的要对应上
     */
    private static final String DATASOURCE_CONFIG_PREFIX = "spring.datasource.secondary";

    /**
     * TODO
     * 数据源名称
     */
    private static final String dataSourceBeanName = "com.latico.example.springboot.dao.secondary.dataSource";

    /**
     * TODO
     */
    private static final String sqlSessionFactoryBeanName = "com.latico.example.springboot.dao.secondary.sqlSessionFactory";

    /**
     * TODO
     */
    private static final String transactionManagerBeanName = "com.latico.example.springboot.dao.secondary.transactionManager";
    /**
     * TODO
     */
    static final String sqlSessionTemplateBeanName = "com.latico.example.springboot.dao.secondary.sqlSessionTemplate";

    /**
     * 数据源，拷贝后需要修改bean名称
     */
    @Resource(name = dataSourceBeanName)
    private DataSource dataSource;

    /**
     * 在这里，用了什么数据源就返回什么数据源，如果是返回{@link DataSource}，那么springboot2.0会默认使用HikariCP作为数据源
     *
     * @return
     */
    @Bean(name = dataSourceBeanName)
    @ConfigurationProperties(prefix = DATASOURCE_CONFIG_PREFIX)
    public DataSource dataSource() {
        //默认方式,springboot2.0会默认使用HikariCP作为数据源
//        return DataSourceBuilder.create().build();

        //druid方式
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = sqlSessionFactoryBeanName)
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //指定扫描的xml文件
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = transactionManagerBeanName)
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = sqlSessionTemplateBeanName)
    public SqlSessionTemplate sqlSessionTemplateSecondary(@Qualifier(sqlSessionFactoryBeanName) SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}