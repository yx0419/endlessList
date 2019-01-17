package cs143;

import java.util.Iterator;

/**
 * This data class represents an ordered collection in an endless circular list.
 *
 * @param <E> the type of values stored in the list
 */
public class EndlessList<E> implements Iterable<E> {

    //fields
    private Node<E> cursor;

    /**
     * Adds a value before the current one and moves the cursor to the new
     * value. If the list is empty the value is simply added and becomes the
     * current one.
     *
     * @param value the value to add to the list
     */
    public void addPrev(E value) {

        if (cursor == null) {
            Node<E> head = new Node<E>(value);
            head.setNext(head);
            head.setPrev(head);
            cursor = head;

        } else {
            Node<E> newNode = new Node<E>(value, cursor.getPrev(), cursor);
            cursor.getPrev().setNext(newNode);
            cursor.setPrev(newNode);
            cursor = newNode;
        }
    }

    /**
     * Adds a value after the current one and moves the cursor to the new value.
     * If the list is empty the value is simply added and becomes the current
     * one.
     *
     * @param value the value to add to the list
     */
    public void addNext(E value) {

        if (cursor == null) {
            Node<E> head = new Node<E>(value);
            head.setNext(head);
            head.setPrev(head);
            cursor = head;

        } else {
            Node<E> newNode = new Node<E>(value, cursor, cursor.getNext());
            cursor.getNext().setPrev(newNode);
            cursor.setNext(newNode);
            cursor = newNode;
        }
    }

    /**
     * Removes the current value from the list and moves the cursor to the next
     * value, returning the removed value. Returns a null if the list is empty.
     * If this is the last value in the list the cursor becomes null.
     *
     * @return the value removed
     */
    public E remove() {

        if (cursor == null) {
            return null;
        } else if (cursor.getNext() == cursor) {
            E e = cursor.getValue();
            cursor = null;
            return e;
        } else {
            E e = cursor.getValue();
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getNext();
            return e;
        }

    }

    /**
     * Returns the value at the current cursor position. Returns a null if the
     * list is empty.
     *
     * @return the value
     */
    public E getValue() {

        if (cursor == null) {
            return null;
        }
        return cursor.getValue();
    }

    /**
     * Changes the current value at the current cursor position. Returns false
     * if the list is empty and true if the change is made.
     *
     * @param value the new value
     * @return true if successful, false if not
     */
    public boolean setValue(E value) {

        if (cursor != null) {
            cursor.setValue(value);
            return true;
        }
        return false;
    }

    /**
     * Moves the cursor to the previous value in the list and returns that
     * value. Returns a null if the list is empty.
     *
     * @return the value
     */
    public E getPrev() {

        if (cursor != null) {
            cursor = cursor.getPrev();
            return cursor.getValue();
        }
        return null;
    }

    /**
     * Moves the cursor to the next value in the list and returns that value.
     * Returns null if the list is empty.
     *
     * @return the value
     */
    public E getNext() {

        if (cursor != null) {
            cursor = cursor.getNext();
            return cursor.getValue();
        }
        return null;
    }

    /**
     * Moves the cursor to the next occurrence of the given value, moving
     * forward in the list. If the value is not found the cursor remains at the
     * same position in the list.
     *
     * @param value the value to search for
     * @return true if the value is found, false if not
     */
    public boolean moveToNext(E value) {

        Node<E> check = cursor.getNext();
        while (check.getValue() != value) {
            check = check.getNext();
            if (check.equals(cursor)) {
                return false;
            }
        }
        cursor = check;
        return true;
    }

    /**
     * Moves the cursor to the next occurrence of the given value, moving
     * backwards in the list. If the value is not found the cursor remains at
     * the same position in the list.
     *
     * @param value the value to search for
     * @return true if the value is found, false if not
     */
    public boolean moveToPrev(E value) {

        Node<E> check = cursor.getPrev();
        while (!check.getValue().equals(value)) {
            check = check.getPrev();
            if (check.equals(cursor)) {
                return false;
            }
        }
        cursor = check;
        return true;
    }

    /**
     * Provides and EndlessList iterator.
     *
     * @return the iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new EndlessListIterator();
    }

    /**
     * Private class used to create an EndlessList iterator.
     */
    private class EndlessListIterator implements Iterator<E> {

        //fields
        private Node<E> firstNode = cursor;
        private boolean handledStart = false;

        /**
         * Reports if the current node has not been reported yet by calling
         * next(). Returns false if the list is empty.
         *
         * @return true if the current node has not been reported, false if it
         * has
         */
        @Override
        public boolean hasNext() {

            if ((cursor == null)) {
                return false;
            }
            if (handledStart == true) {

                return false;
            }
            return true;
        }
        //return false;

        /**
         * Returns the current value in the list and moves to the next.
         *
         * @return the current value, or null if the list is empty
         */
        @Override
        public E next() {

            if (cursor != null) {
                E e = cursor.getValue();
                cursor = cursor.getNext();
                if (cursor == firstNode) {
                    handledStart = true;
                }
                return e;
            }
            return null;
        }

        /**
         * Removes the last value returned from next(). This assumes that next
         * is called before each remove. If this is the only value in the list
         * the cursor becomes null.
         */
        @Override
        public void remove() {
            if ((cursor.getNext() == cursor)) {
                cursor = null;
            } else {
                cursor.getPrev().getPrev().setNext(cursor);
                cursor.setPrev(cursor.getPrev().getPrev());
            }
        }

    }

}
