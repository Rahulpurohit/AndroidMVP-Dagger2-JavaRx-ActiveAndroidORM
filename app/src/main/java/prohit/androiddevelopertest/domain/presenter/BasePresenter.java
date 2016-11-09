package prohit.androiddevelopertest.domain.presenter;

import prohit.androiddevelopertest.domain.views.BaseView;

/**
 * Created by Rahul Purohit on 11/8/2016.
 */

public interface BasePresenter<T>  {


    void start();

    void finish();

    void setView(BaseView<T> view);
}
