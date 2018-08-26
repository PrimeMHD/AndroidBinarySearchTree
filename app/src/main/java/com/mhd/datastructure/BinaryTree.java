package com.mhd.datastructure;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private BinaryTree LChild;
    private BinaryTree RChild;
    private BinaryTree root;
    private Double data;


    private int currentMaxIndex=0;
    private List<BinaryTree> nodes;
    //这里采用二叉树的数列存储
    public BinaryTree(BinaryTree LChild, BinaryTree RChild, Double data) {
        this.LChild = LChild;
        this.RChild = RChild;
        this.data = data;
    }

    public BinaryTree(Double data) {
        this(null,null,data);
    }

    public BinaryTree(){
        super();
    }
    public void createBST(Double[] objs){
        nodes=new ArrayList<BinaryTree>(50);
        //这里预估一下问题的规模，用户不太可能会插入超过50个节点
        for(Double object:objs){
            nodes.add(new BinaryTree(object));
        }
        root=nodes.get(0);
        for(int i=0;i<objs.length/2;i++){
            nodes.get(i).LChild=nodes.get(i*2+1);
            nodes.get(i).root=nodes.get(i);
            if(i*2+2<nodes.size()){
                nodes.get(i).RChild=nodes.get(2*i+2);
            }
        }

    }
    private void insertNode(Double obj){
        for(int i=0;i<=currentMaxIndex;){
            if(nodes.get(i)==null) {
                i++;
                continue;
            }
            if(obj>=nodes.get(i).data){
                if(nodes.get(i).RChild==null){
                    nodes.add(2*i+2,new BinaryTree(obj));
                    nodes.get(i).RChild=new BinaryTree(obj);
                    currentMaxIndex=2*i+2;
                    return;
                }
                else{
                    i=2*i+2;
                }
            }
            else{
                if(nodes.get(i).LChild==null){
                    nodes.add(2*i+1,new BinaryTree(obj));
                    nodes.get(i).RChild=new BinaryTree(obj);
                    currentMaxIndex=2*i+1;
                    return;
                }
                else{
                    i=2*i+1;
                }
            }

        }
    }

    private void deleteNode(Double obj){




    }





}
