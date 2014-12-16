package rbtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

import tester.*;

/**
 * test class for RBTree
 * 
 * @author Chieh Lee
 * @version 11.10.2013
 * 
 * @param <T> T
 */
public class RBTreeTest<T> {

    /**
     * constructor for test class
     */
    public RBTreeTest() { // this constructor is empty

    }

    // initiate variables
    private Comparator<String> stringByLex = new MyStringByLex();
    private Comparator<String> stringByLength = new MyStringByLength();
    // private Comparator<String> stringByLength = new StringByLength();
    private RBTree<String> rbt0;
    private RBTree<String> rbt0a;
    private RBTree<String> rbt0b;
    private RBTree<String> rbt0c;
    private RBTree<String> rbt0e;
    private RBTree<String> rbt1 = RBTree.emptyTree(stringByLex);
    // this test variable is legal RBTree without using insert
    // case 4 situation (not balanced)
    private RBTree<String> rbt2 = RBTree.node(stringByLex, rbt1, rbt1, "c",
            "R");
    private RBTree<String> rbt3 = RBTree.node(stringByLex, rbt1, rbt2, "b",
            "R");
    private RBTree<String> rbt4 = RBTree.node(stringByLex, rbt1, rbt3, "a",
            "B");

    private RBTree<String> rbt2b = RBTree.node(stringByLex, rbt1, rbt1, "c",
            "B");
    private RBTree<String> rbt3b = RBTree.node(stringByLex, rbt1, rbt2b, "b",
            "B");
    private RBTree<String> rbt4b = RBTree.node(stringByLex, rbt1, rbt3b, "a",
            "B");

    private RBTree<String> rbt5; // balanced RBTree (case4) from rbt4
    private RBTree<String> rbt6; // balanced rbt5 (no change should be applied)

    // this test variable is made without using insert
    // case 2 situation (not balanced)
    private RBTree<String> rbt12 = new Node<String>(stringByLex, rbt1, rbt1,
            "b", "R");
    private RBTree<String> rbt13 = new Node<String>(stringByLex, rbt12, rbt1,
            "c", "R");
    private RBTree<String> rbt14 = new Node<String>(stringByLex, rbt1, rbt13,
            "a", "B");

    private RBTree<String> rbt15; // balanced RBTree (case2) from rbt14

    /**
     * produce some instance
     * 
     * @param t
     */
    private void creation(Tester t) {
        RBTree<String> rbt11a;
        RBTree<String> rbt11b;

        rbt5 = rbt4.balance();
        rbt4b = rbt4b.balance();
        rbt6 = rbt5.balance();
        rbt15 = rbt14.balance();

        rbt11b = rbt1.insert("a");
        rbt11b = rbt11b.insert("q");
        rbt11b = rbt11b.insert("z");
        rbt11b = rbt11b.insert("b");

        rbt11a = rbt1.insert("g");
        rbt11a = rbt11a.insert("b");
        rbt11a = rbt11a.insert("c");
        rbt11a = rbt11a.insert("z");
        rbt11a = rbt11a.insert("d");
        rbt11a = rbt11a.insert("y");
        rbt11a = rbt11a.insert("x");
        rbt11a = rbt11a.insert("a");

    }

    /**
     * test basic creators
     * 
     * @param t
     *            given tester
     */
    void testBasicCreator(Tester t) {
        // test basic creator

        rbt0 = RBTree.emptyTree(stringByLex);
        t.checkExpect(rbt0.comp.toString().equals("StringByLex"),
                "test emptyTree basic creator");
        t.checkExpect(rbt0.isEmpty(), "test emptyTree test isEmpty()");
        rbt0 = RBTree.node(stringByLex, rbt1, rbt1, "a", "R");
        t.checkExpect(rbt0.getValue().equals("a"),
                "test node basic creator_get value");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "b", "B");
        rbt0b = new Node<String>(stringByLex, rbt0, rbt0, "b", "B");
        t.checkExpect(rbt0a.getLeft().getValue().equals("a"));
        t.checkExpect(rbt0a.getRight().getValue().equals("a"));
        t.checkExpect(rbt0a.equals(rbt0b),
                "basic creator method test with make new instance");

    }

    /**
     * test isempty(), getValue(), getLeft(), getRight(), getColor() 
     * makeBlack()
     * 
     * @param t
     *            given tester
     */
    void testMethods(Tester t) {
        // test isEmpty();
        rbt0 = RBTree.emptyTree(stringByLex);
        t.checkExpect(rbt0.isEmpty(), "rbt0 is an emptyTree");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "b", "B");
        t.checkFail(rbt0a.isEmpty(), "rbt0a is not an emptyTree");

        // test getValue();
        t.checkException(new NoSuchElementException("value empty!"), rbt0,
                "getValue");
        t.checkExpect(rbt0a.getValue().equals("b"));

        // test getLeft();
        t.checkException(new NoSuchElementException("left empty!"), rbt0,
                "getLeft");
        t.checkExpect(rbt0a.getLeft().equals(rbt0));

        // test getRight();
        t.checkException(new NoSuchElementException("right empty!"), rbt0,
                "getRight");
        t.checkExpect(rbt0a.getRight().equals(rbt0));

        // test getColor():
        t.checkExpect(rbt1.getColor().equals("B"), "test getColor empty");
        t.checkExpect(rbt0a.getColor().equals("B"));

        // test makeBlack();
        t.checkExpect(rbt0a.makeBlack().getColor().equals("B"),
                "makeBlack a black node, should remain the same");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "b", "R");
        t.checkException(new NoSuchElementException("makeblack empty!"), rbt0,
                "makeBlack");
        t.checkExpect(rbt0a.makeBlack().getColor().equals("B"),
                "makeBlack a red node");
        t.checkException(new NoSuchElementException("makeblack empty!"),
                rbt0a.getLeft(), "makeBlack");
    }

    /**
     * test balance(), insert(), insert2()
     * 
     * @param t
     *            given tester
     */
    void testBalanceNInsert(Tester t) {
        creation(t);

        RBTree<String> rbt11;

        // test balance method case 1 (without insert method/made up cases)
        rbt0 = RBTree.emptyTree(stringByLex);
        rbt0e = RBTree.emptyTree(stringByLex);
        rbt0e = rbt0e.insert("a");
        rbt0e = rbt0e.insert("b");
        rbt0e = rbt0e.insert("c");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "R");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0, "b", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().getValue().equals("b"), "7");
        t.checkExpect(rbt0c.balance().getColor().equals("R"), "8");
        t.checkExpect(rbt0c.balance().getLeft().getValue().equals("a"), "9");
        t.checkExpect(rbt0c.balance().getLeft().getColor().equals("B"), "10");
        t.checkExpect(rbt0c.balance().getRight().getValue().equals("c"), "11");
        t.checkExpect(rbt0c.balance().getRight().getColor().equals("B"), "12");

        t.checkExpect(rbt0.balance().equals(rbt0));

        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "13");

        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "b", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "14");

        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "b", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "15");

        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "16");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "R");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "15");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0, "b", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "16");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "17");

        // test balance method case 2 (without insert method/made up cases)
        t.checkExpect(rbt15.getValue().equals("b"), "balance case1 getValue");
        t.checkExpect(rbt15.getColor().equals("R"), "balance case1 getColor");
        t.checkExpect(rbt15.getRight().getValue().equals("c"),
                "balance case1 getRightValue");
        t.checkExpect(rbt15.getRight().getColor().equals("B"),
                "balance case1 getRightColor");
        t.checkExpect(rbt15.getLeft().getValue().equals("a"),
                "balance case1 getLeftValue");
        t.checkExpect(rbt15.getLeft().getColor().equals("B"),
                "balance case1 getLeftColor");

        // test balance method case 3 (without insert method/made up cases)
        rbt0 = RBTree.emptyTree(stringByLex);
        rbt0e = RBTree.emptyTree(stringByLex);
        rbt0e = rbt0e.insert("a");
        rbt0e = rbt0e.insert("b");
        rbt0e = rbt0e.insert("c");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "b", "R");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0a, "a", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().getValue().equals("b"), "26");
        t.checkExpect(rbt0c.balance().getColor().equals("R"), "27");
        t.checkExpect(rbt0c.balance().getLeft().getValue().equals("a"), "28");
        t.checkExpect(rbt0c.balance().getLeft().getColor().equals("B"), "29");
        t.checkExpect(rbt0c.balance().getRight().getValue().equals("c"), "30");
        t.checkExpect(rbt0c.balance().getRight().getColor().equals("B"), "31");

        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "32");

        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "b", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "33");

        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "b", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "34");

        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "35");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "R");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0a, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "36");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0a, "b", "R");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "37");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0a, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0b, rbt0, "c", "B");
        t.checkExpect(rbt0c.balance().equals(rbt0c), "38");

        // test balance method case 4 (without insert method/made up cases)
        t.checkExpect(rbt5.getValue().equals("b"), "balance case4 getValue");
        t.checkExpect(rbt5.getColor().equals("R"), "balance case4 getColor");
        t.checkExpect(rbt5.getRight().getValue().equals("c"),
                "balance case4 getRightValue");
        t.checkExpect(rbt5.getRight().getColor().equals("B"),
                "balance case4 getRightColor");
        t.checkExpect(rbt5.getLeft().getValue().equals("a"),
                "balance case4 getLeftValue");
        t.checkExpect(rbt5.getLeft().getColor().equals("B"),
                "balance case4 getLeftColor");

        // test balance method with other cases (else)
        // it should just return same instance without making any change
        // this is an already balanced case (rbt5)
        t.checkExpect(rbt6.equals(rbt5), "balance else case");
        t.checkExpect(rbt6.getValue().equals(rbt5.getValue()),
                "balance else case getValue");
        // this is a red-black rule violated tree, count as else case
        t.checkExpect(rbt4b.getValue().equals("a"),
                "balance bad case_all black");
        t.checkExpect(rbt4b.getLeft().equals(rbt1),
                "balance bad case_all black_get left");
        t.checkExpect(rbt4b.getRight().getValue().equals("b"),
                "balance bad case_all black_get right value");

        // test insert method
        rbt11 = rbt1.insert("a"); // insert "a" into an emptyTree
        t.checkExpect(rbt11.getValue().equals("a"),
                "insert one element get value");
        t.checkExpect(rbt11.getColor().equals("R"),
                "insert one element get color");
        rbt11 = rbt11.insert("b");
        // b > a, should insert in the right side of a
        t.checkExpect(rbt11.getValue().equals("a"), "insert b get this value");
        // root node's color will change to black
        t.checkExpect(rbt11.getColor().equals("B"), "insert b get this color");
        t.checkExpect(rbt11.getRight().getValue().equals("b"),
                "insert b get right value");
        t.checkExpect(rbt11.getRight().getColor().equals("R"),
                "insert b get right value");
        // insert c to current RBTree, it should balance because it meet the
        // case 4
        rbt11 = rbt11.insert("c");
        t.checkExpect(rbt11.getValue().equals("b"), "insert c get this value");
        t.checkExpect(rbt11.getColor().equals("B"), "insert c get this value");

        rbt11 = RBTree.emptyTree(stringByLex);

        rbt11 = rbt11.insert("g");
        t.checkExpect(rbt11.getValue().equals("g"), "insert b get this value");
        // root node's color will change to black
        t.checkExpect(rbt11.getColor().equals("R"), "insert b get this color");
        // insert a, when given string < this.value
        rbt11 = rbt11.insert("a");
        t.checkExpect(rbt11.getLeft().getValue().equals("a"),
                "given string < this value");
        t.checkExpect(rbt11.getColor().equals("B"), "root makeblack");
        t.checkExpect(rbt11.getLeft().getColor().equals("R"),
                "inserted node color");
        // insert z, when given string > this.value
        rbt11 = rbt11.insert("z");
        t.checkExpect(rbt11.getRight().getValue().equals("z"),
                "given string > this value");
        t.checkExpect(rbt11.getRight().getColor().equals("R"),
                "inserted node color");

        rbt0 = RBTree.emptyTree(stringByLex);
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "R");
        t.checkExpect(rbt0a.insert("b").getRight().getValue().equals("b"),
                "51");
        t.checkExpect(rbt0a.insert("a").getColor().equals("B"), "52");
        t.checkExpect(rbt0a.insert2("b").getRight().getValue().equals("b"),
                "53");
        t.checkExpect(rbt0a.insert2("a").getColor().equals("R"), "54");

        // test insert2 method
        rbt11 = RBTree.emptyTree(stringByLex);

        rbt11 = rbt11.insert2("g");
        t.checkExpect(rbt11.getValue().equals("g"), "insert2 b get this value");
        // root node's color will change to black
        t.checkExpect(rbt11.getColor().equals("R"), "insert2 b get this color");
        // insert a, when given string < this.value
        rbt11 = rbt11.insert2("a");
        t.checkExpect(rbt11.getLeft().getValue().equals("a"),
                "given string < this value");
        t.checkExpect(rbt11.getColor().equals("R"), "root doesn't makeblack");
        t.checkExpect(rbt11.getLeft().getColor().equals("R"),
                "inserted node color");
        // insert z, when given string > this.value
        rbt11 = rbt11.insert2("z");
        t.checkExpect(rbt11.getRight().getValue().equals("z"),
                "given string > this value");
        t.checkExpect(rbt11.getColor().equals("R"), "root doesn't makeblack");
        t.checkExpect(rbt11.getRight().getColor().equals("R"),
                "inserted node color");
    }

    /**
     * test RepOk and its helper methods
     * 
     * @param t
     *            given tester
     */
    void testRepOK(Tester t) {
        rbt0 = RBTree.emptyTree(stringByLex);
        RBTree<String> rbt0d;
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0d = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0e = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");

        // test compRight
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        Node<String> rbt1a = (Node<String>) rbt0a;
        Node<String> rbt1b = (Node<String>) rbt0b;
        t.checkExpect(rbt1a.compRight(stringByLex), "77");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0a, "a", "B");
        rbt1a = (Node<String>) rbt0a;
        rbt1b = (Node<String>) rbt0b;
        t.checkExpect(rbt1b.compRight(stringByLex), "78");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0a, "z", "B");
        rbt1a = (Node<String>) rbt0a;
        rbt1b = (Node<String>) rbt0b;
        t.checkFail(rbt1b.compRight(stringByLex), "79");

        // test compLeft
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt1a = (Node<String>) rbt0a;
        t.checkExpect(rbt1a.compLeft(stringByLex), "80");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0, "z", "B");
        rbt1a = (Node<String>) rbt0a;
        rbt1b = (Node<String>) rbt0b;
        t.checkExpect(rbt1b.compLeft(stringByLex), "81");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0, "a", "B");
        rbt1a = (Node<String>) rbt0a;
        rbt1b = (Node<String>) rbt0b;
        t.checkFail(rbt1b.compLeft(stringByLex), "82");

        // test maxHeight
        rbt0d = rbt0d.insert("b");
        rbt0d = rbt0d.insert("c");
        rbt0d = rbt0d.insert("d");
        rbt0d = rbt0d.insert("f");
        rbt0d = rbt0d.insert("g");
        t.checkExpect(rbt0d.maxHeight(), 4, "83");

        // test minHeight
        t.checkExpect(rbt0d.minHeight(), 2, "84");

        // test minBlack
        t.checkExpect(rbt0d.minBlack(), 2, "85");

        // test maxBlack
        t.checkExpect(rbt0d.maxBlack(), 2, "86");

        // test noRedRed
        t.checkExpect(rbt0.noRedRed());
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "R");
        t.checkExpect(rbt0c.noRedRed(), "87");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "c", "R");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "R");
        t.checkFail(rbt0c.noRedRed(), "88");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "R");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "R");
        t.checkFail(rbt0c.noRedRed(), "89");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "R");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "c", "R");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "R");
        t.checkFail(rbt0c.noRedRed(), "90");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "c", "R");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "B");
        t.checkExpect(rbt0c.noRedRed(), "91");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "c", "R");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "Z");
        t.checkFail(rbt0c.noRedRed(), "91.5");

        // test checkNode
        // correct tree
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        rbt1a = (Node<String>) rbt0b;
        t.checkExpect(rbt1a.checkNode(), "94");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "R");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "R");
        rbt1a = (Node<String>) rbt0b;
        t.checkFail(rbt1a.checkNode(), "95");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0d = RBTree.node(stringByLex, rbt0, rbt0, "d", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0d, "c", "R");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "R");
        rbt1a = (Node<String>) rbt0b;
        t.checkFail(rbt1a.checkNode(), "96");

        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0d = RBTree.node(stringByLex, rbt0, rbt0, "d", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0d, "c", "R");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        rbt1a = (Node<String>) rbt0b;
        t.checkFail(rbt1a.checkNode(), "97");

        // test checkNode(Comparator<string> comp)
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        rbt1a = (Node<String>) rbt0b;
        t.checkExpect(rbt1a.checkRL(stringByLex), "98");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        rbt1a = (Node<String>) rbt0b;
        t.checkFail(rbt1a.checkRL(stringByLex), "99");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        rbt1a = (Node<String>) rbt0b;
        t.checkFail(rbt1a.checkRL(stringByLex), "100");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        rbt1a = (Node<String>) rbt0b;
        t.checkFail(rbt1a.checkRL(stringByLex), "101");

        // test preRepOK
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkExpect(rbt0b.preRepOK(stringByLex), "102");
        // FFFF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "Z");
        rbt0d = RBTree.node(stringByLex, rbt0, rbt0, "d", "Z");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0d, "c", "R");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "R");
        t.checkFail(rbt0b.preRepOK(stringByLex), "103");
        // TFTT
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkFail(rbt0b.preRepOK(stringByLex), "104");
        // FTTT
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "Z");
        t.checkFail(rbt0b.preRepOK(stringByLex), "105");
        // TTTF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "R");
        rbt0d = RBTree.node(stringByLex, rbt0, rbt0, "d", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "Z");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkFail(rbt0b.preRepOK(stringByLex), "106");
        // TFFT
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "Z");
        rbt0d = RBTree.node(stringByLex, rbt0a, rbt0, "d", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0d, rbt0c, "b", "B");
        t.checkFail(rbt0b.preRepOK(stringByLex), "107");
        // TFTF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0d = RBTree.node(stringByLex, rbt0, rbt0, "d", "Z");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0d, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkFail(rbt0b.preRepOK(stringByLex), "107");
        // TTFT
        rbt0d = RBTree.node(stringByLex, rbt0, rbt0, "d", "Z");
        rbt0a = RBTree.node(stringByLex, rbt0d, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkFail(rbt0b.preRepOK(stringByLex), "108");
        // TTFF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "Z");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "Z");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkFail(rbt0b.preRepOK(stringByLex), "109");
        // FFFT
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "Z");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "Z");
        t.checkFail(rbt0b.preRepOK(stringByLex), "110");
        // FFTF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "Z");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "Z");
        t.checkFail(rbt0b.preRepOK(stringByLex), "110");
        // FTFF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "Z");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "Z");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "Z");
        t.checkFail(rbt0b.preRepOK(stringByLex), "111");
        // TFFF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "Z");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "Z");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkFail(rbt0b.preRepOK(stringByLex), "112");
        // FFTT
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "z", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "Z");
        t.checkFail(rbt0b.preRepOK(stringByLex), "113");
        // FTFT
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "F");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "Z");
        t.checkFail(rbt0b.preRepOK(stringByLex), "114");
        // FTTF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "F");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "Z");
        t.checkFail(rbt0b.preRepOK(stringByLex), "114");

        // test repOK
        t.checkExpect(rbt0.repOK());
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkExpect(rbt0b.repOK(), "115");
        // TF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "R");
        t.checkFail(rbt0b.repOK(), "116");
        // FF
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "R");
        t.checkFail(rbt0b.repOK(), "117");
        // FT
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "b", "B");
        rbt0c = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0b = RBTree.node(stringByLex, rbt0a, rbt0c, "b", "B");
        t.checkFail(rbt0b.repOK(), "118");
    }

    /**
     * test treetoArray, contains, size
     * 
     * @param t
     *            given tester
     */
    void testMethods2(Tester t) {
        // test treeToArray;
        ArrayList<String> atest = new ArrayList<String>();
        rbt0 = RBTree.emptyTree(stringByLex);
        t.checkExpect(rbt0.treeToArray().equals(atest), "empty tree");
        rbt0 = rbt0.insert("g");
        t.checkExpect(rbt0.treeToArray().get(0).equals("g"),
                "tree with one node");
        rbt0 = rbt0.insert("z");
        rbt0 = rbt0.insert("a");
        t.checkExpect(rbt0.treeToArray().get(0).equals("a"),
                "tree with mutiple nodes");
        t.checkExpect(rbt0.treeToArray().get(1).equals("g"));
        atest.add("a");
        atest.add("g");
        atest.add("z");
        t.checkExpect(rbt0.treeToArray().equals(atest),
                "check this arraylist is the same as example");
        rbt0 = rbt0.insert("b");
        rbt0 = rbt0.insert("c");
        // insert "b" and "c" into rbt0, because insert method will
        // balance the tree, therefore get(1) will be "b" rather than "g"
        t.checkFail(rbt0.treeToArray().get(1).equals("g"));
        t.checkExpect(rbt0.treeToArray().get(1).equals("b"));
        rbt0 = rbt0.insert("w");
        rbt0 = rbt0.insert("x");
        t.checkExpect(rbt0.getValue().equals("b"),
                "check the root value of current rbt0");
        /* the tree right now has g, z, a, b, c, w, x
         * will look like
         *          bB
         *        /    \
         *      aB      wR
         *             /  \
         *           gB   zB
         *          /     /
         *        cR    xR
         * treeToArray should be: a, b, c, g, w, x, z
        */
        ArrayList<String> atest2 = new ArrayList<String>();
        atest2.add("a");
        atest2.add("b");
        atest2.add("c");
        atest2.add("g");
        atest2.add("w");
        atest2.add("x");
        atest2.add("z");
        t.checkExpect(rbt0.treeToArray().get(1).equals(rbt0.getValue()),
                "test currently rbt0 meet the example");
        t.checkExpect(rbt0.treeToArray().equals(atest2), "rbt0 meets example");

        // test size()
        // rbt1 is an emptyTree
        t.checkExpect(rbt1.size(), 0, "emptyTree size");
        t.checkExpect(rbt0.size(), 7, "rbt0 size");
        rbt0 = rbt0.insert("y");
        t.checkExpect(rbt0.size(), 8, "rbt0 insert one more size");

               
        /* the tree right now has g, z, a, b, c, w, x, y
         * will look like
         *          wB
         *        /    \
         *      bB      yB
         *     /  \    /  \
         *   aB   gB xB    zB
         *       /       
         *     cR        
         * treeToArray should be: a, b, c, g, w, x, y, z
        */
        
        
        // test contains
        t.checkFail(rbt1.contains("a"), "test emptyTree");
        t.checkExpect(rbt0.contains("b"), "test rbt0 given b");
        t.checkExpect(rbt0.contains("a"), "test rbt0 given a");
        t.checkFail(rbt0.contains("s"), "test rbt0 given s");
        rbt0 = RBTree.emptyTree(stringByLex);
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "c", "B");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "B");
        t.checkExpect(rbt0c.contains("a"), "111");
        t.checkExpect(rbt0c.contains("b"), "112");
        t.checkExpect(rbt0c.contains("c"), "113");
        t.checkFail(rbt0c.contains("s"), "114");
        rbt0a = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0b = RBTree.node(stringByLex, rbt0, rbt0, "a", "B");
        rbt0c = RBTree.node(stringByLex, rbt0a, rbt0b, "b", "B");
        t.checkExpect(rbt0c.contains("a"), "115");
    }

    /**
     * test equals, toString, hashCode
     * 
     * @param t
     *            given tester
     */

    void testOverride(Tester t) {
        rbt0a = RBTree.emptyTree(stringByLex);
        rbt0e = RBTree.emptyTree(stringByLex);
        rbt0 = RBTree.emptyTree(stringByLex);
        RBTree<String> rbt0f = null;
        RBTree<String> rbt0g = rbt0a.insert("a");
        RBTree<String> rbt0h = rbt0a.insert("a");

        rbt0 = rbt0.insert("g");
        rbt0 = rbt0.insert("z");
        rbt0 = rbt0.insert("a");
        rbt0 = rbt0.insert("b");
        rbt0 = rbt0.insert("c");
        rbt0 = rbt0.insert("w");
        rbt0 = rbt0.insert("x");

        rbt0c = RBTree.emptyTree(stringByLex);
        rbt0c = rbt0c.insert("g");
        rbt0c = rbt0c.insert("z");
        rbt0c = rbt0c.insert("a");
        rbt0c = rbt0c.insert("b");
        rbt0c = rbt0c.insert("c");
        rbt0c = rbt0c.insert("w");
        rbt0c = rbt0c.insert("x");

        /*
         * the tree right now has g, z, a, b, c, w, x will look like bB / \ aB
         * wR / \ gB zB / / cR xR treeToArray should be: a, b, c, g, w, x, z
         */

        rbt0b = RBTree.emptyTree(stringByLex);
        rbt0b = rbt0b.insert("z");
        rbt0b = rbt0b.insert("a");
        rbt0b = rbt0b.insert("b");
        rbt0b = rbt0b.insert("c");
        rbt0b = rbt0b.insert("w");
        rbt0b = rbt0b.insert("x");

        // test equals
        t.checkFail(rbt0.equals(rbt0f), "check null(rbt0b");
        t.checkExpect(rbt0a.equals(rbt0e), "check two empty tree");
        t.checkExpect(rbt0g.equals(rbt0h), "check two with one node");
        t.checkFail(rbt0a.equals(rbt0), "check different tree");
        t.checkFail(rbt0.equals(rbt0b), "web-cat is stupid");
        t.checkExpect(rbt0.equals(rbt0c), "web-cat worst invention ever");
        RBTree<String> rbt20a = new Node<String>(stringByLex, rbt0a, rbt0a,
                "a", "R");
        RBTree<String> rbt20b = new Node<String>(stringByLex, rbt0a, rbt0a,
                "b", "R");
        RBTree<String> rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a,
                "b", "R");
        RBTree<String> rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a,
                "b", "R");
        t.checkFail(rbt20a.equals(rbt20b), "F T T T");
        rbt20a = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20b = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "B");
        t.checkFail(rbt20a.equals(rbt20b), "T F T T");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt20c, rbt0a, "a", "B");
        rbt20b = new Node<String>(stringByLex, rbt20d, rbt0a, "a", "B");
        t.checkFail(rbt20a.equals(rbt20b), "T T F T");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt0a, rbt20d, "a", "B");
        rbt20b = new Node<String>(stringByLex, rbt0a, rbt20c, "a", "B");
        t.checkFail(rbt20a.equals(rbt20b), "T T T F");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20a = new Node<String>(stringByLex, rbt0a, rbt20d, "a", "R");
        rbt20b = new Node<String>(stringByLex, rbt0a, rbt20c, "b", "B");
        t.checkFail(rbt20a.equals(rbt20b), "F F T T");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt20d, rbt0a, "a", "R");
        rbt20b = new Node<String>(stringByLex, rbt20c, rbt0a, "b", "R");
        t.checkFail(rbt20a.equals(rbt20b), "F T F T");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt0a, rbt20d, "a", "R");
        rbt20b = new Node<String>(stringByLex, rbt0a, rbt20c, "b", "R");
        t.checkFail(rbt20a.equals(rbt20b), "F T T F");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt20d, rbt0a, "a", "R");
        rbt20b = new Node<String>(stringByLex, rbt20c, rbt0a, "a", "B");
        t.checkFail(rbt20a.equals(rbt20b), "T F F T");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt0a, rbt20d, "a", "R");
        rbt20b = new Node<String>(stringByLex, rbt0a, rbt20c, "a", "B");
        t.checkFail(rbt20a.equals(rbt20b), "T F T F");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt20d, rbt20d, "a", "R");
        rbt20b = new Node<String>(stringByLex, rbt20c, rbt20c, "a", "R");
        t.checkFail(rbt20a.equals(rbt20b), "T T F F");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt20d, rbt0a, "b", "B");
        rbt20b = new Node<String>(stringByLex, rbt20c, rbt0a, "a", "R");
        t.checkFail(rbt20a.equals(rbt20b), "F F F T");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt0a, rbt20d, "b", "B");
        rbt20b = new Node<String>(stringByLex, rbt0a, rbt20c, "a", "R");
        t.checkFail(rbt20a.equals(rbt20b), "F F T F");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt20d, rbt20d, "b", "R");
        rbt20b = new Node<String>(stringByLex, rbt20c, rbt20c, "a", "R");
        t.checkFail(rbt20a.equals(rbt20b), "F T F F");
        rbt20c = new Node<String>(stringByLex, rbt0a, rbt0a, "a", "R");
        rbt20d = new Node<String>(stringByLex, rbt0a, rbt0a, "b", "R");
        rbt20a = new Node<String>(stringByLex, rbt20d, rbt20d, "a", "B");
        rbt20b = new Node<String>(stringByLex, rbt20c, rbt20c, "a", "R");
        t.checkFail(rbt20a.equals(rbt20b), "F T F F");

        // test hashCode
        t.checkExpect(rbt0a.hashCode(), 0, "test emptyTree");
        t.checkExpect(rbt0.hashCode(), 1268, "test rbt0");

        // test toString
        t.checkExpect(rbt0a.toString().equals(""), "test emptyTree");
        t.checkExpect(rbt0.toString().equals("a, b, c, g, w, x, z"),
                "test rbt0");
        t.checkExpect(rbt0b.toString().equals("a, b, c, w, x, z"),
                "test rbt0b");

    }

    /**
     * test comparator
     * 
     * @param t
     *            tester
     */
    void testComparator(Tester t) {
        rbt0 = RBTree.emptyTree(stringByLex);
        t.checkExpect(rbt0.repOK());
        t.checkExpect(stringByLength.compare("a", "a"), 0);
        t.checkExpect(stringByLength.compare("aa", "a"), 1);
        t.checkExpect(stringByLength.compare("a", "aa"), -1);

    }

}
