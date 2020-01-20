package test.List去重;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private String name;

    private int age;

    //对象中重写equals()方法和hashCode()方法
    //重写equals方法
    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return name.equals(user.getName()) && (age==user.getAge());
    }

    //重写hashCode方法
    @Override
    public int hashCode() {
        String str = name + age;
        return str.hashCode();
    }
}
