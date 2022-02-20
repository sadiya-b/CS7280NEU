package btrees;

import java.util.LinkedList;
import java.util.Queue;

public class Btree {
    /* Pointer to the root node. */
    private Node root;

    //minimum degree
    private int nodesize;

    /**
     * Constructor for BTree
     * @param nodesize - We initialise to be empty
     */
    public Btree(int nodesize){
        this.root = null;
        this.nodesize = nodesize;
    }

    /** B tree functions for Public
     * these functions will call functions in Node.java
     * to help with Btree operations */

    /* This function calls nodeLookup in Node.java
    it wil return true or false
     */
    public boolean lookup(int value){
        return root == null? false: this.root.nodeLookup(value);
    }

    /**
     * function to insert a new node in btree
     * @param value
     */
    public void insert(int value){
        /* If we tree is empty i.e. root=null, we create a new root node.
        we will mark leaf node as true, since it doesn't have any children
         */
        if(root == null){
            root = new Node(nodesize, true);
            root.values[0] = value;
            root.cntValues = 1;
        }
        else{
            //to avoid duplicates, we check if the value is already present in the tree
            if(lookup(value)){
                /*
                if it enters the for loop, we simply return since we don't want to insert a duplicate
                 */
                return;
            }
            else{
                //checking if node is full
                if(root.cntValues == 2*nodesize-1){
                    //if full = creating a temp node
                    Node n = new Node(nodesize,false);

                    //creating root the child of new node 'n'
                    n.children[0] = root;

                    /*
                     we are calling split on the root node and intializing the index to the 0.
                     */
                    n.split(0,root);

                    //here we are simply adding value to the node
                    int index = 0;
                    if(n.values[0]<value){
                        index++;
                    }
                    n.children[index].add(value);

                    root = n;
                }
                else{
                    root.add(value);
                }
            }
        }
    }

    /**
     * Implementing level order traversal for b-tree
     * Approach is to add null after every level we encounter.
     */
    public void display(){
        if(this.root == null){
            System.out.println("[]");
            return;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        q.add(null);

        while(q.size()>1){
            Node curr = q.poll();

            /* if we encounter a null node, we will simply add a null to queue to mark end of
            * curr level. */
            if(curr==null){
                System.out.println();
                q.add(null);
                continue;
            }

            StringBuilder sb = new StringBuilder();
            for(int i=0;i<curr.children.length;i++){
                if(curr.children[i] != null){
                    q.add(curr.children[i]);
                }
            }
            for(int val:curr.values){
                sb.append("|");
                sb.append(val + "|");
            }
            System.out.println(sb.toString());
        }
    }
}
