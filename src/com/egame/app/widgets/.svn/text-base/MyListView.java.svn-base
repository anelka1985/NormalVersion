package com.egame.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.egame.R;
import com.egame.utils.common.L;

/**
 * @desc 拖动加载的listview
 * 
 * @Copyright lenovo
 * 
 * @Project EGame4th
 * 
 * @Author zhangrh@lenovo-cw.com
 * 
 * @timer 2012-2-20
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class MyListView extends ListView implements OnScrollListener {

	private static final String TAG = "listview";
	/** 所有内容加载完毕 */
	private final static int FOOT_DISMISS = 9;
	private final static int FOOT_RELEASE_To_REFRESH = 10;
	private final static int FOOT_PULL_To_REFRESH = 11;
	private final static int FOOT_REFRESHING = 12;
	private final static int FOOT_DONE = 13;

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	private LayoutInflater inflater;

	private LinearLayout footView;

	private TextView footTipsTextview;
	// private TextView footLastUpdatedTextView;
	private ImageView footArrowImageView;
	private ProgressBar footProgressBar;

	private RotateAnimation headAnimation;
	private RotateAnimation headReverseAnimation;

	private RotateAnimation footAnimation;
	private RotateAnimation footReverseAnimation;

	private boolean footIsRecored;

	private int footContentHeight;

	private int footStartY;
	private int footFirstItemIndex;

	private int footState;

	private boolean footIsBack;

	private OnRefreshListener refreshListener;

	public MyListView(Context context) {
		super(context);
		init(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		inflater = LayoutInflater.from(context);

		footView = (LinearLayout) inflater.inflate(R.layout.egame_foot, null);

		footArrowImageView = (ImageView) footView.findViewById(R.id.foot_arrowImageView);
		float mDensity = context.getApplicationContext().getResources().getDisplayMetrics().density;
		footArrowImageView.setMinimumWidth(Math.round(46 * mDensity));
		footArrowImageView.setMinimumHeight(Math.round(66 * mDensity));
		footProgressBar = (ProgressBar) footView.findViewById(R.id.foot_progressBar);
		footTipsTextview = (TextView) footView.findViewById(R.id.foot_tipsTextView);
		// footLastUpdatedTextView = (TextView)
		// footView.findViewById(R.id.foot_lastUpdatedTextView);

		measureView(footView);
		footContentHeight = footView.getMeasuredHeight();

		footView.setPadding(0, 0, 0, -footContentHeight);
		footView.invalidate();
		addFooter();
		// addFooterView(footView, null, false);
		setOnScrollListener(this);

		headAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		headAnimation.setInterpolator(new LinearInterpolator());
		headAnimation.setDuration(250);
		headAnimation.setFillAfter(true);

		headReverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		headReverseAnimation.setInterpolator(new LinearInterpolator());
		headReverseAnimation.setDuration(200);
		headReverseAnimation.setFillAfter(true);

		footAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		footAnimation.setInterpolator(new LinearInterpolator());
		footAnimation.setDuration(250);
		footAnimation.setFillAfter(true);

		footReverseAnimation = new RotateAnimation(0, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		footReverseAnimation.setInterpolator(new LinearInterpolator());
		footReverseAnimation.setDuration(200);
		footReverseAnimation.setFillAfter(true);

		footState = FOOT_DONE;
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
		footFirstItemIndex = firstVisiableItem + visibleItemCount;
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}

	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (footFirstItemIndex == getCount() && !footIsRecored) {
				footIsRecored = true;
				footStartY = (int) event.getY();
				L.d(TAG, "在down时候记录当前位置‘");
			}
			break;
		case MotionEvent.ACTION_UP:
			if (footState != FOOT_REFRESHING) {
				if (footState == FOOT_DONE) {
					// 什么都不做
				}
				if (footState == FOOT_PULL_To_REFRESH) {
					footState = FOOT_DONE;
					changeFooterViewByState();
					L.d(TAG, "由上拉加载更多状态，到done状态");
				}
				if (footState == FOOT_RELEASE_To_REFRESH) {
					footState = FOOT_REFRESHING;
					changeFooterViewByState();
					onFootRefresh();
					L.d(TAG, "由松开加载更多状态，到done状态");
				}
			}
			footIsRecored = false;
			footIsBack = false;
			break;
		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			if (!footIsRecored && footFirstItemIndex == getCount()) {
				L.d(TAG, "在move时候记录下位置");
				footIsRecored = true;
				footStartY = tempY;
			}
			if (footState != FOOT_REFRESHING && footIsRecored) {
				// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
				// 可以松手去加载更多了
				if (footState == FOOT_RELEASE_To_REFRESH) {
					// setSelection(headFirstItemIndex);
					setSelection(getCount() - 1);
					// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
					if (((footStartY - tempY) / RATIO < footContentHeight) && (footStartY - tempY) > 0) {
						footState = FOOT_PULL_To_REFRESH;
						changeFooterViewByState();
						L.d(TAG, "由松开加载更多状态转变到上拉加载更多状态");
					}
					// 一下子推到顶了
					else if (footStartY - tempY <= 0) {
						footState = FOOT_DONE;
						changeFooterViewByState();
						L.d(TAG, "由松开加载更多状态转变到done状态");
					}
					// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
					else {
						// 不用进行特别的操作，只用更新paddingTop的值就行了
					}
				}
				// 还没有到达显示松开加载更多的时候,DONE或者是PULL_To_REFRESH状态
				if (footState == FOOT_PULL_To_REFRESH) {
					setSelection(getCount() - 1);
					// 下拉到可以进入RELEASE_TO_REFRESH的状态
					if ((footStartY - tempY) / RATIO >= footContentHeight) {
						footState = FOOT_RELEASE_To_REFRESH;
						footIsBack = true;
						changeFooterViewByState();
						L.d(TAG, "由done或者上拉加载更多状态转变到松开加载更多");
					}
					// 上推到顶了
					else if (footStartY - tempY <= 0) {
						footState = FOOT_DONE;
						changeFooterViewByState();
						L.d(TAG, "由DOne或者上拉加载更多状态转变到done状态");
					}
				}
				// done状态下
				if (footState == FOOT_DONE) {
					if (footStartY - tempY > 0) {
						footState = FOOT_PULL_To_REFRESH;
						changeFooterViewByState();
					}
				}
				// 更新headView的size
				if (footState == FOOT_PULL_To_REFRESH) {
					footView.setPadding(0, 0, 0, (footStartY - tempY) / RATIO - footContentHeight);
				}
				// 更新headView的paddingTop
				if (footState == FOOT_RELEASE_To_REFRESH) {
					footView.setPadding(0, 0, 0, (footStartY - tempY) / RATIO - footContentHeight);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	public void addFooter() {
		addFooterView(footView, null, false);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeFooterViewByState() {
		switch (footState) {
		case FOOT_DISMISS:
			this.removeFooterView(footView);
			// footView.setVisibility(View.GONE);
		case FOOT_RELEASE_To_REFRESH:
			footArrowImageView.setVisibility(View.VISIBLE);
			footProgressBar.setVisibility(View.GONE);
			footTipsTextview.setVisibility(View.VISIBLE);
			// footLastUpdatedTextView.setVisibility(View.VISIBLE);
			footArrowImageView.clearAnimation();
			footArrowImageView.startAnimation(footAnimation);
			footTipsTextview.setText("松开加载更多");
			L.d(TAG, "当前状态，松开加载更多");
			break;
		case FOOT_PULL_To_REFRESH:
			footProgressBar.setVisibility(View.GONE);
			footTipsTextview.setVisibility(View.VISIBLE);
			// footLastUpdatedTextView.setVisibility(View.VISIBLE);
			footArrowImageView.clearAnimation();
			footArrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (footIsBack) {
				footIsBack = false;
				footArrowImageView.clearAnimation();
				footArrowImageView.startAnimation(footReverseAnimation);
				footTipsTextview.setText("上拉加载更多");
			} else {
				footTipsTextview.setText("上拉加载更多");
			}
			L.d(TAG, "当前状态，下拉加载更多");
			break;
		case FOOT_REFRESHING:
			footView.setPadding(0, 0, 0, 0);
			footProgressBar.setVisibility(View.VISIBLE);
			footArrowImageView.clearAnimation();
			footArrowImageView.setVisibility(View.INVISIBLE);
			footTipsTextview.setText("正在加载...");
			// footLastUpdatedTextView.setVisibility(View.VISIBLE);

			L.d(TAG, "当前状态,正在加载...");
			break;
		case FOOT_DONE:
			footView.setPadding(0, 0, 0, -footContentHeight);
			footProgressBar.setVisibility(View.GONE);
			footArrowImageView.clearAnimation();
			footArrowImageView.setImageResource(R.drawable.egame_foot_arrow);
			footTipsTextview.setText("上拉加载更多");
			// footLastUpdatedTextView.setVisibility(View.VISIBLE);

			L.d(TAG, "当前状态，done");
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	public interface OnRefreshListener {

		public void onFootRefresh();
	}

	public void onFootRefreshComplete(int state) {
		footState = state;
		// footLastUpdatedTextView.setText("最近更新:" + new
		// Date().toLocaleString());
		changeFooterViewByState();
	}

	private void onFootRefresh() {
		if (refreshListener != null) {
			refreshListener.onFootRefresh();
		}
	}

	// 此方法直接照搬自网络上的一个下拉加载更多的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		// footLastUpdatedTextView.setText("最近更新:" + new
		// Date().toLocaleString());
		super.setAdapter(adapter);
	}

}
