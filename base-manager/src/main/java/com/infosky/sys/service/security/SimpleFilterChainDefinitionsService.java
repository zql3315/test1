package com.infosky.sys.service.security;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * 加载第三方角色资源配置服务类,可以通过调用updatePermission 来动态修改资源权限
 * 
 */
public class SimpleFilterChainDefinitionsService extends AbstractFilterChainDefinitionsService {

    @Override
    public Map<String, String> initOtherPermission() {
        // extend to load other permission
        Map<String, String> map = Collections.synchronizedMap(new LinkedHashMap<String, String>());
        //		map.put("/role/list","perms[home:system:role:view]");
        map.put("/**", "authc,perms");
        return map;
    }

}
