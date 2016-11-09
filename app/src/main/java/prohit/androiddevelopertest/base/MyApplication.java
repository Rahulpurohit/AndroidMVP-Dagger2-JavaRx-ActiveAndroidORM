package prohit.androiddevelopertest.base;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import prohit.androiddevelopertest.component.ApplicationComponent;
import prohit.androiddevelopertest.component.DaggerApplicationComponent;
import prohit.androiddevelopertest.module.ApplicationModule;
import prohit.androiddevelopertest.module.NetworkModule;

/**
 * Created by Rahul Purohit on 11/7/2016.
 */

public class MyApplication extends Application {
    private static MyApplication myApp;
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
        ActiveAndroid.initialize(this);
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).networkModule(new NetworkModule(this))
                .build();
    }
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static MyApplication getApplication() {
        return myApp;
    }

    private static void setApplication(MyApplication context) {
        MyApplication.myApp = context;
    }


}
