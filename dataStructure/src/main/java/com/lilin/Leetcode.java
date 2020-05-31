package com.lilin;

public class Leetcode {


    public static void main(String[] args) {
        ListNode head = new ListNode(9);
        /*ListNode n1 = new ListNode(9);
        ListNode n2 = new ListNode(9);
        ListNode n3 = new ListNode(9);
        head.next = n1;
        head.next.next = n2;
        head.next.next.next = n3;*/

        ListNode head2 = new ListNode(9);
        /*ListNode nn1 = new ListNode(2);
        ListNode nn2 = new ListNode(3);
        ListNode nn3 = new ListNode(4);
        head2.next = nn1;
        head2.next.next = nn2;
        head2.next.next.next = nn3;*/

        /*list(head);
        list(removeNthFromEnd(head, 3));*/

        //System.out.println(getNodeData(head));


        //list(additionTwoLinked(head, head2));
        //list(addTwoNumbers(head, head2));
        //System.out.println(18/10);
        int[] arr = {-3, 4, 3, 90};
        System.out.println(twoSum(arr, 0)[0] + twoSum(arr, 0)[1]);

    }

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *  nums=[0,5,2,4,9]
     *  target=6
     *  [2,3]
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;

        /**
         * int[] indexs = new int[2];
         *
         *         // 建立k-v ，一一对应的哈希表
         *         HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>();
         *         for(int i = 0; i < nums.length; i++){
         *             if(hash.containsKey(nums[i])){
         *                 indexs[0] = i;
         *                 indexs[1] = hash.get(nums[i]);
         *                 return indexs;
         *             }
         *             // 将数据存入 key为补数 ，value为下标
         *             hash.put(target-nums[i],i);
         *         }
         */
    }

    /**
     * 将两个链表中的数字相加  得到一个新的链表组成的数字
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode cursor = root;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int l1Val = l1 != null ? l1.val : 0;
            int l2Val = l2 != null ? l2.val : 0;
            int sumVal = l1Val + l2Val + carry;
            carry = sumVal / 10;

            ListNode sumNode = new ListNode(sumVal % 10);
            cursor.next = sumNode;
            cursor = sumNode;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return root.next;
    }

    /**
     * 题目1、找到一个链表的倒数第几个元素  删除它  并返回删除后的链表
     *
     * @param head 头结点
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        list(pre);
        ListNode temp = pre;

        int length = length(head);
        System.out.println("数组长度" + length);
        int startIndex = 1;
        int whichNodeIndex;

        //判断链表是不是为空
        if (head == null) {
            return null;
        }
        //判断倒数的值是不是大于链表的总长度
        if (n > length) {
            throw new RuntimeException("过界了");
        }
        whichNodeIndex = length - n + 1;
        while (true) {
            if (whichNodeIndex == startIndex) {
                temp.next = temp.next.next;
                return pre.next;
            }
            temp = temp.next;
            startIndex++;
        }
    }

    public static int length(ListNode head) {
        int length = 0;
        ListNode temp = head;
        if (head == null) {
            return 0;
        } else {
            while (true) {
                if (temp == null) {
                    break;
                }
                temp = temp.next;
                length++;
            }
            return length;
        }
    }

    /**
     * 遍历这个链表
     */
    public static void list(ListNode root) {
        ListNode temp = root;
        if (temp == null) {
            System.out.println("空链表");
        }
        while (true) {
            if (temp == null) {
                System.out.println("已经取出链表中的所有元素");
                break;
            }
            System.out.println(temp.val);
            temp = temp.next;
        }
    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，
     * 并且它们的每个节点只能存储 一位 数字。
     * <p>
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * <p>
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 示例：
     * <p>
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * @param l1
     * @param l2
     */
    public static ListNode additionTwoLinked(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode temp = head;
        String sb1 = getNodeData(l1);
        String sb2 = getNodeData(l2);
        String result = String.valueOf(Integer.valueOf(sb1) + Integer.valueOf(sb2));
        StringBuilder s = new StringBuilder(result);
        s.reverse();
        String dd = s.toString();
        for (int i = 0; i < dd.length(); i++) {
            if (temp.next == null) {
                String ddd = dd.charAt(i) + "";
                temp.next = new ListNode(Integer.valueOf(ddd));
            }
            temp = temp.next;
        }
        return head.next;
    }

    public static String getNodeData(ListNode l1) {
        StringBuilder sb = new StringBuilder();
        ListNode temp = l1;
        while (true) {
            sb.append(temp.val);
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        return sb.reverse().toString();
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int x) {
        val = x;
    }
}