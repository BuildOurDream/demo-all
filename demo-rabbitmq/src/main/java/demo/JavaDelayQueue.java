package demo;

import cn.hutool.core.convert.Convert;
import lombok.Data;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * <p>java内置延时队列</p>
 *
 * @Author J.Star
 * @Date 2021-07-24
 */
public class JavaDelayQueue {

    @Data
    static class CustomDelayed implements Delayed {

        private String content;

        private Long ttl;

        public CustomDelayed(String content, Long ttl) {
            this.content = content;
            this.ttl = System.currentTimeMillis() + ttl * 1000;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(ttl - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DelayQueue delayQueue = new DelayQueue();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                delayQueue.add(new CustomDelayed("test" + i, Convert.toLong(i)));
            }
        }).start();

        while (true) {
            Delayed take = delayQueue.take();
            System.out.println(take);
        }
    }
}
