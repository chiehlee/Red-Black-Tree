package rbtree;

import java.util.Comparator;

/**
 * class implements Comparator<String> that this comparator compare Strings by
 * their length.
 * 
 * @author Chieh Lee
 * @version 10.8.2013
 */
public class MyStringByLength implements Comparator<String> {

    /**
     * compare two string by each length if length of s0 is greater than s1,
     * return positive 1 if length of s0 is equal to s0 , return 0 if length of
     * s0 is smaller than s1, return negative 1
     * 
     * @param s0 string 1
     * @param s1 string 2
     * @return int
     */
    public int compare(String s0, String s1) {
        return s0.length() - s1.length();

    }

}