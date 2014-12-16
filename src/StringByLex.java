import java.util.Comparator;

/**
 * class implements Comparator<String> that this comparator compare Strings
 * lexicographically.
 * 
 * @author Chieh Lee
 * @version 10.8.2013
 */
public class StringByLex implements Comparator<String> {

    /**
     * compare two string by each length if s0 is lexicographically less than
     * s1, return positive 1 if s0 and s1 are identical , return 0 if s0 is
     * lexicographically greater than s1, return negative 1 in this case, 'a
     * consider the greatest
     * 
     * @param s1 string 1
     * @param s2 string 2
     * @return int
     */
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);

    }

    /**
     * override the toString method
     * 
     * @return String
     */

    public String toString() {
        return "StringByLex";
    }

}