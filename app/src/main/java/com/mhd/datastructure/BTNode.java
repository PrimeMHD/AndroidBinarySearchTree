package com.mhd.datastructure;

public class BTNode {
    private int thisIndex;
    private int leftChildIndex=0;
    private int rightChildIndex=0;
    private int data;

    public BTNode(int thisIndex, int leftChildIndex, int rightChildIndex, int data) {
        this.thisIndex = thisIndex;
        this.leftChildIndex = leftChildIndex;
        this.rightChildIndex = rightChildIndex;
        this.data = data;
    }

    public BTNode(int thisIndex, int data) {
        this.thisIndex = thisIndex;
        this.data = data;
    }
    BTNode(){
        super();
    }

    public int getThisIndex() {
        return thisIndex;
    }

    public void setThisIndex(int thisIndex) {
        this.thisIndex = thisIndex;
    }

    public int getLeftChildIndex() {
        return leftChildIndex;
    }

    public void setLeftChildIndex(int leftChildIndex) {
        this.leftChildIndex = leftChildIndex;
    }

    public int getRightChildIndex() {
        return rightChildIndex;
    }

    public void setRightChildIndex(int rightChildIndex) {
        this.rightChildIndex = rightChildIndex;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
