package cn.hasfun.framework.java8;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * https://colobu.com/2018/03/12/20-Examples-of-Using-Java%E2%80%99s-CompletableFuture/
 */
public class CompletableFutureExample {

    @Test
    public void test0() throws Exception {
        CompletableFuture completableFuture = CompletableFuture.completedFuture("hello");
        assertTrue(completableFuture.isDone());
        System.out.println(completableFuture.get());
    }


    /**
     * CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)，
     * 异步执行通过ForkJoinPool实现， 它使用守护线程去执行任务。注意这是CompletableFuture的特性，
     * 其它CompletionStage可以override这个默认的行为。
     */
    @Test
    public void test1() {
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {

            System.out.println(Thread.currentThread().getName());
            try {
                Thread.currentThread().sleep(1000 * 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertFalse(completableFuture.isDone());
        // assertTrue(completableFuture.isDone());
    }

    /**
     * 在前一个阶段上应用函数
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        CompletableFuture<String> future = CompletableFuture.completedFuture("hello").thenApply(s -> {
            return s.toUpperCase();
        });
        System.out.println(future.get());
    }

    /**
     * 在前一个阶段上异步应用异步函数
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        CompletableFuture<String> future = CompletableFuture.completedFuture("hello").thenApplyAsync(s -> {
            return s.toUpperCase();
        });
        System.out.println(future.get());
    }

    /**
     * 消费前一阶段的结果
     *
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.completedFuture("hello").thenAccept(s ->
                System.out.println(s)
        );
        System.out.println(future.isDone());
    }

    /**
     * 异步消费前一阶段的结果
     *
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.completedFuture("hello").thenAcceptAsync(s -> {
            System.out.println(Thread.currentThread().getName() + "-" + s.toUpperCase());
        });
        System.out.println(future.isDone());
    }


    @Test
    public void test6() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.completedFuture("hello").thenAcceptAsync(s -> {
            System.out.println(Thread.currentThread().getName() + "-" + s.toUpperCase());
        });

//        CompletableFuture<Void> future2 = future.acceptEitherAsync(CompletableFuture.completedFuture("world").thenApplyAsync(s -> s.toUpperCase()), s -> s + "aa");
  //      System.out.println(future2.isDone());
    }


    /**
     * 异步编程
     * spring MVC DeferredResult 的应用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test7() throws ExecutionException, InterruptedException {
        CompletableFuture future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(10* 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        new Thread(()->{
            future.complete(100);
        }).start();

        System.out.println(future.get());
    }
}
