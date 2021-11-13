package cn.sokary.archs.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;

//import io.github.sokary.app.db.AppDB;
//import io.github.sokary.app.utils.Settings;


public abstract class AppController extends AppCompatActivity implements AppControllerInterface {

    private ProgressDialog pDialogLoading;
    public ProgressBar pBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        pDialogLoading = new ProgressDialog(this);
        pDialogLoading.setTitle("Loading....");

        showLoadingDialog(false);
    }

    public Object getParam(String name){
        return getIntent().getExtras().get(name);
    }

    @Override
    public Object getResource(String key, Object... values) {
        return null;
    }

    @Override
    public boolean dispatch(String key, Object... values) {
        try {
            Class<?>[] classArray = new Class<?>[values.length];
            for (int i = 0; i < values.length; i++) { classArray[i] = values[i].getClass(); }
            java.lang.reflect.Method method = this.getClass().getDeclaredMethod(key, classArray);
            method.setAccessible(true);
            method.invoke(this, values);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Context getCxt(){
        return this;
    }

    @Override
    public void showLoadingDialog(Boolean isShow){
        try {
            if(isShow){ pDialogLoading.show(); }else{ pDialogLoading.hide(); }
        }catch (Exception e){ }
    }
    @Override
    public void showLoadingBar(Boolean isShow){
        try {
            if(pBar!=null){
                if(isShow){ pBar.setVisibility(View.VISIBLE); }else{ pBar.setVisibility(View.INVISIBLE); }
            }
        }catch (Exception e){ }
    }

    @Override
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
