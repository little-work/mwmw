package com.lilin.mwmw;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Data
public class MainTest {





    public static void main(String[] args) {

        Node last = null;
        Node first;

        Object[] elementData={};

        ArrayList aa=new ArrayList(5);
        aa.add(222);
        LinkedList ll=new LinkedList();
        ll.add(222);

        /*elementData=Arrays.copyOf(elementData, 10);
        System.out.println(elementData.length);*/

         Node l = last;
         Node newNode = new Node(l, 2, null);
        last = newNode;
        if (l == null){
            System.out.println("dwdw");
        }else{

        }


    }
}

class Node {
    int item;
    Node next;
    Node prev;

    Node(Node prev, int element, Node next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
