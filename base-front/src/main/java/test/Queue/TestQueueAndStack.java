package test.Queue;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 测试jdk中的栈和队列
 * @author scott
 *
 */
public class TestQueueAndStack {
    
    /**
     * 测试队列
     * <pre>
     * 队列特点，先进先出，后进后出，火车过山洞例子
     * add: 增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
     * offer: 添加一个元素并返回true       如果队列已满，则返回false
     * </pre>
     */
    static void testQueue(){
        Queue<String> queue=new LinkedList<String>();
        //添加几个元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        queue.add("5");
        System.out.println("队列中的元素是:"+queue);
        //弹出元素
        queue.poll();
        System.out.println("队列中的元素是:"+queue);
        //查看队列中首个元素，并不移除
        String peek=queue.peek();
        System.out.println("查看队列中首个元素，并不移除:"+peek);
        System.out.println("队列中的元素是:"+queue);
    }
    
    
    /**
     * 测试栈
     * <pre>
     * 先进后出，后进先出，水桶倒水
     * </pre>
     */
    static void testStack(){
        Stack<String> stack=new Stack<String>();
        //添加几个元素
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");
        stack.add("1");
        stack.add("2");
        stack.add("3");
        stack.add("4");
        stack.add("5");
        System.out.println("栈中的元素是:"+stack);
        //弹出元素
        stack.pop();
        System.out.println("栈中的元素是:"+stack);
        //查看栈中首个元素，并不移除
        String peek=stack.peek();
        System.out.println("查看栈中首个元素，并不移除:"+peek);
        System.out.println("栈中的元素是:"+stack);
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("-------队--------");
        testQueue();
        System.out.println("-------栈--------");
        testStack();
    }

}