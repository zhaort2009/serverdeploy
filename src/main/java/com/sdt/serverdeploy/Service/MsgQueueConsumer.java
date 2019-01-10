package com.sdt.serverdeploy.Service;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class MsgQueueConsumer {
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public void doWork(Runnable task){
        mExecutor.execute(task);
    }

    public String doCallableWork(Callable task){
        Future<String> mFuture = mExecutor.submit(task);
        try {
            return mFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
