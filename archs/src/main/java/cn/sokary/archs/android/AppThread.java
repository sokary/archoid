package cn.sokary.archs.android;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;


public abstract class AppThread {
    private Context ctxt;
    public AppControllerInterface controller;
    private Handler handler=new AppHandler(this);

    public AppThread(AppControllerInterface controller){
        this.ctxt = controller.getCxt();
        this.controller = controller;
    }

    public AppThread(AppControllerInterface controller, Context ctxt){
        this.ctxt = ctxt;
        this.controller = controller;
    }

    public void run(){
        new Thread(){
            private Callback result = new Callback();
            @Override
            public void run() {
                try {
                    result.add("ErrorCode", "");
                    action(result);
                } catch (Exception e) {
                    result.add("ErrorCode", e.getMessage());
                    e.printStackTrace();
                } finally {
                    Message message;
                    message = handler.obtainMessage(1, result);
                    handler.sendMessage(message);
                }
            }
        }.start();
    }
    public Context getContext(){ return ctxt; }
    public abstract void action(Callback result) throws Exception;
    public abstract void handle(Callback result) throws Exception;
    public class Callback{
        private Map<String,Object> params;
        public Callback(){ params = new HashMap(); }
        public void add(String key, Object value){ params.put(key,value); }
        public Object get(String key){ return params.get(key); }
        public int getSize(){ return params.size(); }
    }
    public boolean dispatch(String ActionName, Object...values){
       return controller.dispatch(ActionName, values);
    }
    public Object getter(String key, Object...values){
        return controller.getResource(key, values);
    }

    public static class AppHandler extends Handler{
        private AppThread thread;

        public AppHandler(AppThread thread){
            this.thread = thread;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                AppThread.Callback callback = (AppThread.Callback) msg.obj;
                thread.handle(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {

            }
        }
    }

}
