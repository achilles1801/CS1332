import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * This is a basic set of unit tests for ArrayQueue and LinkedQueue.
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
 * @author CS 1332 TAs
 * @version 1.0
 */
public class QueueTest {

    private static final int TIMEOUT = 200;
    private ArrayQueue<String> array;
    private LinkedQueue<String> linked;

    @Before
    public void setup() {
        array = new ArrayQueue<>();
        linked = new LinkedQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayQueue.INITIAL_CAPACITY],
            array.getBackingArray());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayEnqueue() {
        // ensure correct exception is thrown
        assertThrows(IllegalArgumentException.class, () -> array.enqueue(null));

        array.enqueue("0a");    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());

        // ensure resizing is done correctly
        array = new ArrayQueue<>();
        expected = new Object[ArrayQueue.INITIAL_CAPACITY << 3];
        for (int i = 0; i < 9 << 3; ++i) {
            array.enqueue(String.format("%da", i));
            expected[i] = String.format("%da", i);
        }
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeue() {
        String temp = "0a";
        // ensure correct exception is thrown
        assertThrows(NoSuchElementException.class, () -> array.dequeue());

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, array.size());

        assertSame(temp, array.dequeue());  // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        assertArrayEquals(expected, array.getBackingArray());

        // dequeue everything
        int c = array.size();
        for (int i = 0; i < c; i++) {
            temp = String.format("%da", i + 1);
            assertEquals(temp, array.dequeue());
        }
        assertEquals(0, array.size());

        // ensure backing array is not downsized
        array = new ArrayQueue<>();
        for (int i = 0; i < 9 << 3; ++i) {
            array.enqueue(String.format("%da", i));
        }
        for (int i = 0; i < 9 << 3; ++i) {
            array.dequeue();
        }
        Object[] arr = array.getBackingArray();
        assertEquals(72, arr.length);
        assertEquals(0, array.size());

        // dequeue then enqueue
        // if an IndexOutOfBoundsError occurs, your array isn't properly circular
        array = new ArrayQueue<>();
        for (int i = 0; i < 20; ++i) {
            array.enqueue(String.format("%da", i));
            array.enqueue(array.dequeue());
            arr = array.getBackingArray();
            printArray(arr);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPeek() {
        String temp = "0a";
        // ensure correct exception is thrown
        assertThrows(NoSuchElementException.class, () -> array.peek());

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, array.size());

        assertSame(temp, array.peek());
        array.dequeue();
        assertEquals("1a", array.peek());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedEnqueue() {
        // ensure correct exception is thrown
        assertThrows(IllegalArgumentException.class, () -> linked.enqueue(null));

        linked.enqueue("0a");   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");   // 0a, 1a, 2a, 3a
        linked.enqueue("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("0a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(linked.getTail(), cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeue() {
        String temp = "0a";
        // ensure correct exception is thrown
        assertThrows(NoSuchElementException.class, () -> linked.dequeue());

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        linked.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, linked.size());

        assertSame(temp, linked.dequeue()); // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(linked.getTail(), cur);
        assertEquals("5a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        // dequeue everything
        int c = linked.size();
        for (int i = 0; i < c; ++i) {
            assertEquals(String.format("%da", i + 1), linked.dequeue());
        }
        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertEquals(0, linked.size());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPeek() {
        String temp = "0a";

        // ensure correct exception is thrown
        assertThrows(NoSuchElementException.class, () -> linked.peek());

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, linked.size());

        assertSame(temp, linked.peek());
        linked.dequeue();
        assertEquals("1a", linked.peek());
    }

    private void printArray(Object[] arr) {
        for (Object s : arr) {
            System.out.printf("%s, ", s);
        }
        System.out.println();
    }
}
