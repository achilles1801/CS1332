import java.util.NoSuchElementException;


/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Majd Khawaldeh
 * @version 1.0
 * @userid Majd
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */


 public void addAtIndex(int index, T data) {
    if (index < 0 || index > size) {
        throw new IndexOutOfBoundsException("Index should be between 0 and size");
    } else if (data == null) {
        throw new IllegalArgumentException("Data must not be null!");
    }

    SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<T>(data);
    
    // If list is empty
    if (head == null) {
        head = newNode;
        tail = newNode;
    } else if (index == 0) {  // Insert at the start
        newNode.setNext(head);
        head = newNode;
    } else if (index == size) {  // Insert at the end
        tail.setNext(newNode);
        tail = newNode;
    } else {  // Insert in the middle
        SinglyLinkedListNode<T> current = head;
        for (int i = 0; i < index - 1; i++) { // traverse to the node before the desired position
            current = current.getNext();
        }
        newNode.setNext(current.getNext());
        current.setNext(newNode);
    }

    size++;
}



    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
      public void addToFront(T data) {

        if (data == null) {

            throw new IllegalArgumentException("Data can not be null.");

        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<T>(data);

        newNode.setNext(head);

        head = newNode;

        if (size == 0) {

            tail = newNode;

        }

        size++;

    }




    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
      public void addToBack(T data) {

        if (data == null) {

            throw new IllegalArgumentException("Data can not be null.");

        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<T>(data);

        if (size == 0) {

            head = newNode;

        } else {

            tail.setNext(newNode);

        }

        tail = newNode;

        size++;



    }



    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and size");
        }
        T removedData;
        if (head == null) {
            return null;
        } else if (index == 0) {
            removedData = head.getData();
            //head.setNext(head.getNext());
            head = head.getNext();
            if (size == 1) {
                tail = head;
            }

        } else {
            SinglyLinkedListNode<T> prev = null;
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                prev = curr;
                curr = curr.getNext();
            }
            removedData = curr.getData();
            prev.setNext(curr.getNext());
            if (prev.getNext() == null) {
                tail = prev;
            }
        }
        size--;
        return removedData;

    }
      


    /**
     * Removes and returns the first data of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        T removed = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
        }
        size--;
        return removed;


    }

    /**
     * Removes and returns the last data of the list.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("list is empty");
        }
        T removed = tail.getData();
        if (size == 1) {
            removed = head.getData();
            head = null;
            tail = null;
        } else {
            SinglyLinkedListNode<T> curr = head;
            while (curr != null) {
                if (curr.getNext().getNext() == null) {
                    curr.setNext(null);
                    tail = curr;
                    break;
                }
                curr = curr.getNext();
            }
        }
        size--;
        return removed;
    }
















    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
 public T get(int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index must be between 0 and size.");
    }

    if (index == 0) {
        return head.getData();
    }
    
    if (index == size - 1) {
        return tail.getData();
    }

    SinglyLinkedListNode<T> current = head;
    for (int position = 0; position < index; position++) {
        current = current.getNext();
    }

    return current.getData();
}




    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);

    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;

    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {


        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        if (size == 0) {
            throw new NoSuchElementException("Not found");
        }

        SinglyLinkedListNode<T> curr = head;
        SinglyLinkedListNode<T> prev = null;
        SinglyLinkedListNode<T> currlastocc = null;
        SinglyLinkedListNode<T> prevlastocc = null;

        while (curr != null) {
            if (curr.getData().equals(data)) {
                prevlastocc = prev;
                currlastocc = curr;
            }
            prev = curr;
            curr = curr.getNext();

        }


        if (currlastocc != null) {
            if (currlastocc == head) {
                head = currlastocc.getNext();
            } else {
                prevlastocc.setNext(currlastocc.getNext());
            }
            if (currlastocc == tail) {
                tail = prevlastocc;
            }
            size--;
            return currlastocc.getData();
        } else {
            return null;
        }


    }



    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (size == 0) {
            return null;
        }
        SinglyLinkedListNode<T> current = head;
        T[] myArray = (T[]) new Object[size];
        int position = 0;

        while (current != null) {
            myArray[position] = current.getData();
            current = current.getNext();
            position++;
        }

        return (T[]) myArray;

    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
