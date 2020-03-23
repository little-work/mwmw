package com.lilin.linkedList;

public class TwowayLinkedList {




    //创建一个头结点
    private DoubleLinkNode<String> headNode = new DoubleLinkNode<String>(0, "");


    /**
     * 添加一个新的节点
     * @param dl
     */
    public void add(DoubleLinkNode dl){

        DoubleLinkNode temp=headNode;

        //遍历这个链表到达尾部结束循环
        while(true){
            if(temp.next==null){
                break;
            }
            temp=temp.next;
        }
        temp.next=dl;
        dl.pre=temp;
    }


    /**
     * 删除传入的节点
     * @param dl
     */
    public void delete(DoubleLinkNode dl){

        DoubleLinkNode temp=headNode.next;

        Boolean flag=false;

        while (true){
            if(temp==null){
                break;
            }
            if(temp.no==dl.no){
                flag=true;
                break;
            }
            temp=temp.next;
        }
        if(flag){
            temp.pre.next=temp.next;
            //防止删除最后一个节点  最后一个节点的temp.next=null
            if(temp.next!=null){
                temp.next.pre=temp.pre;
            }
        }
    }
}
class DoubleLinkNode<T> {

    public int no;
    public T data;
    public DoubleLinkNode<T> pre;
    public DoubleLinkNode<T> next;

    public DoubleLinkNode(int no, T data){
        this.no = no;
        this.data = data;
    }

    @Override
    public String toString() {
        return "DoubleLinkNode{" +
                "no=" + no +
                ", data=" + data+"}";
    }
}