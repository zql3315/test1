package test.cglib;

import org.apache.commons.codec.binary.Base64;

/**
 * http://blog.csdn.net/u011130752/article/details/52044976
 * 
 * @author n004881
 *
 */
public class Client {

    public static void main(String[] args) {
        //        BookServiceBean service = BookServiceFactory.getInstance();
        //        doMethod(service);

        BookServiceBean service = BookServiceFactory.getAuthInstance(new MyCglibProxy("boss"));
        service.update();
        BookServiceBean service2 = BookServiceFactory.getAuthInstance(new MyCglibProxy("john"));
        service2.update();

        BookServiceBean service3 = BookServiceFactory.getAuthInstanceByFilter(new MyCglibProxy("jhon"));
        service3.create();
        BookServiceBean service4 = BookServiceFactory.getAuthInstanceByFilter(new MyCglibProxy("jhon"));
        service4.query();

    }

    public static void doMethod(BookServiceBean service) {
        service.create();
        service.update();
        service.query();
        service.delete();
    }
}
