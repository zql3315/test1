package com;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.google.gson.Gson;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.demo.dao.DepartmentDAO;
import com.infosky.demo.entity.dto.DemoDTO;
import com.infosky.demo.entity.dto.DemoMapperDTO;
import com.infosky.demo.entity.po.Child;
import com.infosky.demo.entity.po.Demo;
import com.infosky.demo.entity.po.Department;
import com.infosky.demo.service.impl.ChildService;
import com.infosky.demo.service.impl.DemoService;
import com.infosky.demo.service.impl.DepartmentService;
import com.infosky.framework.web.WebUtil;
import com.infosky.sys.service.impl.UserService;
import com.infosky.sys.web.LoginController;
import com.infosky.wechat.entity.dto.WeiXinKeywordMaterialDTO;
import com.infosky.wechat.service.impl.WeiXinKeywordMaterialService;

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
// 使用junit4进行测试
@ContextConfiguration({
        "classpath*:/spring-*.xml", "classpath*:/spring/*.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class BaseJunit4Test {

    // 模拟request,response
    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    // 注入loginController
    @Autowired
    private LoginController loginController;

    @Resource
    private JdbcTemplate jdbcTemplate;

    // 执行测试方法之前初始化模拟request,response
    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
        response.setHeader("referer", "/");
    }

    /**
     * 
     * @Title：testLogin
     * @Description: 测试用户登录
     */
    @Test
    public void testLogin() {
        try {
            request.setParameter("username", "admin");
            request.setParameter("password", "123456");
            assertEquals("login", loginController.login(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @Title：testLogin
     * @Description: 测试用户登录
     */
    @Test
    public void validate() {
        try {
            request.setParameter("username", "admin");
            request.setParameter("password", "123456");
            assertEquals("login", loginController.login(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        Gson gson = new Gson();
        List<String> list = new ArrayList<String>();
        list.add("234234");
        list.add("sdfsadf");

        System.out.println(gson.toJson(list));
        System.out.println("1111111");
        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void test2() {
        DemoService service = (DemoService) WebUtil.getContext().getBean("demoService");
        service.testSql();
    }

    @Test
    public void test21() {
        DemoService service = (DemoService) WebUtil.getContext().getBean("demoService");
        service.testNativeSql();
    }
    @Test
    public void test23() {
        DemoService service = (DemoService) WebUtil.getContext().getBean("demoService");
        service.testNativeSql1();
    }

    @Test
    public void test22() {
        // WeiXinKeywordService service = (WeiXinKeywordService) WebUtil.getContext().getBean("weiXinKeywordService");
        WeiXinKeywordMaterialService service = (WeiXinKeywordMaterialService) WebUtil.getContext().getBean(
                "weiXinKeywordMaterialService");
        try {
            // System.out.println(service.find("1"));
            WeiXinKeywordMaterialDTO dto = service.find("8a94d1bd543bdede01543bf21ae50014");
            if (dto != null) service.delete(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        DemoService service = (DemoService) WebUtil.getContext().getBean("demoService");
        service.testSpecifications();
    }

    @Test
    public void test4() {
        UserService service = (UserService) WebUtil.getContext().getBean("userService");
        Searchable searchable = new SearchRequest();
        searchable.addSearchParam("id", Operator.EQ, "8a94d1b750b80d810150b812a8ff0006");
        System.out.println(service.findAll(searchable) + "----22------");
    }

    @Test
    public void test5() {
        DepartmentService service = (DepartmentService) WebUtil.getContext().getBean("departmentService");
        // service.testSpecifications();
        service.testDel();
    }

    @Test
    public void test6() {
        ChildService service = (ChildService) WebUtil.getContext().getBean("childService");
        // service.testSpecifications();
        service.findByDepartment_Name("222");
    }

    @Test
    public void test8() {
        DepartmentService service = (DepartmentService) WebUtil.getContext().getBean("departmentService");
        service.testAdd();
    }

    @Test
    public void testQueryOneToMany() {
        final Department department = new Department();
        department.setAge(1);
        department.setId("1");
        department.setName("1");
        Specification<Department> spec = new Specification<Department>() {

            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p1 = cb.like(root.get("name").as(String.class), "%" + department.getName() + "%");
                Predicate p2 = cb.equal(root.get("id").as(String.class), department.getId());
                Predicate p3 = cb.gt(root.get("age").as(Integer.class), department.getAge());
                Join<Department, Child> depJoin = root.join("child", JoinType.LEFT);
                // SetJoin<Department, Child> depJoin = root.join(root.getModel().getSet("child", Child.class), JoinType.LEFT);
                Predicate p4 = cb.equal(depJoin.get("id").as(String.class), "1");
                // 把Predicate应用到CriteriaQuery去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥 的
                query.where(cb.and(cb.and(p3, cb.or(p1, p2)), p4));
                // 添加分组的功能
                query.orderBy(cb.desc(root.get("id").as(Integer.class)));
                return query.getRestriction();
            }
        };
        DepartmentDAO dao = (DepartmentDAO) WebUtil.getContext().getBean("departmentDAO");
        dao.findAll(spec);

    }

    @Test
    public void test9() {

        DemoService service = (DemoService) WebUtil.getContext().getBean("demoService");
        Pageable pageable = new PageRequest(0, 10);
        Page<Demo> pageResult = service.findByName("Hello", pageable);
        System.out.println(pageResult.getContent());
    }

    @Test
    public void test10() {

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/translate?characterEncoding=utf8";
            String sql = "select *  from t_demo";
            conn = DriverManager.getConnection(url, "root", "123456");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs !=null ) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(ps !=null ) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn !=null ) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        DemoDTO demoDTO = jdbcTemplate.execute(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select * from t_demo where id = 1 ");
            }
        }, new PreparedStatementCallback<DemoDTO>() {

            public DemoDTO doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
                pstmt.execute();
                ResultSet rs = pstmt.getResultSet();
                DemoDTO demoDTO = new DemoDTO();
                while (rs.next()) {
                    demoDTO.setAge(rs.getInt("age"));
                    demoDTO.setId(rs.getString("id"));
                    demoDTO.setName(rs.getString("name"));
                }
                return demoDTO;
            }
        });

        System.out.println(JSONObject.fromObject(demoDTO) + "===============");
        try {
            String sql = "select * from t_demo";
            List<Map<String, Object>> tt = jdbcTemplate.queryForList(sql);
            System.out.println(JSONArray.fromObject(tt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String name = "aaa";
            String SELECT_BY_ID = "SELECT COUNT(*) FROM t_demo WHERE name = ?";
            int i = jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[] {
                name
            }, Integer.class);
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String name = "aaa";
            String SELECT_BY_ID = "SELECT * FROM t_demo WHERE name = ?";
            Map<String, Object> map = jdbcTemplate.queryForMap(SELECT_BY_ID, new Object[] {
                name
            });
            System.out.println(JSONObject.fromObject(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            List<DemoDTO> list1 = null;
            String ID = "1";
            String sql1 = "SELECT * FROM t_demo WHERE ID=?";
            list1 = jdbcTemplate.query(sql1, new Object[] {
                ID
            }, new DemoMapperDTO());
            System.out.println(JSONArray.fromObject(list1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
