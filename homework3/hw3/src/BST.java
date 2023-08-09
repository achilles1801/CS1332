import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Majd Khawaldeh
 * @version 1.0
 * @userid Majd
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * Office Hours with Indira
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data can not be null!");
        }
        size = 0;
        for (T item : data) {
            add(item);
        }

    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        root = addHelper(data, root);

    }

    /**
     * helper method for add(T data)
     *
     * @param data data being inserted
     * @param node the current node in the tree
     * @return the updated tree
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        }
        int val = data.compareTo(node.getData());
        if (val > 0) {
            node.setRight(addHelper(data, node.getRight()));

        } else if (val < 0) {
            node.setLeft(addHelper(data, node.getLeft()));

        }
        return node;

    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {

//        if (data == null) {
//            throw new IllegalArgumentException("Data cannot be null!");
//        }
//        BSTNode<T> removed = new BSTNode<>(null);
//        root = removeHelper(data, root, removed);
//        return removed.getData();


    }

    /**
     * helper method for remove(T data)
     *
     * @param data    the data to remove
     * @param current the current node of the tree
     * @param removed the node to be removed
     * @return the parent node of the node to be removed
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> current, BSTNode<T> removed) {
//        if (current == null) {
//            throw new NoSuchElementException("Data has not been found!");
//        } else {
//            int val = data.compareTo(current.getData());
//            if (val > 0) {
//                current.setRight(removeHelper(data, current.getRight(), removed));
//            } else if (val < 0) {
//                current.setLeft(removeHelper(data, current.getLeft(), removed));
//
//            } else {
//                removed.setData(current.getData());
//                size--;
//                if (current.getLeft() == null || current.getRight() == null) {
//                    return current.getLeft() != null ? current.getLeft() : current.getRight();
//                } else {
//                    BSTNode<T> dummy = new BSTNode<>(null);
//                    current.setRight(getSuccessor(current.getRight(), dummy));
//                    current.setData(dummy.getData());
//                    return current;
//
//                }
//
//            }
//        }
//        return current;

    }


    /**
     * helper method for remove(T data)
     *
     * @param current the current node
     * @param dummy   node to hold the successor data
     * @return the right child of my current node
     */
    private BSTNode<T> getSuccessor(BSTNode<T> current, BSTNode<T> dummy) {
//        if (current.getLeft() == null) {
//            dummy.setData(current.getData());
//            return current.getRight();
//        }
//        current.setLeft(getSuccessor(current.getLeft(), dummy));
//        return current;
    }


    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        return getHelper(data, root);


    }

    /**
     * helper method for get(T data)
     *
     * @param data the data we are searching for
     * @param curr the current node in the tree
     * @return the data in the tree equal to the parameter
     */
    private T getHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Data has not been found!");
        } else {
            int val = data.compareTo(curr.getData());
            if (data.equals(curr.getData())) {
                return curr.getData();
            } else if (val < 0) {
                return getHelper(data, curr.getLeft());
            } else if (val > 0) {
                return getHelper(data, curr.getRight());
            }
            return null;

        }

    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException(" Data must not be null!");
        }
        return containsHelper(data, root);


    }

    /**
     * helper method for contains(T data)
     *
     * @param data the data we are searching for
     * @param curr the current node in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean containsHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        } else {
            int val = data.compareTo(curr.getData());
            if (data.equals(curr.getData())) {
                return true;
            } else if (val < 0) {
                return containsHelper(data, curr.getLeft());

            } else if (val > 0) {
                return containsHelper(data, curr.getRight());

            }

        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> data = new ArrayList<>();
        preorderHelper(root, data);
        return data;
    }

    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }

    }
//    public List<T> preorder() {
//        List<T> data = new ArrayList<>();
//        preorderHelper(root, data);
//        return data;
//
//
//    }

    /**
     * Helper method for preorder()
     *
     * @param node the root node of a tree
     * @param list the list storing the data
     */
//    private void preorderHelper(BSTNode<T> node, List<T> list) {
//        if (node != null) {
//            list.add(node.getData());
//            preorderHelper(node.getLeft(), list);
//            preorderHelper(node.getRight(), list);
//        }
//
//
//    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> data = new ArrayList<>();
        inorderHelper(root, data);
        return data;

    }

    /**
     * Helper method for inorder()
     *
     * @param node the root node of a tree
     * @param list the list storing the data
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }

    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> data = new ArrayList<>();
        postorderHelper(root, data);
        return data;

    }

    /**
     * Helper method for postorder()
     *
     * @param node the root node of a tree
     * @param list the list storing the data
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> data = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> curr = queue.poll();
            data.add(curr.getData());
            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.gerRight());
            }

        }
        return data;
    }


//    public List<T> levelorder() {
//        List<T> data = new ArrayList<>();
//        Queue<BSTNode<T>> queue = new LinkedList<>();
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            BSTNode<T> current = queue.poll();
//            data.add(current.getData());
//            if (current.getLeft() != null) {
//                queue.add(current.getLeft());
//
//            }
//            if (current.getRight() != null) {
//                queue.add(current.getRight());
//
//            }
//
//        }
//        return data;
//
//    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * helper method for height()
     *
     * @param node the current node in the tree
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int heightHelper(BSTNode<T> node) {
        return node == null ? -1 : Math.max(heightHelper(node.getLeft()), heightHelper(node.getRight())) + 1;


    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;

    }

    /**
     * Removes all elements strictly greater than the passed in data.
     * <p>
     * This must be done recursively.
     * <p>
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * <p>
     * Using ?????
     * <p>
     * EXAMPLE: Given the BST below composed of Integers:
     * <p>
     * 50
     * /    \
     * 25      75
     * /  \
     * 12   37
     * /  \    \
     * 10  15    40
     * /
     * 13
     * <p>
     * pruneGreaterThan(27) should remove 37, 40, 50, 75. Below is the resulting BST
     * 25
     * /
     * 12
     * /  \
     * 10  15
     * /
     * 13
     * <p>
     * Should have a running time of O(n)??? O(log(n)) for balanced tree
     *
     * @param data the threshold data. Elements greater than data should be removed
     * @param tree the root of the tree to prune nodes from
     * @param <T>  the generic typing of the data in the BST
     * @return the root of the tree with all elements greater than data removed
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThan(BSTNode<T> tree, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        tree = pruneFinder(tree, data);
        return tree;


    }

    /**
     * Helper method for pruneGreaterThan(BSTNode tree, T data)
     *
     * @param curr node with the threshold data
     * @param data the root of the tree to prune nodes from
     * @param <T>  the generic typing of the data in the BST
     * @return the root of the tree with all the elements greater than data removed
     */

    private static <T extends Comparable<? super T>> BSTNode<T> pruneFinder(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) > 0) {
            return pruneFinder(curr.getLeft(), data);


        } else {
            curr.setRight(pruneFinder(curr.getRight(), data));
            return curr;
        }


    }


    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
