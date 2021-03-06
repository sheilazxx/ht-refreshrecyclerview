/*
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.netease.hearttouch.htrefreshrecyclerview.viewimpl;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 垂直方向刷新的实现
 */
public abstract class HTVerticalRecyclerViewImpl extends HTBaseRecyclerViewImpl {
    public HTVerticalRecyclerViewImpl(Context context) {
        this(context, null);
    }

    public HTVerticalRecyclerViewImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HTVerticalRecyclerViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScrollJob = new VerticalDownScrollJob();
    }

    class VerticalDownScrollJob extends ScrollJob {

        void handleScroll(boolean isFinish) {
            int curY = mScroller.getCurrY();
            int deltaY = curY - mLastFlingXY;
            if (!isFinish) {
                mLastFlingXY = curY;
                updatePos(deltaY);
                post(this);
            } else {
                finish();
            }
        }

        void tryToScrollTo(int to, int duration) {
            if (mHTViewHolderTracker.isSamePos(to)) {
                return;
            }
            mStartPos = mHTViewHolderTracker.getCurrentPos();
            mTargetPos = to;
            int distance = to - mStartPos;

            removeCallbacks(this);

            mLastFlingXY = 0;

            if (!mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }
            mScroller.startScroll(0, 0, 0, distance, duration);
            post(this);
            mScrollRunning = true;
        }
    }

}
