package com.mhd.datastructure;

public class BTNode {

    private BTNode leftChild;
    private BTNode rightChild;
    private int data;

    public BTNode(BTNode leftChild, BTNode rightChild, int data) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.data = data;
    }

    public BTNode(int data) {
        this.data = data;
    }

    public BTNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BTNode leftChild) {
        this.leftChild = leftChild;
    }

    public BTNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BTNode rightChild) {
        this.rightChild = rightChild;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
