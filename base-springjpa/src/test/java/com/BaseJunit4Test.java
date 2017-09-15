package com;

import java.util.Collection;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.demo.entity.dto.DemoDTO;
import com.infosky.demo.service.impl.DemoService;

/**
 *使用junit4进行测试
 * 
 * @author n004881
 * 
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath*:/config/spring-*.xml"
})
public class BaseJunit4Test {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DemoService demoService;

    // 执行测试方法之前初始化模拟request,response
    @Before
    public void setUp() {
    }

    /**
     * 
     */
    @Test
    public void test() {
        Collection<DemoDTO> list = demoService.findAll();
        System.out.println("=============" + JSONArray.fromObject(list));
    }

    /**
     * 回滚测试
     */
    @Test
    @Rollback(true)
    public void rollabckTest() {
        DemoDTO demo = new DemoDTO();
        demo.setAge(1);
        demo.setName("333");
        demoService.save(demo);
    }

}
