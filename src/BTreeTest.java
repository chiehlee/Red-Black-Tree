import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import rbtree.*;
import tester.*;

/**
 * class BTreeTest for testing BTree
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * @param <T>
 * 
 */
public class BTreeTest<T> {

    /**
     * constructor for test class
     */
    public BTreeTest() { // this constructor is empty

    }

    private Comparator<String> stringByLex = new StringByLex();
    private Comparator<String> stringByLength = new StringByLength();
    private BTree<String> bt1 = BTree.binTree(stringByLex);
    private BTree<String> bt2 = BTree.binTree(stringByLex);
    private BTree<String> bt3;
    private BTree<String> btmt = new BTree<String>(
            RBTree.emptyTree(stringByLex));
    private ArrayList<String> a3 = new ArrayList<String>();

    /**
     * test reset, count and contains
     * 
     * @param t
     *            tester
     */
    void testMethods(Tester t) {
        // test reset
        bt2.reset(stringByLex);
        t.checkExpect(bt2.equals(btmt), "1");

        // test count
        ArrayList<String> a = new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        // t.checkExpect(BTree.count(bt2), 0, "1.5");
        // t.checkExpect(BTree.count(a), 3, "1.75");

        // test contains
        ArrayList<String> a0 = new ArrayList<String>();
        a0.add("a");
        a0.add("b");
        a0.add("c");
        bt2.reset(stringByLex);
        bt2.build(a0);
        t.checkExpect(bt2.contains("a"), "9");
        t.checkFail(bt2.contains("z"), "9");

    }

    /**
     * test build and its overload method
     * 
     * @param t
     *            tester
     */
    void testBuild(Tester t) {
        bt2.reset(stringByLex);
        bt2.build(bt1);
        bt2.reset(stringByLex);
        ArrayList<String> a1 = new ArrayList<String>();
        a1.add("a");
        a1.add("b");
        a1.add("c");
        bt1.build(a1);
        t.checkExpect(bt1.rbt.toString().equals("a, b, c"), "3");
        bt1.reset(stringByLex);
        bt1.active = 1;
        t.checkException(new ConcurrentModificationException("Iterator on"),
                bt1, "build", a1);
        bt1.active = 0;
        bt1.build(a1, 2);
        t.checkExpect(bt1.rbt.toString().equals("a, b"), "5");
        bt1.active = 1;
        t.checkException(new ConcurrentModificationException("Iterator on"),
                bt1, "build", a1, 2);
        bt1.active = 0;
        a1.add("d");
        a1.add("e");
        bt1.build(a1, -1);
        t.checkExpect(bt1.rbt.toString().equals("a, b, c, d, e"), "6");

    }

    /**
     * test equals, override, hashCode
     * 
     * @param t tester
     */
    void testOverride(Tester t) {
        // test equal
        bt2.reset(stringByLex);
        bt1.reset(stringByLex);
        t.checkFail(bt2.equals(bt3), "8");

        a3 = new ArrayList<String>();
        a3.add("a");
        a3.add("b");
        a3.add("c");
        bt2.build(a3);
        bt1.build(a3);
        t.checkExpect(bt2.equals(bt1), "10");
        a3.add("d");
        bt1.reset(stringByLex);
        bt1.build(a3);
        t.checkFail(bt2.equals(bt1), "11");

        // test toString
        bt2.reset(stringByLex);
        t.checkExpect(bt2.toString().equals(""), "12");
        t.checkExpect(bt1.toString().equals("a, b, c, d"), "13");

        // test hashcode
        bt2.reset(stringByLex);
        t.checkExpect(bt2.hashCode(), 9000);
        t.checkExpect(bt1.hashCode(), 9674);
    }

    /**
     * test repOK
     * 
     * @param t
     *            tester
     */
    void testRepOK(Tester t) {
        bt2.reset(stringByLex);
        bt1.reset(stringByLex);
        t.checkExpect(bt2.repOK(), "14");
        bt1.build(a3);
        t.checkExpect(bt1.repOK(), "15");
    }

    /**
     * test iterator method: next, hasnext, (remove is unimplemented)
     * 
     * @param t
     *            tester
     */
    void testIterator(Tester t) {
        bt2.reset(stringByLex);
        bt1.reset(stringByLex);
        bt2.build(a3);
        Iterator<String> bit1 = bt1.iterator();
        Iterator<String> bit2 = bt2.iterator();
        t.checkFail(bit1.hasNext(), "16");
        t.checkExpect(bit2.hasNext(), "17");
        t.checkExpect(bit2.next().equals("a"));
        t.checkExpect(bit2.next().equals("b"));
        t.checkExpect(bt2.active, 1);
        t.checkExpect(bit2.next().equals("c"));
        t.checkExpect(bit2.next().equals("d"));
        t.checkExpect(bt2.active, 0);
        t.checkException(new NoSuchElementException("no more entity"), bit1,
                "next");
        t.checkException(new UnsupportedOperationException(""), bit2, "remove");

    }

    /**
     * test comparator
     * 
     * @param t
     *            tester
     */
    void testComparator(Tester t) {
        bt2.reset(stringByLength);
        t.checkExpect(stringByLength.compare("a", "a"), 0);
        t.checkExpect(stringByLength.compare("aa", "a"), 1);
        t.checkExpect(stringByLength.compare("a", "aa"), -1);

    }

    /**
     * test visitor
     * 
     * @param t
     *            tester
     */
    void testVisitor(Tester t) {
        ArrayList<String> a = new ArrayList<String>(Arrays.asList("a", "b",
                "c", "d", "e", "f", "g", "h"));
        ArrayList<String> a2 = new ArrayList<String>(Arrays.asList("g", "z",
                "a", "b", "c", "w", "x"));
        BTree<String> bt4 = BTree.binTree(stringByLex);
        BTree<String> bt5 = BTree.binTree(stringByLex);
        BTree<String> bt6 = BTree.binTree(stringByLex);
        BTree<String> bt7 = BTree.binTree(stringByLex);
        bt4.build(a);
        
        /* the tree right now has a, b, c, d, e, f, g
         * bt4.rbt;
         *          dB
         *        /    \
         *      bB      fR
         *     /  \    /  \
         *   aB   cB eB    gB
         * treeToArray should be: a, b, c, d, e, f, g
        */
        bt5.build(a2);
        /* the tree right now has g, z, a, b, c, w, x
         * bt5.rbt;
         *          bB
         *        /    \
         *      aB      wR
         *             /  \
         *           gB   zB
         *          /     /
         *        cR    xR
         * treeToArray should be: a, b, c, g, w, x, z
        */
        
        ArrayList<String> a5 = new ArrayList<String>();
        a5.add("a");
        a5.add("b");
        a5.add("c");
        bt6.build(a5);
        
        ArrayList<String> a6 = new ArrayList<String>();
        a6.add("y");
        a6.add("x");
        a6.add("z");
        bt7.build(a6);

        // test Height<String>
        t.checkExpect(bt4.accept(new Height<String>()), 4);
        t.checkExpect(bt5.accept(new Height<String>()), 4);
        t.checkExpect(bt6.accept(new Height<String>()), 2);

        // test CountNode<String>
        t.checkExpect(bt4.accept(new CountNodes<String>()).toString()
                .equals("[8, 7, 1]"));
        t.checkExpect(bt5.accept(new CountNodes<String>()).toString()
                .equals("[7, 4, 3]"));
        t.checkExpect(bt6.accept(new CountNodes<String>()).toString()
                .equals("[3, 3, 0]"));

        // test BlackHeight<String>
        t.checkExpect(bt4.accept(new BlackHeight<String>()), 3);
        t.checkExpect(bt5.accept(new BlackHeight<String>()), 2);
        t.checkExpect(bt6.accept(new BlackHeight<String>()), 2);
        t.checkExpect(bt7.accept(new BlackHeight<String>()), 1);

        // test PathLengths<String>
        t.checkExpect(bt4.accept(new PathLengths<String>()).toString()
                .equals("[3, 3, 3, 3, 3, 3, 3, 4, 4]"));
        t.checkExpect(bt5.accept(new PathLengths<String>()).toString()
                .equals("[2, 2, 4, 4, 3, 4, 4, 3]"));
        t.checkExpect(bt6.accept(new PathLengths<String>()).toString()
                .equals("[2, 2, 2, 2]"));

    }

}
