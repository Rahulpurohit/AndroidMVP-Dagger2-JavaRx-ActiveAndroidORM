package prohit.androiddevelopertest.base;

import prohit.androiddevelopertest.model.response.TaskResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Rahul Purohit on 11/7/2016.
 */

public interface ApiService {

    @GET("u/6890301/tasks.json")
    Observable<TaskResponse> getTask();
}
