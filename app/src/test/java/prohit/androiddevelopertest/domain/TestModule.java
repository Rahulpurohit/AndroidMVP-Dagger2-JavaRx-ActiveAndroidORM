package prohit.androiddevelopertest.domain;

import javax.inject.Singleton;

import dagger.Component;
import prohit.androiddevelopertest.domain.presenter.TaskPresenterTest;
import prohit.androiddevelopertest.module.ApplicationModule;
import prohit.androiddevelopertest.module.NetworkModule;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface TestModule {
    void inject(TaskPresenterTest taskPresenterTest);

}