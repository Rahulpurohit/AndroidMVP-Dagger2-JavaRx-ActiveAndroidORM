package prohit.androiddevelopertest.domain.interactors;

import prohit.androiddevelopertest.model.response.TaskResponse;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Rahul Purohit on 11/7/2016.
 */

public interface TaskInteractor extends Interactor {


    Subscription execute(Subscriber<TaskResponse> subscriber);


}
