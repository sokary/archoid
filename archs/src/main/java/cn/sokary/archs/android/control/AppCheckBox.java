package cn.sokary.archs.android.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.TableLayout;

import androidx.annotation.DrawableRes;

import java.util.List;

import cn.sokary.archs.android.AppControllerInterface;
import cn.sokary.archs.android.AppEntity;
import cn.sokary.archs.android.AppFormatter;

public class AppCheckBox extends androidx.appcompat.widget.AppCompatCheckBox {
    AppControllerInterface opener;
    //region Super
    public AppCheckBox(Context context) {
        super(context);
    }

    public AppCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion

    public AppCheckBox(AppControllerInterface opener){
        super(opener.getCxt());
        this.opener = opener;
    }

    public AppCheckBox padding(int p){
        this.setPadding(p,p,p,p);
        return this;
    }
    public AppCheckBox padding(int left, int top, int right, int bottom){
        this.setPadding(left, top, right, bottom);
        return this;
    }
    public AppCheckBox margin(int left, int top, int right, int bottom){
        TableLayout.LayoutParams params = (TableLayout.LayoutParams) getLayoutParams();
        params.setMargins(left, top, right, bottom);
        return this;
    }
    public AppCheckBox textSize(int s){
        this.setTextSize(s);
        return this;
    }
    public AppCheckBox textLeft(){
        this.setGravity(Gravity.LEFT);
        return this;
    }
    public AppCheckBox textCenter(){
        this.setGravity(Gravity.CENTER);
        return this;
    }
    public AppCheckBox textRight(){
        this.setGravity(Gravity.RIGHT);
        return this;
    }
    public AppCheckBox background(@DrawableRes int did){
        this.setBackground(getContext().getResources().getDrawable(did));
        return this;
    }
    public AppCheckBox bindData(String value){
        this.setText(value);
        return this;
    }
    public AppCheckBox bindData(AppEntity en){
        this.setText(AppFormatter.appEntityToName(en));
        return this;
    }
    public AppCheckBox bindData(String[] values){
        String d = "";
        for(String v:values){
            d = d + v + ", ";
        }
        d = ( d + ",").replace(", ,", "");
        this.setText(d);
        return this;
    }
    public AppCheckBox bindData(List<AppEntity> values){
        String d = "";
        for(AppEntity v:values){
            d = d + AppFormatter.appEntityToName(v) + ", ";
        }
        d = ( d + ",").replace(", ,", "");
        this.setText(d);
        return this;
    }

    public AppCheckBox onChanged(String openerMethod){
        this.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                opener.dispatch(openerMethod);
            }
        });
        return this;
    }
    public AppCheckBox onChanged(AppControllerInterface opener, String openerMethod){
        this.opener = opener;
        this.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                opener.dispatch(openerMethod);
            }
        });
        return this;
    }

}
