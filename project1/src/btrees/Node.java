package btrees;

import java.util.Arrays;

public class Node {
    //an array of values in the node
    int[] values;

    /* this is the max degree defined for the Btree
     */
    int nodesize;

    //array to store children
    Node[] children;

    //current number of values in the tree
    int cntValues;

    //to check if node is leaf or not
    boolean isLeaf;

    /**
     * Constructor to initialize NODE data structure
     *
     * @param degree - intializing minimum degree required
     * @param leaf - to intialize if node is leaf
     */

    public Node(int degree, boolean leaf){
        this.nodesize = degree;
        this.isLeaf = leaf;

        //Allocating size for keys and children based on the assumption mentioned in readme file
        this.values = new int[2*nodesize -1];
        this.children = new Node[2*nodesize];

        //filling the arrays to be -1 for ease in display
        //-1 means, there is no value in the node.
        Arrays.fill(values,-1);

        //intializing the count of values to be 0 because the node doesn't contain any values.
        this.cntValues = 0;
    }

    /**
     * This method checks if a value is present in the tree
     * it returns true of false
     * @param value
     * @return
     */
    public boolean nodeLookup(int value) {
        int counter =0;

        while(counter < cntValues && value > values[counter]){
            counter++;
        }

        if(counter < cntValues && values[counter] == value){
            return true;
        }
        //once we know that there are no more children, i.e no further levels/nodes to
        //traverse, we will return false.
        if(isLeaf){
            return false;
        }

        //we are traversing to the children of the current node and recursively calling nodelookup.
        return children[counter].nodeLookup(value);
    }


    /**
     * Split method will split a node into two when it is full.
     * it will select the middle element and move it to a higher level
     * the value smaller to it, becomes left child, the values greater become right child
     * @param i
     * @param n
     */
    public void split(int i, Node n) {
        //creating a new node to hold values of node 'n'
        int j = nodesize-1;
        Node n1 = new Node(n.nodesize,n.isLeaf);
        n1.cntValues = nodesize-1;

        //copy values from n to n1
        for(int k=0;k<nodesize-1;k++){
            n1.values[k] = n.values[k+nodesize];
            //once the values are copied, we want to free up the previous node, therefore we
            //make the values -1 to represent empty space.
            n.values[k+nodesize] = -1;
        }

        //copy children if n is not a leaf node
        if(!n.isLeaf){
            for(int k=0;k<nodesize;k++){
                n1.children[k] = n.children[k+nodesize];
                //similar to copying values, once we create a new node,
                //we can make the children null to free up space.
                n.children[k+nodesize] = null;
            }
        }

        //re-intializing values in node 'n'
        n.cntValues = nodesize-1;

        //adding a new child
        for(int k = cntValues; k>= i+1;k--){
            children[k+1] = children[k];
        }
        //linking new node to the child.
        children[i+1] = n1;

        //moving all the others keys to one space ahead of the
        //previous created node
        for(int k = cntValues-1;k>=i;k--){
            values[k+1] = values[k];
        }
        //copying the middle value of n to this node
        values[i] = n.values[nodesize-1];

        n.values[nodesize-1] = -1;

        //increase count of keys current node.
        cntValues++;
    }

    /**
     * This functions will add a value to an already existing node
     * @param value
     */
    public void add(int value) {
        //index to index of rightmost value
        int index = cntValues-1;

        //find the location of the new key and shifting keys
        if(isLeaf){
            while(index>=0 && values[index] > value){
                values[index+1] = values[index];
                index--;
            }

            //adding key to its appropriate index
            values[index+1] = value;
            cntValues++;
        }
        else{
            while(index >= 0 && values[index] > value){
                index--;
            }

            //checking if the node is full
            if(children[index+1].cntValues == 2*nodesize-1){
                //splitting the node
                split(index+1,children[index+1]);

                if(values[index+1] < value){
                    index++;
                }
            }
            children[index+1].add(value);
        }
    }
}
