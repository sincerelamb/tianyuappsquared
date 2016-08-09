package com.tygas.tianyu.tianyu.ui.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/1/25.
 */
public class FocuGirdView extends GridView {


    public FocuGirdView(Context
                                context) {

        super(context);

    }


    public FocuGirdView(Context
                                context, AttributeSet attrs) {

        super(context,
                attrs);

    }


    public FocuGirdView(Context
                                context, AttributeSet attrs, int efStyle) {

        super(context,
                attrs, efStyle);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightSpec;


        if (getLayoutParams().height
                == LayoutParams.WRAP_CONTENT) {

//            //
//            The great Android "hackatlon", the love, the magic.
//
//                    //
//                    The two leftmost bits in the height measure spec have
//
//            //
//            a special meaning, hence we can 't use them to describe height.

            heightSpec
                    = MeasureSpec.makeMeasureSpec(

                    Integer.MAX_VALUE
                            >> 2,
                    MeasureSpec.AT_MOST);

        } else {

//            //
//            Any other height should be respected as is.

            heightSpec
                    = heightMeasureSpec;

        }


        super.onMeasure(widthMeasureSpec,
                heightSpec);

    }


}
