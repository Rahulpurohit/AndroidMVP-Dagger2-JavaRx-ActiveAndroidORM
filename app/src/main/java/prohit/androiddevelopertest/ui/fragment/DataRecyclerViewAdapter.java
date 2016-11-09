package prohit.androiddevelopertest.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import prohit.androiddevelopertest.R;
import prohit.androiddevelopertest.model.response.Task;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link OnItemClick}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DataRecyclerViewAdapter extends RecyclerView.Adapter<DataRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;

    public OnItemClick getmListener() {
        return mListener;
    }

    public void setmListener(OnItemClick mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClick {
        /**
         * Change Status of Task
         *
         * @param item
         */
        void onClick(Task item);

        void onLongClick(View view, Task item);

    }

    private OnItemClick mListener;

    private SparseBooleanArray selectedItems;

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public DataRecyclerViewAdapter(List<Task> items, OnItemClick listener) {
        mValues = items;
        mListener = listener;
        selectedItems = new SparseBooleanArray();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getName());
        holder.itemView.setActivated(selectedItems.get(position, false));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClick(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onLongClick(view, holder.mItem);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public Task getItem(int idx) {
        return mValues.get(idx);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
