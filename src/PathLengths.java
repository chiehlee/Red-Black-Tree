import java.util.ArrayList;
import java.util.Comparator;

import rbtree.*;

/**
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * 
 * @param <T>
 */
public class PathLengths<T> implements RBTreeVisitor<T, ArrayList<Integer>> {
    /**
     * global instance of ArrayList<Integer>, each height from root to empty
     * will be added into this ArrayList<Integer>
     */
    ArrayList<Integer> a = new ArrayList<Integer>();
    /**
     * k the layer accumulator;
     */
    int k = 0;

    /**
     * constructor of PathLengths
     */
    PathLengths() { // this constructor is empty
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
        a.add(k);
        return a;
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
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> visitNode(Comparator<T> comp, RBTree<T> left,
            RBTree<T> right, T value, String color) {
        k = k + 1;
        int save = k;
        left.accept(this);
        k = save;
        right.accept(this);

        return a;

    }

}
