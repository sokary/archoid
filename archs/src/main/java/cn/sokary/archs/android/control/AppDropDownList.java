package cn.sokary.archs.android.control;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sokary.archs.android.AppControllerInterface;
import cn.sokary.archs.android.AppEntity;

public class AppDropDownList<T> extends AppCompatSpinner {

    public static AppDropDownList create(AppControllerInterface opener, int width, int height, int weight){
        return new AppDropDownList(opener, width, height, weight);
    }

    //region Super
    public AppDropDownList(Context context) {
        super(context);
    }

    public AppDropDownList(Context context, int mode) {
        super(context, mode);
    }

    public AppDropDownList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppDropDownList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AppDropDownList(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public AppDropDownList(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }
    //endregion

    //region Private
    private AppControllerInterface opener;
    private List<T> items = new ArrayList<T>();
    private String toValue(T item){
        if(item!=null){
            if(item instanceof AppEntity){
                if(item!=null){
                    return ((AppEntity) item).getLabel();
                }
            }else if(item instanceof String){
                return (String)item;

            }else{

            }
        }else{
            return "---";
        }

        return "";
    }
    private int toValueId(T item){
        if(item instanceof AppEntity){
            if(item!=null){
                return ((AppEntity) item).id;
            }
        }else{

        }

        return 0;
    }
    //endregion

    public AppDropDownList(AppControllerInterface opener, int width, int height, int weight){
        super(opener.getCxt());
        this.opener = opener;
        this.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
    }

    public AppDropDownList bindData(List<T> list, T item) {
        this.items.clear();
        this.items.addAll(list);
        String[] strArray =new String[items.size()];
        for(int i=0; i<items.size(); i++){ strArray[i] =  toValue(items.get(i));  }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, strArray);
        this.setAdapter(adapter);
        if(item !=null){
            for(int i=0; i<this.getAdapter().getCount(); i++){
                if(this.getItemAtPosition(i).equals(toValue(item))){
                    this.setSelection(i);
                }
            }
        }
        return this;
    }

    public AppDropDownList bindData(T[] items, T item){
        List<T> list = new ArrayList<T>(Arrays.asList(items));
        return bindData(list, item);
    }

    public AppDropDownList bindDataWidthEmpty(List<T> list, T item) {

        List<T> lis = new ArrayList<T>();
        lis.add(null);
        lis.addAll(list);
        bindData(lis, item);

        return this;
    }

    public T getSelected(){
        if(this.getSelectedItem()!=null){
            for (T op:this.items) {
                if(op!=null){
                    if(toValue(op).equals(this.getSelectedItem().toString().trim())){
                        return op;
                    }
                }
            }
        }
        return null;
    }
    public String getSelectedText(){
        return this.getSelectedItem().toString().trim();
    }
    public int getSelectedId(){
        if(this.getSelectedItem()!=null){
            for (T op:this.items) {
                if(op!=null){
                    if(toValue(op).equals(this.getSelectedItem().toString().trim())){
                        return toValueId(op);
                    }
                }
            }
        }
        return 0;
    }

    public AppDropDownList padding(int p){
        this.setPadding(p,p,p,p);
        return this;
    }
    public AppDropDownList padding(int left, int top, int right, int bottom){
        this.setPadding(left, top, right, bottom);
        return this;
    }
    public AppDropDownList margin(int left, int top, int right, int bottom){
        TableLayout.LayoutParams params = (TableLayout.LayoutParams) getLayoutParams();
        params.setMargins(left, top, right, bottom);
        return this;
    }
    public AppDropDownList background(@DrawableRes int did){
        this.setBackground(getContext().getResources().getDrawable(did));
        return this;
    }
    public AppDropDownList setSelected(String strToString) {
        for(int i=0; i<this.getAdapter().getCount(); i++){
            if(this.getItemAtPosition(i).equals(strToString)){
                this.setSelection(i);
            }
        }
        return this;
    }

    public AppDropDownList onChangedAction(String methodName){
        this.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opener.dispatch(methodName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return this;
    }
    public AppDropDownList onChangedAction(AppControllerInterface opener, String methodName){
        this.opener = opener;
        this.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opener.dispatch(methodName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return this;
    }
    public AppDropDownList textCenter(){
        this.setGravity(Gravity.CENTER);
        return this;
    }


}
