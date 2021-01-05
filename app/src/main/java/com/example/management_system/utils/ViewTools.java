package com.example.management_system.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewTools {
    /**
     * 設置View的寬度（像素）。若設置爲自適應則應該傳入MarginLayoutParams.WRAP_CONTENT
     * @param view
     * @param width
     */
    public static void setLayoutWidth(View view, int width)
    {
       /* MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height);
        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        //view.setLayoutParams(layoutParams);
        ViewGroup.MarginLayoutParams  layoutParams =newLayParms(view, margin);
        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
        view.requestLayout();*/
        if (view.getParent() instanceof FrameLayout){
            FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams) view.getLayoutParams();
            lp.width=width;
            view.setLayoutParams(lp);
            //view.setX(x);
            view.requestLayout();
        }
        else if (view.getParent() instanceof RelativeLayout){
            RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams)view.getLayoutParams();
            lp.width=width;
            view.setLayoutParams(lp);
            //view.setX(x);
            view.requestLayout();
        }
        else if (view.getParent() instanceof LinearLayout){
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)view.getLayoutParams();
            lp.width=width;
            view.setLayoutParams(lp);
            //view.setX(x);
            view.requestLayout();
        }else{
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            System.out.println(view.getParent().getClass());
            lp.width = width;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
    }
}
