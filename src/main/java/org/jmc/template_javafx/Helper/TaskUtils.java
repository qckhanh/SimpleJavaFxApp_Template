package org.jmc.template_javafx.Helper;

import javafx.concurrent.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskUtils {
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static <T> Task<T> createTask(Callable<T> callable) {
        return new Task<>() {
            @Override
            protected T call() throws Exception {
                return callable.call(); // Execute the passed method and return the result
            }
        };
    }

    public static <T> void run(Task<T> tTask){
        Thread backgroundThread = new Thread(tTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }


    // Shutdown the executor service
    public static void shutdown() {
        executor.shutdown();
    }


    public static <T> void countDown(CountDownLatch latch, Callable<T> callable) {
        new Thread(() -> {
            try {
                latch.await();  // Wait for the latch to reach zero
                callable.call(); // Execute the passed callable
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
