package test.BeanUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/16
 * @描述
 */
public class User implements Serializable {
    private static final long serialVersionUID = 5210726534179789239L;

    private String name ;
    private int age ;
    private boolean gender ;   //性别：trye-男，false-女
    private Date birthday ; //生日
    private String[] hobbies ; //爱好
    private String address ;
    private List<String> strong ; //特长
    private Map<String, String> fault ; //缺点
    private Student Student ; //内嵌学生对象

    private User() {}

    /*private static final User user = new User();

    public static User getInstance() {
        return user;
    }*/
    private static User user;
    public static User getInstance(){
        if (user == null){
            synchronized (User.class){
                if (user == null){
                    user = new User();
                }
            }
        }
        return user;
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
    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public List<String> getStrong() {
        return strong;
    }

    public void setStrong(List<String> strong) {
        this.strong = strong;
    }

    public Map<String, String> getFault() {
        return fault;
    }

    public void setFault(Map<String, String> fault) {
        this.fault = fault;
    }

    public Student getStudent() {
        return Student;
    }

    public void setStudent(Student student) {
        Student = student;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", hobbies=" + Arrays.toString(hobbies) +
                ", address='" + address + '\'' +
                ", strong=" + strong +
                ", fault=" + fault +
                ", Student=" + Student +
                '}';
    }
}
