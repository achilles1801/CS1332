import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.*;


/**
 * This is a basic set of unit tests for BST.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author Prajval Manivannan
 * @version 1.0
 */
public class BSTPMTest {

    private static final int TIMEOUT = 200;
    private BST<Integer> tree;
    private BST<Integer> degenerateTree;
    private BST<Integer> empty;

    @Before
    public void setup() {
        tree = new BST<>();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(null);
        assertThrows(IllegalArgumentException.class, () -> new BST<>(list));
        list.clear();
        for (int i = 0; i < 5; i++) {
            list.add(i + 1);
        }
        //Let's create our degenerate tree
        assertThrows(IllegalArgumentException.class, () -> new BST<>(null));
        degenerateTree = new BST<>(list);
        //Let's create our empty tree
        empty = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());

        //Let's test our degenerate tree out
        assertEquals(5, degenerateTree.size());
        BSTNode<Integer> curr = degenerateTree.getRoot();
        int c = 0;

        while (curr != null) {
            c++;
            assertEquals((Integer) c, (curr.getData()));
            assertNull(curr.getLeft());
            curr = curr.getRight();
        }
        assertEquals(5, c);
        //Perfect -- ready for testing the degenerate tree
        assertNull(empty.getRoot());
        assertThrows(NullPointerException.class, () -> empty.getRoot().getLeft());
        assertThrows(NullPointerException.class, () -> empty.getRoot().getRight());
        //Empty tree is empty tree
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        /*
              2
             /
            0
             \
              1
        */

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        tree = new BST<>(data);

        assertEquals(3, tree.size());

        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getRight()
                     .getData());

    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        /*
              1
             / \
            0   2
        */

        tree.add(1);
        tree.add(0);
        tree.add(2);

        assertEquals(3, tree.size());

        assertEquals((Integer) 1, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 2, tree.getRoot().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        //Removal Case - Node with Zero Child
        /*
              6
             / \
            2   8
           / \
          1   4
             /
            3 <-
        */
        tree.add(6);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(3);
        tree.add(1);

        assertEquals((Integer) 3, tree.remove(3));
        assertEquals((Integer) 6, tree.getRoot().getData());
        assertEquals((Integer) 8, tree.getRoot().getRight().getData());
        assertNull(tree.getRoot().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight());
        assertEquals((Integer) 2, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertEquals((Integer) 4,
                tree.getRoot().getLeft().getRight().getData());
        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        assertNull(tree.getRoot().getLeft().getRight().getRight());
        //Removal Case - Node with One Left Child
        /*
              6
             / \
            2   8
           / \
          1   4 <-
             /
            3
        */
        tree = new BST<>();
        tree.add(6);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(3);
        tree.add(1);
        Integer temp = 4;
        assertEquals((Integer) 4, tree.remove(4));

        assertEquals((Integer) 6, tree.getRoot().getData());
        assertEquals((Integer) 8, tree.getRoot().getRight().getData());
        assertNull(tree.getRoot().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight());
        assertEquals((Integer) 2, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertEquals((Integer) 3,
                tree.getRoot().getLeft().getRight().getData());
        assertNull(tree.getRoot().getLeft().getRight().getRight());
        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        //Removal Case - Node with One Right Child
        /*
              1
             / \
            0   2 <-
                 \
                  3
                   \
                    4

            ->

              1
             / \
            0   3
                 \
                  4
        */
        tree = new BST<>();
        temp = 2;
        tree.add(1);
        tree.add(0);
        tree.add(temp);
        tree.add(3);
        tree.add(4);
        assertEquals(5, tree.size());

        assertSame(temp, tree.remove(2));

        assertEquals(4, tree.size());

        assertEquals((Integer) 1, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 3, tree.getRoot().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getRight()
                     .getRight().getData());

        temp = 1;
        tree = new BST<>();
        //Removal Case - Node with Two Children
        /*
              1 <-
             / \
            0   2
                 \
                  3
                   \
                    4

            ->

              2
             / \
            0   3
                 \
                  4
        */

        tree.add(temp);
        tree.add(0);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        assertEquals(5, tree.size());

        assertSame(temp, tree.remove(1));

        assertEquals(4, tree.size());

        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 3, tree.getRoot().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getRight()
                     .getRight().getData());

        //Removal from degenerate tree
        /*
            1
             \
              2
               \
                3
                 \
                  4
                   \
                    5
         */
        assertEquals((Integer) 1, degenerateTree.remove(1));
        assertEquals((Integer) 2, degenerateTree.getRoot().getData());
        assertEquals((Integer) 3,
                degenerateTree.getRoot().getRight().getData());
        assertEquals((Integer) 4,
                degenerateTree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 5,
                degenerateTree.getRoot().getRight().getRight().getRight().getData());
        assertNull(degenerateTree.getRoot().getRight().getRight().getRight().getRight());
        assertEquals(4, degenerateTree.size());

        assertThrows(java.util.NoSuchElementException.class, () -> tree.remove((Integer) 10));

        assertThrows(IllegalArgumentException.class, () -> empty.remove(null));
        assertThrows(java.util.NoSuchElementException.class, () -> empty.remove((Integer) 0));
        assertEquals(0, empty.size());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        Integer temp200 = 200;
        Integer temp185 = 185;
        Integer temp190 = 190;
        Integer temp195 = 195;
        Integer temp215 = 215;
        Integer temp205 = 205;
        Integer temp210 = 210;

        Integer temp400 = 400;
        Integer temp401 = 401;
        Integer temp402 = 402;
        Integer temp403 = 403;
        Integer temp404 = 404;

        /*
                  200
              /        \
            185         215
             \         /
              190     205
               \       \
                195     210
        */

        tree.add(temp200);
        tree.add(temp185);
        tree.add(temp190);
        tree.add(temp195);
        tree.add(temp215);
        tree.add(temp205);
        tree.add(temp210);
        assertEquals(7, tree.size());

        // We want to make sure the data we retrieve is the one from the tree,
        // not the data that was passed in. The Integers need to be outside of
        // the range [-128, 127] so that they are not cached.
        assertSame(temp185, tree.get(185));
        assertSame(temp190, tree.get(190));
        assertSame(temp195, tree.get(195));
        assertSame(temp200, tree.get(200));
        assertSame(temp205, tree.get(205));
        assertSame(temp210, tree.get(210));
        assertSame(temp215, tree.get(215));

        BST<Integer> tempDegenerateTree = new BST<>();
        tempDegenerateTree.add(temp400);
        tempDegenerateTree.add(temp401);
        tempDegenerateTree.add(temp402);
        tempDegenerateTree.add(temp403);
        tempDegenerateTree.add(temp404);

        /*
        400
           \
           401
             \
             402
               \
               403
                 \
                 404
         */
        assertSame(temp400, tempDegenerateTree.get(400));
        assertSame(temp401, tempDegenerateTree.get(401));
        assertSame(temp402, tempDegenerateTree.get(402));
        assertSame(temp403, tempDegenerateTree.get(403));
        assertSame(temp404, tempDegenerateTree.get(404));

        assertThrows(java.util.NoSuchElementException.class, () -> empty.get((Integer) 0));
        assertThrows(IllegalArgumentException.class, () -> empty.get(null));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
                3
             /     \
            0       6
             \     /
              1   4
               \   \
                2   5
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(6);
        tree.add(4);
        tree.add(5);
        assertEquals(7, tree.size());

        assertFalse(tree.contains(9));
        assertFalse(tree.contains(10));
        assertTrue(tree.contains(0));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(6));
        assertFalse(tree.contains(7));
        assertFalse(tree.contains(8));

        for (int i = 0; i < 5; i++) {
            assertTrue(degenerateTree.contains(i + 1));
            assertFalse(degenerateTree.contains(i + 10));
        }
        assertThrows(IllegalArgumentException.class, () -> empty.get(null));
        assertThrows(java.util.NoSuchElementException.class, () -> empty.get((Integer) 0));
    }

    @Test(timeout = TIMEOUT)
    public void testPreorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> preorderNormal = new ArrayList<>();
        preorderNormal.add(3);
        preorderNormal.add(0);
        preorderNormal.add(1);
        preorderNormal.add(2);
        preorderNormal.add(8);
        preorderNormal.add(4);
        preorderNormal.add(6);
        preorderNormal.add(5);
        preorderNormal.add(7);

        // Should be [3, 0, 1, 2, 8, 4, 6, 5, 7]
        assertEquals(preorderNormal, tree.preorder());

        List<Integer> degeneratePreorder = new ArrayList<>();
        degeneratePreorder.add(1);
        degeneratePreorder.add(2);
        degeneratePreorder.add(3);
        degeneratePreorder.add(4);
        degeneratePreorder.add(5);

        // Should be [1, 2, 3, 4, 5]
        assertEquals(degeneratePreorder, degenerateTree.preorder());

        assertArrayEquals(new Integer[]{}, empty.preorder().toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testInorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> inorderNormal = new ArrayList<>();
        inorderNormal.add(0);
        inorderNormal.add(1);
        inorderNormal.add(2);
        inorderNormal.add(3);
        inorderNormal.add(4);
        inorderNormal.add(5);
        inorderNormal.add(6);
        inorderNormal.add(7);
        inorderNormal.add(8);

        // Should be [0, 1, 2, 3, 4, 5, 6, 7, 8]
        assertEquals(inorderNormal, tree.inorder());

        List<Integer> degenerateInorder = new ArrayList<>();
        degenerateInorder.add(1);
        degenerateInorder.add(2);
        degenerateInorder.add(3);
        degenerateInorder.add(4);
        degenerateInorder.add(5);

        // Should be [1, 2, 3, 4, 5]
        assertEquals(degenerateInorder, degenerateTree.inorder());

        assertArrayEquals(new Integer[]{}, empty.inorder().toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> postorder = new ArrayList<>();
        postorder.add(2);
        postorder.add(1);
        postorder.add(0);
        postorder.add(5);
        postorder.add(7);
        postorder.add(6);
        postorder.add(4);
        postorder.add(8);
        postorder.add(3);

        // Should be [2, 1, 0, 5, 7, 6, 4, 8, 3]
        assertEquals(postorder, tree.postorder());

        List<Integer> degeneratePostorder = new ArrayList<>();
        degeneratePostorder.add(5);
        degeneratePostorder.add(4);
        degeneratePostorder.add(3);
        degeneratePostorder.add(2);
        degeneratePostorder.add(1);

        // Should be [5, 4, 3, 2, 1]
        assertEquals(degeneratePostorder, degenerateTree.postorder());

        assertArrayEquals(new Integer[]{}, empty.postorder().toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> levelorder = new ArrayList<>();
        levelorder.add(3);
        levelorder.add(0);
        levelorder.add(8);
        levelorder.add(1);
        levelorder.add(4);
        levelorder.add(2);
        levelorder.add(6);
        levelorder.add(5);
        levelorder.add(7);

        // Should be [3, 0, 8, 1, 4, 2, 6, 5, 7]
        assertEquals(levelorder, tree.levelorder());

        List<Integer> degenerateLevelOrder = new ArrayList<>();
        degenerateLevelOrder.add(1);
        degenerateLevelOrder.add(2);
        degenerateLevelOrder.add(3);
        degenerateLevelOrder.add(4);
        degenerateLevelOrder.add(5);
        assertEquals(degenerateLevelOrder, degenerateTree.levelorder());

        assertArrayEquals(new Integer[]{}, empty.levelorder().toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
              2
             /
            0
             \
              1
        */

        tree.add(2);
        tree.add(0);
        tree.add(1);
        assertEquals(3, tree.size());

        assertEquals(2, tree.height());

        /*
            1
             \
              2
               \
                3
                 \
                  4
                   \
                    5
         */

        assertEquals(5, degenerateTree.size());
        assertEquals(4, degenerateTree.height());

        assertEquals(0, empty.size());
        assertEquals(-1, empty.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        /*
              2
             /
            0
             \
              1
        */

        tree.add(2);
        tree.add(0);
        tree.add(1);
        assertEquals(3, tree.size());

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());

        degenerateTree.clear();
        assertEquals(0, degenerateTree.size());
        assertNull(degenerateTree.getRoot());

        empty.clear();
        assertEquals(0, empty.size());
        assertNull(empty.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testPruneGreaterThan() {
        /*
                    50
                  /    \
                25      75
               /  \
              12   37
             /  \    \
            10  15    40
               /
              13
        */

        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(12);
        tree.add(37);
        tree.add(10);
        tree.add(15);
        tree.add(40);
        tree.add(13);
        assertEquals(9, tree.size());

        //Create our list of integers here for level order checking
        List<Integer> nodeList = new LinkedList<>();
        //Let's test with prune(38)
        BSTNode<Integer> temp = BST.pruneGreaterThan(tree.getRoot(), 38);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{25, 12, 37, 10, 15, 13},
                nodeList.toArray());
        //Let's test with prune(25)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 25);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{25, 12, 10, 15, 13}, nodeList.toArray());
        //Let's test with prune(41)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 41);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{25, 12, 37, 10, 15, 40, 13},
                nodeList.toArray());
        //Let's test with prune(38) again
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 38);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{25, 12, 37, 10, 15, 13},
                nodeList.toArray());
        //Let's test with prune(36)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 36);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{25, 12, 10, 15, 13}, nodeList.toArray());
        //Let's test with prune(13)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 13);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{12, 10, 13}, nodeList.toArray());
        //Let's test with prune(0)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 0);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{}, nodeList.toArray());
        //Let's test with prune(51)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 51);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{50, 25, 12, 37, 10, 15, 40, 13},
                nodeList.toArray());
        //Let's test with prune(76)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 76);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{50, 25, 75, 12, 37, 10, 15, 40, 13},
                nodeList.toArray());
        //Let's test with prune(11)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(tree.getRoot(), 11);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{10}, nodeList.toArray());

        //Testing degenerate tree now!

        //Let's test with prune(2)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(degenerateTree.getRoot(), 2);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{1, 2}, nodeList.toArray());
        //Let's test with prune(0)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(degenerateTree.getRoot(), 0);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{}, nodeList.toArray());
        //Let's test with prune(4)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(degenerateTree.getRoot(), 4);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, nodeList.toArray());
        //Let's test with prune(5)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(degenerateTree.getRoot(), 5);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, nodeList.toArray());
        //Let's test with prune(6)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(degenerateTree.getRoot(), 6);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, nodeList.toArray());

        //Testing empty tree now!

        //Let's test with prune(0)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(empty.getRoot(), 0);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{}, nodeList.toArray());

        //Let's test with prune(100)
        regenerateTrees();
        nodeList = new LinkedList<>();
        temp = BST.pruneGreaterThan(empty.getRoot(), 100);
        generatelevelorder(temp, nodeList);
        assertArrayEquals(new Integer[]{}, nodeList.toArray());
    }

    /**
     * A helper method that generate a level order traversal to help with
     * testing.
     *
     * @param root the root of the BST
     * @param list the list containing data in a level order traversal
     */
    private void generatelevelorder(BSTNode<Integer> root,
                                     List<Integer> list) {
        BSTNode<Integer> curr = root;
        Queue<BSTNode<Integer>> queue = new LinkedList<>();
        queue.add(curr);
        while (!queue.isEmpty()) {
            BSTNode<Integer> node = queue.poll();
            if (node != null) {
                list.add(node.getData());
                queue.add(node.getLeft());
                queue.add(node.getRight());
            }
        }
    }

    /**
     * A helper method to regenerate the trees (empty, degenerate, and example
     * tree) for prune
     */
    private void regenerateTrees() {
        tree = new BST<>();
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(12);
        tree.add(37);
        tree.add(10);
        tree.add(15);
        tree.add(40);
        tree.add(13);

        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i + 1);
        }
        degenerateTree = new BST<>(list);
        empty = new BST<>();
    }
}