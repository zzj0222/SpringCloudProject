package com.demo.admin.test;

import com.demo.admin.AdminApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description: 单元测试base class
 * @Author zheng.shk
 * @Date 15:21 2018/3/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=AdminApplication.class)
@WebAppConfiguration
public class BaseTest {

}
