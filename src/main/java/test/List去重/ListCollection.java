package test.List去重;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述
 */
public class ListCollection {

    // 遍历后判断赋给另一个list集合，保持原来顺序
    public static void ridRepeat1(List<String> list) {
        System.out.println("list = [" + list + "]");
        List<String> listNew = new ArrayList<String>();
        for (String str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        System.out.println("listNew = [" + listNew + "]");
    }

    // set集合去重，保持原来顺序
    public static void ridRepeat2(List<String> list) {
        System.out.println("list = [" + list + "]");
        List<String> listNew = new ArrayList<String>();
        Set set = new HashSet();
        for (String str : list) {
            if (set.add(str)) {
                listNew.add(str);
            }
        }
        System.out.println("listNew = [" + listNew + "]");
    }

    // Set去重     由于Set的无序性，不会保持原来顺序
    public static void ridRepeat3(List<String> list) {
        System.out.println("list = [" + list + "]");
        Set set = new HashSet();
        List<String> listNew = new ArrayList<String>();
        set.addAll(list);
        listNew.addAll(set);
        System.out.println("listNew = [" + listNew + "]");
    }

    // Set去重（将ridRepeat3方法缩减为一行） 无序
    public static void ridRepeat4(List<String> list) {
        System.out.println("list = [" + list + "]");
        List<String> listNew = new ArrayList<String>(new HashSet(list));
        System.out.println("listNew = [" + listNew + "]");
    }

    // Set去重并保持原先顺序的两种方法
    public static void ridRepeat5(List<String> list) {
        System.out.println("list = [" + list + "]");
        List<String> listNew = new ArrayList<String>(new TreeSet<String>(list));
        List<String> listNew2 = new ArrayList<String>(new LinkedHashSet<String>(list));
        System.out.println("listNew = [" + listNew + "]");
        System.out.println("listNew2 = [" + listNew2 + "]");
    }
    //利用java8的stream去重
    public static void ridRepeat6(List<String> list){
        List uniqueList = list.stream().distinct().collect(Collectors.toList());
        System.out.println(uniqueList.toString());
    }
    //使用Java8新特性stream去重
    public static void ridRepeat7(){
        List<User> userList = new ArrayList<User>();
        userList.add(new User("小黄",10));
        userList.add(new User("小红",23));
        userList.add(new User("小黄",78));
        userList.add(new User("小黄",10));
        //根据name属性去重
        List<User> unique1 = userList.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparing(User::getName))), ArrayList::new));

        System.out.println(unique1.toString());

        //根据name,age属性去重
        List<User> unique2 = userList.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparing(o -> o.getName() + ";" + o.getAge()))), ArrayList::new)
        );

        System.out.println(unique2.toString());
    }

    //对象中重写equals()方法和hashCode()方法
    public static void ridRepeat8(){
        List<User> userList = new ArrayList<User>();
        userList.add(new User("小黄",10));
        userList.add(new User("小红",23));
        userList.add(new User("小黄",78));
        userList.add(new User("小黄",10));

        //使用HashSet,无序
        Set<User> userSet = new HashSet<User>();
        userSet.addAll(userList);
        System.out.println(userSet);

        //使用LinkedHashSet，有序
        List<User> listNew = new ArrayList<User>(new LinkedHashSet(userList));
        System.out.println(listNew.toString());
    }

    public static void main(String[] args) {
        ridRepeat8();

        ArrayList<String> strings = new ArrayList<>(new HashSet<String>(new ArrayList<String>()));
    }
}
