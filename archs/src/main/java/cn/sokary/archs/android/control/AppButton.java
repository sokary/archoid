package cn.sokary.archs.android.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import cn.sokary.archs.android.AppControllerInterface;

public class AppButton extends androidx.appcompat.widget.AppCompatButton {

    AppControllerInterface opener;

    public AppButton(Context context) {
        super(context);
    }
    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AppButton(AppControllerInterface opener, int width, int height, int weight){
        super(opener.getCxt());
        this.opener = opener;
        this.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));

    }

    public AppButton bindData(String value){
        this.setText(value);
        return this;
    }

    public AppButton onClickedAction(AppControllerInterface opener, String methodName){
        this.opener = opener;
        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                opener.dispatch(methodName);
            }
        });

        return this;
    }

    public AppButton onClickedAction(String methodName){
        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                opener.dispatch(methodName);
            }
        });

        return this;
    }


}
