package com.beanstack.utility.stepprogress.listeners;


import com.beanstack.utility.stepprogress.StepProgressBar;
import com.beanstack.utility.stepprogress.components.StateItem;

public interface OnStateItemClickListener {

    void onStateItemClick(StepProgressBar stepProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState);

}
