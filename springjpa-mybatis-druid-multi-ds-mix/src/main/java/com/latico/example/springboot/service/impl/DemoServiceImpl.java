package com.latico.example.springboot.service.impl;

import com.latico.example.springboot.bean.pojo.DemoTimeBean;
import com.latico.example.springboot.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


/**
 * <PRE>
 *
 * </PRE>
 * <B>项	       目：</B>研发中心公共-综合网管
 * <B>技术支持：</B>凯通科技股份有限公司 (c)
 *
 * @author <B><a href="mailto:landingdong@gdlaticosoft.com"> 蓝鼎栋 </a></B>
 * @version <B>V1.0 2018年10月11日</B>
 * @since <B>JDK1.6</B>
 */
@Service
public class DemoServiceImpl implements DemoService {

    /**
     * 日志
     */
    private final static Logger LOG = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String serverTimeStr() {
        DemoTimeBean time = new DemoTimeBean();
        time.setTime(new Timestamp(System.currentTimeMillis()));

        String str = "机组ID：服务器当前时间：" + time.toString();
        LOG.info("字符串类型完成请求处理,结果:{}", str);
        return str;
    }

    @Override
    public DemoTimeBean serverTime() {
        DemoTimeBean time = new DemoTimeBean();
        time.setTime(new Timestamp(System.currentTimeMillis()));

        String str = "机组ID：服务器当前时间：" + time.toString();
        LOG.info("对象类型完成请求处理,结果:" + str);
        return time;
    }

}