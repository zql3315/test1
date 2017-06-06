package com;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.infosky.common.util.SerializeUtil;
import com.infosky.redis.cluster.RedisClusterTemplate;
import com.infosky.redis.service.RedisUtilsService;


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
    "classpath*:/applicationContext-test.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class Junit4Test {

    @Autowired
    RedisUtilsService redisUtilsService;
    
    @Resource
    RedisClusterTemplate redisClusterTemplate;
    
    @Test
    public void testa() {
//        System.out.println(redisClusterTemplate.set("aaa","111"));
//        System.out.println(redisClusterTemplate.set("bbb","222"));
//        System.out.println(redisClusterTemplate.set("ccc","333"));
        System.out.println(redisClusterTemplate.get("aaa"));
        System.out.println(redisClusterTemplate.get("bbb"));
        System.out.println(redisClusterTemplate.get("ccc"));
    }
    @Test
    public void test() {
        
        
        redisUtilsService.set("aaa", "111");
        redisUtilsService.set("bbb", "222");
        redisUtilsService.set("ccc", "333");
        
        System.out.println(redisUtilsService.get("aaa"));
        System.out.println(redisUtilsService.get("bbb"));
        System.out.println(redisUtilsService.get("ccc"));
        
        System.out.println(redisUtilsService.getAllShards());
        System.out.println(redisUtilsService.getAllShardInfo());
        
//        System.out.println(redisClusterTemplate.get("aaa"));
//        System.out.println(redisClusterTemplate.get("bbb"));
//        System.out.println(redisClusterTemplate.get("ccc"));
        
        
//        redisUtilsService.hset("hashs", "555", "");
        Set<String> fields = redisUtilsService.hkeys("hashs");
        
        for(String field : fields){
            System.out.println(field+"=="+redisUtilsService.hget("hashs", field));
        }
        byte[] value = redisUtilsService.get("obj".getBytes());
        Object object = SerializeUtil.deserialize(value);
        if (object != null) {
            System.out.println(JSONObject.fromObject(object));
        }
    }

    @Test
    public void test1() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        redisUtilsService.set("oba".getBytes(), SerializeUtil.serialize(map));
    }
    @Test
    public void test2() {
        System.out.println(System.getProperty("user.dir"));
        byte[] bytes = redisUtilsService.get("e84351b2-763b-4eec-9ef7-919f3e613621".getBytes());
        if(bytes !=null){
            Session s = (Session)SerializeUtil.deserialize(bytes);
            System.out.println(JSONObject.fromObject(s));
        }
    }
    
}
