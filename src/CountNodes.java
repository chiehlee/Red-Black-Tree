import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import rbtree.*;

/**
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * @param <T>
 *            T
 */
public class CountNodes<T> implements RBTreeVisitor<T, ArrayList<Integer>> {
    /**
     * total nodes count
     */
    int total = 0;
    /**
     * black nodes count
     */
    int bnode = 0;
    /**
     * red nodes count
     */
    int rnode = 0;

    /**
     * constructor of CountNodes
     */
    CountNodes() { // this constructor is empty
    }

    /**
     * visit empty tree
     * 
     * @param comp
     *            given comparator<T>
     * @param color
     *            the color associated with this node;
     * 
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> visitEmpty(Comparator<T> comp, String color) {
        return new ArrayList<Integer>(Arrays.asList(total, bnode, rnode));
    }

    /**
     * visit non-empty node
     * 
     * @param comp
     *            given comparator<T>
     * @param left
     *            left RBTree<T>
     * @param right
     *            right RBTree<T>
     * @param value
     *            value associated with this node
     * @param color
     *            the color associated with this node;
     * 
     * 
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> visitNode(Comparator<T> comp, RBTree<T> left,
            RBTree<T> right, T value, String color) {
        if (color.equals("B")) {
            total = total + 1;
            bnode = bnode + 1;
            left.accept(this);
            right.accept(this);
        }
        else {
            total = total + 1;
            rnode = rnode + 1;
            left.accept(this);
            right.accept(this);
        }
        return new ArrayList<Integer>(Arrays.asList(total, bnode, rnode));

    }

}
