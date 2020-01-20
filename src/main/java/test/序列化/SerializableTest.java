package test.序列化;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.*;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */

// 序列化和反序列化
class SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
// 对象赋值
        User user = new User();
        user.setName("老王");
        user.setAge(30);
        System.out.println(user);
        //1) Java 原生序列化方式
        // 创建输出流（序列化内容到磁盘）
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.out"));
        // 序列化对象
        oos.writeObject(user);
        oos.flush();
        oos.close();
        // 创建输入流（从磁盘反序列化）
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.out"));
        // 反序列化
        User user2 = (User) ois.readObject();
        ois.close();
        System.out.println(user2);

        //JSON 格式
        String jsonSerialize = JSON.toJSONString(user);
        User user3 = (User) JSON.parseObject(jsonSerialize, User.class);
        System.out.println(user3);

        //Hessian 方式序列化 Hessian 序列化的优点是可以跨编程语言，比 Java 原生的序列化和反序列化效率高
        // 序列化
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(bo);
        hessianOutput.writeObject(user);
        byte[] hessianBytes = bo.toByteArray();
        // 反序列化
        ByteArrayInputStream bi = new ByteArrayInputStream(hessianBytes);
        HessianInput hessianInput = new HessianInput(bi);
        User user4 = (User) hessianInput.readObject();
        System.out.println(user4);
    }
}

class User implements Serializable {
    private static final long serialVersionUID = 5132320539584511249L;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "{name:" + name + ",age:" + age + "}";
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
}
