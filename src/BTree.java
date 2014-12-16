import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import rbtree.*;

/**
 * class of BTree which implements Iterable<T>
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * 
 * @param <T>
 *            T
 * 
 */
public class BTree<T> implements Iterable<T> {
    /**
     * RBTree<T>(red-black tree)
     */
    public RBTree<T> rbt;
    /**
     * check active 0 = no (default) 1 = yes
     */
    int active = 0;
    /**
     * instance ArrayList<T> for Iterator
     */
    ArrayList<T> a;

    /**
     * constructor of BTree
     * 
     * @param rbt
     *            this rbt
     */
    public BTree(RBTree<T> rbt) {
        this.rbt = rbt;
    }

    /**
     * Iterator method for BTree convert BTree into ArrayList to satisfy
     * BTreeIterator
     * 
     * @return Iterator<String>
     */
    public Iterator<T> iterator() {
        a = this.rbt.treeToArray();
        return new BTreeIterator();
    }

    /**
     * Factory method to generate an empty binary search tree with the given
     * <code>Comparator</code>
     * 
     * @param comp
     *            the given <code>Comparator</code>
     * @param <T>
     *            T
     * @return new empty binary search tree that uses the given
     *         <code>Comparator</code> for ordering
     */
    public static <T> BTree<T> binTree(Comparator<T> comp) {
        return new BTree<T>(RBTree.emptyTree(comp));
    }

    /**
     * EFFECT:reset the current state of BTree to the original
     * 
     * @param comp
     *            associated comparator
     * 
     */
    public void reset(Comparator<T> comp) {
        this.rbt = RBTree.emptyTree(comp);
    }

    /**
     * Method that accepts a visitor that produces a value of the type R
     * 
     * @param v
     *            the given visitor
     * @param <R>
     *            the type of elements this tree holds
     * @return the result of the visitor method
     */
    public <R> R accept(RBTreeVisitor<T, R> v) {
        // NOTE: tree is the name of my RBTree field. You would
        // replace tree with the name of your RBTree field.
        return rbt.accept(v);
    }

    /**
     * count how many string in the given iter
     * 
     * @param iter
     *            given string iterator
     * @return int how many words
     */

    /*
     * public static int count(Iterable<String> iter) { int i = 0; for
     * (@SuppressWarnings("unused") String s : iter) { i++; } return i; }
     */

    /**
     * Modifies: this binary search tree by inserting the <code>String</code>s
     * from the given <code>Iterable</code> collection The tree will not have
     * any duplicates - if an item to be added equals an item that is already in
     * the tree, it will not be added.
     * 
     * @param iter
     *            the given <code>Iterable</code> collection
     */

    public void build(Iterable<T> iter) {
        for (T s : iter) {
            if (active == 0) {
                this.rbt = this.rbt.insert(s);
            }
            else {
                throw new ConcurrentModificationException("Iterator on");
            }

        }
    }

    /**
     * Modifies: this binary search tree by inserting the <code>String</code>s
     * from the given <code>Iterable</code> collection The tree will not have
     * any duplicates - if an item to be added equals an item that is already in
     * the tree, it will not be added. overloaded method
     * 
     * @param iter
     *            the given <code>Iterable</code> collection
     * @param numStrings
     *            number of <code>String</code>s to iterate through and add to
     *            BTree. If numStrings is negative or larger than the number of
     *            <code>String</code>s in iter then all <code>String</code>s in
     *            iter should be inserted into the tree
     */

    public void build(Iterable<T> iter, int numStrings) {
        for (T s : iter) {
            if (active == 0) {
                if (numStrings != 0) {
                    this.rbt = this.rbt.insert(s);
                    numStrings--;
                }
                else {
                    return;
                }
            }
            else {
                throw new ConcurrentModificationException("Iterator on");
            }
        }
    }

    /**
     * Effect: Checks to see if this BTree contains s
     * 
     * @param s
     *            <code>T</code> to look for in this
     * @return whether this contains s
     */
    public boolean contains(T s) {
        return rbt.contains(s);
    }

    /**
     * Effect: Produces a <code>String</code> that consists of all
     * <code>String</code>s in this tree separated by comma and a space,
     * generated in the order defined by this tree's <code>Comparator</code>. So
     * for a tree with <code>Strings</code> "hello" "bye" and "aloha" ordered
     * lexicographically, the result would be "aloha, bye, hello"
     * 
     * @return String
     */
    public String toString() {
        return this.rbt.toString();
    }

    /**
     * Effect: Produces false if o is not an instance of BTree. Produces true if
     * this tree and the given BTree contain the same <code>String</code>s and
     * are ordered by the same <code>Comparator</code>. So if the first tree was
     * built with Strings "hello" "bye" and "aloha" ordered lexicographically,
     * and the second tree was built with <code>String</code>s "aloha" "hello"
     * and "bye" and ordered lexicographically, the result would be true.
     * 
     * @param o
     *            the object to compare with this
     * @return boolean
     */
    public boolean equals(Object o) {
        if (!(o instanceof BTree)) {
            return false;
        }
        else {
            @SuppressWarnings("unchecked")
            BTree<T> b2 = ((BTree<T>) o);
            return this.rbt.equals(b2.rbt);
        }

    }

    /**
     * Effect: Produces an integer that is compatible with the implemented
     * equals method and is likely to be different for objects that are not
     * equal.
     * 
     * @return int
     */
    public int hashCode() {
        return this.rbt.hashCode() + 9000;
    }

    /**
     * check this BTree works fine
     * 
     * @return boolean
     */
    public boolean repOK() {
        return this.rbt.repOK();
    }

    /**
     * Iterator class for BTree this iterator itreates BTree converted to
     * Arraylist
     * 
     * @author Chieh Lee
     * @version 10.8.2013
     */
    class BTreeIterator implements Iterator<T> {
        /**
         * pointer of current index
         */
        int current;
        
        /**
         * constructor for BTreeIterator
         */
        BTreeIterator() {
            this.current = 0;
            if (this.hasNext()) {
                active = active + 1;
            }
        }

        /**
         * check whether iterator has next
         * 
         * @return boolean
         */
        public boolean hasNext() {
            return current < a.size();
        }

        /**
         * pop next String of iterator
         * 
         * @return String
         */
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("no more entity");
            }
            T k1 = a.get(current);
            current = current + 1;
            if (!this.hasNext()) {
                active = active - 1;
            }
            return k1;

        }

        /**
         * throw a exception due to unnecessary method;
         */
        public void remove() {
            throw new UnsupportedOperationException("");

        }

    }

}
