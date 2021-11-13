package cn.sokary.archs.android.control;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;

import java.util.List;

import cn.sokary.archs.android.AppControllerInterface;
import cn.sokary.archs.android.AppEntity;
import cn.sokary.archs.android.AppFormatter;

public class AppTextBox extends androidx.appcompat.widget.AppCompatEditText {

    public static AppTextBox create(AppControllerInterface opener, int width, int height, int weight){
        return new AppTextBox(opener, width, height, weight);
    }

    //region Super
    public AppTextBox(Context context) {
        super(context);
    }

    public AppTextBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public AppTextBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion

    AppControllerInterface opener;
    public AppTextBox(AppControllerInterface opener, int width, int height, int weight){
        super(opener.getCxt());
        this.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
        this.opener = opener;
    }


    public AppTextBox padding(int p){
        this.setPadding(p,p,p,p);
        return this;
    }
    public AppTextBox padding(int left, int top, int right, int bottom){
        this.setPadding(left, top, right, bottom);
        return this;
    }
    public AppTextBox margin(int left, int top, int right, int bottom){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.setMargins(left, top, right, bottom);
        return this;
    }
    public AppTextBox textSize(int s){
        this.setTextSize(s);
        return this;
    }
    public AppTextBox textLeft(){
        this.setGravity(Gravity.LEFT);
        return this;
    }
    public AppTextBox textCenter(){
        this.setGravity(Gravity.CENTER);
        return this;
    }
    public AppTextBox textRight(){
        this.setGravity(Gravity.RIGHT);
        return this;
    }
    public AppTextBox background(@DrawableRes int did){
        this.setBackground(getContext().getResources().getDrawable(did));
        return this;
    }
    public AppTextBox setNumber(){
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
        return this;
    }
    public AppTextBox bindData(String value){
        this.setText(value);
        return this;
    }
    public AppTextBox bindData(AppEntity en){
        this.setText(AppFormatter.appEntityToName(en));
        return this;
    }
    public AppTextBox bindData(String[] values){
        String d = "";
        for(String v:values){
            d = d + v + ", ";
        }
        d = ( d + ",").replace(", ,", "");
        this.setText(d);
        return this;
    }
    public AppTextBox bindData(List<AppEntity> values){
        String d = "";
        for(AppEntity v:values){
            d = d + AppFormatter.appEntityToName(v) + ", ";
        }
        d = ( d + ",").replace(", ,", "");
        this.setText(d);
        return this;
    }
    public AppTextBox onChangedAction(String openerMethod){
        this.addTextChangedListener( new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { opener.dispatch(openerMethod);; }
        } );
        return this;
    }
    public AppTextBox onChangedAction(AppControllerInterface opener, String openerMethod){
        this.opener = opener;
        this.addTextChangedListener( new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { opener.dispatch(openerMethod);; }
        } );
        return this;
    }


}
