/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item it;
        Node next;
        Node last;

        private Node(Item i) {
            it = i;
            next = null;
            last = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node added = new Node(item);
        if (isEmpty()) {
            head = added;
            tail = added;
            size++;
            return;
        }
        added.next = head;
        head.last = added;
        head = added;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node added = new Node(item);
        if (isEmpty()) {
            head = added;
            tail = added;
            size++;
            return;
        }
        added.last = tail;
        tail.next = added;
        tail = added;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node temp = head;
        head = head.next;
        head.last = null;
        size--;
        return temp.it;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Node temp = tail;
        tail = tail.last;
        tail.next = null;
        size--;
        return temp.it;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item temp = current.it;
            current = current.next;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
