package test.List集合;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/7
 * @描述
 */
public class Person {
    private String name;
    private int age;
    private String province;

    public Person(String name, int age, String province) {
        this.name = name;
        this.age = age;
        this.province = province;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
}
