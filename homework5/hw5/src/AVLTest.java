import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * These tests are pretty exhaustive.
 * @author Michael Ortega
 * @version 3.0
 */
public class AVLTest {
    private static final int TIMEOUT = 200;
    private AVL<Integer> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotation() {
        // right rotation
        /*
                    5                   4
                   /                   / \
                  4         ->        3   5
                 /
                3
         */
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        // loaded right rotation
        /*
                    10                    5
                   / \                  /  \
                  5  15     ->         3   10
                 / \                 /    / \
                3   8               1    8   15
               /
              1
         */
        avlTree = new AVL<>();
        avlTree.add(10);
        avlTree.add(5);
        avlTree.add(15);
        avlTree.add(8);
        avlTree.add(3);
        avlTree.add(1);
        root = avlTree.getRoot();
        assertEquals(6, avlTree.size());

        assertEquals((Integer) 5, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 10, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 8, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 15, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        // left rotation
        /*
                 3                     4
                  \                   / \
                   4         ->      3   5
                    \
                     5
         */
        avlTree = new AVL<>();
        avlTree.add(3);
        avlTree.add(4);
        avlTree.add(5);
        root = avlTree.getRoot();
        assertEquals(3, avlTree.size());

        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        // loaded left rotation
        /*
                    3                     8
                   / \                  /  \
                  1   8     ->        3    10
                     / \             / \     \
                    5  10           1   5    15
                         \
                         15
         */
        avlTree = new AVL<>();
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(8);
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(15);
        root = avlTree.getRoot();
        assertEquals(6, avlTree.size());

        assertEquals((Integer) 8, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 10, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 15, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotationRoot() {
        // right left rotation
        /*
                3               4
                 \             / \
                  5     ->    3   5
                 /
                4
         */
        avlTree.add(3);
        avlTree.add(5);
        avlTree.add(4);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        // loaded right left rotation
        /*
                2               3
               / \             / \
              1   6     ->    2   6
                 / \         /   / \
                3   7       1   5  7
                 \
                  5

         */
        avlTree = new AVL<>();
        avlTree.add(2);
        avlTree.add(1);
        avlTree.add(6);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(5);
        root = avlTree.getRoot();
        assertEquals(6, avlTree.size());

        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 2, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 6, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 7, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        // left right rotation
        /*
                 5             4
                /             / \
               3     ->      3   5
                \
                 4
         */
        avlTree = new AVL<>();
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(4);

        assertEquals(3, avlTree.size());

        root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        // loaded left right rotation
        /*
                6                3
               / \             /  \
              2   7     ->    2    6
             / \             /    / \
            1   3           1    5  7
                 \
                  5

         */
        avlTree = new AVL<>();
        avlTree.add(6);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(1);
        avlTree.add(3);
        avlTree.add(5);
        root = avlTree.getRoot();
        assertEquals(6, avlTree.size());

        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 2, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 6, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 7, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        // Two children, close predecessor with no children
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       386   856
                 / \                       \
               386 526                      526
         */
        Integer toBeRemoved = new Integer(477);
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);

        assertSame(toBeRemoved, avlTree.remove(new Integer(477)));

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 526, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        /* Two children, close predecessor with one child
                     646                     646
                   /    \                  /    \
                 477    856     ->       386    856
                 / \    / \             /  \    / \
              386 526 713 942         370 526 713 942
              /
            370
         */
        toBeRemoved = 477;

        avlTree = new AVL<>();
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);
        avlTree.add(713);
        avlTree.add(942);
        avlTree.add(370);

        assertSame(toBeRemoved, avlTree.remove(477));

        assertEquals(7, avlTree.size());

        root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 370, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 526, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 713, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 942, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        // Two children, far predecessor with no children
        /*
                     646                     589
                   /    \                  /    \
                 477    856     ->       477    856
                 / \    / \             /  \    / \
              386 526 713 942         386 526 713 942
                    \
                   589
         */
        toBeRemoved = 646;

        avlTree = new AVL<>();
        avlTree.add(toBeRemoved);
        avlTree.add(477);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);
        avlTree.add(713);
        avlTree.add(942);
        avlTree.add(589);

        assertSame(toBeRemoved, avlTree.remove(new Integer(646)));

        assertEquals(7, avlTree.size());

        root = avlTree.getRoot();
        assertEquals((Integer) 589, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 477, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 526, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 713, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 942, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        // Two children, far predecessor with one child
        /*
                     646                     526
                   /    \                  /    \
                 477    856     ->       477    856
                 / \    / \             /  \    / \
              386 526 713 942         386 498 713 942
                   /
                 498
         */
        toBeRemoved = 646;

        avlTree = new AVL<>();
        avlTree.add(toBeRemoved);
        avlTree.add(477);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);
        avlTree.add(713);
        avlTree.add(942);
        avlTree.add(498);

        assertSame(toBeRemoved, avlTree.remove(new Integer(646)));

        assertEquals(7, avlTree.size());

        root = avlTree.getRoot();
        assertEquals((Integer) 526, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 477, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 498, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 713, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 942, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        // One left child with left right rotation needed
        /*
                     646                    646                  646                     526
                   /    \                  /  \                  / \                   /    \
                 477    856     ->       477  713    ->       526  713     ->       477     646
                 / \    /               /  \        left      / \        right     /  \     / \
              386 526 713             386  526       rot    477  563       rot   386 498  563 713
                  / \                      / \             /  \
                498 563                  498 563         386  498
         */
        toBeRemoved = 856;

        avlTree = new AVL<>();
        avlTree.add(646);
        avlTree.add(477);
        avlTree.add(toBeRemoved);
        avlTree.add(713);
        avlTree.add(386);
        avlTree.add(526);
        avlTree.add(498);
        avlTree.add(563);

        assertSame(toBeRemoved, avlTree.remove(new Integer(856)));

        assertEquals(7, avlTree.size());
        root = avlTree.getRoot();
        assertEquals((Integer) 526, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 477, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 646, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 498, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 563, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 713, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        /*
                        477
                       /   \
                     386   526
                             \
                             646
         */
        Integer maximum = new Integer(646);
        avlTree.add(477);
        avlTree.add(526);
        avlTree.add(386);
        avlTree.add(maximum);

        assertSame(maximum, avlTree.get(new Integer(646)));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        avlTree.add(new Integer(477));
        avlTree.add(new Integer(526));
        avlTree.add(new Integer(386));
        avlTree.add(new Integer(646));

        assertEquals(true, avlTree.contains(new Integer(477)));
        assertEquals(true, avlTree.contains(new Integer(386)));
        assertEquals(true, avlTree.contains(new Integer(646)));
        assertEquals(false, avlTree.contains(new Integer(387)));
        assertEquals(false, avlTree.contains(new Integer(700)));
        assertEquals(false, avlTree.contains(new Integer(500)));
    }


    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
                     646
                    /   \
                  477   856
                  / \
                386 526
         */
        avlTree.add(646);
        avlTree.add(386);
        avlTree.add(856);
        avlTree.add(526);
        avlTree.add(477);

        assertEquals(2, avlTree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testFindMedian() {
        // left heavy
        /*
                    76
                 /      \
               34        90
              /  \
            20    40

            median: 40
         */

        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);
        avlTree.add(20);
        avlTree.add(40);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals(root.getLeft().getRight().getData(), avlTree.findMedian());

        // balanced
        /*
                    76
                 /      \
               34        90
              /  \      /   \
            20    40  81    100

            median: 76
         */
        avlTree.add(81);
        avlTree.add(100);
        assertEquals(root.getData(), avlTree.findMedian());

        // right heavy
        /*
                    76
                 /      \
               34        90
              /  \      /   \
            20    40  81    100
                     / \
                    79 85

            median: 79
         */
        avlTree.add(79);
        avlTree.add(85);
        assertEquals((Integer) 79, avlTree.findMedian());

        // right heavy but bf = 0 at root
        /*
                     76
                 /       \
               34         90
              /  \       /   \
            20    40   81     100
           / \        / \     / \
         10  25      79 85   99 103

            median: 79
         */
        avlTree.add(10);
        avlTree.add(25);
        avlTree.add(99);
        avlTree.add(103);
        assertEquals((Integer) 79, avlTree.findMedian());

        // left heavy but bf = 0 at root
        /*
                              76
                         /          \
                      34             90
                    /    \          /    \
                  20      40       81    100
                 / \     / \      / \     / \
               10  25   37 58    79 85   99 103
              / \  / \                      / \
            7  14 21 26                   101  111

            median: 58
         */

        avlTree.add(37);
        avlTree.add(58);
        avlTree.add(7);
        avlTree.add(14);
        avlTree.add(21);
        avlTree.add(26);
        avlTree.add(101);
        avlTree.add(111);
        assertEquals((Integer) 58, avlTree.findMedian());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructorAndClear() {
        /*
                     7
                    / \
                   1   24
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(7);
        toAdd.add(24);
        toAdd.add(1);
        avlTree = new AVL<>(toAdd);

        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testDuplicateAdd() {
        avlTree.add(1);
        avlTree.add(1);
        assertEquals(1, avlTree.size());
    }

    @Test(timeout = TIMEOUT * 4)
    public void addDegeneratively() {
        // Adding in a degenerate manner
        /*
               1               2             2              4                  4
                \             / \           / \            / \               /   \
                 2     ->    1   3   ->    1   4   ->     2   5      ->    2      6
                  \               \           / \        / \   \          / \    / \
                   3               4         3   5      1   3   6        1   3  5  7
                                    \             \              \
                                     5             6              7

         */
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(3);
        avlTree.add(4);
        avlTree.add(5);
        avlTree.add(6);
        avlTree.add(7);
        assertEquals(7, avlTree.size());
        AVLNode<Integer> root = avlTree.getRoot();

        assertEquals((Integer) 4, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 2, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 6, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 7, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        // very large trees, set n accordingly
        // if n is too large it may time out, so adjust the timeout
        int n = 15;
        int size = (int) Math.pow(2, n + 1) - 1;

        // right degeneration
        avlTree = new AVL<>();
        for (int i = 1; i <= size; ++i) {
            avlTree.add(i);
        }
        assertEquals(size, avlTree.size());
        root = avlTree.getRoot();
        assertEquals((Integer) (int) Math.ceil(size / 2.0), root.getData());
        assertEquals(n, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        // left degeneration
        avlTree = new AVL<>();
        for (int i = size; i > 0; --i) {
            avlTree.add(i);
        }
        assertEquals(size, avlTree.size());
        root = avlTree.getRoot();
        assertEquals((Integer) (int) Math.ceil(size / 2.0), root.getData());
        assertEquals(n, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void randomBehaviour() {
        /* Instead of writing out every possible rotation case for remove, this
         method adds and removes randomly. If your methods are implemented correctly,
         all roots should have a balance factor of -1, 0, or 1. Also tests median, height,
         contains, and get. */
        ArrayList<Integer> list;
        Random random = new Random();
        int item;
        int cap = 51 + (int) (Math.random() * 81 - 20);

        for (int i = 0; i < 100; ++i) {
            avlTree = new AVL<>();
            list = new ArrayList<>();

            // populate tree and list
            for (int j = 0; j < cap; ++j) {
                item = (int) (j * (Math.random() * 4));
                if (list.contains(item)) {
                    continue;
                }
                list.add(item);
                avlTree.add(list.get(list.indexOf(item)));
            }
            assertEquals(list.size(), avlTree.size());

            // remove from tree
            for (int k = 0 ; k < list.size() * 0.5; ++k) {
                try {
                    item = list.remove(random.nextInt(list.size()));
                    assertEquals((Integer) item, avlTree.remove(item));
                } catch (NoSuchElementException ignored) {
                }
            }
            Collections.sort(list);
            assertEquals(list.size(), avlTree.size());
            for (Integer integer : list) {
                assertEquals(integer, avlTree.get(integer));
            }

            if (list.size() % 2 == 1) {
                // inorder();
                // System.out.println(list);
                assertEquals(list.get(list.size() / 2), avlTree.findMedian());
            }
            // the worst possible height of an AVL would be in the case of adding in a
            // fibonacci sequence, which would be 1.44log(n)
            assertTrue(avlTree.height() < 1.5*(Math.log(list.size()) / Math.log(2)));
            assertNotEquals(2, avlTree.getRoot().getBalanceFactor());

            // check for a few values
            for (int m = 0; m < list.size() * 0.3; ++m) {
                item = list.get(random.nextInt(list.size()));
                assertTrue(avlTree.contains(item));
                assertFalse(avlTree.contains((int) (m + Math.random() * 500 + 500)));
            }
        }
    }

    public void levelorder() {
        List<AVLNode<Integer>> queue = new java.util.LinkedList<>();
        AVLNode<Integer> curr;
        queue.add(avlTree.getRoot());
        while (!queue.isEmpty()) {
            if (queue.get(0) == null) {
                break;
            }
            curr = queue.remove(0);
            System.out.print(curr.getData() + " ");
            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
            }
        }
        System.out.println();
    }

    public void inorder() {
        System.out.print("[");
        inorder(avlTree.getRoot());
        System.out.print("]\n");
    }

    public void inorder(AVLNode<Integer> root) {
        if (root == null) {
            return;
        }
        inorder(root.getLeft());
        System.out.print(root.getData() + ", ");
        inorder(root.getRight());
    }
}