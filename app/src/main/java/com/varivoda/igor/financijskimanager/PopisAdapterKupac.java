package com.varivoda.igor.financijskimanager;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import tablice.Kupac;

public class PopisAdapterKupac extends ListAdapter<Kupac, PopisAdapterKupac.PopisHolder> {
    private onKupacClickListener listener;
    private static final DiffUtil.ItemCallback<Kupac> DIFF_CALLBACK = new DiffUtil.ItemCallback<Kupac>() {
        @Override
        public boolean areItemsTheSame(@NonNull Kupac oldKupac, @NonNull Kupac newKupac) {
            return oldKupac.getId() == newKupac.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Kupac oldKupac, @NonNull Kupac newKupac) {
            return oldKupac.getImeKupac().equals(newKupac.getImeKupac()) && oldKupac.getPrezimeKupac().equals(newKupac.getPrezimeKupac())
                    && oldKupac.getAdresa().equals(newKupac.getAdresa()) && oldKupac.getPbrMjesto() == newKupac.getPbrMjesto();
        }
    };

    public PopisAdapterKupac() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PopisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.option_item, viewGroup, false);
        return new PopisHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull PopisHolder popisHolder, int i) {
        Kupac kupac = getItem(i);
        popisHolder.setIsRecyclable(false);
        String id = String.valueOf(kupac.getId());
        String prikaz_kupca = popisHolder.itemView.getContext().getString(R.string.prikaz_zaposlenika,kupac.getImeKupac(),kupac.getPrezimeKupac(),id);
        popisHolder.textView.setText(prikaz_kupca);
    }



    public Kupac getItemAt(int pos) {
        return getItem(pos);
    }

    class PopisHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public PopisHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.opt);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onKupacClick(getItem(position));
                }
            });
        }
    }

    public interface onKupacClickListener {
        void onKupacClick(Kupac kupac);
    }

    public void setOnKupacClickListener(onKupacClickListener listener) {
        this.listener = listener;
    }
}
