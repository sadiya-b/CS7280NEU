package btrees;

public class BtreeTest {
    public static void main(String[] args){
        //using the example to display the quiz 2
        int[] array = new int[]{10,20,30,40,50,60,70,80,90,100};

        Btree t = new Btree(2);
        for(int i: array){
            t.insert(i);
        }

        System.out.println(t.lookup(50));

        System.out.println("----tree display---");
        t.display();
        System.out.println("-----after adding duplicate value----");
        t.insert(50);
        t.display();
        /**
         * the tree doesn't change, our implementation works and it doesn't add a duplicate value/
         */
    }

}