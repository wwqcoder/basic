package cn.wwq.diyqueue;

/**
 * 定义出队列接口
 * @param <T>
 */
public interface Queue<T> {

    /**
     * 放数据
     * @param item  入参数
     * @return
     */
    Boolean put(T item);

    /**
     * 拿数据
     * @return
     */
    T take();

    class Node<T>{

        //数据本身
        T item;

        //下一个元素
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }

}
