package prohit.androiddevelopertest.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import prohit.androiddevelopertest.R;
import prohit.androiddevelopertest.model.response.Task;
import prohit.androiddevelopertest.ui.MainActivity;


public class DataListFragment extends Fragment implements ActionMode.Callback, RecyclerView.OnItemTouchListener, DataRecyclerViewAdapter.OnItemClick {
    private ArrayList<Task> taskArrayList;
    private DataRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetector;
    private ActionMode actionMode;

    public DataListFragment() {
    }

    public static DataListFragment newInstance(ArrayList<Task> taskList) {
        DataListFragment fragment = new DataListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", taskList);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskArrayList = getArguments().getParcelableArrayList("list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addOnItemTouchListener(this);

            adapter = new DataRecyclerViewAdapter(taskArrayList, DataListFragment.this);
            recyclerView.setAdapter(adapter);

            gestureDetector =
                    new GestureDetectorCompat(getActivity(), new RecyclerViewDemoOnGestureListener());
        }
        return view;
    }


    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        String title = getString(
                R.string.selected_count,
                adapter.getSelectedItemCount());
        actionMode.setTitle(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_delete:
                List<Integer> selectedItemPositions = adapter.getSelectedItems();
                int currPos;
                for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                    currPos = selectedItemPositions.get(i);
                    final Task item = adapter.getItem(currPos);
                    item.setDeleted(true);
                    item.save();
                }
                actionMode.finish();
                adapter.setmListener(this);
                ((MainActivity) getActivity()).lockView(false);
                ((MainActivity) getActivity()).setupView();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.actionMode = null;
        adapter.clearSelections();
        adapter.setmListener(this);
        ((MainActivity) getActivity()).lockView(false);

    }


    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null)
                view.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        // item click
                        int idx = recyclerView.getChildAdapterPosition(view);
                        if (actionMode != null) {
                            myToggleSelection(idx);
                            return;
                        }

                    }
                });


            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            actionMode =
                    ((AppCompatActivity) getActivity()).startSupportActionMode(DataListFragment.this);
            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            adapter.setmListener(null);
            ((MainActivity) getActivity()).lockView(true);
            super.onLongPress(e);
        }
    }

    @Override
    public void onClick(Task item) {
        Task task = Task.load(Task.class, item.getId());

        ((MainActivity) getActivity()).showUndoBar(task);

    }

    @Override
    public void onLongClick(View view, Task item) {


    }

}
