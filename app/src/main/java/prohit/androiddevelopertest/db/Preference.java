package prohit.androiddevelopertest.db;

import android.content.SharedPreferences;

import javax.inject.Inject;

import prohit.androiddevelopertest.base.MyApplication;

/**
 * Created by Rahul Purohit on 21-01-2016.
 */
public class Preference {
    public final static String PREF_NAME = "com.test.preferences";
    private final SharedPreferences mPref;
    @Inject
    MyApplication mContext;
    private SharedPreferences.Editor mEditor = null;

    @Inject
    public Preference(SharedPreferences mPref) {
        this.mPref = mPref;
    }

    public void add(String key, String value) {
        mEditor = mPref.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void add(String key, Integer value) {
        mEditor = mPref.edit();
        mEditor.putInt(key, value);
        mEditor.commit();

    }

    public void add(String key, Long value) {
        mEditor = mPref.edit();
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void add(String key, boolean value) {
        mEditor = mPref.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void clear(String key) {
        mEditor = mPref.edit();
        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * Default value is null
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return mPref.getString(key, null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public void addBoolean(String key, Boolean value) {
        mEditor = mPref.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public Long getLong(String key, Long defaultValue) {
        return mPref.getLong(key, defaultValue);
    }
}
