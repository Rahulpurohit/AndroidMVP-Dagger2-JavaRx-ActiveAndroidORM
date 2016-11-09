package prohit.androiddevelopertest.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import prohit.androiddevelopertest.R;
import prohit.androiddevelopertest.base.MyApplication;
import prohit.androiddevelopertest.domain.presenter.TaskPresenter;
import prohit.androiddevelopertest.domain.views.BaseView;
import prohit.androiddevelopertest.model.response.Task;
import prohit.androiddevelopertest.model.response.TaskResponse;
import prohit.androiddevelopertest.ui.fragment.DataListFragment;

public class MainActivity extends AppCompatActivity implements BaseView<TaskResponse> {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab)
    FloatingActionButton mFabBtn;

    @BindView(R.id.tabs)
    TabLayout mTabs;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.progressbar)
    View mLoadView;
    @BindView(R.id.swipelayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.txtnoitem)
    TextView mTxtNoItem;
    @Inject
    TaskPresenter mTaskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);

        MyApplication.getApplication().getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mTaskPresenter.setView(this);
        mTaskPresenter.start();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTaskPresenter.setView(MainActivity.this);
                mTaskPresenter.start();
            }
        });
        //Init Local Data
        setupView();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTaskPresenter.finish();
    }

    @Override
    public void setLoading(boolean isLoading) {
        mLoadView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mSwipeLayout.setRefreshing(false);
        if (isLoading)
            onNoTaskFound(!isLoading);
        else {
            int totalTask = getTaskList(0).size() + getTaskList(1).size();
            onNoTaskFound(totalTask == 0);
        }
    }

    @Override
    public void setModel(TaskResponse object) {
        setupView();
    }

    ViewPagerAdapter adapter;

    public void setupView() {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            int totalTask = getTaskList(0).size() + getTaskList(1).size();
            onNoTaskFound(totalTask == 0);
        }


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    actMenu.setVisible(true);
                else
                    actMenu.setVisible(false);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabs.setupWithViewPager(mViewPager, true);
    }

    @Override
    public void error(Throwable t) {
        setupView();

    }

    public void showUndoBar(final Task task) {
        task.setState(task.getState() == 1 ? 0 : 1);
        task.setModified(true);
        task.save();
        setupView();
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator), "Task " + task.getName() + " is Changed Status", Snackbar.LENGTH_LONG)
                .setAction("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        task.setState(task.getState() == 1 ? 0 : 1);
                        task.setModified(true);
                        task.save();
                        Snackbar snackbar1 = Snackbar.make(findViewById(R.id.coordinator), "Task " + task.getName() + " is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        setupView();
                    }
                });

        snackbar.show();
    }

    public void lockView(final boolean b) {
        mToolbar.setVisibility(b ? View.GONE : View.VISIBLE);
        mSwipeLayout.setEnabled(!b);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return b;
            }
        });
        LinearLayout tabStrip = ((LinearLayout) mTabs.getChildAt(0));
        tabStrip.setEnabled(!b);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(!b);
        }
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        @Override
        public int getItemPosition(Object object) {

            return POSITION_NONE;
        }

        List<Task> taskList;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);


        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Pending" : "Done";
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    taskList = getTaskList(0);
                    return DataListFragment.newInstance((ArrayList<Task>) taskList);
                }
                case 1: {
                    taskList = getTaskList(1);
                    return DataListFragment.newInstance((ArrayList<Task>) taskList);
                }
            }
            return DataListFragment.newInstance((ArrayList<Task>) taskList);

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private List<Task> getTaskList(int state) {
        return new Select()
                .from(Task.class)
                .where("State = ? and IsDeleted=?", state, 0)
                .orderBy("Name ASC")
                .execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);//Menu Resource, Menu
        actMenu = menu.getItem(0);

        return true;
    }

    MenuItem actMenu;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.actionmenu:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_new_task_pending, null);
                final EditText taskName = (EditText) view.findViewById(R.id.taskname);
                builder.setTitle(R.string.add_new);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String name = taskName.getText().toString();
                        if (TextUtils.isEmpty(name)) {
                            taskName.setError(getString(R.string.pls_enter_name));
                        } else
                            try {
                                Task task = new Task();
                                task.setLocal(true);
                                task.setName(name);
                                task.setState(0);
                                task.setModified(true);
                                task.setsId(-1);
                                task.save();
                                setupView();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                    }
                });
                builder.setView(view);
                builder.create().show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onNoTaskFound(boolean b) {
        mTxtNoItem.setVisibility(b ? View.VISIBLE : View.GONE);
    }
}
