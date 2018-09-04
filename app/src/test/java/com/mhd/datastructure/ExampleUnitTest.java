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
    public void testBinaryTree() throws ClassNotFoundException, NoSuchMethodException {
        BinaryTree binaryTree=new BinaryTree(new BTNode(5));

        int[] doubles = {3,2,4,6,7};
        binaryTree.addNodes(doubles);
        binaryTree.deleteNode(5);

        binaryTree.preTraverse("print");
        //assertEquals(2,binaryTree.getNode(4).getData());
        //assertEquals(5,binaryTree.getRoot().getLeftChild().getData());

    }
}