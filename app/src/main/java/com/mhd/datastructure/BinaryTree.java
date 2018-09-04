package com.mhd.datastructure;

import android.util.Log;
import android.widget.TextView;

import com.mhd.datastructure.View.NodeOnTreeView;
import com.mhd.datastructure.View.TreeView;

import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTree {


    public interface operationListener{
        void onNodeInsert(BTNode btNode);
        void onNodeDelete(BTNode btNode,boolean isRoot);
        void onNodeSelect(int dataToSelect,BTNode btNode);
    }


    private static final int maxSize=50;
    private BTNode root;
    private TreeView treeView;
    private static final String TAG="BinaryTree";
    public static final int SEQTYPE_BIGTOSMALL=0x1;
    public static final int SEQTYPE_SMALLTOBIG=0x2;


    public TreeView getTreeView() {
        return treeView;
    }

    public void setTreeView(TreeView treeView) {
        this.treeView = treeView;
    }

    public BinaryTree(BTNode root) {
        this.root = root;
    }
    public BinaryTree(){

    }



    private operationListener BTOperationListener;

    public void addNodes(int[] datas) {
        for (int data:datas){
            insertNode(data);
        }
    }

    public void insertNode(int data) {
        int layer=0;
        int rank=0;
        if(root==null){
            layer=0;
            rank=0;
            root=new BTNode(data);
            treeView.insertNode(new NodeOnTreeView(root,layer,rank));
            if(BTOperationListener!=null)
                BTOperationListener.onNodeInsert(root);
            return;
        }
        BTNode Nodeptr=root;
        while(Nodeptr!=null) {
            layer++;
            if (data >= Nodeptr.getData()) {
                rank=2*rank+1;
                if (Nodeptr.getRightChild() == null) {
                    BTNode tempBTNode=new BTNode(data);
                    Nodeptr.setRightChild(tempBTNode);
                    treeView.insertNode(new NodeOnTreeView(tempBTNode,layer,rank));
                    if(BTOperationListener!=null)
                        BTOperationListener.onNodeInsert(tempBTNode);
                    return;
                } else {
                    Nodeptr = Nodeptr.getRightChild();

                }
            }
            else{
                rank=2*rank;
                if(Nodeptr.getLeftChild()==null){
                    BTNode tempBTNode=new BTNode(data);
                    Nodeptr.setLeftChild(tempBTNode);
                    treeView.insertNode(new NodeOnTreeView(tempBTNode,layer,rank));
                    if(BTOperationListener!=null)
                        BTOperationListener.onNodeInsert(tempBTNode);
                    return;
                }else {

                    Nodeptr=Nodeptr.getLeftChild();
                }
            }
        }
    }



    public BTNode getRoot() {
        return root;
    }

    public void setRoot(BTNode root) {
        this.root = root;
    }

    public boolean deleteNode(int data) {
        BTNode NodeParentPtr=root;
        BTNode NodePtr = root;
        boolean goRight=true;
        while (NodePtr != null) {
            if (NodePtr.getData() == data) {
                //删除一个节点需要分4种情况
                if (NodePtr.getLeftChild() == null && NodePtr.getRightChild() == null) {

                    if(NodePtr==root){
                        //根节点的情况
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,true);
                        root=null;
                    }
                    else{
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,false);
                        if(goRight)
                            NodeParentPtr.setRightChild(null);
                        else
                            NodeParentPtr.setLeftChild(null);
                    }
                    Log.d(TAG,"DeleteNodeMark1");

                }//叶子节点
                else if (NodePtr.getRightChild() != null && NodePtr.getLeftChild() == null) {
                    if(NodePtr==root){
                        root= NodePtr.getRightChild();
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,true);
                    }
                    else{
                        if(goRight)
                            NodeParentPtr.setRightChild(NodePtr.getRightChild());
                        else
                            NodeParentPtr.setLeftChild(NodePtr.getRightChild());
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,false);
                    }
                    Log.d(TAG,"DeleteNodeMark2");
                    preTraverse("print");

                }//只有右子树
                else if (NodePtr.getRightChild() == null && NodePtr.getLeftChild() != null) {
                    if(NodePtr==root){
                        root=NodePtr.getLeftChild();
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,true);
                    }
                    else{
                        NodeParentPtr.setLeftChild(NodePtr.getLeftChild());
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,false);
                    }
                    Log.d(TAG,"DeleteNodeMark3");
;
                }//只有左子树
                else {
                    if(NodePtr==root){
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,true);
                    }else{
                        if(BTOperationListener!=null)
                            BTOperationListener.onNodeDelete(NodePtr,false);
                    }
                    Log.d(TAG,"DeleteNodeMark4");
                    BTNode RightNodePtr;
                    BTNode RightParentPtr;
                    RightNodePtr=NodePtr.getRightChild();
                    RightParentPtr=NodePtr;
                    while(RightNodePtr.getLeftChild()!=null){
                        RightParentPtr=RightNodePtr;
                        RightNodePtr=RightNodePtr.getLeftChild();

                    }
                    NodePtr.setData(RightNodePtr.getData());
                    if(RightParentPtr==NodePtr){
                        NodePtr.setRightChild(RightNodePtr.getRightChild());
                    }
                    else{
                        RightParentPtr.setLeftChild(RightNodePtr.getRightChild());
                    }
                    Log.d(TAG,"DeleteNodeMark4");

                }//左右子树都有

                treeView.deleteNode(NodePtr);
                return true;
            } else if (data > NodePtr.getData()) {
                NodeParentPtr=NodePtr;
                NodePtr = NodePtr.getRightChild();
                goRight=true;
            } else {
                NodeParentPtr=NodePtr;
                NodePtr = NodePtr.getLeftChild();
                goRight=false;
            }
        }

        return false;

    }

    public void preTraverse(String visitType){
        BTNode cur;
        VisitMethod visitMethod =new VisitMethod();
        cur=root;
        Stack<BTNode> stack =new Stack<BTNode>();
        stack.push(root);
        while(!stack.empty()){
            cur=stack.pop();
            if(cur.getRightChild()!=null)
                stack.push(cur.getRightChild());
            VisitMethod.visit(cur,visitType);
            if(cur.getLeftChild()!=null)
                stack.push(cur.getLeftChild());

        }

    }
    static class VisitMethod{
        static void visit(BTNode btNode,String visitType){
            switch (visitType){
                case "print":
                    printNode(btNode);
                    break;
                    default:
                        break;
            }
        }
        private static void printNode(BTNode btNode){
            System.out.println(btNode.getData()+" ");
        }
    }

    public void reCalculatePos(){
        //TODO
    }

    public void setBTOperationListener(operationListener BTOperationListener) {
        this.BTOperationListener = BTOperationListener;
    }
    public List<BTNode> getAllNodesBySequence(int SeqType){

        List<BTNode> retList=new ArrayList<BTNode>();
        switch (SeqType){
            case SEQTYPE_BIGTOSMALL:


                break;
            case SEQTYPE_SMALLTOBIG:
                break;
        }
        return retList;
    }

    public BTNode getBTNode(int data){
        BTNode cur=root;
        while(cur!=null){
            if(cur.getData()==data){
                BTOperationListener.onNodeSelect(data,cur);
                return cur;
            }
            else if(data>cur.getData())
                cur=cur.getRightChild();
            else
                cur=cur.getLeftChild();

        }
        BTOperationListener.onNodeSelect(data,null);
        return null;

    }
}
