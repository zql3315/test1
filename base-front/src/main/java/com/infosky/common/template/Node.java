package com.infosky.common.template;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "node")
public class Node {

    /**
     * 节点规模,即节点下(包括子节点)的叶子数
     */
    private int size;

    /**
     * 复合结构属性引用名
     */
    private String name;

    /**
     * node节点className
     */
    private String dataType;

    /**
     * 基本人类型的属性
     */
    private List<Leaf> leaves;

    /**
     * 复合结构的属性节点
     */
    private List<Node> nodes;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "dataType")
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @XmlElement(name = "size")
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @XmlElements({
        @XmlElement(name = "leaf", type = Leaf.class)
    })
    public List<Leaf> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<Leaf> leaves) {
        this.leaves = leaves;
    }

    @XmlElements({
        @XmlElement(name = "node", type = Node.class)
    })
    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @XmlRootElement(name = "leaf")
    public static class Leaf {

        private String name;

        private Integer position;

        private String dataType;

        private String desc;

        private List<String> list;

        private String convert;

        @XmlElement(name = "name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name = "position")
        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        @XmlElement(name = "dataType")
        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        @XmlElement(name = "desc")
        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @XmlElements({
            @XmlElement(name = "check", type = String.class)
        })
        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        @XmlElement(name = "convert")
        public String getConvert() {
            return convert;
        }

        public void setConvert(String convert) {
            this.convert = convert;
        }
    }

    public static void main(String[] args) {
        List<String> checkList = new ArrayList<String>();
        checkList.add("java.Check");
        Node node = new Node();

        Leaf l1 = new Leaf();
        l1.setName("aa");
        l1.setDataType("java.lang.String");
        l1.setPosition(1);
        l1.setDesc("aaaaa");
        l1.setList(checkList);

        Leaf l2 = new Leaf();
        l2.setName("bb");
        l2.setDataType("java.lang.String");
        l2.setPosition(1);
        l2.setDesc("aaaaa");
        l2.setList(checkList);

        Leaf l3 = new Leaf();
        l3.setName("cc");
        l3.setDataType("java.lang.String");
        l3.setPosition(1);
        l3.setDesc("aaaaa");
        l3.setList(checkList);

        List<Leaf> ls = new ArrayList<Leaf>();
        ls.add(l1);
        ls.add(l2);
        ls.add(l3);

        node.setDataType("cn.com.git.ibcrm.model.project.ProjectConvPriceInfo");
        node.setLeaves(ls);

        Node node2 = new Node();
        node2.setDataType("cn.com.git.ibcrm.model.project.ProjectInfo");
        node2.setName("name2");
        node2.setLeaves(ls);

        Node node1 = new Node();
        node1.setName("name1");
        node1.setDataType("cn.com.git.ibcrm.model.project.ProjectInfo");
        node1.setLeaves(ls);

        Node node3 = new Node();
        node3.setDataType("cn.com.git.ibcrm.model.project.ProjectInfo");
        node3.setName("name3");
        node3.setLeaves(ls);

        List<Node> ns1 = new ArrayList<Node>();
        ns1.add(node3);
        node1.setNodes(ns1);

        List<Node> ns = new ArrayList<Node>();
        ns.add(node1);
        ns.add(node2);

        node.setNodes(ns);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //StringWriter sw = new StringWriter();

            // output pretty printed  
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            //jaxbMarshaller.marshal(object, file);  
            jaxbMarshaller.marshal(node, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
