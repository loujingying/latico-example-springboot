package com.latico.example.springboot.swagger;

/**
 * <PRE>
 *
 * </PRE>
 * <B>项	       目：</B>
 * <B>技术支持：</B>latico
 *
 * @author <B><a href="mailto:latico@qq.com"> latico </a></B>
 * @version <B>V1.0 2018年10月11日</B>
 * @since <B>JDK1.6</B>
 */
public interface DemoService {

    /**
     * @return 字符串类型
     */
    String serverTimeStr();

    /**
     * @return 对象类型
     */
    DemoTimeParam serverTime();

}
