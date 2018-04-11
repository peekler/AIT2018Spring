package hu.ait.android.todorecylerview.touch;

public interface TodoTouchHelperAdapter {

    void onItemDismiss(int position);

    void onItemMove(int fromPosition, int toPosition);
}
