package com.itc.mn.Structures.Lists;

import com.itc.mn.Structures.Node;

import java.util.ArrayList;

/**
 * This creates a double linked list with numerical values.
 * The nodes already have a frequency indicator, so we don't have
 * duplicated nodes with same values
 */
public class DoubleLinkedList {

    private Node root;
    private String label;

    /**
     * Creates an empty Double Linked list
     */
    public DoubleLinkedList(){
        root = null;
    }

    /**
     * Creates a double linked list with a given name, in case
     * it happens to be many
     * @param label Name of the linked list
     */
    public DoubleLinkedList(String label){
        root = null;
        this.label = label;
    }

    public void insertNode(double value){
        if(isListEmpty())
            root = new Node(value);
        else{
            Node newNode = new Node(value); // Instantiate the node
            Node tmp = root; // Begin from root
            while (tmp.getValue() < value && tmp.getNext() != null) // While our new value is bigger or the next node isn't null
                tmp = tmp.getNext(); // We will traverse the list
            if(tmp == root){ // If the final node is our root
                if(tmp.getValue() == newNode.getValue()) // If our value is the same as our new node
                    tmp.frequency(true); // We increase the frequency
                else { // Else
                    if(newNode.getValue() < tmp.getValue()){
                        root.setPrevious(newNode); // We assign the new node to the previous pointer of root
                        newNode.setNext(root); // Define the new node next pointer to the root
                        root = newNode; // Set the new node as the new root
                    }
                    else{
                        tmp.setNext(newNode);
                        newNode.setPrevious(tmp);
                    }
                }
            }
            else{ // Else
                if(tmp.getValue() == value) // If our value is the same
                    tmp.frequency(true); // We increase the frequency
                else{ // Else
                    if(tmp.getValue() < newNode.getValue()){ // If the last node is less than the new onw
                        tmp.setNext(newNode); // Reference the last node to the new next one
                        newNode.setPrevious(tmp); //  Link the new node to the last one
                    }
                    else{
                        tmp.getPrevious().setNext(newNode); // We set the next node (of the previous one) point our new node
                        newNode.setPrevious(tmp.getPrevious()); // Then we say our new node previous value is the previous of the actual
                        newNode.setNext(tmp); // We define the new node next pointer as our actual node
                        tmp.setPrevious(newNode); // And finally we say out actual node previous node is our new node
                    }
                }
            }
        }
    }

    public ArrayList getDoubleArray() throws NullPointerException{
        if(isListEmpty())
            throw new NullPointerException("There's no root available");
        else {
            ArrayList<double[]> array = new ArrayList(0);
            Node tmp = root;
            while (tmp.getNext() != null) {
                array.add(new double[]{tmp.getValue(), tmp.getFrequency()});
                tmp = tmp.getNext();
            }
            array.add(new double[]{tmp.getValue(), tmp.getFrequency()});
            return array;
        }
    }

    /**
     * Returns the root of the list
     * @return Node
     * @throws NullPointerException if there's no root available
     */
    public Node getRoot()throws NullPointerException {
        if(root!= null)
            return root;
        else
            throw new NullPointerException("There's no root available");
    }

    /**
     * Returns the last node available
     * @return Node
     * @throws NullPointerException in case there's no root available
     */
    public Node getLastNode()throws NullPointerException{
        if(isListEmpty())
            throw new NullPointerException("There's no root available");
        else {
            Node tmp = root;
            while (tmp.getNext()!= null)
                tmp = tmp.getNext();
            return tmp;
        }
    }

    /**
     * Returns only a value list
     * @return ArrayList with values
     * @throws NullPointerException if list is empty
     */
    public ArrayList getValueList()throws NullPointerException{
        if(isListEmpty())
            throw new NullPointerException("List is empty");
        else{
            ArrayList<Double> values = new ArrayList(0);
            Node tmp = root;
            while (tmp.getNext() != null){
                values.add(tmp.getValue());
                tmp = tmp.getNext();
            }
            values.add(tmp.getValue());
            return values;
        }
    }

    /**
     * Returns an ArrayList of double[] that holds value and frequency of given data
     * @return ArrayList of values
     * @throws NullPointerException if list is empty
     */
    public ArrayList getValFreqList()throws NullPointerException{
        if(isListEmpty())
            throw new NullPointerException("List is empty");
        else{
            ArrayList<Double[]> values = new ArrayList<Double[]>(0);
            Node tmp = root;
            while (tmp.getNext() != null){
                values.add(new Double[]{tmp.getValue(), tmp.getFrequency()});
                tmp = tmp.getNext();
            }
            values.add(new Double[]{tmp.getValue(), tmp.getFrequency()});
            return values;
        }
    }

    public boolean isListEmpty(){
        return root == null;
    }

}
