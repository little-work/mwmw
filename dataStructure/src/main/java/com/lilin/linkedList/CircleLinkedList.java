package com.lilin.linkedList;

public class CircleLinkedList {


    public static void main(String[] args) {
        CircleLinkedList cll = new CircleLinkedList();

        cll.init(5);

        cll.list();
        cll.josepFu(2,2);


    }


    //头结点
    private CircleNode head = null;

    /**
     * 创建一个num大小的环形列表
     *
     * @param num
     */
    public void init(int num) {
        if (num < 0) {
            return;
        }
        //变量-当前节点
        CircleNode curnode = null;

        for (int i = 1; i <= num; i++) {
            CircleNode node = new CircleNode(i);
            //创建一个头结点  让他自己指向自己
            if (i == 1) {
                //创建一个头结点
                head = node;
                //自己指向自己  形成闭环
                head.next = head;
                //确定一个当前节点
                curnode = head;
            } else {
                curnode.next = node;
                node.next = head;
                curnode = node;
            }
        }
    }

    /**
     * 打印这个环形链表
     */
    public void list() {

        if (head == null) {
            return;
        }
        CircleNode temp = head;
        while (true) {
            //说明到达了环形链表的结尾处
            if (temp.next == head) {
                System.out.println(temp);
                //证明这是一个环形链表
                System.out.println(temp.next);
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }


    /**
     * 约瑟夫问题：
     * 环形列表，从第N个节点开始数数，数K个，数到那个人出列，再从出列的下一个开始数
     * K个，直到最后一个人
     *
     * @param startNo
     * @param no
     */
    public void josepFu(int startNo, int no) {

        if (startNo < 1 || no < 0) {
            System.out.println("输入的参数有误");
            return;
        }
        //当前节点
        CircleNode curNode = head;
        //当前节点的上一个节点
        CircleNode curNodePre = head;

        //找到最后一个节点
        while (true) {
            if (curNodePre.next == head) {
                break;
            }
            curNodePre = curNodePre.next;
        }
        //从第几个开始数
        for (int i = 1; i < startNo; i++) {
            curNodePre = curNodePre.next;
            curNode = curNode.next;
        }

        //开始数  循环的数
        while (true) {
            //只剩一个节点  跳出循环
            if (curNode == curNodePre) {
                break;
            }
            //开始移动
            for (int j = 1; j < no; j++) {
                curNodePre = curNodePre.next;
                curNode = curNode.next;
            }
            System.out.println("出圈"+curNode);
            curNodePre.next = curNode.next;
            curNode = curNode.next;
        }
        System.out.println("最后一个"+curNode);

    }

}

class CircleNode {


    private int no;

    public CircleNode next;

    public CircleNode(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "CircleNode{" +
                "no=" + no +
                '}';
    }
}
