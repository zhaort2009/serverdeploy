package com.sdt.serverdeploy.Service;

import com.sdt.serverdeploy.Utility.MsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MsgQueueService {


    private LinkedBlockingQueue<String> mMsgQueue;

    private MsgQueueConsumer mMsgConsumer;

    public MsgQueueService(){
        System.out.println("mQueue initial");
        this.mMsgQueue = new LinkedBlockingQueue<>();
    }

    @Autowired
    public void setMsgQueueConsumer(MsgQueueConsumer myConsumer){
        this.mMsgConsumer = myConsumer;
        //this.startService();
    }

    public void pushQueue(String msg){
        try {
            System.out.println("Push:"+msg);
            mMsgQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String popQueue(){
        try {
            System.out.println("Pop");
            return mMsgQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getAllMsg(){
        Iterator<String> iterator = mMsgQueue.iterator();
        StringBuffer sb = new StringBuffer();
        while(iterator.hasNext()) {
            sb.append(iterator.next() + "@");
        }
        return sb.toString().substring(0,sb.length()-1);
    }
    public int getSize(){
        return mMsgQueue.size();
    }

    public String doOneCmd(){
        String result =  mMsgConsumer.doCallableWork(new Callable() {
            @Override
            public Object call() throws Exception {
                String message;
                String ret = "";
                if (getSize() > 0) {
                    message = popQueue();
                    MsgHandler msgHandler = new MsgHandler(message);
                    ret = msgHandler.dealMsg();

                    System.out.println(Thread.currentThread().getName()+":"+message+" :ret="+ret);
                }
                return ret;
            }
        });
        return result;
    }


    public void startService(){
        mMsgConsumer.doWork(new Runnable() {
            @Override
            public void run() {
                String message;
                while(true) {
                    if (getSize() > 0) {
                        message = popQueue();
                        MsgHandler msgHandler = new MsgHandler(message);
                        msgHandler.dealMsg();
                        System.out.println(Thread.currentThread().getName()+":"+message);
                    }
                }
            }
        });
    }


}
