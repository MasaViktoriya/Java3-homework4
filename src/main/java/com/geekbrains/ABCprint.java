package com.geekbrains;

public class ABCprint {

    private final Object monitor =  new Object();

    private String  firstLetter = "A";
    private String  nextLetter = "B";
    private String  lastLetter = "C";
    private String currentLetter = firstLetter;

    public static void main(String[] args) {
        ABCprint abcPrint = new ABCprint();

        Thread threadA = new Thread(() -> abcPrint.printLetter("A"));
        Thread threadB = new Thread(() -> abcPrint.printLetter("B"));
        Thread threadC = new Thread(() -> abcPrint.printLetter("C"));
        threadA.start();
        threadB.start();
        threadC.start();
    }

    private void printLetter(String letter){
        synchronized (monitor){
            try {
                for (int i = 0; i < 5; i++) {
                    while (!currentLetter.equals(letter)){
                        monitor.wait();
                    }
                    System.out.print(letter);
                    currentLetter = nextLetter;
                    nextLetter = lastLetter;
                    lastLetter = firstLetter;
                    firstLetter = currentLetter;
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
