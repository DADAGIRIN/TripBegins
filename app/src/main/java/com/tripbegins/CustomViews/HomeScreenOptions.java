package com.tripbegins.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripbegins.R;

public class HomeScreenOptions extends LinearLayout {

    ImageView imageView;
    TextView textView;

    public HomeScreenOptions(Context context) {
        super(context);
        initializeView(context, null);
    }

    public HomeScreenOptions(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs);
    }

    public HomeScreenOptions(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context, attrs);
    }

    private void initializeView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.home_options, this);

        if (attrs != null) {

            imageView = (ImageView) findViewById(R.id.iv_options);
            textView =(TextView) findViewById(R.id.tv_options);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeScreenOptions);
            CharSequence text = typedArray.getString(R.styleable.HomeScreenOptions_text);
            int src = typedArray.getResourceId(R.styleable.HomeScreenOptions_src, 0);

            if (src > 0) {
                imageView.setBackgroundResource(src);
            }

            if (text != null) {
                textView.setText(text);
            }

            typedArray.recycle();
        }
    }

}
