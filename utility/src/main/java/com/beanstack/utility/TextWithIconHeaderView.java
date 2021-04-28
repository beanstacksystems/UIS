package com.beanstack.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TextWithIconHeaderView extends LinearLayout {


    TextView mHeaderTitle;
    ImageView mHeaderImage;

    public TextWithIconHeaderView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TextWithIconHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TextWithIconHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        inflate(context, R.layout.text_icon_content_header, this);

        mHeaderTitle = (TextView) findViewById(R.id.tvHeaderTitle);
        mHeaderImage = (ImageView) findViewById(R.id.ivHeaderImage);

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ContentHeaderView, defStyle, 0);

            final String headerTitle = a.getString(R.styleable.ContentHeaderView_headerTitle);
            final Drawable headerImage = a.getDrawable(R.styleable.ContentHeaderView_headerSrc);

            a.recycle();

            setHeaderTitle(headerTitle);
            setHeaderImage(headerImage);
        }

    }


    public void setHeaderTitle(String headerTitle) {
        this.mHeaderTitle.setText(headerTitle);
    }

    public void setHeaderImage(Drawable headerImage) {
        this.mHeaderImage.setImageDrawable(headerImage);
    }


}
