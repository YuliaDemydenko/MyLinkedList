package com.company;

public class Main {

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        for (int i = 20; i < 30; i++) {
            list.add(0, i);
        }
        if (list.contains(25)){
            System.out.println("Item was found.");
        }
        else{
            System.out.println("Item not found");
        }

        list.addFirst(5);
        System.out.println(list);
        list.addLast(5);
        System.out.println(list);
        list.removeFirst();
        System.out.println(list);
        list.removeLast();
        System.out.println(list);
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        list.remove(0);
        list.remove(list.size() - 1);
        System.out.println(list);
        java.util.Iterator<Integer> itr2 = list.listIterator();
        //Testing the ListIterator
        while (itr2.hasNext()){
            itr2.next();
            itr2.remove();
            System.out.println(list);
        }

    }
}
