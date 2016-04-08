package com.company;

import java.util.*;
import java.util.Iterator;
import java.util.List;
import java.lang.reflect.*;

public class MyLinkedList<T> implements List<T> {

    private Node<T> begin;
    private Node<T> end;
    private int size;
    private int modCount = 0;


    public MyLinkedList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    private void doClear() {
        begin = new Node<>(null, null, null);
        end = new Node<>(null, begin, null);
        begin.next = end;
        size = 0;
        modCount++;
    }

    public boolean add(T x) {
        add(size(), x);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int idx = 0;
        remove(getNode(idx));
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
                for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T s : c) {
           add(s);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int i = index;
        for (T s : c) {
            add(i++, s);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c)
        {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
                for (Object o : c)
        {
            add((T) o.toString());
        }
        return true;
    }

    public void add(int idx, T x) {
        addBefore(getNode(idx, 0, size()), x);
    }

    private void addBefore(Node<T> p, T x) {
        Node<T> newNode = new Node<>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        size++;
        modCount++;
    }

    public T remove(int idx) {
        return remove(getNode(idx));
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<T> current = begin.next;
        while (current != null) {
            if (current.equals(o)) {
                return index;
            }
            index++;
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = 0;
        Node<T> current = end.prev;
        while (current != null) {
            if (current.equals(o)) {
                return index;
            }
            index++;
            current = current.prev;
        }
        return -1;
    }

    private T remove(Node<T> p) {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        size--;
        modCount++;

        return p.data;
    }

    public T get(int idx) {
        return getNode(idx).data;
    }

    public T set(int idx, T newVal) {
        Node<T> p = getNode(idx);
        T oldVal = p.data;

        p.data = newVal;
        return oldVal;
    }
    private Node<T> getNode(int idx) {
        return getNode(idx, 0, size() - 1);
    }

    private Node<T> getNode(int idx, int lower, int upper) {
       Node<T> p;
        if (idx < lower || idx > upper) {
            throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());
        }
        if (idx < size() / 2) {
            p = begin.next;
            for (int i = 0; i < idx; i++) {
                p = p.next;
            }
        } else {
            p = end;
            for (int i = size(); i > idx; i--) {
                p = p.prev;
            }
        }
        return p;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node<T> p = begin;
        while (p.next != end){
            if (p.next.data == o){
                return true;
            }
            p = p.next;
        }
        return false;
    }
    /**
     * Returns a String representation of this collection.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (T x : this){
            sb.append(x + " ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<T> x = begin; x != null; x = x.next)
        result[i++] = x.data;
       return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size)
        a = (T1[])Array.newInstance(a.getClass().getComponentType(), size);
        int i = 0;
        Object[] result = a;
        for (Node<T> x = begin; x != null; x = x.next)
        result[i++] = x.data;
        if (a.length > size)
        a[size] = null;
          return a;
    }
    /**
     * @return the iterator
     */
    public ListIterator<T> listIterator(){
       return new LinkedListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
      return new LinkedListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
            if(fromIndex<0 || fromIndex>size() || toIndex<fromIndex || toIndex>size()){
                throw new IndexOutOfBoundsException();
            }

        List<T> n= new MyLinkedList<T>();
        Node<T> startNode = begin;
            int counter=0;
            while(startNode!=null){
                if(counter>fromIndex && counter<toIndex){
                    n.add((T) startNode);
                }
                startNode=startNode.next;
                counter++;
            }
            return n;
    }

    /**
     * This is the doubly-linked list node.
     */
    public class Node<T> {
        public T data;
        public Node<T> prev;
        public Node<T> next;

        public Node(T d, Node<T> p, Node<T> n) {
            data = d;
            prev = p;
            next = n;
        }
    }

    private class LinkedListIterator implements ListIterator<T> {
        private Node<T> current = begin.next;
        private int nextIndex;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public LinkedListIterator(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException("Index: " + index +
                        ", Size: " + size);
            if (index < (size >> 1)) {
                current = begin.next;
                current = current.next;
            } else {
                current = begin;
                for (nextIndex = size; nextIndex > index; nextIndex--)
                    current = current.prev;
            }
        }

        public LinkedListIterator() {
        //    begin.next = begin.prev = begin;
        }

        @Override
        public boolean hasNext() {
            return current != end;
        }
        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            MyLinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove = false;
        }

        public boolean hasPrevious(){
            return current.equals(begin);
        }

        public T previous(){
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }
            T prevItem = current.prev.data;
            current = current.prev;
            okToRemove = true;
            return prevItem;
        }

        public void add(T e){
            Node<T> n = new Node<>(e,current.prev,current.next);
            current.prev.next = n;
            current.next.prev = n;
        }

        public void set(T e){
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (current.equals(begin) || current.equals(end)){
                throw new NoSuchElementException();
            }
            current.data = e;
        }

        public int previousIndex(){
            previous();
            return nextIndex-1;
        }

        public int nextIndex(){
            previous();
            return nextIndex;
        }
    }

    public void addFirst(T x){
        addBefore(begin.next, x);
        //size++;
    }

    public void addLast(T x){
        addBefore(end, x);
        //size++;
    }

    public T removeFirst(){
        return remove(begin.next);
    }
    public T removeLast(){
        return remove(end.prev);
    }
    public T getFirst(){
        return getNode(0,0,0).data;
    }
    public T getLast(){
        return getNode(size - 1,0, size -1).data;
    }
}


