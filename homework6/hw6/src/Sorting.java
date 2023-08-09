import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Majd Khawaldeh
 * @version 1.0
 * @userid majd
 * @GTID 903677841
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                  null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null!");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                  null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null!");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                swap(arr, j, j - 1);
                j--;
            }
        }
    }

    /**
     * Implement bubble sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                  null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null!");
        }

        int j = arr.length - 1;
        while (j != 0) {
            int i = 0;
            int lastSwapped = 0;
            while (i < j) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    lastSwapped = i;
                }
                i++;
            }
            j = lastSwapped;
        }
    }

    /**
     * Swap two elements in an array
     *
     * @param arr the array that contains items to swap
     * @param i   first item
     * @param j   second item
     * @param <T> data type to sort
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                  null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null!");
        }
        if (arr.length > 1) {
            T[] left = (T[]) new Object[arr.length / 2];
            T[] right = (T[]) new Object[arr.length - left.length];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];

            }
            for (int j = 0; j < right.length; j++) {
                right[j] = arr[left.length + j];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, comparator, left, right);

        }
    }

    /**
     * Merges separated arrays
     *
     * @param arr        the array to be sorted
     * @param comparator comparator used to compare values
     * @param left       left array
     * @param right      right Array
     * @param <T>        data type to sort
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator, T[] left, T[] right) {
        int i = 0;
        int j = 0;
            for (int k = 0; k < arr.length; k++) {
                if (i >= left.length) {
                    arr[k] = right[j++];
                } else if (j >= right.length) {
                    arr[k] = left[i++];
                } else if (comparator.compare(left[i], right[j]) <= 0) {
                    arr[k] = left[i++];
                } else {
                    arr[k] = right[j++];
                }
            }

//            if (j >= right.length || (i < left.length && comparator.compare(left[i], right[j]) <= 0)) {
//                arr[k] = left[i++];
//            } else {
//                arr[k] = right[j++];
//            }
        }


    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null!");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int divisor = 1;
        int mod = 10;
        boolean continueSorting = true;
        while (continueSorting) {
            continueSorting = false;
            for (int num : arr) {
                int bucketInd = num / divisor;
                if (bucketInd / 10 != 0) {
                    continueSorting = true;
                }
                if (buckets[bucketInd % mod + 9] == null) {
                    buckets[bucketInd % mod + 9] = new LinkedList<>();
                }
                buckets[bucketInd % mod + 9].add(num);
            }
            int arrInd = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrInd++] = num;
                    }
                    bucket.clear();
                }
            }
            divisor *= 10;
        }
    }


    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int size = data.size();
        int[] sorted = new int[size];
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = heap.remove();
        }
        return sorted;
    }
}



public static <T> void insertion(T[] arr, Compar){
    //if
    for(int i = 1; i< arr.length; i++){
        int j= i;
        while (j>0 && comparotro.compare(arr[j-1],arr[j])>0){
            swap(arr, j,j-1);
            j--;
        }
    }
}
public static <T> void selection() {
    //if
    for (int i=0; i<arr.length-1;i++){
        int min = i;
        for(int j=i+1; j<arr.length; j++){
            if(comparator.comapare(arr[j],arr[min])<0){
                min=j;
            }
        }
        swap(arr, min, i);
    }
}