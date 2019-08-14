package cycu.nclab.moocs2018.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cycu.nclab.moocs2018.R;
import cycu.nclab.moocs2018.recyclerview.AccountListFragment.OnListFragmentInteractionListener;
import cycu.nclab.moocs2018.room.MoneyEntity;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MoneyEntity} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<MoneyEntity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<MoneyEntity> accounts, OnListFragmentInteractionListener listener) {
        mValues = accounts;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_account_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mAccount = mValues.get(position);
        holder.mCategory.setText(mValues.get(position).getCategory());
        holder.mItem.setText(mValues.get(position).getItem());
        holder.mPrice.setText(String.valueOf(mValues.get(position).getPrice()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mAccount);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCategory;
        public final TextView mItem;
        public final TextView mPrice;
        public MoneyEntity mAccount;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCategory = (TextView) view.findViewById(R.id.categoryLabel);
            mItem = (TextView) view.findViewById(R.id.subCategory);
            mPrice = (TextView) view.findViewById(R.id.itemCost);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPrice.getText() + "'";
        }
    }
}
