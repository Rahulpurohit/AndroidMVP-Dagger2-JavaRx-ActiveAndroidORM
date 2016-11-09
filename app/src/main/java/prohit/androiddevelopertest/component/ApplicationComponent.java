package prohit.androiddevelopertest.component;


import javax.inject.Singleton;

import dagger.Component;
import prohit.androiddevelopertest.module.ApplicationModule;
import prohit.androiddevelopertest.module.NetworkModule;
import prohit.androiddevelopertest.ui.MainActivity;


/**
 * Created by Rahul Purohit on 6/21/2016.
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(MainActivity activity);

}
