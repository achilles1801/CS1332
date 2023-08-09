import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Majd Khawaldeh
 * @version 1.0
 * @userid majd
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     * <p>
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of
     *                                  length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern, CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator cannot be null");
        }

        List<Integer> matches = new ArrayList<>();
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int i = 0; i <= textLength - patternLength; i++) {
            int j;
            for (j = 0; j < patternLength; j++) {
                if (comparator.compare(text.charAt(i + j), pattern.charAt(j)) != 0) {
                    break;
                }
            }

            if (j == patternLength) {
                matches.add(i);
            }
        }

        return matches;
    }


    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input text.
     * <p>
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     * <p>
     * Ex. ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws IllegalArgumentException if the pattern or comparator
     *                                  is null
     */
    public static int[] buildFailureTable(CharSequence pattern, CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Pattern or comparator is null");
        }
        int[] table = new int[pattern.length()];
        if (pattern.length() != 0) {
            table[0] = 0;
        }
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(j), pattern.charAt(i)) == 0) {
                table[j++] = ++i;
            } else {
                if (i != 0) {
                    i = table[i - 1];
                } else {
                    table[j++] = i;
                }
            }
        }
        return table;

    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of
     *                                  length 0
     * @throws IllegalArgumentException if text or comparator is null
     */

    public static List<Integer> kmp(CharSequence pattern, CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator cannot be null");
        }
        List<Integer> indexList = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return indexList;
        }
        int[] shift = buildFailureTable(pattern, comparator);
        int index = 0;
        int pIndex = 0;
        while (index + pattern.length() <= text.length()) {
            while (pIndex < pattern.length() && comparator.compare(pattern.charAt(pIndex),
                    text.charAt(index + pIndex)) == 0) {
                pIndex++;
            }
            if (pIndex == 0) {
                index++;
            } else {
                if (pIndex == pattern.length()) {
                    indexList.add(index);
                }
                index += pIndex - shift[pIndex - 1];
                pIndex = shift[pIndex - 1];
            }
        }
        return indexList;

    }


    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }

        Map<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }

        return lastTable;
    }


    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * <p>
     * <p>
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of
     *                                  length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern, CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator cannot be null");
        }

        List<Integer> matches = new ArrayList<>();
        Map<Character, Integer> lastTable = buildLastTable(pattern);

        int n = text.length();
        int m = pattern.length();
        int skip;

        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (comparator.compare(text.charAt(i + j), pattern.charAt(j)) != 0) {
                    skip = Math.max(1, j - lastTable.getOrDefault(text.charAt(i + j), -1));
                    break;
                }
            }
            if (skip == 0) {
                matches.add(i);
                skip = Math.max(1, m - lastTable.getOrDefault(text.charAt(i + m - 1), -1));
            }
        }

        return matches;
    }


    /**
     * This method is OPTIONAL and for extra credit only.
     * <p>
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     * <p>
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or has
     *                                  length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern, CharSequence text,
                                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator cannot be null");
        }


        List<Integer> matches = new ArrayList<>();
        if (text.length() < pattern.length()) {
            return matches;
        }
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int[] failureTable = buildFailureTable(pattern, comparator);

        int n = text.length();
        int m = pattern.length();
        int skip;
        int rightMatchedPosition = -1;
        int galilSkip = -1;

        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (j <= rightMatchedPosition) {
                    skip = galilSkip;
                    break;
                }

                if (comparator.compare(text.charAt(i + j), pattern.charAt(j)) != 0) {
                    skip = Math.max(1, j - lastTable.getOrDefault(text.charAt(i + j), -1));
                    rightMatchedPosition = -1;
                    break;
                }
            }

            if (skip == 0) {
                matches.add(i);
                galilSkip = m - failureTable[m - 1];
                rightMatchedPosition = failureTable[m - 1] - 1;
                skip = galilSkip;
            }
        }

        return matches;
    }

}
