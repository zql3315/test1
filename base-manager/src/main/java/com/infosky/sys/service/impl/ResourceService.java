package com.infosky.sys.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.common.util.date.DateUtils;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.notify.dao.NotificationDAO;
import com.infosky.notify.entity.po.Notification;
import com.infosky.sys.dao.ResourceDAO;
import com.infosky.sys.entity.dto.PermissionDTO;
import com.infosky.sys.entity.dto.ResourceDTO;
import com.infosky.sys.entity.po.Permission;
import com.infosky.sys.entity.po.Resource;
import com.infosky.task.dao.TaskDAO;
import com.infosky.task.entity.po.Task;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zan
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Lazy(value = false)
@Transactional
public class ResourceService extends JpaService<Resource, ResourceDTO, PageResult<ResourceDTO>, String> {

    @Autowired
    private ResourceDAO dao;

    @Autowired
    protected ExcelResolver<ResourceDTO, PageResult<ResourceDTO>, String> resolver;

    @Autowired
    protected TaskDAO taskDAO;

    @Value("${export.folder}")
    private String folder;

    @Value("${export.file}")
    private String file;

    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private PushService<String> pushService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户角色查询资源
     * @param roleId
     * @param pid
     * @return
     */
    public List<ResourceDTO> findByRoleId(String[] roleId, String pid) {
        List<PermissionDTO> permissionlist = new ArrayList<PermissionDTO>();
        List<ResourceDTO> list = new ArrayList<ResourceDTO>();
        for (int i = 0; i < roleId.length; i++) {
            permissionlist = BeanMapper.mapList(dao.findByRoleId(roleId[i], pid), PermissionDTO.class);
            for (int j = 0; j < permissionlist.size(); j++) {
                list.add(permissionlist.get(j).getResource());
            }
        }
        return list;
    }

    /**
     * Ztree
     * 
     * @param rid 角色id
     * @return
     */
    public List<Map<String, Object>> buildTree(String rid) {
        //查询sys_resouce
        Searchable s = new SearchRequest();
        s.addSearchParam("parent.id", Operator.ISNULL, null);
        Specification<Resource> specification = DynamicSearchUtils.toSpecification(s);
        List<Resource> resources = dao.findAll(specification);
        Subject subject = SecurityUtils.getSubject();
        List<Permission> permissionList = new ArrayList<Permission>();
        if (StringUtils.isNotBlank(rid)) {
            permissionList = dao.findByRoleId(rid);
        }
        return iterator(resources, subject, permissionList);
    }

    /**
     * 迭代子节点
     * 
     * @param resources
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected List<Map<String, Object>> iterator(List<Resource> resources, Subject subject, List<Permission> permissionList) {
        List<Map<String, Object>> treeData = Lists.newArrayList();
        for (Resource r : resources) {
            List<String> list = Lists.newArrayList();
            //查看菜单默认需要有view权限
            list.add("view");
            this.getPermPattern(r, list);
            Collections.reverse(list);
            if (!subject.isPermitted(StringUtils.join(list, ":"))) {
                continue;
            }

            Map<String, Object> node = Maps.newHashMap();
            node.put("id", r.getId());
            node.put("pId", r.getParent() != null ? r.getParent().getId() : null);
            node.put("name", r.getName());
            node.put("type", r.getType());
            if (r.getType().equals("1")) {
                node.put("iconSkin", "  ico_open ");
            }
            if (r.getType().equals("2")) {
                node.put("iconSkin", "  ico_docu ");
            }
            if (r.getType().equals("3")) {
                node.put("iconSkin", "  modelbutton ");
            }
            for (Permission permission : permissionList) {
                if (permission.getResource().getId().equals(r.getId())) node.put("checked", true);
            }
            node.put("open", true);

            //如果有子节点
            if (!r.getChildren().isEmpty()) {
                List<Map<String, Object>> subNode = iterator(r.getChildren(), subject, permissionList);
                node.put("children", subNode);
            }

            treeData.add(node);
        }
        return treeData;
    }

    private List<String> getPermPattern(Resource resource, List<String> list) {
        list.add(resource.getSn());

        Resource parent = resource.getParent();
        if (parent != null) {
            list = getPermPattern(parent, list);
        }

        return list;
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Resource, String> getDAO() {
        return dao;
    }

    //@Scheduled(cron = "0/5 * * * * *")
    public void ScheduledExport() throws JAXBException {
        Searchable s = new SearchRequest();

        s.addSearchParam("type", Operator.EQ, "1");
        s.addSearchParam("status", Operator.EQ, "1");

        Specification<Task> spec = DynamicSearchUtils.toSpecification(s);

        //查询导出任务
        List<Task> tasks = taskDAO.findAll(spec);

        if (tasks.isEmpty()) {
            return;
        }

        OutputStream os = null;

        JAXBContext jaxbContext = JAXBContext.newInstance(SearchRequest.class);
        Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
        String ymd = DateUtils.date2Str(new Date(), "yyyyMMdd");
        File xlsDir = new File(folder + File.separator + ymd);
        //判断目录是否存在
        if (!xlsDir.exists()) {
            xlsDir.mkdirs();
        }

        String ymdhms = DateUtils.date2Str(new Date(), "yyyyMMddHHmmss");

        for (Task task : tasks) {
            //更新任务为 执行中
            task.setStatus("2");
            taskDAO.save(task);

            try {
                SearchRequest sr = (SearchRequest) jaxbUnMarshaller.unmarshal(new StringReader(task.getRequestbody()));

                os = new FileOutputStream(new File(folder + File.separator + ymd + File.separator + ymdhms + file));
                String templateName = "template/resource.xml";
                resolver.doWrite2FS(os, this, sr, null, templateName);

                //更新任务为 成功
                task.setStatus("3");
                taskDAO.save(task);

                //添加通知
                Notification notify = new Notification();

                notify.setSystem("Excel");
                notify.setTitle("Excel 导出");
                notify.setTargetId(task.getCreator());
                notify.setIsRead("non-read");
                notify.setDate(new Date());
                notify.setAttachment(folder + File.separator + ymd + File.separator + ymdhms + file);

                notificationDAO.save(notify);

                Searchable ss = new SearchRequest();
                ss.addSearchParam("isRead", Operator.EQ, "non-read");

                Specification<Notification> specification = DynamicSearchUtils.toSpecification(ss);

                List<Notification> notifies = notificationDAO.findAll(specification);

                Map<String, Object> data = Maps.newHashMap();
                data.put("notifications", notifies);
                data.put("count", notifies.size());

                pushService.push("admin", data);
            } catch (Exception e) {
                e.printStackTrace();
                //更新任务为 失败
                task.setStatus("4");
                taskDAO.save(task);
            }
        }
    }

    public Collection<ResourceDTO> findParentResource() {
        Collection<Resource> resources = dao.findParentResource();

        return BeanMapper.mapList(resources, ResourceDTO.class);
    }

    public void deleteReById(String reId) {
        Searchable s = new SearchRequest();
        s.addSearchParam("resource.id", Operator.EQ, reId);
        List<PermissionDTO> pList = (List<PermissionDTO>) permissionService.findAll(s);
        permissionService.deleteAll(pList);
        dao.delete(reId);
    }
}
