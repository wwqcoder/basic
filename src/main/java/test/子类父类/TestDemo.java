package test.子类父类;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/15
 * @描述
 */
class Person1 {

    /*public Person(){
        System.out.println("tom");
    }*/

    public Person1(String name)
    {
        System.out.println("My name is  "+name);
    }
}
class Man extends Person1{

    public Man(){
        //super();
        super("jack");

    }
}
public class TestDemo   {
    public static void main(String []args) {
        Man m = new Man();
    }
}
