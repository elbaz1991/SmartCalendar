package fr.amu.univ.smartcalendar.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by j.Katende on 05/05/2017.
 */

public class SmartCalendarThreadManager {
    private static SmartCalendarThreadManager instance = null;

    /**
     * Sets the amount of time an idle thread will wait for a task before terminating
     */
    private static final int KEEP_ALIVE_TIME = 1;

    /**
     * Sets the time units to seconds
     */
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * Sets the initial thread pool size to 8
     */
    private static final int CORE_POOL_SIZE = 8;

    /**
     * Sets the maximum thread pool size
     */
    private static final int MAXIMUM_POOL_SIZE = 8;

    /**
     * Gets the number of available cores
     */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    /**
     * A queue of runnable related file download process
     */
    private final BlockingQueue<Runnable> downloadWorkQueue;

    /**
     * A queue of runnable for background process
     */
    private final BlockingQueue<Runnable> processWorkQueue;

    static {
        instance = new SmartCalendarThreadManager();
    }

    /**
     * Constructs the work queues an thread pools for download and process
     */
    private SmartCalendarThreadManager(){
        downloadWorkQueue = new LinkedBlockingDeque<>();

        processWorkQueue = new LinkedBlockingDeque<>();
    }

    public static void cancelAll(){
        /*
         * Creates an array of all waiting runnable in thread pool
         */
        Runnable[] runnableArray = new Runnable[instance.processWorkQueue.size()];
        instance.processWorkQueue.toArray(runnableArray);

        int len = runnableArray.length;

        synchronized(instance){
            for(int runIndex = 0; runIndex < len; runIndex++){
                /*Thread thread = runnableArray[runIndex].mThread;
                if(thread != null){
                    thread.interrupt();
                }*/
            }
        }
    }
}
