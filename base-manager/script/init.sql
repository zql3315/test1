
LOCK TABLES bs_project WRITE;
INSERT INTO bs_project (id,project_name,project_location,rpackage,struct,TYPE,staticDir,viewdir,url,project_user,project_password,driver,ftldir) VALUES 
('1','cms','F:/360Downloads','cn.cms','1','1','F:/wks/work/b2r/b2r-build/src/main/webapp/static','views','jdbc:mysql://127.0.0.1:3306/efsdb?useUnicode=true&characterEncoding=utf-8','root','root','com.mysql.jdbc.Driver','classpath:/ftl/');
UNLOCK TABLES;

LOCK TABLES bs_table WRITE;
INSERT INTO bs_table (id,NAME,ename,sn,description) VALUES 
('2086c00a4be33e85014be34a85360000','sys_user','User','user','用户表'),
('2086c00a4be33e85014be36422850002','sys_role_user','RoleUser','roleUser',' 角色与用户'),
('2c9086814bcdd6a7014bcddac5070002','sys_permission','Permission','permission','权限');
UNLOCK TABLES;

LOCK TABLES bs_project_table WRITE;
INSERT INTO bs_project_table (id,pid,tid) VALUES 
('2c9086814bcdd6a7014bcddac5080003','1','2c9086814bcdd6a7014bcddac5070002'),
('2086c00a4be33e85014be34a85420001','1','2086c00a4be33e85014be34a85360000'),
('2086c00a4be33e85014be36422860003','1','2086c00a4be33e85014be36422850002');
UNLOCK TABLES;

LOCK TABLES sys_resource WRITE;
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('0', 'aaa', 'fa-home home-icon', 'root', '0', 'home', '1', '#', NULL);
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('1', '系统管理', 'fa-cog', '系统管理', '1', 'system', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('11', '用户管理', 'fa-user', '用户管理', '1', 'user', '2', '/user/preview', '1');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('111', '密码文本框', 'fa-save', '密码文本框', '5', 'password', '3', '#', '11');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('112', '重置密码', 'fa-save', '重置密码', '1', 'resetPassword', '3', '##', '11');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('12', '角色管理', 'fa-users', '角色管理', '2', 'role', '2', '/role/preview', '1');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('121', '增加角色', 'fa-plus', '增加角色', '2', 'add', '3', '/role/toAdd', '12');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('13', '资源管理', 'fa-truck', '资源管理', '3', 'resource', '2', '/resource/preview', '1');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('14', '组织机构管理', 'fa-lock', '组织机构管理', '4', 'organization', '2', '/organization/preview', '1');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('15', '在线管理', 'fa-lock', '当前有多少人在线', '5', 'online', '2', '/user/online', '1');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('16', '字典管理', 'fa-truck', '字典管理', 6, 'dictionary', '2', '/dictionary/preview/', '1');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('17', '区域管理', 'fa-truck', '区域管理', 7, 'area', '2', '/area/index/', '1');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('2', '内容管理', 'fa-file-text-o', '内容管理', '2', 'content', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('21', '内容发布', 'fa-save', '内容发布', '1', 'publish', '2', '/cms/article/preview', '2');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('22', '内容发布', 'fa-save', '内容下线', '2', 'offline', '2', '#', '2');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('3', '示例管理', 'fa-book', '示例管理', '3', 'demo', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('31', 'ztree示例', 'fa-inbox', 'ztree示例', '1', 'tree', '2', '/demo/ztreePreview', '3');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('32', '示例管理', 'fa-inbox', '示例管理', '1', 'demoManager', '2', '/demo/preview', '3');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('33','二维码demo','fa-camera-retro ','二维码','3','qrcode','2','/demo/qrcode/index','3');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('4', '项目管理', 'fa-file-text-o', '项目管理', '2', 'project', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('41', '项目发布', 'fa-save', '项目发布', '1', 'publish', '2', '/project/preview', '4');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('42', '项目数据表', 'fa-save', '项目数据表', '1', 'publish', '2', '/table/preview', '4');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('5', '报表管理', 'fa-signal', '报表管理', '1', 'report', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('51', '统计图表', 'fa-file-text-o', '统计图表', '1', 'pieChart', '1', '/report/index', '5');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('6', '任务管理', 'fa-file-text-o', '任务管理', '2', 'taskManager', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('61', '任务管理列表', 'fa-book', '任务管理', '1', 'taskList', '2', '/task/preview', '6');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('7', '访问日志', 'fa-file-text-o', '访问日志', '2', 'visitLog', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('71', '访问日志', 'fa-book', '访问日志', '1', 'visitLogList', '2', '/visitLog/preview', '7');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('8', '微信管理', 'fa-file-text-o', '微信管理', '2', 'WeiXinManager', '1', '#', '0');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('81', '微信自定义菜单', 'fa-save', '微信自定义菜单', '2', 'customMenu', '2', '/weiXin/customMenu/preview', '8');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('82', '微信公众账号配置', 'fa-user', '微信公众账号配置', '1', 'weiXinPublicAccount', '2', '/weiXin/weiXinPublicAccount/toEdit', '8');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('83', '微信关键词', 'fa-user', '微信关键词', '3', 'weiXinKeyword', '2', '/weiXin/weiXinKeyword/preview', '8');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('84', '素材管理', 'fa-user', '素材管理', '3', 'MaterialManager', '2', '/weiXin/weiXinMaterial/preview', '8');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('8a94d1b756dffd7f0156e00c0f6f0001', '二维码', 'fa-truck', '二维码', 3, 'qrcode', '2', '/demo/qrcode/index', '3');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('8a94d1b756dffd7f0156e00df0120003', '系统状态', 'fa-truck', '系统状态', 5, 'status', '1', '/demo/status/index', '3');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('8a94d1b75741c3e2015746f2a5a50001', '地图分布', 'fa-truck', '系统状态', 5, 'maps', '1', '/demo/map/', '3');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('8a94d1b757e165820157e166c9920001', '百度编辑器', 'fa-truck', '百度编辑器', '6', 'bdedit', '2', '/demo/bdedit/', '3');
INSERT INTO sys_resource (id,description,icons,NAME,priority,sn,TYPE,url,pid) VALUES ('8a94d1b757f4b4790157f535b0b70001', '百度地图', 'fa-truck', '百度地图', '7', 'bdmap', '2', '/demo/bdmap/', '3');
UNLOCK TABLES;

LOCK TABLES sys_operation WRITE;
INSERT INTO sys_operation VALUES ('1','增','add'),('2','删','delete'),('3','改','update'),('4','查','view'),('5','All','*');
UNLOCK TABLES;

LOCK TABLES sys_role WRITE;
INSERT INTO sys_role (id,NAME,sn,description,created_date,creator,STATUS) VALUES 
('2086c00a4be7dac8014be7dff2900001','超级管理员','super','超级管理员','2015-03-05 00:00:00','zql',-1),
('2c9086814bce38cd014bce39a9270000','测试角色','test','测试角色','2015-02-18 00:00:00','zql',0);
UNLOCK TABLES;

LOCK TABLES sys_permission WRITE;
INSERT INTO sys_permission (id,rid,reid,oid) VALUES 
('0','2086c00a4be7dac8014be7dff2900001','0','5'),
('1','2c9086814bce38cd014bce39a9270000','3','1'),
('2','2c9086814bce38cd014bce39a9270000','3','2'),
('3','2c9086814bce38cd014bce39a9270000','3','3'),
('4','2c9086814bce38cd014bce39a9270000','3','4');
UNLOCK TABLES;

LOCK TABLES sys_user WRITE;
INSERT INTO sys_user (id,loginname,NAME,PASSWORD,salt,email,telephone,pid,STATUS)VALUES 
('2086c00a4be405d3014be40cb7f50000','admin','admin','57dde8da093e9c67a44be14393feac6010c953af','7f2fc1a662a84db3','111@qq.com','13913874862',NULL,-1),
('2086c00a4be7e71f014be7f19a8b0000','test',' 测试','f63b0678c1f01cb43ccd142edb46b94cc5b3510a','0b71e0c7f52912c7','222@.com','13913874862','2086c00a4be405d3014be40cb7f50000',0);
UNLOCK TABLES;

LOCK TABLES sys_role_user WRITE;
INSERT INTO sys_role_user (id,rid,user_id)VALUES 
('2086c00a4be405d3014be40cb8010001','2c9086814bce38cd014bce39a9270000','2086c00a4be405d3014be40cb7f50000'),
('2086c00a4be7e71f014be7f19a9c0001','2086c00a4be7dac8014be7dff2900001','2086c00a4be7e71f014be7f19a8b0000');
UNLOCK TABLES;

LOCK TABLES sys_dictionary WRITE;
INSERT INTO sys_dictionary (id,datakey,itemcode,itemvalue,pid)VALUES 
('1','dd_resource_type_key','1','模块',NULL),('2','dd_resource_type_key','2','页面',NULL),
('3','dd_user_status_key','0','待激活',NULL),('4','dd_user_status_key','1','正式',NULL),
('5','dd_user_status_key','2','禁用',NULL),('6','dd_user_status_key','3','删除',NULL),
('7','dd_project_type_key','0','normal',NULL),('8','dd_project_type_key','1','maven',NULL),
('9','dd_project_struct_key','0','normal',NULL),('10','dd_project_struct_key','1','web',NULL),
('11','dd_whether_key','0','否',NULL),('12','dd_whether_key','1','是',NULL),('13','dd_resource_type_key','3','元素',NULL);
UNLOCK TABLES;

LOCK TABLES weixin_publicaccount WRITE;
INSERT  INTO weixin_publicaccount (id,accesstoken,accesstokencreatetime,accesstokenexpiresin,accountname,accountwxid,appid,appsecret,createtime,default_reply_keyword,first_reply_keyword,ticket,ticketcreatetime,ticketexpiresin,token,userid) 
VALUES ('1','aaaaaaa','2016-03-16 09:57:14','7200','11','1','wxfb72f1a7d061d631','f65872e4b45f7ef3d9ebec601a336ce1','2016-03-16 10:01:46','Hello','Hello','bbbbb','2016-03-16 09:57:14','7200','13954685391 ',1);
UNLOCK TABLES;