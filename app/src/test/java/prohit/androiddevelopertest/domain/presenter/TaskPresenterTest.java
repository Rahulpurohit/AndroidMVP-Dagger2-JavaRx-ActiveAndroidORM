package prohit.androiddevelopertest.domain.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import prohit.androiddevelopertest.base.ApiService;
import prohit.androiddevelopertest.base.MyApplication;
import prohit.androiddevelopertest.domain.interactorImp.TaskInteractorImpl;
import prohit.androiddevelopertest.domain.interactorImp.TestScheduler;
import prohit.androiddevelopertest.domain.views.BaseView;
import prohit.androiddevelopertest.model.response.TaskResponse;
import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Rahul Purohit on 11/9/2016.
 */
public class TaskPresenterTest {


    @Mock
    BaseView<TaskResponse> mockView;

    @Mock
    MyApplication myApplication;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void testPresenterWithMockService() {

        ApiService userService = mock(ApiService.class);
        TaskPresenter presenter = new TaskPresenter(new TaskInteractorImpl(userService, new TestScheduler()));
        when(userService.getTask()).thenReturn(Observable.create(new Observable.OnSubscribe<TaskResponse>() {
            @Override
            public void call(Subscriber<? super TaskResponse> subscriber) {
                TaskResponse taskResponse = new TaskResponse();
                subscriber.onNext(taskResponse);
                subscriber.onCompleted();
            }
        }));

        presenter.setView(mockView);
        presenter.start();

        verify(mockView, times(1)).setLoading(true);
        verify(mockView, times(1)).setLoading(false);
        verify(mockView, times(1)).setModel(any(TaskResponse.class));
        verify(mockView, never()).error(any(Throwable.class));
    }


}