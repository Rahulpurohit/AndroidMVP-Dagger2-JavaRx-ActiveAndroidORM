package prohit.androiddevelopertest.utill;

import rx.Scheduler;

/**
 * Created by Rahul Purohit on 11/8/2016.
 */

public interface BaseScheduler {

    Scheduler mainThread();

    Scheduler backgroundThread();
}
