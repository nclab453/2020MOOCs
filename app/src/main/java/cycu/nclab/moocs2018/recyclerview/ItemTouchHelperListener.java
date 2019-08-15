package cycu.nclab.moocs2018.recyclerview;

public interface ItemTouchHelperListener {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}