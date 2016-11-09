package prohit.androiddevelopertest.domain.presenter;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.List;

import javax.inject.Inject;

import prohit.androiddevelopertest.domain.interactors.TaskInteractor;
import prohit.androiddevelopertest.domain.views.BaseView;
import prohit.androiddevelopertest.model.response.Task;
import prohit.androiddevelopertest.model.response.TaskResponse;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Rahul Purohit on 11/8/2016.
 */

public class TaskPresenter implements BasePresenter<TaskResponse> {
    private Subscription subscription = Subscriptions.empty();

    TaskInteractor taskInteractor;
    private BaseView<TaskResponse> view;

    @Inject
    public TaskPresenter(TaskInteractor taskInteractor) {
        this.taskInteractor = taskInteractor;
    }

    @Override
    public void start() {
        if (view != null) {
            subscription = taskInteractor.execute(getSubscriber());
        }
    }

    @Override
    public void finish() {
        subscription.unsubscribe();
        this.view = null;
    }

    @Override
    public void setView(BaseView<TaskResponse> view) {
        this.view = view;
    }


    private Subscriber<TaskResponse> getSubscriber() {
        return new Subscriber<TaskResponse>() {
            @Override
            public void onStart() {
                super.onStart();
                view.setLoading(true);
            }

            @Override
            public void onCompleted() {
                view.setLoading(false);
            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.setLoading(false);
                    view.error(e);
                }
            }

            @Override
            public void onNext(TaskResponse response) {
                ActiveAndroid.beginTransaction();
                try {
                    final List<Task> taskList = response.getTask();
                    for (int i = 0; i < taskList.size(); i++) {
                        Task item = taskList.get(i);
                        item.setLocal(false);
                        try {
                            Task task = new Select()
                                    .from(Task.class)
                                    .where("SID = ?", item.getsId())
                                    .orderBy("RANDOM()")
                                    .executeSingle();
                            if (task != null) {
                                task.setLocal(false);
                                task.setName(item.getName());
                                task.setsId(item.getsId());
                                if (!item.isModified())
                                    task.setState(item.getState());
                                task.save();
                            } else {
                                item.save();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            item.save();

                        }//1 is the id

                    }
                    ActiveAndroid.setTransactionSuccessful();
                } catch (Exception ex) {

                } finally {
                    ActiveAndroid.endTransaction();
                }
                if (view != null) {

                    view.setModel(response);
                }
            }
        };
    }
}
