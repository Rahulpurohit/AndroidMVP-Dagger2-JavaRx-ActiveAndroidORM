package prohit.androiddevelopertest.utill;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rahul Purohit on 11/8/2016.
 */

public class AppScheduler implements BaseScheduler {

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler backgroundThread() {
        return Schedulers.io();
    }
}
