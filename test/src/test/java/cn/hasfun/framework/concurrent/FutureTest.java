package cn.hasfun.framework.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

public class FutureTest {

    ConcurrentHashMap<Integer,Future>  futureConcurrentHashMap = new ConcurrentHashMap<Integer,Future>();

    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
           Future future =  executorService.submit(new Callable<Object>() {
               @Override
               public Object call() throws Exception {
                   return System.currentTimeMillis();
               }
            });
            futureConcurrentHashMap.put(i,future);
        }

        for (Future future : futureConcurrentHashMap.values()){
           System.out.println(future.get());
        }

    }



    @Test
    public void mockAsynHttpRequest() throws Exception{
        ExecutorService  executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.currentThread().sleep(1000*60);
                return "response is ok";
            }
        });
        System.out.println("finish"+future.get());
    }

}
