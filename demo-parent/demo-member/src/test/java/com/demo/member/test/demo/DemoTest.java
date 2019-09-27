package com.demo.member.test.demo;

import com.demo.member.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author zheng.shk
 * @Date 15:26 2018/3/7
 */
public class DemoTest extends BaseTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void demo(){
        logger.info("我是一个junit测试类");
    }
}

