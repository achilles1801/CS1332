import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * adjust iterations/timeout accordingly.
 *
 * @author Michael
 * @version 1.0
 */
public class SortingTest {
    private static final int ITERATIONS = 500;

    private static final int TIMEOUT = ITERATIONS * 2;

    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;

    private ComparatorPlus<Integer> intComp;

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Alex
                index 1: Ivan
                index 2: Miguel
                index 3: Paige
                index 4: Brooke
                index 5: Sanjana
                index 6: Yotam
                index 7: Nick
                index 8: Reece
                index 9: Destini
         */

        /*
            Sorted Names:
                index 0: Alex
                index 1: Brooke
                index 2: Destini
                index 3: Ivan
                index 4: Miguel
                index 5: Nick
                index 6: Paige
                index 7: Reece
                index 8: Sanjana
                index 9: Yotam
         */

        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Miguel");
        tas[3] = new TeachingAssistant("Paige");
        tas[4] = new TeachingAssistant("Brooke");
        tas[5] = new TeachingAssistant("Sanjana");
        tas[6] = new TeachingAssistant("Yotam");
        tas[7] = new TeachingAssistant("Nick");
        tas[8] = new TeachingAssistant("Reece");
        tas[9] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[4];
        tasByName[2] = tas[9];
        tasByName[3] = tas[1];
        tasByName[4] = tas[2];
        tasByName[5] = tas[7];
        tasByName[6] = tas[3];
        tasByName[7] = tas[8];
        tasByName[8] = tas[5];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();
        intComp = myInteger.getComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testSelectionSort() {
        Sorting.selectionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 45 && comp.getCount() != 0);

        // large randomly generated list (avg case)
        Integer[] list;
        Integer[] sortedList;
        ComparatorPlus<Integer> intComp;
        int n = ITERATIONS;

        for (int i = 0; i < ITERATIONS; ++i) {
            intComp = myInteger.getComparator();
            list = new Integer[n];
            sortedList = new Integer[n];
            for (int j = 0; j < n; ++j) {
                list[j] = ((int) (Math.random() * j * 10 - Math.random() * 100));
                sortedList[j] = list[j];
            }
            Sorting.selectionSort(list, intComp);
            Arrays.sort(sortedList);
            assertArrayEquals(sortedList, list);
            assertTrue("Number of comparisons: " + intComp.getCount(),
                    Math.abs(intComp.getCount() - (int) (Math.pow(n, 2) + n) / 2) <= n);
        }

        // small lists
        list = new Integer[1];
        list[0] = 0;
        intComp = myInteger.getComparator();
        sortedList = list;
        Sorting.selectionSort(list, intComp);
        assertArrayEquals(sortedList, list);
        list = new Integer[2];
        list[0] = 0;
        list[1] = -4;
        sortedList = list;
        Arrays.sort(sortedList);
        Sorting.selectionSort(list, intComp);
        assertArrayEquals(sortedList, list);

        // WCS list
        // (N^2 - N) / 2 + 1 comparisons
        n = 100;
        list = new Integer[100];
        sortedList = new Integer[100];
        for (int i = 99; i >= 0; --i) {
            list[99 - i] = i;
            sortedList[i] = i;
        }
        Sorting.selectionSort(list, intComp);
        assertArrayEquals(sortedList, list);
        assertEquals("Number of comparisons: " + intComp.getCount() + "\n(N^2 - N)/2: "
                + (int) (Math.pow(n, 2) - n) / 2, (int) ((Math.pow(n, 2) - n) / 2) + 1, intComp.getCount());

    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 24 && comp.getCount() != 0);

        Integer[] list;
        Integer[] sortedList;
        ComparatorPlus<Integer> intComp;

        // small lists
        list = new Integer[1];
        list[0] = 0;
        intComp = myInteger.getComparator();
        sortedList = list;
        Sorting.insertionSort(list, intComp);
        assertArrayEquals(sortedList, list);
        list = new Integer[2];
        list[0] = 0;
        list[1] = -4;
        sortedList = list;
        Arrays.sort(sortedList);
        Sorting.insertionSort(list, intComp);
        assertArrayEquals(sortedList, list);

        // large randomly generated list (avg case)
        int n = ITERATIONS;

        for (int i = 0; i < ITERATIONS; ++i) {
            intComp = myInteger.getComparator();
            list = new Integer[n];
            sortedList = new Integer[n];
            for (int j = 0; j < n; ++j) {
                list[j] = ((int) (Math.random() * j * 10 - Math.random() * 100));
                sortedList[j] = list[j];
            }
            Sorting.insertionSort(list, intComp);
            Arrays.sort(sortedList);
            assertArrayEquals(list, sortedList);
            assertTrue("Number of comparisons: " + intComp.getCount() + "\n(N^2 - N)/2: "
                            + (int) (Math.pow(n, 2) - n) / 2,  intComp.getCount() >= n - 1
                    && intComp.getCount() < (int) (Math.pow(n, 2) - n) / 2);
        }

        // already sorted
        // N - 1 comparisons or fewer
        list = new Integer[100];
        sortedList = new Integer[100];
        intComp = myInteger.getComparator();
        for (int i = 0; i < 100; ++i) {
            list[i] = i;
            sortedList[i] = i;
        }
        Sorting.insertionSort(list, intComp);
        assertArrayEquals(sortedList, list);
        assertTrue("Number of comparisons: " + intComp.getCount() + "\nN - 1: " + (n - 1),
                intComp.getCount() <= n - 1);

        // WCS list
        // (N^2 - N)/2 comparisons
        n = 100;
        list = new Integer[100];
        sortedList = new Integer[100];
        intComp = myInteger.getComparator();
        for (int i = 99; i >= 0; --i) {
            list[99 - i] = i;
            sortedList[i] = i;
        }
        Sorting.insertionSort(list, intComp);
        assertArrayEquals(sortedList, list);
        assertEquals("Number of comparisons: " + intComp.getCount(), (int) (Math.pow(n, 2) - n) / 2, intComp.getCount());
    }

    @Test(timeout = TIMEOUT * 3)
    public void testBubbleSort() {
        Sorting.bubbleSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 44 && comp.getCount() != 0);

        Integer[] list;
        Integer[] sortedList;
        ComparatorPlus<Integer> intComp;

        // small lists
        list = new Integer[1];
        list[0] = 0;
        intComp = myInteger.getComparator();
        sortedList = list;
        Sorting.bubbleSort(list, intComp);
        assertArrayEquals(sortedList, list);
        list = new Integer[2];
        list[0] = 0;
        list[1] = -4;
        sortedList = list;
        Arrays.sort(sortedList);
        Sorting.bubbleSort(list, intComp);
        assertArrayEquals(sortedList, list);

        // large randomly generated list (avg case)
        int n = ITERATIONS;

        for (int i = 0; i < ITERATIONS; ++i) {
            intComp = myInteger.getComparator();
            list = new Integer[n];
            sortedList = new Integer[n];
            for (int j = 0; j < n; ++j) {
                list[j] = ((int) (Math.random() * j * 10 - Math.random() * 100));
                sortedList[j] = list[j];
            }
            Sorting.bubbleSort(list, intComp);
            Arrays.sort(sortedList);
            assertArrayEquals(sortedList, list);
            assertTrue("Number of comparisons: " + intComp.getCount() + "\nN(N - 1)/2: "
                    + (int) (Math.pow(n, 2) - n) / 2,  intComp.getCount() >= n - 1
                    && intComp.getCount() <= (int) (Math.pow(n, 2) - n) / 2);
        }

        // already sorted
        // N - 1 comparisons or fewer
        n = 100;
        list = new Integer[100];
        sortedList = new Integer[100];
        intComp = myInteger.getComparator();
        for (int i = 0; i < n; ++i) {
            list[i] = i;
            sortedList[i] = i;
        }
        Sorting.bubbleSort(list, intComp);
        assertArrayEquals(list, sortedList);
        assertTrue("Number of comparisons: " + intComp.getCount() + "\nN - 1: " + n,
                intComp.getCount() <= n - 1);

        // WCS list
        // (N^2 - N)/2 comparisons
        list = new Integer[100];
        sortedList = new Integer[100];
        intComp = myInteger.getComparator();
        for (int i = 99; i >= 0; --i) {
            list[99 - i] = i;
            sortedList[i] = i;
        }
        Sorting.bubbleSort(list, intComp);
        assertArrayEquals(sortedList, list);
        assertEquals("Number of comparisons: " + intComp.getCount(), (int) (Math.pow(n, 2) - n) / 2, intComp.getCount());
    }


    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 21 && comp.getCount() != 0);

        Integer[] list;
        Integer[] sortedList;
        ComparatorPlus<Integer> intComp;

        // small lists
        list = new Integer[1];
        list[0] = 0;
        intComp = myInteger.getComparator();
        sortedList = list;
        Sorting.mergeSort(list, intComp);
        assertArrayEquals(sortedList, list);
        list = new Integer[2];
        list[0] = 0;
        list[1] = -4;
        sortedList = list;
        Arrays.sort(sortedList);
        Sorting.mergeSort(list, intComp);
        assertArrayEquals(sortedList, list);

        // large randomly generated list (avg case)
        int n = ITERATIONS;
        int nlogn = n * (int) (Math.log(n) / Math.log(2));

        for (int i = 0; i < ITERATIONS; ++i) {
            intComp = myInteger.getComparator();
            list = new Integer[n];
            sortedList = new Integer[n];
            for (int j = 0; j < n; ++j) {
                list[j] = ((int) (Math.random() * j * 10 - Math.random() * 100));
                sortedList[j] = list[j];
            }
            Sorting.mergeSort(list, intComp);
            Arrays.sort(sortedList);
            assertArrayEquals(sortedList, list);
            assertTrue("Number of comparisons: " + intComp.getCount() + "\nNlogN: "
                    +  nlogn, Math.abs(intComp.getCount() - nlogn) <= n);
        }

        // WCS list
        // NlogN - 2^logN + 1 comparisons at most
        n = 150;
        int logn = (int) (Math.log(n) / Math.log(2));
        nlogn = logn * n;
        list = new Integer[n];
        sortedList = new Integer[n];
        intComp = myInteger.getComparator();
        for (int i = n - 1; i >= 0; --i) {
            list[n - i - 1] = i;
            sortedList[i] = i;
        }
        Sorting.mergeSort(list, intComp);
        assertArrayEquals(sortedList, list);
        assertTrue("Number of comparisons: " + intComp.getCount(), intComp.getCount() <= nlogn - Math.pow(2, logn) + 1);

    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);

        int[] list;
        int[] sortedList;

        // small lists
        list = new int[1];
        list[0] = 1;
        sortedList = list;
        Sorting.lsdRadixSort(list);
        assertArrayEquals(sortedList, list);
        list = new int[2];
        list[0] = 1;
        list[1] = -4;
        sortedList = list;
        Arrays.sort(sortedList);
        Sorting.lsdRadixSort(list);
        assertArrayEquals(sortedList, list);

        // large randomly generated list (avg case)
        int n = ITERATIONS;

        for (int i = 0; i < ITERATIONS; ++i) {
            list = new int[n];
            sortedList = new int[n];
            for (int j = 0; j < n; ++j) {
                list[j] = ((int) (Math.random() * j * 10 - Math.random() * 100));
                sortedList[j] = list[j];
            }
            Sorting.lsdRadixSort(list);
            Arrays.sort(sortedList);
            assertArrayEquals(sortedList, list);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);

        List<Integer> list;
        int[] sortedList;

        // small lists
        list = new ArrayList<>(2);
        sortedList = new int[1];
        list.add(1);
        sortedList[0] = 1;
        actualArray = Sorting.heapSort(list);
        assertArrayEquals(sortedList, actualArray);
        sortedList = new int[2];
        list.add(-4);
        sortedList[0] = -4;
        sortedList[1] = 1;
        actualArray = Sorting.heapSort(list);
        assertArrayEquals(sortedList, actualArray);

        // large randomly generated list (avg case)
        int n = 500;

        for (int i = 0; i < ITERATIONS; ++i) {
            list = new ArrayList<>(n);
            sortedList = new int[n];
            for (int j = 0; j < n; ++j) {
                list.add(((int) (Math.random() * j * 10 - Math.random() * 100)));
                sortedList[j] = (list.get(j));
            }
            actualArray = Sorting.heapSort(list);
            Arrays.sort(sortedList);
            assertArrayEquals(sortedList, actualArray);
        }
    }

    private static class myInteger {
        public int value;
        public myInteger(int value) {this.value = value; }
        public static ComparatorPlus<Integer> getComparator() {
            return new ComparatorPlus<Integer>() {
                private int count = 0;
                @Override
                public int compare(Integer o1, Integer o2) {
                    ++count;
                    return o1 - o2;
                }
                public int getCount() {
                    return count;
                }
            };
        }
    }

    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof TeachingAssistant
                && ((TeachingAssistant) other).name.equals(this.name);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}