package demo.juc;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-07-10
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {
        /*Thread thread = new Thread(()->{
            while (true) {
                System.out.println(Thread.currentThread().getName());
            }
        }, "aaa");

        thread.setDaemon(true);
        thread.start();

        System.out.println(Thread.currentThread().getName());*/


        /*//死锁
        Object a = new Object();
        Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + "获取到锁a,准备获取锁b");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {

                }
            }
        }).start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + "获取到锁b,准备获取锁a");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {

                }
            }
        }).start();*/

        /*//CountDownLatch
        CountDownLatch countDownLatch = new CountDownLatch(10);
        new Thread(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("人都走完了,可以关门了");
        }).start();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }*/

        /*//CyclicBarrier
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()-> {
            System.out.println("召唤神龙");
        });
        for (int i = 0; i < 7; i++) {
            final int j = i + 1;
            new Thread(()->{
                System.out.println("搜集到第" + j + "颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }*/

        //Semaphore
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "停车");
                    TimeUnit.SECONDS.sleep(RandomUtil.randomInt(5));
                    System.out.println(Thread.currentThread().getName() + "离开");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i + 1)).start();
        }
    }

    @Test
    void testCo() {
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(()-> {
//            try {
////                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return "asdasd";
        }).thenAccept(System.out::println);
        System.out.println(1122131);
    }

}
