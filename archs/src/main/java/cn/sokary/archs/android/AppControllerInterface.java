package cn.sokary.archs.android;

import android.content.Context;

public interface AppControllerInterface {
    public Object getResource(String key, Object... values);
    public Context getCxt();
    public boolean dispatch(String key, Object... values);
    public void showLoadingDialog(Boolean isShow);
    public void showLoadingBar(Boolean isShow);
    public void showToast(String msg);
}
