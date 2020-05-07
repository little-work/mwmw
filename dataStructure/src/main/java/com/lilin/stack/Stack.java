package com.lilin.stack;

import java.util.regex.Pattern;

public class Stack<T> {


    private int maxNum;
    private int top = -1;
    private T[] stack;


    public Stack(int maxNum) {
        this.maxNum = maxNum;
        this.stack = (T[]) new Object[maxNum];
    }

    public Stack() {
        this.maxNum = 20;
        this.stack = (T[]) new Object[20];
    }

    /**
     * 判断栈是不是满了
     */
    public boolean isFull() {
        if (top == (maxNum - 1)) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        if (top == -1) {
            return true;
        }
        return false;
    }

    /**
     * 入栈
     *
     * @param obj
     */
    public void add(T obj) {
        if (this.isFull()) {
            System.out.println("这个栈已经满了，不能添加元素了");
            throw new RuntimeException("这个栈已经满了，不能添加元素了");
        }
        top++;
        stack[top] = (T) obj;
    }

    /**
     * 取出栈中的数据
     *
     * @return
     */
    public T get() {
        if (isEmpty()) {
            throw new RuntimeException("栈空了");
        }
        T obj = stack[top];
        top--;
        return obj;
    }

    /**
     * 查看栈顶值
     * @return
     */
    public T seeTop(){
        return stack[top];
    }
    /**
     * 遍历栈
     */
    public void seeStack() {
        if (isEmpty()) {
            System.out.println("这个栈没有元素");
            return;
        }
        for (int i = top; i > -1; i--) {
            System.out.println(stack[i]);
        }
    }

    /**
     * 返回运算符的优先级
     *
     * @param operator
     * @return
     */
    public static int priority(char operator) {
        if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '+' || operator == '-') {
            return 1;
        } else {
            return 0;
        }
    }

    public static int Calculate(int a, int b, char c,char d) {
        a=d=='-'?-a:+a;
        b=c=='-'?-b:+b;
        int res;
        switch (c) {
            case '+':
                res = a + b;
                break;
            case '-':
                res=b<0?a+b:a-b;
                break;
            case '*':
                res = a * b;
                break;
            case '/':
                res = a / b;
                break;
            default:
                throw new RuntimeException("无效的运算");
        }
        return res;
    }



    public static void main(String[] args) {
        /*Stack stack=new Stack(3);
        stack.add(56);
        stack.add("fgsdfd");
        stack.add(21321);
        stack.add("礼品");
        stack.seeStack();
        System.out.println("-----------------");
        System.out.println("取："+stack.getEnmu());
        System.out.println("取："+stack.getEnmu());
        System.out.println("取："+stack.getEnmu());
        System.out.println("取："+stack.getEnmu());
        stack.seeStack();*/

        /**
         * 实现计算器的功能
         * 运算3*5-6+9
         *
         *
         */
        //System.out.println(3-5+8*6+9);
        //System.out.println(Calculate(3,5,'-','-'));
        String expression = "100+2*3-1";
        StringBuilder sb=new StringBuilder();
        int index = 0;
        Stack<Integer> num = new Stack(10);
        Stack<Character> operator = new Stack(10);
        while (true) {
            char num1 = expression.substring(index, index + 1).charAt(0);
            if (Character.isDigit(num1)) {
                //是数字就加进来
                sb.append(num1);
                /**
                 * 处理多位数
                 */
                while (true){
                    //超过表达式长度就跳出
                    if((index+1) ==expression.length()){
                        break;
                    }
                    //看看后面一位是不是数字
                    char num2 = expression.substring(index+1, index + 2).charAt(0);
                    if(!Character.isDigit(num2)){
                        //不是则跳出
                        break;
                    }else {
                        //后面还是一个数
                        index++;
                        char num3 = expression.substring(index, index + 1).charAt(0);
                        sb.append(num3);
                    }
                }
                num.add(Integer.parseInt(sb.toString()));
                sb.setLength(0);
            } else {
                if (!operator.isEmpty()) {
                    char n=operator.get();
                    if (priority(num1) - priority(n) > 0) {
                        operator.add(n);
                        operator.add(num1);
                        index++;
                        continue;
                    } else if (priority(num1) - priority(n) <= 0) {
                        int a = num.get();
                        int b = num.get();
                        int c = Calculate(b,a,n,'+');
                        num.add(c);
                        operator.add(num1);
                    }
                } else {
                    operator.add(num1);
                }
            }
            index++;
            if(index==expression.length()){
                while (true){
                    if(num.top==0){
                        System.out.println("计算结果为:"+num.get());
                        break;
                    }else {
                        int a = num.get();
                        int b = num.get();
                        char o1=operator.get();
                        char o2;
                        if(operator.top==-1){
                            o2='+';
                        }else {
                             o2=operator.seeTop();
                        }
                        int c = Calculate(b,a,o1,o2);
                        num.add(c);
                    }
                }
                break;
            }
        }
    }
}
