package com.infosky.demo.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.excel.ExcelRequest;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.common.util.EhcacheUtil;
import com.infosky.demo.entity.dto.DemoDTO;
import com.infosky.demo.service.impl.AsyncService;
import com.infosky.demo.service.impl.DemoService;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.redis.service.RedisUtilsService;
import com.infosky.sys.service.impl.ExcelResolver;
import com.infosky.sys.service.impl.OperationService;
import com.infosky.sys.service.impl.ResourceService;

/**
 * demo演示
 * 
 * @author n004881
 */
@Controller
@RequestMapping("/demo")
@Description("demo测试")
public class DemoController extends CrudController<String, PageResult<DemoDTO>, DemoDTO> {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private ResourceService service;

    @Autowired
    private OperationService operationService;

    @Autowired
    private DemoService demoService;

    @Autowired
    CacheManager cache;

    @Autowired
    private ExcelResolver<DemoDTO, PageResult<DemoDTO>, String> excelResolver;

    @Autowired
    RedisUtilsService redisUtilsService;

    @Autowired
    public AsyncService asyncService;

    @RequestMapping(value = "test1", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String test1() {

        // Object obj = cache.getCache("shiro-activeSessionCache");
        // if (obj != null) {
        // EhCache ehcache = (EhCache) obj;
        // System.out.println(ehcache.size());
        // }
        // Subject subject = SecurityUtils.getSubject();
        // if (subject != null) {
        // System.out.println(subject.getSession().getId());
        // }
        // Collection<Session> sessions = sessionDAO.getActiveSessions();
        // if(sessions!=null && sessions.size() > 0 ){
        // for (Session session : sessions) {
        // System.out.println("登录id:" + session.getId());
        // System.out.println("登录getLastAccessTime:" + session.getLastAccessTime());
        // System.out.println("登录ip:" + session.getHost());
        // System.out.println("登录用户:" + session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
        // System.out.println("最后操作日期:" + session.getLastAccessTime());
        // System.out.println("=============");
        //
        // }
        // }
        return "我们1";
    }

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        try {
            //            JedisCluster JedisCluster = (redis.clients.jedis.JedisCluster) WebUtil.getContext().getBean("jedisCluster");
            System.out.println(redisUtilsService.get("aaa"));
            System.out.println(redisUtilsService.get("bbb"));
            System.out.println(redisUtilsService.get("ccc"));
            System.out.println(redisUtilsService.getClusterNodes());
            System.out.println(redisUtilsService.get("aaa"));
            System.out.println(redisUtilsService.getShard("32.1.29.110"));
            System.out.println(redisUtilsService.getShardInfo("32.1.29.110"));
            System.out.println(redisUtilsService.hkeys("a*"));
            // 获取所有的keys
            System.out.println(redisUtilsService.hkeys("hashs"));
        } catch (BeansException e) {
            e.printStackTrace();
        }
        demoService.count();
        return "demo/index";
    }

    /**
     * 返回虚拟机状态
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "status/index")
    public String status(HttpServletRequest request) {
        DecimalFormat df = new DecimalFormat("0.00");
        double totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024.0;// 理论获取的最大内存
        double maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024.0;// 当前已经获取的内存
        double freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024.0;// 空闲内存
        request.setAttribute("freeMemory", df.format(freeMemory));
        request.setAttribute("totalMemory", df.format(totalMemory));
        request.setAttribute("maxMemory", df.format(maxMemory));

        return "demo/status";
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public String get() {
        return "我们991";
    }

    @RequestMapping(value = "test2.json")
    @ResponseBody
    public String test2() {
        return "我们991";
    }

    @RequestMapping(value = "test")
    @ResponseBody
    public Map<String, String> test() {
        // EhcacheUtil.put("test", "a", "1111");
        System.out.println(EhcacheUtil.get("test", "a"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "我们");
        map.put("b", "我们");
        map.put("c", "我们");
        map.put("d", null);
        return map;
    }

    /**
     * 页面缓存测试
     * 
     * @return
     */
    @RequestMapping(value = "pageCache")
    @ResponseBody
    public String pageCache() {
        System.out.println("123123");
        return "123123123";
    }

    @RequestMapping(value = "ztree")
    @ResponseBody
    public List<Map<String, Object>> ztree() {
        return service.buildTree(null);
    }

    @RequestMapping(value = "/ztreePreview")
    public String getView(Model model) {
        logger.info("日志测试");

        model.addAttribute("operations", operationService.findAll(null));

        return "/demo/ztree";
    }

    @RequestMapping(value = "/preview2")
    public String getView2(Model model) {
        return "/demo/demoForm";
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    @Description(value = "导入数据")
    public Map<String, Object> importExcel(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            // 获取项目根路径
            String templatePath = this.getClass().getClassLoader().getResource("template/demo.xml").getPath();
            Map<String, Object> extInfo = new HashMap<String, Object>();

            ExcelRequest excelRequest = new ExcelRequest();
            excelRequest.setPageSize(10);
            excelRequest.setPosition(0);
            excelRequest.setSuffix(".xls");
            excelRequest.setExtInfo(extInfo);
            excelRequest.setTemplate(templatePath);

            // 文件上传路径
            String excelPath = request.getParameter("excelPath");
            // 获取绝对路径
            String rootpath = request.getSession().getServletContext().getRealPath(excelPath);
            InputStream inputStream = new FileInputStream(new File(rootpath));

            excelResolver.doRead2DB(inputStream, demoService, excelRequest);
            result.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/exportData")
    @ResponseBody
    @Description(value = "导出数据")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        try {
            Searchable s = DynamicSearchUtils.toSearchable(request);
            s.addSearchParam("name", Operator.LIKE, request.getParameter("name"));

            // 以系统当前时间为excel的名字
            String fileName = Long.toString(System.currentTimeMillis()) + ".xls";

            // 获得输出流
            OutputStream os = response.getOutputStream();
            response.reset();

            // 设置导出excel提示框的名字以及内容的编码方式
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/msexcel; charset=UTF-8");

            excelResolver.doWrite2FS(os, demoService, s, null, "template/demo.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/async", method = RequestMethod.GET)
    @ResponseBody
    public Callable<String> async() {
        return new Callable<String>() {

            public String call() throws Exception {

                // Do some work..  
                Thread.sleep(3000L);

                return "The String ResponseBody";
            }
        };
    }

    /**
     * 异步方法测试
     * 
     * @return
     */
    @RequestMapping(value = "testAsyncMethod")
    @ResponseBody
    public String testAsyncMethod() {
        System.out.println("start");
        asyncService.asyncMethod();
        System.out.println("end");
        return "我已经执行了";
    }

    public PagingService<DemoDTO, PageResult<DemoDTO>, String> getService() {
        return demoService;
    }

}
