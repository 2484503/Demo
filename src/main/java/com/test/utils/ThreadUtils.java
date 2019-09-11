package com.test.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 多线程处理Utils
 *
 * @author lijn
 */
public class ThreadUtils {

    /**
     * 开启list.size个线程，运行实现Callable接口的内部类的方法，并获取线程返回值的Future
     */
    public static <T> List<Future<T>> startSubThread(List<Callable<T>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        int count = list.size();
        ExecutorService threadPool = Executors.newFixedThreadPool(count);
        // 获取返回值的resultList
        List<Future<T>> resultList = new ArrayList<Future<T>>(count);
        // 循环创建线程进行处理数据，将线程的返回数据按顺序放入list中
        for (Callable<T> model : list) {
            // 获取返回数据
            Future<T> future = threadPool.submit(model);
            resultList.add(future);
        }
        // 关闭线程池
        threadPool.shutdown();
        return resultList;
    }

    /**
     * @param model 实现Runnable接口
     * @param count 为开启线程数
     */
    public static void startExeThread(Runnable model, int count) {
        ExecutorService threadPool = Executors.newFixedThreadPool(count);
        // 循环创建线程进行处理数据，将线程的返回数据按顺序放入list中
        for (int x = 0; x < count; x++) {
            threadPool.execute(model);
        }
        // 关闭线程池
        threadPool.shutdown();
    }
}
