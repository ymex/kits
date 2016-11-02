package cn.ymex.cute.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.SoundEffectConstants;
import android.view.View;

import cn.ymex.cute.R;


public class RecyclerItemClickSupport {
    /**
     * Interface definition for a callback to be invoked when an item in the
     * RecyclerView has been clicked.
     */
    public interface OnItemClickListener {
        /**
         * Callback method to be invoked when an item in the RecyclerView
         * has been clicked.
         *
         * @param parent The RecyclerView where the click happened.
         * @param view The view within the RecyclerView that was clicked
         * @param position The position of the view in the adapter.
         * @param id The row id of the item that was clicked.
         */
        void onItemClick(RecyclerView parent, View view, int position, long id);
    }

    /**
     * Interface definition for a callback to be invoked when an item in the
     * RecyclerView has been clicked and held.
     */
    public interface OnItemLongClickListener {
        /**
         * Callback method to be invoked when an item in the RecyclerView
         * has been clicked and held.
         *
         * @param parent The RecyclerView where the click happened
         * @param view The view within the RecyclerView that was clicked
         * @param position The position of the view in the list
         * @param id The row id of the item that was clicked
         *
         * @return true if the callback consumed the long click, false otherwise
         */
        boolean onItemLongClick(RecyclerView parent, View view, int position, long id);
    }

    private static final int tag = R.id.cute_recyclerview_click_tag;

    private final RecyclerView mRecyclerView;
    private final TouchListener mTouchListener;

    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    private RecyclerItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;

        mTouchListener = new TouchListener(recyclerView);
        recyclerView.addOnItemTouchListener(mTouchListener);
    }

    /**
     * Register a callback to be invoked when an item in the
     * RecyclerView has been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * Register a callback to be invoked when an item in the
     * RecyclerView has been clicked and held.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        if (!mRecyclerView.isLongClickable()) {
            mRecyclerView.setLongClickable(true);
        }

        mItemLongClickListener = listener;
    }

    public static RecyclerItemClickSupport addTo(RecyclerView recyclerView) {
        RecyclerItemClickSupport recyclerItemClickSupport = from(recyclerView);
        if (recyclerItemClickSupport == null) {
            recyclerItemClickSupport = new RecyclerItemClickSupport(recyclerView);
            recyclerView.setTag(tag, recyclerItemClickSupport);
        } else {
            // TODO: Log warning
        }

        return recyclerItemClickSupport;
    }

    public static void removeFrom(RecyclerView recyclerView) {
        final RecyclerItemClickSupport recyclerItemClickSupport = from(recyclerView);
        if (recyclerItemClickSupport == null) {
            // TODO: Log warning
            return;
        }

        recyclerView.removeOnItemTouchListener(recyclerItemClickSupport.mTouchListener);
        recyclerView.setTag(tag, null);
    }

    public static RecyclerItemClickSupport from(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return null;
        }

        return (RecyclerItemClickSupport) recyclerView.getTag(tag);
    }

    private class TouchListener extends ClickItemTouchListener {
        TouchListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public boolean performItemClick(RecyclerView parent, View view, int position, long id) {
            if (mItemClickListener != null) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                mItemClickListener.onItemClick(parent, view, position, id);
                return true;
            }

            return false;
        }

        @Override
        public boolean performItemLongClick(RecyclerView parent, View view, int position, long id) {
            if (mItemLongClickListener != null) {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return mItemLongClickListener.onItemLongClick(parent, view, position, id);
            }

            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
