package test.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.CallbackFilter;

public class MyProxyFilter implements CallbackFilter {

    @Override
    public int accept(Method arg0) {
        //过滤器用来过滤query方法
        if (!"query".equalsIgnoreCase(arg0.getName())) return 0;
        return 1;
    }
}