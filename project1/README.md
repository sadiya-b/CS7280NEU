# README
Setup:
You can open the project on IntelliJ. If encountered with Junit package issue, use junit4 and openjdk16. 

Node.java
I have initialized my value size and children size, based on the following theory [1]: 
A B-tree is parameterized by a value t ≥ 2, called its minimum degree. Every B-tree with minimum
degree t must satisfy the following twp degree invariants:
(a) min-degree: Each node of the tree except the root must contain at least t − 1 keys (and hence
must have at least t children if it is not a leaf). The root must have at least 1 key (two children).
(b) max-degree: Each node of the tree must contain no more than 2t − 1 keys. A node with exactly
2t − 1 keys is called “full.” (In practice, this limit derives from the size of the disk block used to
store a node. Note that 2t − 1 is always an odd number.)

BTree.java
This class contains methods public methods such as lookup, insert which call node.java methods of adding a value, splitting a node and searching a value.
The display method is my approach of a level order traversal for btree. 

BtreeTest.java
This class consists of our main method. I’ve used quiz 1 questions of inserting the following values:
int[] array = new int[]{10,20,30,40,50,60,70,80,90,100};
1. First test - lookup
- I’m searching for ‘50’ to see if it accurately searches the value in the btree. 
2. Second test - Display method + insert:
output on running the code::
-------
|40||-1||-1|

|20||-1||-1|
|60||80||-1|

|10||-1||-1|
|30||-1||-1|
|50||-1||-1|
|70||-1||-1|
|90||100||-1|

- since it’s level order, the first n0ode ‘40’ is the root, 20,60,80 are level 2 and so on. 

3. Third test: test to see if  a duplicate value is entered
- I used insert method to add ‘50’ in the tree and checked my answer using display, both the trees in second test case and third are identical, which means my code handles duplication. 


Overall Output: 
true
----tree display---
|40||-1||-1|

|20||-1||-1|
|60||80||-1|

|10||-1||-1|
|30||-1||-1|
|50||-1||-1|
|70||-1||-1|
|90||100||-1|

-----after adding duplicate value----

|40||-1||-1|

|20||-1||-1|
|60||80||-1|

|10||-1||-1|
|30||-1||-1|
|50||-1||-1|
|70||-1||-1|
|90||100||-1|


References:
[1] https://classes.engineering.wustl.edu/cse241/handouts/btree.pdf
[2] https://www.geeksforgeeks.org/insert-operation-in-b-tree/
[3] https://www.geeksforgeeks.org/introduction-of-b-tree-2/


