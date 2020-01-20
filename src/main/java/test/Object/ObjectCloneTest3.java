package test.Object;

import cn.hutool.core.bean.BeanUtil;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/24
 * @描述  深复制
 */
public class ObjectCloneTest3 {
    public static void main(String[] args) {
        Person p1 = new Person(10, "ll", new Race("yellow", "Asia"));
        Person p2 = (Person) p1.clone();
        System.out.println(p1.getName() == p2.getName());//浅复制
        System.out.println(p1.getRace() == p2.getRace());//深复制
       System.out.println(p1.getTestArray() == p2.getTestArray());//深复制

    }

}
class Person implements Cloneable {
    int age;
    String name;
    Race race;
    int[] testArray = { 1, 23, 5, 6, 0 };

    Person(int age, String name, Race race) {
        this.age = age;
        this.name = name;
        this.race = race;
    }

    @Override
    public Person clone() {
        Person o = null;

        try {
            o = (Person) super.clone();
            o.setRace(this.race.clone());
            o.setTestArray(testArray.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return o;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setTestArray(int[] testArray) {
        this.testArray = testArray;
    }

    public int[] getTestArray() {
        return testArray;
    }

}

class Race implements Cloneable {
    String color; // 颜色
    String distribution; // 分布

    public Race(String color, String distribution) {
        super();
        this.color = color;
        this.distribution = distribution;
    }

    @Override
    public Race clone() throws CloneNotSupportedException {
        return (Race) super.clone();
    }

}

