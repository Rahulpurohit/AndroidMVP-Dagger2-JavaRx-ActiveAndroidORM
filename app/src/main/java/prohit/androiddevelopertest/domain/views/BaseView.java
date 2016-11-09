package prohit.androiddevelopertest.domain.views;

/**
 * Created by Rahul Purohit on 11/8/2016.
 */

public interface BaseView<T> {

    void setLoading(boolean isLoading);

    void setModel(T object);

    void error(Throwable t);
}
