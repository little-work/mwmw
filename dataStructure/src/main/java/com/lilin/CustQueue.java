package com.lilin;

import java.util.LinkedList;
import java.util.List;


/**
 * 队列 先入先出
 * 当存入数据的时候队列的尾部rear加1  当rear和maxSize相等的时候就说明这个队列满了
 * 当取出数据的时候队列的头部front加1
 *
 * @param <T>
 */
public class CustQueue<T> {

    private int maxSize = 0;   //队列可以放多少个元素  比如maxSize=5最多放4个元素
    private List<T> list;
    private int front = 0;   //指向队列的第一个元素
    private int rear = 0;    //指向队列的最后一个元素


    //初始化有界队列
    public CustQueue(int size) {
        list = new LinkedList<>();
        this.maxSize = size;
    }

    //判断队列是不是满的
    public boolean isFull() {
        return (rear+1) % maxSize == front;
    }

    //判断队列是不是空了
    public boolean ifEmpty() {
        return rear == front;
    }

    //向队列中添加元素
    public String  add(T t) {
        if (isFull()) {
            System.out.println("队列满了，不能添加数据了");
            return "队列满了，不能添加数据了";
        }
        if(list.contains(t)){
            System.out.println("不能添加相同的元素");
            return "不能添加相同的元素";
        }
        list.add(t);
        rear = (rear + 1) % maxSize;

        System.out.println("添加了一个元素:"+t+",现在rear="+rear+",front="+front);
        System.out.println("现在队列中的元素有:"+list);
        return "添加了一个元素:"+t+",现在rear="+rear+",front="+front;
    }

    public T get() {
        if (ifEmpty()) {
            System.out.println("队列为空，不能取数据了");
            return null;
        }
        T t = list.get(front);
        front = (front + 1) % maxSize;

        System.out.println("取出一个元素:"+t+",现在rear="+rear+",front="+front);
        System.out.println("现在队列中的元素有:"+list);

        return t;
    }

    //列举现在队列中的元素
    public String  getQueue() {
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for (int i = front; i < front + ((rear + maxSize - front) % maxSize); i++) {
            //System.out.println(list.get(i % maxSize));
            sb.append(list.get(i % maxSize)+"--");
        }
        sb.append("]");
        return sb.toString();
    }
    //列举现在队列的头元素
    public T getHeadEnum(){
       return list.get(front);
    }


}
