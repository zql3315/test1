package com.infosky.sys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.infosky.common.Action;
import com.infosky.common.template.Node;
import com.infosky.common.template.Node.Leaf;
import com.infosky.common.util.ReflectUtils;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.framework.service.exception.ServiceException;

/**
 * 
 * XML节点描述文件解析类,主要完成模板<->数据模型的互转
 * 
 * @version  [版本号, 2014年8月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class NodeResolver<K extends Serializable> {

    /**
     * Array->DTO的转换
     * @param template
     * @param row
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public DTO<K> doExecute(Node node, Object... row) throws ServiceException {
        DTO<K> tagertObj = null;

        try {
            //最上层的父节点
            tagertObj = (DTO<K>) ReflectUtils.newInstance(node.getDataType());

            //递归填充组合结构的对象
            this.doSearch(node, tagertObj, new Action<K>() {

                @Override
                public Object[] doAction(DTO<K> target, Leaf leaf, Object... row) {
                    if (!doCheck(target, leaf, row)) {
                        throw new ServiceException("异常字段为:" + leaf.getName() + ",异常值为:" + row[leaf.getPosition()]);
                    }

                    Object obj = doConvert(target, leaf, row);

                    try {
                        PropertyUtils.setProperty(target, leaf.getName(), obj);
                    } catch (Exception e) {
                        throw new ServiceException("字段" + leaf.getName() + "异常,类型为:" + obj.getClass() + "值为:" + obj, e);
                    }
                    return row;
                }

                @Override
                public boolean doCheck(DTO<K> target, Leaf leaf, Object... row) {
                    //校验数组中的值是否合法
                    Object value = row[leaf.getPosition()];
                    List<String> checkList = leaf.getList();

                    if (!check(checkList, value)) {
                        return false;
                    }

                    return true;
                }

                @Override
                public Object doConvert(Object obj, Leaf leaf, Object... row) {
                    Object value = row[leaf.getPosition()];
                    String c = leaf.getConvert();
                    obj = convert(c, value);

                    return obj;
                }
            }, row);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return tagertObj;
    }

    /**
     * DTO->Array的转换  
     * @param template
     * @param row
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Object[] doExecute(Node node, DTO<K> rowObj) throws ServiceException {
        Object[] row = null;

        try {
            //初始化数组
            row = new Object[node.getSize()];

            row = this.doSearch(node, rowObj, new Action<K>() {

                @Override
                public Object[] doAction(DTO<K> target, Leaf leaf, Object... row) {
                    //target为null或者必需取到的属性值为null 默认都为异常
                    Object value = null;
                    try {
                        if (null != target) {
                            value = PropertyUtils.getProperty(target, leaf.getName());
                        }
                    } catch (Exception e) {
                        throw new ServiceException("获取字段" + leaf.getName() + "异常", e);
                    }

                    row[leaf.getPosition()] = (null == target ? leaf.getDesc() : value);

                    return row;
                }

                @Override
                public boolean doCheck(DTO<K> target, Leaf leaf, Object... row) {
                    return true;
                }

                @Override
                public Object doConvert(Object obj, Leaf leaf, Object... row) {
                    return obj;
                }
            }, row);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return row;
    }

    /**
     * 读模板
     * @param template
     * @return
     * @throws JAXBException 
     * @throws FileNotFoundException 
     * @see [类、类#方法、类#成员]
     */
    public Node doRead(String template) throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
        Node node = (Node) jaxbUnMarshaller.unmarshal(new FileInputStream(new File(template)));

        return node;
    }

    /**
     * 递归遍历到每个叶子节点处理
     * @param currentNode
     * @param rowObj
     * @param action
     * @param row
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    protected Object[] doSearch(Node currentNode, DTO<K> rowObj, Action<K> action, Object... row) {
        //当前节点下属性
        List<Leaf> leafs = currentNode.getLeaves();

        Object[] srcRow = Arrays.copyOf(row, row.length);

        //迭代填充属性
        for (Leaf leaf : leafs) {
            row = action.doAction(rowObj, leaf, row);
        }

        //子节点
        List<Node> nodeSubs = currentNode.getNodes();

        //如果无子节点了直接返回
        if (null == nodeSubs) {
            return row;
        }

        for (Node node : nodeSubs) {
            DTO<K> subObj = null;
            try {
                if (rowObj != null) {
                    subObj = (DTO<K>) PropertyUtils.getProperty(rowObj, node.getName());
                }
            } catch (Exception e) {
                throw new ServiceException("获取字段" + node.getName() + "异常", e);
            }

            //数组前后是否发生变化,如果数组没有发生变化且父类get出来的subObj为null则说明为Array->Object的改变,否则为Object->Array的改变
            if (Arrays.equals(srcRow, row) && subObj == null) {
                try {
                    subObj = (DTO<K>) ReflectUtils.newInstance(node.getDataType());
                } catch (Exception e) {
                    throw new ServiceException("获取类型" + node.getDataType() + "异常", e);
                }
            }

            srcRow = Arrays.copyOf(row, row.length);
            row = doSearch(node, subObj, action, row);

            if (Arrays.equals(srcRow, row)) {
                //将subObj填充到父类
                try {
                    PropertyUtils.setProperty(rowObj, node.getName(), subObj);
                } catch (Exception e) {
                    throw new ServiceException("字段" + node.getName() + "异常,类型为:" + subObj.getClass() + "值为:" + subObj, e);
                }
            }
        }

        return row;
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param checkList
     * @param targetValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    private boolean check(List<String> checkList, Object targetValue) {
        try {
            //无校验规则,直接返回校验通过
            if (null == checkList) {
                return true;
            }

            for (String className : checkList) {

                Class checkObj = Class.forName(className);
                Method m = ReflectionUtils.findMethod(checkObj, "check");
                Boolean isCheck = (Boolean) m.invoke(checkObj, new Object[] {
                    targetValue
                });
                if (!isCheck) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param className
     * @param targetValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    private Object convert(String className, Object targetValue) {
        try {
            if (null == className) {
                return targetValue;
            }

            Class convObj = Class.forName(className);
            Method m = ReflectionUtils.findMethod(convObj, "convert", null);
            Object obj = m.invoke(convObj.newInstance(), targetValue);

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
