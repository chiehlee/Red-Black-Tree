package rbtree;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * abstract class for binary search tree
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * @param <T>
 *            T
 */
public abstract class RBTree<T> {
    /**
     * Comparator<T>
     */
    Comparator<T> comp;

    /**
     * basic creator of EmptyTree with given comparator
     * 
     * @param comp
     *            given comparator
     * @param <T>
     *            T
     * @return RBTree<T> instance of EmptyTree
     */
    public static <T> RBTree<T> emptyTree(Comparator<T> comp) {
        return new EmptyTree<T>(comp);
    }

    /**
     * basic creator of Node with given fields
     * 
     * @param comp
     *            given comparator
     * @param left
     *            left node
     * @param right
     *            right node
     * @param value
     *            value of this node
     * @param color
     *            color of this node
     * @param <T>
     *            T
     * @return RBTree<T> instance of Node
     */
    public static <T> RBTree<T> node(Comparator<T> comp, RBTree<T> left,
            RBTree<T> right, T value, String color) {
        return new Node<T>(comp, left, right, value, color);
    }

    /**
     * retrieve the comparator of this RBTree<T>
     * 
     * @return comp this Comparator<T>
     */
    public Comparator<T> getComp() {
        return this.comp;
    }

    /**
     * abstract method of insert s
     * 
     * @param v1
     *            the given string
     * @return BST
     */
    abstract public RBTree<T> insert(T v1);

    /**
     * helper method of insert, this method handles nodes except from the root
     * the difference is insert2 doesn't call makeBlack() to this node but let
     * the inserting node keeps its color
     * 
     * @param v1
     *            given string
     * @return RBTree<T>
     */
    abstract RBTree<T> insert2(T v1);

    /**
     * abstract method of treeToArray
     * 
     * @param <T>
     * 
     * @return ArrayLIst<String>
     */
    abstract public ArrayList<T> treeToArray();

    /**
     * determine this BST empty
     * 
     * @return boolean
     */
    abstract public boolean isEmpty();

    /**
     * determine this BST contains s
     * 
     * @param s
     *            given string
     * @return boolean
     */
    abstract public boolean contains(T s);

    /**
     * return the size of bst
     * 
     * @return int
     */
    abstract public int size();

    /**
     * helper method for repOK
     * 
     * @param comp
     *            given comparator
     * @return boolean
     */
    abstract boolean preRepOK(Comparator<T> comp);

    /**
     * abstract method for repOK for BST
     * 
     * @return boolean
     */
    abstract public boolean repOK();

    /**
     * get value of bst
     * 
     * @return String
     */
    abstract public T getValue();

    /**
     * EFFECT: balance the tree according the red-black tree rule
     * 
     * @return RBTree<T>
     */
    abstract RBTree<T> balance();

    /**
     * return the current color string
     * 
     * @return String
     */
    public abstract String getColor();

    /**
     * return the right node
     * 
     * @return RBTree<T>
     */
    abstract RBTree<T> getRight();

    /**
     * return the left node
     * 
     * @return RBTree<T>
     */
    abstract RBTree<T> getLeft();

    /**
     * change the color of this node to "B" (black) remain the rest
     * 
     * @return RBTree<T>
     */
    abstract RBTree<T> makeBlack();

    /**
     * return the longest path of this node
     * 
     * @return int
     */
    public abstract int maxHeight();

    /**
     * return the shortest path of this node
     * 
     * @return int
     */
    abstract int minHeight();

    /**
     * count how many black node in the longest path of this node
     * 
     * @return int
     */
    abstract int minBlack();

    /**
     * count how many black node in the shortest path of this node
     * 
     * @return int
     */
    abstract int maxBlack();

    /**
     * determine if this color is red, no red children
     * 
     * @return boolean
     */
    abstract boolean noRedRed();

    /**
     * accept method for visitor
     * 
     * @param v
     *            given RBTreeVisitor<T, R>s
     * @param <R>
     *            R
     * @return R
     */
    public abstract <R> R accept(RBTreeVisitor<T, R> v);

    /**
     * overriding equal method
     * 
     * @param o
     *            given Object
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof RBTree)) {
            return false;
        }
        RBTree<T> rbt2 = ((RBTree<T>) o);
        if (this.size() != rbt2.size()) {
            return false;
        }
        if (this.isEmpty()) {
            return rbt2.isEmpty();
        }
        else {
            return this.getValue().equals(rbt2.getValue())
                    && this.getColor().equals(rbt2.getColor())
                    && this.getLeft().equals(rbt2.getLeft())
                    && this.getRight().equals(rbt2.getRight());
        }
    }

    /**
     * return hashCode of this RBTree<T> base on its size
     * 
     * @return int
     */
    public int hashCode() {
        if (this.isEmpty()) {
            return this.size();
        }
        else {
            return this.getColor().hashCode() + this.getValue().hashCode()
                    + this.getLeft().hashCode() + this.getRight().hashCode();
        }
    }

    /**
     * override the toString method
     * 
     * @return String
     */
    public String toString() {
        ArrayList<T> a = this.treeToArray();
        String k = "";
        if (!a.isEmpty()) {
            for (int i = 0; i < a.size() - 1; i++) {
                k = k + a.get(i) + ", ";
            }
            k = k + a.get(a.size() - 1);
            return k;
        }
        else {
            return "";
        }
    }

}
