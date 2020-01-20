package test.线程;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述 两个线程交替打印1到100
 */
import java.util.concurrent.TimeUnit;

public class SumThread {

    public void one() throws InterruptedException{
        synchronized (this) {
            boolean flag = true;

            while (flag) {

                for(int i = 1; i <= 99;i += 2){
                    System.out.println(i);

                    if(i==99){
                        flag = false;
                        this.notify();
                        break;
                    }
                    this.notify();
                    this.wait();
                }
            }
        }
    }

    public void two() throws InterruptedException{
        synchronized (this) {
            boolean flag = true;

            while (flag) {

                for(int i = 2; i <= 100;i += 2){
                    System.out.println(i);

                    if(i==100){
                        flag = false;
                        this.notify();
                        break;
                    }
                    this.notify();
                    this.wait();
                }
            }
        }
    }



    public static void main(String[] args) throws Exception {
        SumThread sumThread = new SumThread();

        new Thread(()->{
            try {
                sumThread.one();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            try {
                sumThread.two();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
