package com;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.demo.entity.dto.DemoDTO;
import com.infosky.demo.entity.po.Demo;
import com.infosky.demo.service.impl.DemoService;
import com.infosky.framework.web.PageResult;

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
     * 
     */
    @Test
    public void testPage() {
        PageResult<Demo> page = new PageResult<Demo>();
        page.setLength(2);
        Page<Demo> list = demoService.findByName("1", page);
        System.out.println("======testPage=======" + JSONObject.fromObject(list));
    }

    /**
     * 
     */
    @Test
    public void testPage2() {
        PageResult<Demo> page = new PageResult<Demo>();
        page.setLength(2);
        Page<Demo> result = demoService.findByName2("1", 1, page);
        System.out.println("======testPage2=======" + JSONObject.fromObject(result));
    }

    /**
     * 
     */
    @Test
    public void testPage3() {
        PageResult<Demo> page = new PageResult<Demo>();
        page.setLength(2);
        List<Demo> result = demoService.findByName3("1", 1, page);
        System.out.println("======testPage3=======" + JSONArray.fromObject(result));
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
