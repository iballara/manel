package manel.com.manel.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import manel.com.manel.R;

public class LogItemAdapter extends RecyclerView.Adapter<LogItemAdapter.ViewHolder>{

    private ArrayList<String> mDataSet;

    public LogItemAdapter(ArrayList<String> mDataSet) {
        this.mDataSet = mDataSet;
    }

    public void addItem(int position, String item) {

        mDataSet.add(item);
        notifyItemInserted(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: Com li passo l'String?
        //holder.textView.setText("");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.id_textView);
        }
    }
}
