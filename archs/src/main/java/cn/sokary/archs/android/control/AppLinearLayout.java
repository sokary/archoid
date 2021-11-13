package cn.sokary.archs.android.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import cn.sokary.archs.android.AppControllerInterface;

public class AppLinearLayout extends LinearLayout {

    private AppControllerInterface opener;

    //region Super
    public AppLinearLayout(Context context) {
        super(context);
    }

    public AppLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //endregion

    public AppLinearLayout(AppControllerInterface opener, int width, int height, int weight){
        super(opener.getCxt());
        this.opener = opener;
        this.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
    }

    public static AppLinearLayout create(AppControllerInterface opener, int width, int height, int weight){
        return new AppLinearLayout(opener, width, height, weight);
    }

    public AppLinearLayout background(@DrawableRes int did){
        this.setBackground(getContext().getResources().getDrawable(did));
        return this;
    }

    public AppLinearLayout vertical(){
        this.setOrientation(LinearLayout.VERTICAL);
        return this;
    }
    public AppLinearLayout horizontal(){
        this.setOrientation(LinearLayout.HORIZONTAL);
        return this;
    }
    public AppLinearLayout add(View view){
        this.addView(view);
        return this;
    }

    public AppLinearLayout padding(int top, int right, int bottom, int left){
        this.setPadding(top, right, bottom, left);
        return this;
    }

    public AppLinearLayout padding(int p ){
        this.setPadding(p, p, p, p);
        return this;
    }

}
