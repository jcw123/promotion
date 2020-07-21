package com.im.sky.javacore.threads;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author jiangchangwei
 * @date 2020-5-15 下午 4:34
 *
 * 串行执行，好巧妙
 **/
public class SerialExecutor implements Executor {

    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(2);
        SerialExecutor serialExecutor = new SerialExecutor(executor);
        for(int i = 0; i < 10; i++) {
            int finalI = i;
            serialExecutor.execute(() -> {
                System.out.println(finalI);
            });
        }
    }

    final Queue<Runnable> tasks = new ArrayDeque<>();

    final Executor executor;

    Runnable active;

    public SerialExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public synchronized void execute(Runnable command) {
        tasks.offer(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                }finally {
                    scheduleNext();
                }
            }
        });
        if(active == null) {
            scheduleNext();
        }
    }


    protected synchronized void scheduleNext() {
        if((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }
}
