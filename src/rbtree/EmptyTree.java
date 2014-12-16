package rbtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * subclass EmptyTree extends BST represent empty tree
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * 
 * @param <T>
 *            T
 */
public class EmptyTree<T> extends RBTree<T> {

    /**
     * constructor of EmptyTree carry a comparator comp
     * 
     * @param comp
     *            Comparator<String>
     */
    public EmptyTree(Comparator<T> comp) {
        this.comp = comp;
    }

    /**
     * insert new string into EmptyTree return a new Node which have this string
     * value
     * 
     * @param v1
     *            the given string value
     * @return BST
     */
    public RBTree<T> insert(T v1) {
        return new Node<T>(comp, this, this, v1, "R");
    }

    /**
     * helper method for insert basically this method does the recursion without
     * turning this node into black, but keep its color, the insert method is
     * for the first node (root), and root is always black
     * 
     * @param v1
     *            string wish to be inserted
     * @return RBTree<T>
     */
    public RBTree<T> insert2(T v1) {
        return this.insert(v1);
    }

    /**
     * convert this tree to ArrayList<String>
     * 
     * @return ArrayList<String> return a empty ArrayList<String> since its
     *         empty tree
     */
    public ArrayList<T> treeToArray() {
        return new ArrayList<T>();
    }

    /**
     * determine whether this tree is empty
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return true;
    }

    /**
     * determine this BST contains s
     * 
     * @param s
     *            given String s
     * @return boolean always false
     */
    public boolean contains(T s) {
        return false;

    }

    /**
     * return the size of bst
     * 
     * @return int
     */
    public int size() {
        return 0;
    }

    /**
     * determine the root (this node) is black
     * 
     * @param comp
     *            givnem Comparator<String>
     * @return boolean
     */

    boolean preRepOK(Comparator<T> comp) {
        return this.repOK();
    }

    /**
     * repOK for empty Tree
     * 
     * @return boolean
     */
    public boolean repOK() {
        return this instanceof RBTree;
    }

    /**
     * get value of bst
     * 
     * @return String
     */
    public T getValue() {
        throw new NoSuchElementException("value empty!");
    }

    /**
     * EFFECT: balance the tree according the red-black tree rule
     * 
     * @return RBTree<T>
     */
    public RBTree<T> balance() {
        return this;

    }

    /**
     * return the current color string
     * 
     * @return String
     */
    public String getColor() {
        return "B";
    }

    /**
     * return the right node
     * 
     * @return RBTree<T>
     */
    RBTree<T> getRight() {
        throw new NoSuchElementException("right empty!");
    }

    /**
     * return the left node
     * 
     * @return RBTree<T>
     */
    RBTree<T> getLeft() {
        throw new NoSuchElementException("left empty!");
    }

    /**
     * change the color of this node to "B" (black) remain the rest
     * 
     * @return RBTree<T>
     */
    RBTree<T> makeBlack() {
        throw new NoSuchElementException("makeblack empty!");
    }

    /**
     * return the longest path
     * 
     * @return int
     */
    public int maxHeight() {
        return 0;
    }

    /**
     * return the shortest path
     * 
     * @return int
     */
    int minHeight() {
        return 0;
    }

    /**
     * return number of black nodes within the shortest path
     * 
     * @return int
     */
    int minBlack() {
        return 0;
    }

    /**
     * return number of black nodes within the longest path
     * 
     * @return int
     */
    int maxBlack() {
        return 0;
    }

    /**
     * determine if this color is red, no red children
     * 
     * @return boolean
     */
    boolean noRedRed() {
        return true;
    }

    /**
     * accept method for visitor
     * 
     * @param v
     *            given RBTreeVisitor<T, R>s
     * @param <R>
     *            R
     * @return R
     */
    public <R> R accept(RBTreeVisitor<T, R> v) {
        return v.visitEmpty(comp, this.getColor());
    }

}
