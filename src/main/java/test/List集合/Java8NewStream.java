package test.List集合;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/15
 * @描述
 */
public class Java8NewStream {

    @Test
    public void test1(){
        List<String> title = Arrays.asList("1","2","3");
        Stream<String> stringStream = title.stream();
        stringStream.forEach(System.out::println);
    }
    @Test
    public void test2(){
        List<Integer> numbers = Arrays.asList(1,2,5,9,4,4,8,9,5);
        List<Integer> numbers2 = numbers.stream()
                .filter( f -> f % 2 == 0)
                .distinct()
                .collect(Collectors.toList());
                //.forEach(System.out::println);
        System.out.println(numbers2);
    }
    @Test
    public void test3(){
        List<Integer> numbers = Arrays.asList(1,2,5,9,9,9);
        List<Integer> numbers2 = Arrays.asList(4,4,8,9,5);
        //Set<Integer> collect = numbers.stream().collect(Collectors.toSet());
        //System.out.println(collect);

        List<Integer> numbers3 = new ArrayList<>();
        Optional<Integer> any = numbers3.stream().findAny();
        System.out.println(any);

    }
}
