package test.cglib;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

public class BookServiceFactory {

    private static BookServiceBean service = new BookServiceBean();

    private BookServiceFactory() {
    }

    public static BookServiceBean getInstance() {
        return service;
    }

    /**
     * 提供一个使用代理的实例生成方法
     * 
     * @param myProxy
     * @return
     */
    public static BookServiceBean getAuthInstance(MyCglibProxy myProxy) {
        Enhancer en = new Enhancer();
        //进行代理     
        en.setSuperclass(BookServiceBean.class);
        en.setCallback(myProxy);
        //生成代理实例     
        return (BookServiceBean) en.create();
    }
    
    /**
     * 在工场中新增一个使用了过滤器的实例生成方法
     * 
     * @param myProxy
     * @return
     */
    public static BookServiceBean getAuthInstanceByFilter(MyCglibProxy myProxy){    
        Enhancer en = new Enhancer();     
        en.setSuperclass(BookServiceBean.class);     
        en.setCallbacks(new Callback[]{myProxy,NoOp.INSTANCE});     
        en.setCallbackFilter(new MyProxyFilter());     
        return (BookServiceBean)en.create();     
    } 
}
