/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Node head;

    private class Node {
        Item it;
        Node next;

        private Node(Item i) {
            it = i;
            next = null;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }


    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node added = new Node(item);
        if (isEmpty()) {
            head = added;
            size++;
            return;
        }
        added.next = head;
        head = added;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        Node travel = head;
        int n = StdRandom.uniform(size);
        if (n == 0) {
            Node temp = head;
            head = head.next;
            return temp.it;
        }
        for (int i = 0; i < n - 1; i++) {
            travel = travel.next;
        }
        Node temp = travel.next;
        travel.next = temp.next;
        return temp.it;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        Node travel = head;
        int n = StdRandom.uniform(size);
        for (int i = 0; i < n; i++) {
            travel = travel.next;
        }
        return travel.it;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item temp = current.it;
            current = current.next;
            return temp;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}
