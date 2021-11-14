package cn.sokary.archs.android.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.sokary.archs.android.AppControllerInterface;
import cn.sokary.archs.android.AppFormatter;

public class AppCheckBoxList<T> extends RecyclerView {
    public @NonNull AppControllerInterface opener;
    private String callbackMethodName="";
    private final List<T> data = new ArrayList<T>();
    private GridAdapter adapter;
    public AppCheckBoxList(@NonNull AppControllerInterface opener, List<T> data, String callbackMethodName){
        super(opener.getCxt());
        this.callbackMethodName= callbackMethodName;
        bindData(opener, data);


    }
    public AppCheckBoxList(@NonNull AppControllerInterface opener, List<T> data, int cols, String callbackMethodName){
        super(opener.getCxt());
        this.callbackMethodName= callbackMethodName;
        bindDataWithMultiColumns(opener, data, cols);

    }
    public AppCheckBoxList(@NonNull Context context) {
        super(context);
    }
    public AppCheckBoxList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public AppCheckBoxList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindDataWithMultiColumns( AppControllerInterface opener, List<T> data,  int cols ) {
        this.opener = opener;
        this.data.clear();
        this.data.addAll(data);

        adapter = new GridAdapter(this, this.data);
        this.setLayoutManager(new GridLayoutManager(opener.getCxt(),cols ));
        this.setItemViewCacheSize(this.data.size());
        this.setAdapter(adapter);
        this.setVisibility(View.VISIBLE);

    }
    public void bindData( AppControllerInterface opener, List<T> data ) {
        this.opener = opener;
        this.data.clear();
        this.data.addAll(data);

        adapter = new GridAdapter(this, this.data);
        this.setLayoutManager(new LinearLayoutManager(opener.getCxt()));
        this.setItemViewCacheSize(this.data.size());
        this.setAdapter(adapter);

    }
    public void bindData(List<T> data){
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    public class GridAdapter<T> extends Adapter<RowHolder>{
        private AppCheckBoxList agv;
        private List<T> data;
        public GridAdapter(AppCheckBoxList agv, List<T> data){
            this.agv = agv;
            this.data = data;
        }
        @NonNull
        @Override
        public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View convertView=null;
            convertView = new LinearLayout(getContext());
            LayoutParams layoutParams=new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(layoutParams);
            RowHolder vh = agv.newRowHolder(convertView);
            return vh;
        }
        @Override
        public void onBindViewHolder(@NonNull RowHolder holder, int position) {
            holder.setItem(data.get(position));
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public RowHolder newRowHolder(View itemView){
        return new RowHolder(opener, itemView, callbackMethodName);
    }

    public static class RowHolder<T> extends ViewHolder {
        public @NonNull AppControllerInterface opener;
        private String callbackMethodName="";
        private CheckBox isActive;
        public RowHolder(@NonNull AppControllerInterface opener, @NonNull View itemView, String callbackMethodName) {
            super(itemView);
            this.opener = opener;
            this.callbackMethodName = callbackMethodName;
            buildUI();
        }
        public void buildUI(){

            this.isActive = new CheckBox(itemView.getContext());

            ((LinearLayout)itemView).addView(isActive);
        };
        public void setItem(T o){
            isActive.setText(AppFormatter.appEntityToName((cn.sokary.archs.android.AppEntity) o));
            isActive.setChecked(!((cn.sokary.archs.android.AppEntity) o).isdel);
            if(!callbackMethodName.equals("")){
                isActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){ //ADD
                            ((cn.sokary.archs.android.AppEntity) o).isdel = false;
                        }
                        else{ //DELETE
                            ((cn.sokary.archs.android.AppEntity) o).isdel = true;
                        }
                        opener.dispatch(callbackMethodName, o);
                    }
                });
            }

        };
    }



}
