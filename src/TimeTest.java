import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Timing Test for BTree
 * 
 * @author Chieh Lee
 * @version 11.14.2013
 */
class TimeTest {

    /**
     * global variable for Iterator
     */
    static Iterator<String> it;
    /**
     * global variable for BTree, will be mutated
     */
    static BTree<String> bt;
    /**
     * global for track time usage of build
     */
    static double totalBuild;
    /**
     * global for track time usage of Iterator
     */
    static double totalIter;
    /**
     * global for track time usage of contains
     */
    static double totalContains;

    /**
     * method for running Iterator when there is a limitation of number of
     * string to be read
     * 
     * @param itt Iterator<String>
     * @param numStrings number of string that going to be read
     * @return ArrayList<String>
     */
    private static ArrayList<String> buildIter(Iterator<String> itt,
            int numStrings) {
        ArrayList<String> result = new ArrayList<String>();
        while (itt.hasNext() && numStrings > 0) {
            result.add(itt.next());
            numStrings--;
        }
        return result;
    }

    /**
     * method for running Iterator when there is a limitation of number of
     * string to be read
     * 
     * @param itt Iterator<String>
     * @return ArrayList<String>
     */
    private static ArrayList<String> buildIter(Iterator<String> itt) {
        ArrayList<String> result = new ArrayList<String>();
        while (itt.hasNext()) {
            result.add(itt.next());
        }
        return result;
    }

    /**
     * helper method for calling contains
     * 
     * @param itt Iterable<T>
     * @param b given BTree<T>
     */
    private static < T> void contains(Iterable<T> itt, BTree<T> b) {
        for (T s : itt) {
            b.contains(s);
        }
    }

    /**
     * method for build method for RBTree
     * 
     * @param comp given Comparator<T>
     * @param fileName given filename in String
     * @param wi word iterated
     */
    private static void outputbuild(Comparator<String> comp, String fileName,
            int wi) {

        // Time variables (double)
        double startTime;
        double endTime;

        // local tree instance;1
        BTree<String> bb = BTree.binTree(comp);

        // build for StringByLex ***
        if (wi > 0) {
            bb.reset(comp);
            startTime = System.currentTimeMillis();
            bb.build(new StringIterator(fileName), wi);
            endTime = System.currentTimeMillis();
            totalBuild = endTime - startTime;
        }
        else {
            bb.reset(comp);
            startTime = System.currentTimeMillis();
            bb.build(new StringIterator(fileName));
            endTime = System.currentTimeMillis();
            totalBuild = endTime - startTime;
        }
        bt = bb;
    }

    /**
     * method for Iterator method for RBTree
     * 
     * @param comp given Comparator<T>
     * @param fileName given filename in String
     * @param wi word iterated
     */
    private static void outputIterator(Comparator<String> comp,
            String fileName, int wi) {

        // time variables;
        double startTime;
        double endTime;

        if (wi > 0) {
            startTime = System.currentTimeMillis();
            TimeTest.buildIter(it, wi);
            endTime = System.currentTimeMillis();
            totalIter = endTime - startTime;
        }
        else {
            startTime = System.currentTimeMillis();
            TimeTest.buildIter(it);
            endTime = System.currentTimeMillis();
            totalIter = endTime - startTime;
        }
    }

    /**
     * method for contains method for RBTree
     * 
     * @param comp given Comparator<T>
     * @param fileName given filename in String
     * @param wi word iterated
     */
    private static void outputcontains(Comparator<String> comp,
            String fileName, int wi) {

        // time variables;
        double startTime;
        double endTime;

        // local tree instance;
        BTree<String> bb = BTree.binTree(comp);

        // contain
        if (wi > 0) {
            bb.reset(comp);
            bb.build(new StringIterator(fileName), wi);
            startTime = System.currentTimeMillis();
            TimeTest.contains(new StringIterator("Contains.txt"), bb);
            endTime = System.currentTimeMillis();
            totalContains = endTime - startTime;
        }
        else {
            bb.reset(comp);
            bb.build(new StringIterator(fileName));
            startTime = System.currentTimeMillis();
            TimeTest.contains(new StringIterator("Contains.txt"), bb);
            endTime = System.currentTimeMillis();
            totalContains = endTime - startTime;
        }
    }

    /**
     * loop and print 3 helper method above
     * 
     * @param comp given Comparator<String>
     * @param fileName given filename in String
     * @param an ArrayList<Integer> list of numString to run
     */
    private static void output3(Comparator<String> comp, String fileName,
            ArrayList<Integer> an) {
        if (!an.isEmpty()) {
            for (int i = 0; i < an.size(); i++) {
                outputbuild(comp, fileName, an.get(i));
                it = bt.iterator();
                outputIterator(comp, fileName, an.get(i));
                outputcontains(comp, fileName, an.get(i));
                System.out
                        .println(comp.toString() + "\t" + fileName + "\t"
                                + an.get(i) + "\t"
                                + bt.accept(new CountNodes<String>()).get(0)
                                + "\t" + totalBuild + "\t" + totalIter + "\t"
                                + totalContains);
            }
            System.out.println();
        }
        else {
            outputbuild(comp, fileName, -1);
            it = bt.iterator();
            outputIterator(comp, fileName, -1);
            outputcontains(comp, fileName, -1);
            System.out.println(comp.toString() + "\t" + fileName + "\t"
                    + bt.accept(new CountNodes<String>()).get(0) + "\t"
                    + totalBuild + "\t" + totalIter + "\t" + totalContains);
        }
    }

    /**
     * loop different Comparator and filename
     * 
     * @param aComparator list of Comparator<String>
     * @param aFileName list of filenames
     * @param an list of numString to run
     */
    public static void testmain(ArrayList<Comparator<String>> aComparator,
            ArrayList<String> aFileName, ArrayList<Integer> an) {

        for (int j = 0; j < aFileName.size(); j++) {
            System.out.println();
            for (int i = 0; i < aComparator.size(); i++) {
                output3(aComparator.get(i), aFileName.get(j), an);

            }
        }

    }

    /**
     * main method
     * 
     * @param arg arg
     */
    public static void main(String[] arg) {

        // variable of comparators
        Comparator<String> stringByLex = new StringByLex();
        Comparator<String> stringReverseByLex = new StringReverseByLex();
        Comparator<String> stringWithOutPrefixByLex = 
                new StringWithOutPrefixByLex();

        // make comparators into an arraylist
        ArrayList<Comparator<String>> aComparator = 
                new ArrayList<Comparator<String>>();
        aComparator.add(stringByLex);
        aComparator.add(stringReverseByLex);
        aComparator.add(stringWithOutPrefixByLex);

        // arraylist for Integers of numStrings
        ArrayList<Integer> an = new ArrayList<Integer>();
        an.add(2000);
        an.add(4000);
        an.add(8000);
        an.add(16000);
        an.add(20000);
        an.add(24000);

        ArrayList<Integer> empty = new ArrayList<Integer>();

        String lex = "lexicographically_ordered.txt";
        String rev = "reverse_ordered.txt";
        String pre = "prefix_ordered.txt";
        String ran = "random_order.txt";
        String hip = "hippooath_DUPLICATES.txt";
        String ili = "iliad.txt";

        ArrayList<String> aStr = new ArrayList<String>();
        aStr.add(lex);
        aStr.add(rev);
        aStr.add(pre);
        aStr.add(ran);

        ArrayList<String> aCL = new ArrayList<String>();
        aCL.add(hip);
        aCL.add(ili);

        System.out.println();

        testmain(aComparator, aStr, an);
        testmain(aComparator, aCL, empty);

        System.out.println();

        // testmain(aComparator, aStr, an);
        System.out.println();
        System.out.println("Ted is bad");

    }

}
