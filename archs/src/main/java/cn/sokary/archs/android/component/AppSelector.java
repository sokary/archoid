package cn.sokary.archs.android.component;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import cn.sokary.archs.android.AppControllerInterface;


public abstract class AppSelector<T> extends AlertDialog.Builder implements AppControllerInterface {

    public final int HORIZONTAL = 0;
    public final int VERTICAL = 1;
    public final int MATCH_PARENT = -1;
    public final int WRAP_CONTENT = -2;

    public @NonNull AppControllerInterface opener;
    private AlertDialog dialog;
    private ProgressBar pBar;
    private LinearLayout lyFrame;
    private T data;


    public AppSelector(AppControllerInterface opener, String title, String okText, String failText, T data){
        super(opener.getCxt());
        this.setCancelable(false);
        this.data = data;
        this.opener=opener;
        this.pBar = new ProgressBar(this.getContext());
        LinearLayout.LayoutParams  pBarParams = new LinearLayout.LayoutParams(24, 24);
        pBarParams.setMargins(10,6,3,0);
        this.pBar.setLayoutParams( pBarParams );
        this.lyFrame = new LinearLayout(this.getContext());
        this.lyFrame.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT) );
        this.lyFrame.setOrientation(LinearLayout.VERTICAL);
        this.lyFrame.setPadding(20,20,20,20);

        LinearLayout lyTitle = new LinearLayout(this.getContext());
        lyTitle.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
        lyTitle.setOrientation(LinearLayout.HORIZONTAL);

        TextView lblTitle = new TextView(this.getContext());
        lblTitle.setText(title);
        lblTitle.setTextSize(24);

        lblTitle.setTextColor(this.getContext().getResources().getColor(android.R.color.background_dark));

        lyTitle.addView(lblTitle);
        lyTitle.setPadding(5,5,5,15);
        lyTitle.addView(pBar);

        lyFrame.addView(lyTitle);

        this.buildUI();

        this.setView(lyFrame);

        showLoadingBar(false);

        if(okText.equals("")==false){ this.setPositiveButton(okText, (dialog, which) -> onOkButtonClicked() ); }

        if(failText.equals("")==false){ this.setNegativeButton(failText, (dialog, which) -> closeWindow()); }

    }

    public void showWindow(){
        dialog = this.create();
        int width = (int)(getCxt().getResources().getDisplayMetrics().widthPixels*0.90f);
        dialog.show();
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        if((int)(dialog.getButton(AlertDialog.BUTTON_POSITIVE).getVisibility())==0){
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onOkButtonClicked();
                }
            });
        }
    }

    public void showWindow(AlertDialog.OnKeyListener listener){
        dialog = this.create();
        dialog.setOnKeyListener(listener);
        int width = (int)(getCxt().getResources().getDisplayMetrics().widthPixels*0.90f);
        dialog.show();
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        if((int)(dialog.getButton(AlertDialog.BUTTON_POSITIVE).getVisibility())==0){
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onOkButtonClicked();
                }
            });
        }
    }

    public void closeWindow(){
        dialog.dismiss();
    }
    protected void addView(View view){
        lyFrame.addView(view);
    }

    protected abstract void buildUI();
    protected abstract void onOkButtonClicked();
    public T getData(){  return data; }

    @Override
    public Object getResource(String key, Object... values) {
        return opener.getResource(key,values);
    }

    @Override
    public Context getCxt() {
        return opener.getCxt();
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
    public void showLoadingDialog(Boolean isShow) {
        if(pBar!=null){
            if (isShow) {
                pBar.setVisibility(View.VISIBLE);
            } else {
                pBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showLoadingBar(Boolean isShow) {
        if(pBar!=null){
            if (isShow) {
                pBar.setVisibility(View.VISIBLE);
            } else {
                pBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showToast(String msg) {
        opener.showToast(msg);
    }






}
