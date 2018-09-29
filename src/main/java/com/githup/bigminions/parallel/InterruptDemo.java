package com.githup.bigminions.parallel;

public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {

        Runnable task = () -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Current Thread is interrupted " + Thread.currentThread().getName());
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted when sleep " + Thread.currentThread().getName() + " currentInterrupted = " + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                }
                Thread.yield();
            }
        };

        Thread thread = new Thread(task, "Demo-1");

        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
