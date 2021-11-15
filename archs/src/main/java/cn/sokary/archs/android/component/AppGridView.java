package cn.sokary.archs.android.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.sokary.archs.android.AppControllerInterface;

public abstract class AppGridView<T> extends RecyclerView {

    public @NonNull AppControllerInterface opener;

    private List<T> data = new ArrayList<T>();
    private GridAdapter adapter;
    private GridPagination pagination;

    public AppGridView(AppControllerInterface opener, List<T> data){
        super(opener.getCxt());
        bindData(opener, data);

    }
    public AppGridView(AppControllerInterface opener, List<T> data, Button btnPrevious, Button btnNext, TextView lblLabel){
        super(opener.getCxt());
        bindDataWithPagination(opener, data, btnPrevious, btnNext, lblLabel);
    }

    public AppGridView(@NonNull Context context) {
        super(context);
    }
    public AppGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public AppGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract @LayoutRes int getItemTemplateLayoutId();
    public abstract AppGridView.RowHolder newRowHolder(View convertView);

    public void bindData( AppControllerInterface opener, List<T> data ) {
        this.opener = opener;
        this.data.clear();
        this.data.addAll(data);

        adapter = new GridAdapter(this, this.data);
        this.setLayoutManager(new LinearLayoutManager(opener.getCxt()));
        this.setItemViewCacheSize(this.data.size());
        this.setAdapter(adapter);

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
    public void bindDataWithPagination( AppControllerInterface opener, List<T> data, Button btnPrevious, Button btnNext, TextView lblLabel ) {
        this.opener = opener;
        adapter = new GridAdapter(this, this.data);
        pagination = new GridPagination(this, btnPrevious, btnNext, lblLabel, data);
        this.setLayoutManager(new LinearLayoutManager(opener.getCxt()));
        this.setItemViewCacheSize(this.data.size());
        this.setAdapter(adapter);
    }
    public void bindDataWithPagination( AppControllerInterface opener, List<T> data, Button btnPrevious, Button btnNext, TextView lblLabel, int pageSize ) {
        this.opener = opener;
        adapter = new GridAdapter(this, this.data);
        pagination = new GridPagination(this, btnPrevious, btnNext, lblLabel, data, pageSize);
        this.setLayoutManager(new LinearLayoutManager(opener.getCxt()));
        this.setItemViewCacheSize(this.data.size());
        this.setAdapter(adapter);
    }

    public void bindData(List<T> data){
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    public class GridAdapter<T> extends Adapter<AppGridView.RowHolder>{
        private AppGridView agv;
        private List<T> data;
        public GridAdapter(AppGridView agv, List<T> data){
            this.agv = agv;
            this.data = data;
        }
        @NonNull
        @Override
        public AppGridView.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(agv.getItemTemplateLayoutId(), parent, false);
            AppGridView.RowHolder vh = agv.newRowHolder(convertView);
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

    public int getPageIndex() {
        if(pagination!=null){
            pagination.getPageIndex();
        }
        return 0;
    }
    public void setPageIndex(int pageIndex) {
        if(pagination!=null){
            pagination.setPage(pageIndex);
        }
    }

    public class GridPagination<T>{

        private AppGridView agv;
        private int pageSize = 7;
        private int pageIndex=0;
        private int pageTotal;

        private Button btnPrevious;
        private TextView lblLabel;
        private Button btnNext;
        private List<T> data;

        public GridPagination(AppGridView agv, Button btnPrevious, Button btnNext, TextView lblLabel, List<T> items) {
            this.agv = agv;
            this.btnPrevious = btnPrevious;
            this.lblLabel = lblLabel;
            this.btnNext = btnNext;
            this.data = items;
            this.pageIndex = 0;

            Double s1 = items.size() * 1.0;
            Double s2 = pageSize * 1.0;

            pageTotal = (int) Math.ceil( Double.valueOf( s1 / s2 ) );
            setPage(0);

            btnPrevious.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    setPage(pageIndex-1);
                }
            });
            btnNext.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    setPage(pageIndex+1);
                }
            });
        }

        public GridPagination(AppGridView agv, Button btnPrevious, Button btnNext, TextView lblLabel, List<T> items, int pageSize) {
            this.agv = agv;
            this.btnPrevious = btnPrevious;
            this.lblLabel = lblLabel;
            this.btnNext = btnNext;
            this.data = items;
            this.pageIndex = 0;
            this.pageSize = pageSize;

            Double s1 = items.size() * 1.0;
            Double s2 = pageSize * 1.0;

            pageTotal = (int) Math.ceil( Double.valueOf( s1 / s2 ) );
            setPage(0);

            btnPrevious.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    setPage(pageIndex-1);
                }
            });
            btnNext.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    setPage(pageIndex+1);
                }
            });
        }

        protected void setPage(int pageIndex){
            if(pageIndex<1){
                btnPrevious.setEnabled(false);
                pageIndex=0;
            }else{
                btnPrevious.setEnabled(true);
            }

            if(pageIndex>=pageTotal-1){
                pageIndex = pageTotal - 1;
                btnNext.setEnabled(false);
            }else{
                btnNext.setEnabled(true);
            }
            this.pageIndex = pageIndex;
            lblLabel.setText( String.valueOf(pageIndex+1) + " / " + String.valueOf(pageTotal) );


            agv.bindData(getData());

        }

        private List<T> getData(){
            int indexFrom = pageIndex * pageSize;
            int indexTo = ( ((pageIndex+1) * pageSize) >= data.size() ) ? data.size() : (pageIndex+1) * pageSize;
            if(data.size()>0){
                return data.subList( indexFrom, indexTo );
            }else{
                return new ArrayList<T>();
            }
        }

        private int getPageIndex(){ return pageIndex; }

    }

    public static abstract class RowHolder<T> extends ViewHolder{
        public @NonNull AppControllerInterface opener;
        public RowHolder(@NonNull AppControllerInterface opener, @NonNull View itemView) {
            super(itemView);
            this.opener = opener;
            buildUI();
        }
        public abstract void buildUI();
        public abstract void setItem(T o);
    }


}
