package cn.hasfun.framework.java8;

import cn.hasfun.framework.java8.Function.MyInterface;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://juejin.im/post/5cc124a95188252d891d00f2
 *
 * BiConsumer：表示接收两个输入参数和不返回结果的操作。
 * BiFunction：表示接受两个参数，并产生一个结果的函数。
 * BinaryOperator：表示在相同类型的两个操作数的操作，生产相同类型的操作数的结果。
 * BiPredicate：代表两个参数谓词（布尔值函数）。
 * BooleanSupplier：代表布尔值结果的提供者。
 * Consumer：表示接受一个输入参数和不返回结果的操作。
 * DoubleBinaryOperator：代表在两个double值操作数的运算，并产生一个double值结果。
 * DoubleConsumer：表示接受一个double值参数，不返回结果的操作。
 * DoubleFunction：表示接受double值参数，并产生一个结果的函数。
 * DoublePredicate：代表一个double值参数谓词（布尔值函数）。
 * DoubleSupplier：表示double值结果的提供者。
 * DoubleToIntFunction：表示接受double值参数，并产生一个int值结果的函数。
 * DoubleToLongFunction：代表接受一个double值参数，并产生一个long值结果的函数。
 * DoubleUnaryOperator：表示上产生一个double值结果的单个double值操作数的操作。
 * Function：表示接受一个参数，并产生一个结果的函数。
 * IntBinaryOperator：表示对两个int值操作数的运算，并产生一个int值结果。
 * IntConsumer：表示接受单个int值的参数并没有返回结果的操作。
 * IntFunction：表示接受一个int值参数，并产生一个结果的函数。
 * IntPredicate：表示一个整数值参数谓词（布尔值函数）。
 * IntSupplier：代表整型值的结果的提供者。
 * IntToDoubleFunction：表示接受一个int值参数，并产生一个double值结果的功能。
 * IntToLongFunction：表示接受一个int值参数，并产生一个long值结果的函数。
 * IntUnaryOperator：表示产生一个int值结果的单个int值操作数的运算。
 * LongBinaryOperator：
 * 表示在两个long值操作数的操作，并产生一个long值结果。
 * LongConsumer：
 * 表示接受一个long值参数和不返回结果的操作。
 * LongFunction：
 * 表示接受long值参数，并产生一个结果的函数。
 * LongPredicate：
 * 代表一个long值参数谓词（布尔值函数）。
 * LongSupplier：
 * 表示long值结果的提供者。
 * LongToDoubleFunction：表示接受double参数，并产生一个double值结果的函数。
 * LongToIntFunction：表示接受long值参数，并产生一个int值结果的函数。
 * LongUnaryOperator：表示上产生一个long值结果单一的long值操作数的操作。
 * ObjDoubleConsumer：表示接受对象值和double值参数，并且没有返回结果的操作。
 * ObjIntConsumer：表示接受对象值和整型值参数，并返回没有结果的操作。
 * ObjLongConsumer：表示接受对象的值和long值的说法，并没有返回结果的操作。
 * Predicate：代表一个参数谓词（布尔值函数）。
 * Supplier：表示一个提供者的结果。
 * ToDoubleBiFunction：表示接受两个参数，并产生一个double值结果的功能。
 * ToDoubleFunction：代表一个产生一个double值结果的功能。
 * ToIntBiFunction：表示接受两个参数，并产生一个int值结果的函数。
 * ToIntFunction：代表产生一个int值结果的功能。
 * ToLongBiFunction：表示接受两个参数，并产生long值结果的功能。
 * ToLongFunction：代表一个产生long值结果的功能。
 * UnaryOperator:表示上产生相同类型的操作数的结果的单个操作数的操作。
 * ————————————————
 */
public class LambdaExample {


    /**
     * List 循环
     *
     * @throws Exception
     */
    @Test
    public void test0() throws Exception {
        Arrays.asList("232", "ssa", "dsa").stream().forEach(System.out::println);
    }

    @Test
    public void test1() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return 10;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 20;
        });

        CompletableFuture<String> f = future1.thenCombine(future2, (x, y) -> {
            return "计算结果：" + x + y;
        });
        System.out.println(f.get());

    }

    @Test
    public void test2() {
        Runnable runnable = () -> System.out.println("ds");
        Consumer<String> comsumer = (x) -> {
            System.out.println(x);
        };

    }


    @Test
    public void test3() {
        MyInterface myInterface = s -> System.out.println(s);
        myInterface.doSomeThing("hello");

        //  MyInterface 等同于 Java8 提供的 Consumer
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("world");

        new LambdaExample().mergeAndPrint("hello", "world", s -> System.out.println(s));

    }

    private void mergeAndPrint(String a, String b, Consumer<String> consumer) {
        consumer.accept(a.concat(b));
    }


    @Test
    public void test4() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        }).start();
        Arrays.asList(21, 33, 53, 232).stream().peek(v -> System.out.println(v)).filter(v -> v > 30).forEach(v -> System.out.println("--" + v));

        List<Integer> a = Arrays.asList(2, 32, 3, 4, 1);
        List<Integer> b = Arrays.asList(42, 132, 63, 64, 61);

        List together = Stream.of(a, b).flatMap(v -> v.stream()).collect(Collectors.toList());

        together.forEach(System.out::println);
    }


    @Test
    public void test5() {

        List<Integer> a = Arrays.asList(2, 32, 3, 4, 1);
        List<Integer> b = Arrays.asList(42, 132, 63, 64, 61);

        List together = Stream.of(a, b).flatMap(v -> v.stream()).collect(Collectors.toList());

        together.forEach(System.out::println);
    }


    /**
     * stream 属于集合
     * map 将一种类型的值转换为另外一种类型的值
     * flatMap 是将多个Stream连接成一个Stream，这时候不是用新值取代Stream的值，与map有所区别，这是重新生成一个Stream对象取而代之。
     * flatMap与map的区别在于 flatMap是将一个流中的每个值都转成一个个流，然后再将这些流扁平化成为一个流 。
     */
    @Test
    public void test6() {
        List<String> a = Arrays.asList("145", "267", "398");

        List<Integer> aa = a.stream().map(v -> Integer.valueOf(v)).collect(Collectors.toList());
        List<String[]> aaa = a.stream().map(v -> v.split("")).collect(Collectors.toList());

        aa.forEach(System.out::println);

        List<String> stream2 = a.stream().flatMap(t -> Arrays.stream(t.split(""))).collect(Collectors.toList());//将多个String[] 转成 string流
        stream2.forEach(System.out::println);

        List<String> b = Arrays.asList("3", "2", "4");

        List<String> stream3 = Stream.of(a, b).flatMap(v -> v.stream()).collect(Collectors.toList());
        stream3.forEach(System.out::println);

    }

    /**
     * list 转map
     */
    @Test
    public void test7() {
        List<Dog> doglist = Arrays.asList(new Dog("xm", 12), new Dog("ss", 5));
        Map<Integer, String> stringMap = doglist.stream().collect(Collectors.toMap(Dog::getAge, Dog::getName));
        stringMap.forEach((i, j) -> System.out.println("key:" + i + ",value:" + j));
    }


    /**
     * 更全面的统计信息，摘要收集器可以返回一个特殊的内置统计对象。
     * 通过它，我们可以简单地计算出最小年龄、最大年龄、平均年龄、总和以及总数量
     */

    @Test
    public void test8() {
        List<Dog> doglist = Arrays.asList(new Dog("xm", 12), new Dog("ss", 5));
        IntSummaryStatistics intSummaryStatistics = doglist.stream().collect(Collectors.summarizingInt(t -> t.getAge()));
        System.out.println(intSummaryStatistics);
    }

    /**
     * forEach
     */
    @Test
    public void test9() {
        Map.of(1, 12, 2, 43).forEach((k, v) -> System.out.println("key:" + k + ",value:" + v));
    }


    /**
     * andThen compose
     */
    @Test
    public void test10() {
        Function<Integer, Integer> sum = v -> v + 100;
        Function<Integer, Integer> multi = v -> v * 100;

        System.out.println(sum.compose(multi).apply(100)); // 先执行参数muti
        System.out.println(sum.andThen(multi).apply(100));// 先执行 调用者sum,后执行muti

        BiFunction<Integer, Integer, Integer> sum2 = (i, j) -> i + j;
        System.out.println(sum2.apply(10, 20));
    }

    /**
     * Predicate
     */
    @Test
    public void test11() {
        Predicate<String> predicate = t -> t.length() > 5;
        Assert.assertEquals(true, predicate.test("212188"));
    }

    /**
     * Supplier
     */
    @Test
    public void test12() {
        Supplier<Dog> supplier = Dog::new;
        supplier.get();
    }

    /**
     * optional
     */
    @Test
    public void test13() {
        Optional<Integer> optional = Optional.of(12);
//        if (optional.isPresent()){
//            System.out.println(optional.get());
//        }
        optional.ifPresent(v -> System.out.println(v));

        optional.orElseGet(() -> 88);

        Company c = new Company();
        c.setEmployeeList(Arrays.asList(new Company.Employee()));
        Optional<Company> companyOptional = Optional.ofNullable(c);
        List employees = companyOptional.map(v -> v.getEmployeeList()).orElseGet(ArrayList::new);
        employees.forEach(System.out::println);
    }

    /**
     *  list stream collect
     */
    @Test
    public void test14() {
        var list = List.of("a", "b", "c", "d", "e");
        List<String> asList = list.stream().collect(() -> new ArrayList<String>(), (list1, s) -> list1.add(s), (list2, list3) -> list3.addAll(list2));
        List<String> asList2 = list.stream().collect(ArrayList::new, ArrayList::add,
                ArrayList::addAll);

        asList.forEach(System.out::println);
    }

    /**
     * filter parallelStream
     * list  transfor to string
     */
    @Test
    public void test15(){
        var list = List.of("s","","b","e","4a","6t","","fd");
        list.stream().filter(s-> !StringUtils.isEmpty(s)).forEach(System.out::println);
        System.out.println("------------");
        list.parallelStream().filter(s-> !StringUtils.isEmpty(s)).forEach(System.out::println);
        System.out.println(list.parallelStream().filter(s-> !StringUtils.isEmpty(s)).collect(Collectors.joining(",")));
    }


    /**
     * 计算结果完成时的转换thenApplyAsync
     * 计算结果完成时的消费thenAcceptAsync
     *                     thenAcceptBothAsync 用来合并结果当两个CompletionStage都正常执行的时候就会执行提供的action，它用来组合另外一个异步的结果
     * 对计算结果的组合 thenComposeAsync
     *
     * 两个future完成的时候应该完成的工作，接下来介绍任意一个future完成时需要执行的工作
     * acceptEither
     * applyToEither
     *
     * @throws Exception
     */
    @Test
    public void test16() throws Exception{
        long now = LocalTime.now().getLong(ChronoField.MILLI_OF_DAY);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.currentThread().sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });
        future.thenApplyAsync(i -> i*100).thenAccept(System.out::println);
        long now2 = LocalTime.now().getLong(ChronoField.MILLI_OF_DAY);
        System.out.println("cost time is "+ (now2-now)+",result:"+future.get());


        CompletableFuture completableFuture =  future.thenAcceptBothAsync(CompletableFuture.supplyAsync(()->{
            return 3;
        }),(x,y)->System.out.println(x+y));
        long now3 = LocalTime.now().getLong(ChronoField.MILLI_OF_DAY);

        System.out.println("cost time is "+ (now3-now2)+",result:"+completableFuture.get());

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 10;
        });
        CompletableFuture<String> f = future2.thenCombine(CompletableFuture.supplyAsync(() -> {
            return 20;
        }),(x,y) -> {return "计算结果："+x+y;});
        System.out.println(f.get());


    }

    /**
     * 排序
     */
    @Test
    public void test17(){
        List list = List.of(12,23,43,5,54,9);
        list.stream().sorted().forEach(System.out::println);

        List<Dog> dogList = List.of(new Dog("w",74),new Dog("j",33),new Dog("g",36));
       //dogList.stream().sorted((d1,d2)->d1.getAge()-d2.getAge()).forEach(v->System.out.println(v.toString()));
        //dogList.stream().sorted(Comparator.comparing(v->v.getAge())).forEach(v->System.out.println(v.toString()));
        dogList.stream().sorted(Comparator.comparing(Dog::getAge)).forEach(v->System.out.println(v.toString()));


    }

}
