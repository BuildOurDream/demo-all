package demo.juc.sync;

import lombok.Data;

import java.util.concurrent.CountDownLatch;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-07-10
 */
@Data
public class SaleTicket {

    public static void main(String[] args) throws InterruptedException {
        Ticket ticket = new Ticket();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(()->{
                countDownLatch.countDown();
                while (Ticket.number > 0) {
                    ticket.sale();
                }
            }, "售票员" + i);
            thread.start();
        }
    }
}

@Data
class Ticket {

    /**
     * 票数
     */
    public static int number = 30;

    /**
     * 卖票
     */
    public void sale() {
//        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出一张票,剩余:" + --number);
//        }
    }
}
