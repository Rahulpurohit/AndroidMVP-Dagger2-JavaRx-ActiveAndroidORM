package prohit.androiddevelopertest.domain.interactorImp;

import prohit.androiddevelopertest.utill.BaseScheduler;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Rahul Purohit on 11/9/2016.
 */

public class TestScheduler implements BaseScheduler {
    @Override
    public Scheduler mainThread() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler backgroundThread() {
        return Schedulers.immediate();
    }
}