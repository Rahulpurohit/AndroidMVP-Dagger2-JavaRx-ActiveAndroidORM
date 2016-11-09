package prohit.androiddevelopertest.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import prohit.androiddevelopertest.base.MyApplication;
import prohit.androiddevelopertest.db.Preference;
import prohit.androiddevelopertest.domain.interactorImp.TaskInteractorImpl;
import prohit.androiddevelopertest.domain.interactors.TaskInteractor;
import prohit.androiddevelopertest.utill.AppScheduler;
import prohit.androiddevelopertest.utill.BaseScheduler;

/**
 * Created by Rahul Purohit.
 * This is base class to define application singleton Approach.
 */

@Module
public class ApplicationModule {

    private final SharedPreferences sharedPreferences;
    private final MyApplication mApp;
    private final Preference preference;

    public ApplicationModule(MyApplication app) {
        mApp = app;
        sharedPreferences = mApp.getSharedPreferences(Preference.PREF_NAME, Context.MODE_PRIVATE);
        preference = new Preference(sharedPreferences);
    }

    @Provides
    MyApplication application() {
        return mApp;
    }

    @Provides
    Preference providePreference() {
        return preference;
    }

    @Singleton
    @Provides
    BaseScheduler providesAppScheduler() {
        return new AppScheduler();
    }

    @Provides
    TaskInteractor providesTaskInteractor(TaskInteractorImpl taskInteractor) {
        return taskInteractor;
    }


}
