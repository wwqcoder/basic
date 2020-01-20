package test.子类父类;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/15
 * @描述
 */
public class Vehicle {
    Vehicle() { //父类构造方法 构造器  4
        System.out.println("父类无参构造器");
    }
    static {//1
        System.out.println("父类静态块初始化");
    }

    {//3
        System.out.println("父类普通初始化块");
    }
}
class Test5 extends Vehicle{
    Test5() {//子类构造方法构造器
        System.out.println("子类无参构造器");
    }
        int speed;
    Test5(int speed){//子类有参构造方法
        this.speed=speed;
        System.out.println(speed);
    }
    static{//2
        System.out.println("子类静态块初始化");
    }
        //普通的初始化块
    {
        System.out.println("子类普通初始化块");
    }
public static void main(String[] args) {
        Vehicle b1=new Vehicle();//    Vehicle is created
        System.out.println("-------------------");
        Test5 b=new Test5(128);//Vehicle is created    instance initializer block invoked 128
        System.out.println("-----------------");
        Test5 b2=new Test5(256);//Vehicle is created    instance initializer block invoked 256
        }
}
