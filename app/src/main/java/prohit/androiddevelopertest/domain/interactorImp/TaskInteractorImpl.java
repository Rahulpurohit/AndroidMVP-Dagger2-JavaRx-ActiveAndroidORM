package prohit.androiddevelopertest.domain.interactorImp;

import javax.inject.Inject;

import prohit.androiddevelopertest.base.ApiService;
import prohit.androiddevelopertest.domain.interactors.TaskInteractor;
import prohit.androiddevelopertest.model.response.TaskResponse;
import prohit.androiddevelopertest.utill.BaseScheduler;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Rahul Purohit on 11/8/2016.
 */

public class TaskInteractorImpl implements TaskInteractor {
    private ApiService apiService;
    private BaseScheduler schedulers;

    @Inject
    public TaskInteractorImpl(ApiService apiService, BaseScheduler baseScheduler) {
        this.apiService = apiService;
        this.schedulers = baseScheduler;
    }

    @Override
    public Subscription execute(Subscriber<TaskResponse> subscriber) {
        return apiService.getTask()
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void run() {

    }

    @Override
    public void cancel() {

    }
}
