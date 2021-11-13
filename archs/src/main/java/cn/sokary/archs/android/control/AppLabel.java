package cn.sokary.archs.android.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.DrawableRes;

import java.util.List;

import cn.sokary.archs.android.AppControllerInterface;
import cn.sokary.archs.android.AppEntity;
import cn.sokary.archs.android.AppFormatter;

public class AppLabel extends androidx.appcompat.widget.AppCompatTextView {
    //region Super
    public AppLabel(Context context) {
        super(context);
    }

    public AppLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion

    private AppControllerInterface opener;

    public AppLabel(AppControllerInterface opener, int width, int height, int weight){
        super(opener.getCxt());
        this.opener = opener;
        this.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));

    }

    public static AppLabel create(AppControllerInterface opener, int width, int height, int weight){
        return new AppLabel(opener, width, height, weight);
    }


    public AppLabel padding(int p){
        this.setPadding(p,p,p,p);
        return this;
    }
    public AppLabel padding(int left, int top, int right, int bottom){
        this.setPadding(left, top, right, bottom);
        return this;
    }
    public AppLabel margin(int left, int top, int right, int bottom){
        TableLayout.LayoutParams params = (TableLayout.LayoutParams) getLayoutParams();
        params.setMargins(left, top, right, bottom);
        return this;
    }
    public AppLabel textSize(int s){
        this.setTextSize(s);
        return this;
    }
    public AppLabel textLeft(){
        this.setGravity(Gravity.LEFT);
        return this;
    }
    public AppLabel textCenter(){
        this.setGravity(Gravity.CENTER);
        return this;
    }
    public AppLabel textRight(){
        this.setGravity(Gravity.RIGHT);
        return this;
    }
    public AppLabel background(@DrawableRes int did){
        this.setBackground(getContext().getResources().getDrawable(did));
        return this;
    }

    public AppLabel bindData(String value){
        this.setText(value);
        return this;
    }
    public AppLabel bindData(AppEntity en){
        this.setText(AppFormatter.appEntityToName(en));
        return this;
    }
    public AppLabel bindData(String[] values){
        String d = "";
        for(String v:values){
            d = d + v + ", ";
        }
        d = ( d + ",").replace(", ,", "");
        this.setText(d);
        return this;
    }
    public AppLabel bindData(List<AppEntity> values){
        String d = "";
        for(AppEntity v:values){
            d = d + AppFormatter.appEntityToName(v) + ", ";
        }
        d = ( d + ",").replace(", ,", "");
        this.setText(d);
        return this;
    }

}
