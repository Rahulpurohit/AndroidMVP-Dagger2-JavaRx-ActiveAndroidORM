package prohit.androiddevelopertest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Purohit on 11/7/2016.
 */

public class TaskResponse {
    @SerializedName("data")
    @Expose
    private List<Task> task = new ArrayList<Task>();

    /**
     * @return The task
     */
    public List<Task> getTask() {
        return task;
    }

    /**
     * @param task The task
     */
    public void setTask(List<Task> task) {
        this.task = task;
    }
}
