package com;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @version [版本号, 2015年2月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 * @author n004881
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
// 使用junit4进行测试
@ContextConfiguration({ "classpath*:/spring-*.xml", "classpath*:/spring/*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class BaseJunit4Action {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;

	// 执行测试方法之前初始化模拟request,response
	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	/**
	 * 
	 * @Title：testLogin
	 * @Description: 测试用户登录
	 */
	@Test
	public void test() {
		try {
			mockMvc.perform((post("/demo/validate/hello").param("username", "").param("password", "1"))).andExpect(status().isOk()).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
