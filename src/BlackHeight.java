import java.util.Comparator;

import rbtree.*;

/**
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * @param <T> T
 */
public class BlackHeight<T> implements RBTreeVisitor<T, Integer> {

    /**
     * constructor of BlackHeight
     */
    BlackHeight() { // this constructor is empty
    }

    /**
     * visit empty tree
     * 
     * @param comp given comparator<T>
     * @param color the color associated with this node;
     * 
     * @return Integer
     */
    public Integer visitEmpty(Comparator<T> comp, String color) {
        return 0;
    }

    /**
     * visit non-empty node
     * 
     * @param comp given comparator<T>
     * @param left left RBTree<T>
     * @param right right RBTree<T>
     * @param value value associated with this node 
     * @param color the color associated with this node;
     * 
     * @return Integer
     */
    public Integer visitNode(Comparator<T> comp, RBTree<T> left,
            RBTree<T> right, T value, String color) {
        if (color.equals("B")) {
            return 1 + left.accept(this);
        }
        else {
            return 0 + left.accept(this);
        }
    }

}
