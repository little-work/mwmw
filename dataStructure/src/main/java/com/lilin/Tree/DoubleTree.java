package com.lilin.mwmw.dataStructure.Tree;

public class DoubleTree {


    public static void main(String[] args) {
        DoubleTree doubleTree = new DoubleTree();
        doubleTree.insert(new TreeNode(3));
        doubleTree.insert(new TreeNode(1));
        doubleTree.insert(new TreeNode(4));
        doubleTree.insert(new TreeNode(7));

        preOrder(doubleTree.root);
    }


    //根节点
    private TreeNode root;

    /**
     * 二叉树添加节点
     *
     * @param treeNode
     */
    public void insert(TreeNode treeNode) {

        TreeNode currentNode;

        if (root == null) {
            this.root = treeNode;
        } else {
            currentNode = this.root;
            while (true) {
                if (treeNode.data > currentNode.data) {
                    if (currentNode.rightTreeNode != null) {
                        currentNode = currentNode.rightTreeNode;
                    } else {
                        currentNode.rightTreeNode = treeNode;
                        break;
                    }
                } else {
                    if (currentNode.leftTreeNode != null) {
                        currentNode = currentNode.leftTreeNode;
                    } else {
                        currentNode.leftTreeNode = treeNode;
                        break;
                    }
                }
            }
        }
    }

    //前序遍历
    public static void preOrder(TreeNode root) {
        if (root != null) {        //树为空的情况
            System.out.print(root.data);    //先遍历树根
            preOrder(root.leftTreeNode);    //再前序遍历左子树
            preOrder(root.rightTreeNode);    //再前序遍历右子树
        }
    }

    //中序遍历
    public void infixOrder(TreeNode current) {
        if (current != null) {
            infixOrder(current.leftTreeNode);
            System.out.print(current.data + " ");
            infixOrder(current.rightTreeNode);
        }
    }

    //后序遍历
    public void postOrder(TreeNode current) {
        if (current != null) {
            postOrder(current.leftTreeNode);
            postOrder(current.rightTreeNode);
            System.out.print(current.data + " ");
        }
    }
}

class TreeNode {

    //数据域(暂定int类型)
    public int data;
    //右节点
    public TreeNode rightTreeNode;
    //左节点
    public TreeNode leftTreeNode;

    public TreeNode(int data) {
        this.data = data;
    }
}