package cn.wwq.thread;

public class StudentClient {
    public static void main(String[] args) {
        Punishment punishment = new Punishment(1000000,"internationalization");

        for (int i = 0; i < 50; i++) {
            Thread a = new Thread(new Student("a"+i,punishment),"a"+i);
            a.start();
        }
    }
}