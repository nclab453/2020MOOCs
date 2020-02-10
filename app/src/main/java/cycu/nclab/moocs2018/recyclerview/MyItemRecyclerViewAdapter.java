package cycu.nclab.moocs2018.recyclerview;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cycu.nclab.moocs2018.R;
import cycu.nclab.moocs2018.recyclerview.AccountListFragment.OnListFragmentInteractionListener;
import cycu.nclab.moocs2018.room.DB_r;
import cycu.nclab.moocs2018.room.MoneyEntity;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MoneyEntity} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperListener {

    final String TAG = this.getClass().getSimpleName();

    Cursor mCursor;
    Context mContext;

    public MyItemRecyclerViewAdapter(Cursor cursor, Context context) {
        mCursor = cursor;
        mContext = context;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getInt(0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_account_cell, parent, false);
        view.setOnClickListener(mItemAction);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCategory.setText(mCursor.getString(1));
        holder.mItem.setText(mCursor.getString(2));
        holder.mPrice.setText(String.valueOf(mCursor.getDouble(3)));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {
        if(!mCursor.moveToPosition(position)) {
            return;
        }
        int id = mCursor.getInt(0);
        DB_r.deleteByID(mContext, id);
        mCursor = DB_r.getAll(mContext);

        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCategory;
        public final TextView mItem;
        public final TextView mPrice;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCategory = (TextView) view.findViewById(R.id.categoryLabel);
            mItem = (TextView) view.findViewById(R.id.subCategory);
            mPrice = (TextView) view.findViewById(R.id.itemCost);
        }

        @Override
        public String toString() {
            return "mPrice = '" + mPrice.getText() + "'";
        }
    }

    private View.OnClickListener mItemAction = new View.OnClickListener() {

        boolean qq = true;
        @Override
        public void onClick(View view) {
            Log.d(TAG, view.toString());
            if (qq) {
                view.animate().rotation(360);
                qq = false;
            }
            else {
                view.animate().rotation(0);
                qq = true;
            }
        }
    };
}
