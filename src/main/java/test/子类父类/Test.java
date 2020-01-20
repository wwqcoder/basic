package test.子类父类;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/15
 * @描述
 */
public class Test {
    public static void main(String[] args) {
        /*System.out.println("子类主方法");
        Student s1 = new Student();
        Student s2 = new Student();
        s1.eat();
        s2.eat();*/

        System.out.println("子类主方法");
        Student s1 = new Student("tom",20,1);
        Student s2 = new Student("jack",25,5);
        s1.eat();
        s2.eat();

    }
}
class Person {
    String name;
    int age;
    {
        System.out.println("父类的非静态代码块");
    }
    static {
        System.out.println("父类的静态代码块");
    }

    Person() {
        System.out.println("父类的无参构造函数");
    }

    Person(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("父类的有参数构造函数");
    }

    void eat() {
        System.out.println("父类吃饭");
    }

}

class Student extends Person {
    int grade;
    {
        System.out.println("子类的非静态代码块");
    }
    static {
        System.out.println("子类的静态代码块");
    }

    Student() {
        // super();//运行子类的要先运行父类无参构造函数，此处省略默认执行父类无参构造函数
        System.out.println("子类的无参构造函数");
    }

    Student(String name, int age, int grade) {
        // super(name,age);//运行子类的有参构造函数要先运行父类的有参构造函数，若父类无有参的，则运行父类无参的构造函数
        this.grade = grade;
        System.out.println("子类的有参数构造函数：" + name + ":" + age + ":" + grade);
    }

    @Override
    void eat() {
        System.out.println("子类吃饭");
    }

}

