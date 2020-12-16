package com.example.testexpandview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.AutoTransition;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeTransform;
import androidx.transition.Explode;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

public class ExpandableView extends ConstraintLayout {

    private ConstraintLayout mView;
    private TextView mSubtitleTextView;
    private TextView mTitleTextView;
    private ImageButton mArrowImageButton;
    private String title = "";
    private String subtitle = "";
    private boolean isExpanded = false;
    public ExpandableView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public ExpandableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpandableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        setAttrs(attrs);
        mView = (ConstraintLayout) inflate(getContext(), R.layout.expandable_layout, this);
        LinearLayout expandableView = mView.findViewById(R.id.expandable_linear_layout);
        mTitleTextView = mView.findViewById(R.id.layout_title_text_view);
        mSubtitleTextView = mView.findViewById(R.id.layout_subtitle_text_view);
        mArrowImageButton = mView.findViewById(R.id.arrow_button);
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                change();
            }
        });

        setTitle(title);
        setSubtitle(subtitle);
    }

    private void change() {
        isExpanded = !isExpanded;
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(400);
        Rotate rotate = new Rotate();
        rotate.setDuration(400);
        TransitionSet transitionSet = new TransitionSet().addTransition(changeBounds).addTransition(rotate);
        TransitionManager.beginDelayedTransition((ViewGroup) getParent(), transitionSet);
        ViewGroup.LayoutParams params = mSubtitleTextView.getLayoutParams();
        params.height = isExpanded? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        mSubtitleTextView.setLayoutParams(params);
        mArrowImageButton.setRotation(isExpanded ? 90:0);
    }

    public void setAttrs(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableView);
        if (a.hasValue(R.styleable.ExpandableView_layout_title_text))
            title = a.getString(R.styleable.ExpandableView_layout_title_text);
        if (a.hasValue(R.styleable.ExpandableView_layout_subtitle_text))
            subtitle = a.getString(R.styleable.ExpandableView_layout_subtitle_text);
        a.recycle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        mTitleTextView.setText(title);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        mSubtitleTextView.setText(subtitle);
    }
}
