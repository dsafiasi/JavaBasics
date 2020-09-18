package bean;

import java.util.concurrent.atomic.AtomicInteger;

public class Student {
    private AtomicInteger id = new AtomicInteger(0);
    private static final Student instance = new Student();

    public static Student getInstance() {
        return instance;
    }
    public int getId() {
        return id.incrementAndGet();
    }

    public static void main(String[] args) {

        int i1 = Student.getInstance().getId();
        int i2 = Student.getInstance().getId();
        int i3 = Student.getInstance().getId();
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);

    }
}
