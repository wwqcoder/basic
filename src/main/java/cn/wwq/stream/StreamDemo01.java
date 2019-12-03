package cn.wwq.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Stream（流）是一个来自数据源的元素队列并支持聚合操作
 *
 * 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
 * 数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
 * 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
 * 和以前的Collection操作不同， Stream操作还有两个基础的特征：
 *
 * Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。
 *                这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
 * 内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。
 *                Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。
 */
public class StreamDemo01 {

    /**
     * stream示例
     */
    //@Test
    public void Stream01(){
        ArrayList<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6);
        nums.stream().filter(num -> null != num).count();
    }

    /**
     * 创建Stream
     * 通过Strem静态方法创建
     * of方法:有两个overload方法，一个接受变长参数，一个接口单一值
     */
   // @test
    public void Stream02(){
        Stream<Integer> stream1 = Stream.of(1, 2, 3, null, 5);
        Stream<String> stream2 = Stream.of("wangweiqi");
    }

    /**
     * generator方法：生成一个无限长度的Stream，
     * 其元素的生成是通过给定的Supplier
     * （这个接口可以看成一个对象的工厂，每次调用返回一个给定类型的对象）
     *  双冒号运算就是 java 中的[方法引用]。 [方法引用]格式为：类名::方法名。
     */
    //@Test
    public void Stream03(){
        Stream.generate(new Supplier<Double>() {
            @Override
            public Double get() {
                return Math.random();
            }
        });

        Stream.generate(() -> Math.random());

        Stream.generate(Math :: random);
    }

    /**
     * iterate方法：也是生成无限长度的Stream，和generator不同的是，
     * 其元素的生成是重复对给定的种子值(seed)调用用户指定函数来生成的。
     * 其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环
     *
     *  也可理解为seed是一个初始值，通过后面的方法，不断迭代。
     */
    //@Test
    public void Stream04(){
        Stream.iterate(1,item -> item + 1).limit(10).forEach(System.out :: println);
    }

    /**
     * 通过Collection子类获取
     * 通过调用Collection的stream的方法获取
     */
   // @Test
    public void Stream05(){
        ArrayList<Integer> nums = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, null, 45, 34);
        nums.stream()
                .filter(num -> null != num)
                .collect(Collectors.toList())
                .forEach(System.out ::println);
    }

    /**
     * 通过Arrays.stream静态方法获取
     */
   // @Test
    public void Stream06(){

        String[] strs = {"a","b","c","d",""};
        Arrays.stream(strs)
                .filter(str -> null != str || "".equals(str))
                .collect(Collectors.toList());
    }

    /**
     * 转换Stream
     *  distinct 去除了重复元素的Stream
     */
    //@Test
    public void Stream07(){
        ArrayList<Integer> nums = Lists.newArrayList(1, 2, 3,3,3,3, 4, 5,5,5,6,6, 6, 7, 8, null, 45, 34);
        nums.stream()
                .filter(num -> null != num)
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out ::print);
    }

    /**
     * map   相当于对每个元素进行方法处理
     * 参数：Function函数，
     *      Function函数的入参是stream的元素，Function函数返回值可以是任意类型
     * 返回值：Function函数返回值的Stream
     * 作用：将T类型经过某种映射（由函数式接口抽象方法实现决定），变成R类型的Stream流
     *  map函数式接口抽象方法的返回值是R，flatMap函数式接口抽象方法返回值是Stream< R >
     */
    //@Test
    public void Stream08(){
        ArrayList<Integer> nums = Lists.newArrayList(1, 6, 7, 8, 45, 34);
        nums.stream()
                .filter(num -> null != num)
                .distinct()
                .map(num -> num + 1)
                .collect(Collectors.toList())
                .forEach(System.out ::println);
    }

    /**
     * flatMap
     * 参数：Function函数，
     *      Function函数的入参是stream的元素，Function函数返回值是Stream的子类
     *  返回值：Function函数返回的Stream中的元素汇聚到一个Stream中进行返回
     *  将T类型经过某种映射（由函数式接口抽象方法实现决定）(原本要转成 Stream< Stream< R > >)，变成R类型的Stream流
     *  flatMap作用就是将返回的Stream< R >拆开，再组合每个值成新的Stream< R >
     */

    //@Test
    public void Stream09() {
        Student[] students = new Student[] { new Student("a.a", 1), new Student("b.b", 2),
                new Student("c.c", 3), new Student("d.d", 4), new Student("e.e", 5) };
        // Stream流中间操作---映射map
        Arrays.asList(students).stream().map(Student::getName).forEach(System.out::println);
        System.out.println("*********************************");
        // 比较区别，map(s->s.split("\\."))收集之后的结果是Stream<String[]>
        Arrays.asList(students).stream().map(Student::getName).map(s->s.split("\\.")).forEach(arr-> System.out.println(Arrays.toString(arr)));
        System.out.println("*********************************");
        // Stream流中间操作---扁平化映射flatmap，看源码可以发现，Function函数式接口的第二个参数是Stream<? extends R>
        // 也即，lambda表达式的返回值是Stream<? extends R>类型
        // 那作用也就显而易见了，把每个返回值的Stream，再一个个拆开，最后综合所有的变成一个新的Stream
        Arrays.asList(students).stream().map(Student::getName).flatMap(s->Arrays.stream(s.split("\\."))).forEach(System.out::println);
    }

    /**
     * peek
     *  仅在对流内元素进行操作时，peek才会被调用，当不对元素做任何操作时，peek自然也不会被调用了
     *  该方法主要用于调试，方便debug查看Stream内进行处理的每个元素。
     */
   // @Test
    public void Stream10() {
        ArrayList<Integer> nums = Lists.newArrayList(1, 6, 7, 8, 45, 34);
        nums.stream()
                .map(num -> num + 2)
                .skip(3)
                .forEach(System.out :: println);

    }

    /**
     * reduce
     *  通过反复修改某个可变对象，而是通过把前一次的汇聚结果当成下一次的入参，反复如此。
     */
    //@Test
    public void Stream11() {
        List<Integer> ints = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        Integer total = ints.stream()
                .reduce((sum, item) -> sum + item)
                .get();
        System.out.println(total);
    }
    /**
     * sorted
     *   排序
     */
    //@Test
    public void Stream12() {
        Random random = new Random();
        random.ints()
                .limit(10)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * parallelStream 是流并行处理程序的代替方法。
     */
   // @Test
    public void Stream13() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings.parallelStream()
                .filter(str -> str.isEmpty())
                .count();
        System.out.println(count);
    }

    /**
     * Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
     */
    //@Test
    public void Stream14() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream()
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());
        System.out.println("筛选列表: " + filtered);

        String mergedString = strings.stream()
                .filter(string -> !string.isEmpty())
                .collect(Collectors.joining(", ","A","B"));
        System.out.println("合并字符串: " + mergedString);
    }
}
