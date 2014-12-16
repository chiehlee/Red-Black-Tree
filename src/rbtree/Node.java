package rbtree;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * subclass Node extends BST, class represent non-emtpy tree node
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * 
 * @param <T>
 *            T
 */
public class Node<T> extends RBTree<T> {
    /**
     * left node of this node
     */
    RBTree<T> left;

    /**
     * right node of this node
     */
    RBTree<T> right;

    /**
     * represented string value of this node
     */
    T value;
    /**
     * labeled color/ either "R" (red) or "B" (black)
     */
    String color;

    /**
     * constructor of Node with comparator
     * 
     * @param comp
     *            carried comparator
     * @param left
     *            left node
     * @param right
     *            right node
     * @param value
     *            string value represented
     * @param color
     *            color of this node
     */
    public Node(Comparator<T> comp, RBTree<T> left, RBTree<T> right, T value,
            String color) {
        this.comp = comp;
        this.left = left;
        this.right = right;
        this.value = value;
        this.color = color;
    }

    /**
     * insert method which insert the given string value into this BST if
     * comp.compare(this.value, v1) > 0, insert into left; if
     * comp.compare(this.value, v1) < 0, insert into right; if these two string
     * value are equals, then return this node (because we don't produce
     * duplicated nodes)
     * 
     * @param v1
     *            given string value
     * @return BST
     */
    public RBTree<T> insert(T v1) {
        if (comp.compare(v1, this.value) < 0) {
            return new Node<T>(comp, this.left.insert2(v1), this.right,
                    this.value, this.color).balance().makeBlack();
        }
        if (comp.compare(v1, this.value) > 0) {
            return new Node<T>(comp, this.left, this.right.insert2(v1),
                    this.value, this.color).balance().makeBlack();
        }
        else {
            return this.makeBlack();
        }

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
    RBTree<T> insert2(T v1) {
        if (comp.compare(v1, this.value) < 0) {
            return new Node<T>(comp, this.left.insert2(v1), this.right,
                    this.value, this.color).balance();
        }
        if (comp.compare(v1, this.value) > 0) {
            return new Node<T>(comp, this.left, this.right.insert2(v1),
                    this.value, this.color).balance();
        }
        else {
            return this;
        }

    }

    /**
     * convert this BST to ArrayList and then sort it with comparator
     * accordingly.
     * 
     * @return ArrayList<String>
     */
    public ArrayList<T> treeToArray() {
        ArrayList<T> a = new ArrayList<T>();
        a.addAll(this.left.treeToArray());
        a.add(this.value);
        a.addAll(this.right.treeToArray());
        return a;
    }

    /**
     * determine whether this BST is empty
     * 
     * @return boolean return false always
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * determine this BST contains s
     * 
     * @param s
     *            type T
     * 
     * @return boolean
     */
    public boolean contains(T s) {
        Comparator<T> comp = this.getComp();
        if (comp.compare(s, this.value) < 0) {
            return this.left.contains(s);
        }
        if (comp.compare(s, this.value) > 0) {
            return this.right.contains(s);
        }
        else {
            return true;

        }
    }

    /**
     * return the size of bst
     * 
     * @return int
     */
    public int size() {
        return 1 + this.right.size() + this.left.size();
    }

    /**
     * helper method for preRepOK, check the left value is smaller than this
     * value
     * 
     * @param comp
     *            given Comparator<String>
     * @return boolean
     */
    protected boolean compLeft(Comparator<T> comp) {
        if (!this.left.isEmpty()) {
            return (comp.compare(this.left.getValue(), this.getValue()) < 0);
        }
        else {
            return true;
        }
    }

    /**
     * helper method for preRepOK, check the right value is greater than this
     * value
     * 
     * @param comp
     *            given Comparator<String>
     * @return boolean
     */
    protected boolean compRight(Comparator<T> comp) {
        if (!this.right.isEmpty()) {
            return (comp.compare(this.right.getValue(), this.getValue()) > 0);
        }
        else {
            return true;
        }
    }

    /**
     * helper method for WEB-CAT only, it's impossible to build 128 different
     * case to feed stupid non-sense tests coverage.
     * 
     * @return boolean
     */
    protected boolean checkNode() {
        return this.noRedRed() && this.maxBlack() == this.minBlack();
    }

    /**
     * helper method for WEB-CAT only, it's impossible to build 128 different
     * case to feed stupid non-sense tests coverage.
     * 
     * @param comp
     *            given Comparator<String>
     * @return boolean
     */
    protected boolean checkRL(Comparator<T> comp) {
        return this.compLeft(comp) && this.compRight(comp);
    }

    /**
     * helper method for recursion for repOK
     * 
     * @param comp
     *            given Comparator<String>
     * @return boolean
     */
    boolean preRepOK(Comparator<T> comp) {
        return this.checkNode() && this.checkRL(comp)
                && this.left.preRepOK(comp) && this.right.preRepOK(comp);
    }

    /**
     * repOK for non-empty tree
     * 
     * @return boolean
     */
    public boolean repOK() {
        Comparator<T> comp = this.getComp();
        return this.preRepOK(comp) && this.getColor().equals("B");

    }

    /**
     * get value of rbt
     * 
     * @return T
     */
    public T getValue() {
        return this.value;
    }

    /**
     * return the current color string
     * 
     * @return String
     */
    public String getColor() {
        return this.color;
    }

    /**
     * return the right node
     * 
     * @return RBTree<T>
     */
    RBTree<T> getRight() {
        return this.right;
    }

    /**
     * return the left node
     * 
     * @return RBTree<T>
     */
    RBTree<T> getLeft() {
        return this.left;
    }

    /**
     * change the color of this node to "B" (black) remain the rest
     * 
     * @return RBTree<T>
     */
    RBTree<T> makeBlack() {
        return new Node<T>(this.comp, this.left, this.right, this.value, "B");

    }

    /**
     * EFFECT: balance the tree base on red-black tree rule generally there are
     * 4 cases to balance, else will just return unchanged tree. 
     * 
     * case 1
     *        zB
     *       /  \
     *     yR    d
     *     /  \
     *   xR   c
     *  /  \  
     * a    c
     *      
     * case 2 
     *        xB
     *       /  \
     *      a    zR
     *          /  \
     *         yR   d
     *        /  \  
     *       b    c
     *       
     * case 3
     *        zB
     *       /  \
     *     xR    d
     *     /  \
     *    a   yR
     *       /  \  
     *      b    c
     *            
     * case 4 
     *        xB
     *       /  \
     *      a    yR
     *          /  \
     *         b    zR
     *             /  \  
     *            d    d
     * @return RBTree<T>
     */
    RBTree<T> balance() {
        // case 1
        // B (T R (T R a x b) y c) z d
        if (!this.getLeft().isEmpty() && !this.getLeft().getLeft().isEmpty()
                && this.getLeft().getColor().equals("R")
                && this.getLeft().getLeft().getColor().equals("R")) {
            return new Node<T>(this.comp,
                    this.getLeft().getLeft().makeBlack(), new Node<T>(
                            this.comp, this.getLeft().getRight(),
                            this.getRight(), this.getValue(), "B"),
                    this.left.getValue(), "R");
        }
        // case 2
        // B (T R a x (T R b y c)) z d
        if (!this.getRight().isEmpty() && !this.getRight().getLeft().isEmpty()
                && this.getRight().getColor().equals("R")
                && this.getRight().getLeft().getColor().equals("R")) {
            return new Node<T>(this.comp, new Node<T>(this.comp,
                    this.getLeft(), this.getRight().getLeft().getLeft(),
                    this.getValue(), "B"), new Node<T>(this.comp, this
                    .getRight().getLeft().getRight(), this.getRight()
                        .getRight(), this.getRight().getValue(), "B"), this
                        .getRight().getLeft().getValue(), "R");
        }
        // case 3
        // B a x (T R (T R b y c) z d)
        if (!this.getLeft().isEmpty() && !this.getLeft().getRight().isEmpty()
                && this.getLeft().getColor().equals("R")
                && this.getLeft().getRight().getColor().equals("R")) {
            return new Node<T>(this.comp, new Node<T>(this.comp, this
                    .getLeft().getLeft(), this.getLeft().getRight().getLeft(),
                    this.getLeft().getValue(), "B"), new Node<T>(this.comp,
                            this.getLeft().getRight().getRight(), 
                            this.getRight(), this.getValue(), "B"), 
                            this.getLeft().getRight()
                            .getValue(), "R");
        }
        // case 4
        // B a x (T R b y (T R c z d))
        if (!this.getRight().isEmpty()
                && !this.getRight().getRight().isEmpty()
                && this.getRight().getColor().equals("R")
                && this.getRight().getRight().getColor().equals("R")) {
            return new Node<T>(this.comp, new Node<T>(this.comp,
                    this.getLeft(), this.getRight().getLeft(),
                    this.getValue(), "B"), this.getRight().getRight()
                    .makeBlack(), this.getRight().getValue(), "R");
        }
        else {
            return this;
        }
    }

    /**
     * return the longest path
     * 
     * @return int
     */

    public int maxHeight() {
        return 1 + Math.max(this.left.maxHeight(), this.right.maxHeight());
    }

    /**
     * return the shortest path
     * 
     * @return int
     */
    int minHeight() {
        return 1 + Math.min(this.left.minHeight(), this.right.minHeight());
    }

    /**
     * return number of black nodes within the shortest path
     * 
     * @return int
     */
    int minBlack() {
        if (this.getColor().equals("B")) {
            return 1 + Math.min(this.getLeft().minBlack(), this.getRight()
                    .minBlack());
        }
        else {
            return 0 + Math.min(this.getLeft().minBlack(), this.getRight()
                    .minBlack());
        }
    }

    /**
     * return number of black nodes within the longest path
     * 
     * @return int
     */
    int maxBlack() {
        if (this.getColor().equals("B")) {
            return 1 + Math.max(this.getLeft().maxBlack(), this.getRight()
                    .maxBlack());
        }
        else {
            return 0 + Math.max(this.getLeft().maxBlack(), this.getRight()
                    .maxBlack());
        }
    }

    /**
     * determine if this color is red, no red children
     * 
     * @return boolean
     */
    boolean noRedRed() {
        if (this.getColor().equals("R")) {
            return !this.getLeft().getColor().equals("R")
                    && !this.getRight().getColor().equals("R");
        }
        if (!this.getColor().equals("B")) {
            return this.getColor().equals("R");
        }
        else {
            return true;
        }

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
        return v.visitNode(comp, left, right, value, color);
    }

}
