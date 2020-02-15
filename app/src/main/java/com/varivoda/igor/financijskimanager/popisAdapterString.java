package com.varivoda.igor.financijskimanager;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class popisAdapterString extends ListAdapter<String, popisAdapterString.PopisHolder> {
    private popisAdapterString.onStringClickListener listener;
    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldKupac, @NonNull String newKupac) {
            return oldKupac.equals(newKupac);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldKupac, @NonNull String newKupac) {
            return oldKupac.equals(newKupac);
        }
    };

    public popisAdapterString() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public popisAdapterString.PopisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.option_item, viewGroup, false);
        return new popisAdapterString.PopisHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull popisAdapterString.PopisHolder popisHolder, int i) {
        String str = getItem(i);
        popisHolder.setIsRecyclable(false);
        popisHolder.textView.setText(str);
    }




    class PopisHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public PopisHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.opt);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onStringClick(getItem(position));
                }
            });
        }
    }

    public interface onStringClickListener {
        void onStringClick(String kupac);
    }

    public void setOnStringClickListener(popisAdapterString.onStringClickListener listener) {
        this.listener = listener;
    }
}
