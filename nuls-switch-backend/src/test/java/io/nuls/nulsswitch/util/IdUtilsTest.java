package io.nuls.nulsswitch.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qinyifeng
 * @date 2019/7/20 10:39
 */
public class IdUtilsTest {



    @Test
    public void getIncreaseIdByNanoTime() throws InterruptedException {
        Map idMap = new ConcurrentHashMap();
        ExecutorService exec = Executors.newFixedThreadPool(100);
        for(int i=0;i<100;i++){
            exec.execute(new Thread(){
                public void run() {
                    for(int i=0;i<1000;i++) {
                        String id = IdUtils.getIncreaseIdByNanoTime();
                        idMap.put(id, id);
                    }
                }
            });
        }
        exec.shutdown();
        Thread.sleep(2000);
        System.out.println(idMap.size());
        Assert.assertTrue(idMap.size() == 100000);
    }
}