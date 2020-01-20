package test.动态代理;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/9/10
 * @描述
 */
public class ProgrammerBigV implements Programmer{
    // 指定程序员大V要让谁发文章(先发文章、后点赞)
    private Java3y java3y ;

    public ProgrammerBigV(Java3y java3y) {
        this.java3y = java3y;
    }

    // 程序员大V点赞评论收藏转发
    public void upvote() {
        System.out.println("程序员大V点赞评论收藏转发！");
    }

    @Override
    public void coding() {

        // 让Java3y发文章
        java3y.coding();

        // 程序员大V点赞评论收藏转发！
        upvote();
    }

    public static void main(String[] args) {

        // 想要发达的Java3y
        Java3y java3y = new Java3y();

        // 受委托程序员大V
        Programmer programmer = new ProgrammerBigV(java3y);

        // 受委托程序员大V让Java3y发文章，大V(自己)来点赞
        programmer.coding();

    }
}
