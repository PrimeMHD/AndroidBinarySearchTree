package com.mhd.datastructure;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testBinaryTree(){
        BinaryTree binaryTree=new BinaryTree();
        BinaryTree binaryTree1=new BinaryTree();
        int[] doubles = {1,5,3,4,2};
        //binaryTree.createBT(doubles);
        binaryTree1.createBST(doubles);
        //assertEquals(2,binaryTree.getNode(4).getData());
        assertEquals(4,binaryTree1.getNode(12).getData());

    }
}