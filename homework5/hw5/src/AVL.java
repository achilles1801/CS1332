import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Majd Khawaldeh
 * @version 1.0
 * @userid majd
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        size = 0;
        for (T item : data) {
            add(item);

        }

    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @param data the data to be added
     * @throws IllegalArgumentException if the data is null
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
     * @param data the data to be added
     * @param curr the current node in the tree
     * @return the updated tree
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        }
        int val = data.compareTo(curr.getData());
        if (val < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));

        } else if (val > 0) {
            curr.setRight(addHelper(data, curr.getRight()));

        } else {
            return curr;
        }
        calculate(curr);
        return balance(curr);

    }

    /**
     * Balances the node by rotation
     *
     * @param curr the node of a tree that will get balanced
     * @return node that is balanced
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight()));
            }
            curr = rotateLeft(curr);
        } else if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
            }
            curr = rotateRight(curr);
        }
        return curr;


    }

    /**
     * Do left rotation
     *
     * @param curr the node that will get rotated
     * @return node that is left-rotated
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getRight();
        curr.setRight(temp.getLeft());
        temp.setLeft(curr);
        calculate(curr);
        calculate(temp);
        return temp;
    }

    /**
     * Do right rotation
     *
     * @param curr the node that will get rotated
     * @return node that is right-rotated
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getLeft();
        curr.setLeft(temp.getRight());
        temp.setRight(curr);
        calculate(curr);
        calculate(temp);
        return temp;

    }

    /**
     * Calculate height and balance factor
     *
     * @param node the node that will get calculated
     */
    private void calculate(AVLNode<T> node) {
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     * <p>
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        return removed.getData();
    }

    /**
     * helper method for remove(T data)
     *
     * @param data    the data to remove
     * @param current the current node of the tree
     * @param removed the node to be removed
     * @return the parent node of the node to be removed
     */

    private AVLNode<T> removeHelper(T data, AVLNode<T> current, AVLNode<T> removed) {
        if (current == null) {
            throw new NoSuchElementException("Data has not been found!");
        } else {
            int val = data.compareTo(current.getData());
            if (val > 0) {
                current.setRight(removeHelper(data, current.getRight(), removed));
            } else if (val < 0) {
                current.setLeft(removeHelper(data, current.getLeft(), removed));

            } else {
                removed.setData(current.getData());
                size--;
                if (current.getLeft() == null || current.getRight() == null) {
                    return current.getLeft() != null ? current.getLeft() : current.getRight();
                } else {
                    AVLNode<T> dummy = new AVLNode<>(null);
                    current.setLeft(getPredecessor(current.getLeft(), dummy));
                    current.setData(dummy.getData());

                }

            }
        }
        calculate(current);
        return balance(current);

    }

    /**
     * Helper method for remove()
     * Finds predecessor node
     *
     * @param current the current node
     * @param dummy   the child of a node that will be removed
     * @return predecessor node of an element that will be removed
     */
    private AVLNode<T> getPredecessor(AVLNode<T> current, AVLNode<T> dummy) {
        if (current.getRight() == null) {
            dummy.setData(current.getData());
            return current.getLeft();
        }
        current.setRight(getPredecessor(current.getRight(), dummy));
        calculate(current);
        return balance(current);
    }


    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException   if the data is not found
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        return getHelper(root, data);

    }

    /**
     * helper method for get(T data)
     *
     * @param curr the current node in the tree
     * @param data the data we are searching for
     * @return the data in the tree equal to the parameter
     */
    public T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data has not been found!");
        } else {
            int val = data.compareTo(curr.getData());
            if (data.equals(curr.getData())) {
                return curr.getData();
            } else if (val < 0) {
                return getHelper(curr.getLeft(), data);
            } else if (val > 0) {
                return getHelper(curr.getRight(), data);
            }
            return null;

        }


    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        try {
            get(data);

        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Finds and retrieves the median data of the passed in AVL.
     * <p>
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 50
     * /    \
     * 25      75
     * /  \     / \
     * 13   37  70  80
     * /  \    \      \
     * 12  15    40    85
     * <p>
     * findMedian() should return 40
     *
     * @return the median data of the AVL
     * @throws NoSuchElementException if the tree is empty or contains an even number of data
     */
    public T findMedian() {
        if (root == null || size % 2 == 0) {
            throw new NoSuchElementException("Data not found!");
        }
        AVLNode<Integer> count = new AVLNode<>(0);
        AVLNode<T> data = new AVLNode<>(null);
        medianHelper(root, count, data);
        return data.getData();


    }

    /**
     * helper method for findMedian()
     *
     * @param curr  the current node
     * @param count node to keep track of how many nodes i've seen
     * @param data  the data of the median node
     */
    public void medianHelper(AVLNode<T> curr, AVLNode<Integer> count, AVLNode<T> data) {
        if (curr == null || data.getData() != null) {
            return;
        }
           // Traverse the left subtree
    if (count.getData() < (size + 1) / 2) {
        medianHelper(curr.getLeft(), count, data);
    }

    // Increase the count if the median hasn't been found yet
    if (data.getData() == null) {
        count.setData(count.getData() + 1);
    }

    // Check if the current node is the median
    if (count.getData() == (size + 1) / 2) {
        data.setData(curr.getData());
        return;
    }

    // Traverse the right subtree
    if (count.getData() < (size + 1) / 2) {
        medianHelper(curr.getRight(), count, data);
    }

    }


    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;

    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);

    }

    /**
     * Returns the height of a specific node
     *
     * @param node the node to look at
     * @return the height of the node
     */
    public int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }

    }

    /**
     * Returns the size of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
