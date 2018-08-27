package com.mhd.datastructure;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private static final int maxSize=50;
    private int currentMaxIndex = -1;
    private List<BTNode> nodes;
    //这里采用二叉树的数列存储


    public BinaryTree(int currentMaxIndex, List<BTNode> nodes) {
        this.currentMaxIndex = currentMaxIndex;
        this.nodes = nodes;
    }

    public BinaryTree(List<BTNode> nodes) {
        this.nodes = nodes;
    }

    BinaryTree() {
        super();
        nodes = new ArrayList<BTNode>(maxSize);
        for(int i=0;i<=maxSize;i++){
            nodes.add(new BTNode());
        }

    }

    public void createBT(int[] datas) {
        int index = 0;
        //这里预估一下问题的规模，用户不太可能会插入超过50个节点
        for (int data : datas) {
            nodes.add(new BTNode(index++, data));
        }
        for (int i = 0; i < datas.length / 2; i++) {
            nodes.get(i).setLeftChildIndex(i * 2 + 1);
            if (i * 2 + 2 < nodes.size()) {
                nodes.get(i).setRightChildIndex(2 * i + 2);
            }
        }
        currentMaxIndex = datas.length - 1;
    }

    public void createBST(int[] datas) {
        for (int data:datas){
            insertNode(data);
        }
    }

    private void insertNode(int data) {
        if(currentMaxIndex==-1){
            nodes.set(0,new BTNode(0,data));
            currentMaxIndex=0;
            return;
        }
        for (int i = 0; i <= currentMaxIndex; ) {
            System.out.println(i);

            if (nodes.get(i) == null) {
                i++;
                continue;
            }
            if (data >= nodes.get(i).getData()) {
                if (nodes.get(i).getRightChildIndex() == 0) {
                    nodes.set(2 * i + 2, new BTNode(2 * i + 2, data));
                    nodes.get(i).setRightChildIndex(2 * i + 2);
                    if (2 * i + 2 > currentMaxIndex)
                        currentMaxIndex = 2 * i + 2;
                    return;
                } else {
                    i = 2 * i + 2;
                }
            } else {
                if (nodes.get(i).getLeftChildIndex() == 0) {
                    nodes.set(2 * i + 1, new BTNode(2 * i + 1, data));
                    nodes.get(i).setLeftChildIndex(2 * i + 1);
                    if (currentMaxIndex < 2 * i + 1)
                        currentMaxIndex = 2 * i + 1;
                    return;
                } else {
                    i = 2 * i + 1;
                }
            }

        }
    }

    public BTNode getNode(int index) {
        return nodes.get(index);
    }


    private void deleteNode(int data) {


    }


}
