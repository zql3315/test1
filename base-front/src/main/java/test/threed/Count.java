package test.threed;

public class Count {  
    public void count() {  
        int num = 0;  
        for(int i = 1; i <= 100; i++) {  
            num += i;  
        }  
        System.out.println(Thread.currentThread().getName() + "----" + num);  
    }  
} 
