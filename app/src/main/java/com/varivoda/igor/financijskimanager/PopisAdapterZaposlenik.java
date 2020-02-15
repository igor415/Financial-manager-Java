package com.varivoda.igor.financijskimanager;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tablice.Prodavac;

public class PopisAdapterZaposlenik extends ListAdapter<Prodavac, PopisAdapterZaposlenik.PopisHolder> {
    private PopisAdapterZaposlenik.onZaposlenikClickListener listener;
    private static final DiffUtil.ItemCallback<Prodavac> DIFF_CALLBACK = new DiffUtil.ItemCallback<Prodavac>() {
        @Override
        public boolean areItemsTheSame(@NonNull Prodavac oldKupac, @NonNull Prodavac newKupac) {
            return oldKupac.getId() == newKupac.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Prodavac oldKupac, @NonNull Prodavac newKupac) {
            return oldKupac.getImeProdavac().equals(newKupac.getImeProdavac()) && oldKupac.getPrezimeProdavac().equals(newKupac.getPrezimeProdavac())
                    && oldKupac.getAdresa().equals(newKupac.getAdresa()) && oldKupac.getPbrMjesto() == newKupac.getPbrMjesto();
        }
    };

    public PopisAdapterZaposlenik() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PopisAdapterZaposlenik.PopisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.option_item, viewGroup, false);
        return new PopisHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull PopisAdapterZaposlenik.PopisHolder popisHolder, int i) {
        Prodavac prodavac = getItem(i);
        popisHolder.setIsRecyclable(false);
        String id = String.valueOf(prodavac.getId());
        String prikaz_zaposlenika = popisHolder.itemView.getContext().getString(R.string.prikaz_zaposlenika,prodavac.getImeProdavac(),prodavac.getPrezimeProdavac(),id);
        popisHolder.textView.setText(prikaz_zaposlenika);
    }



    public Prodavac getItemAt(int pos) {
        return getItem(pos);
    }

    class PopisHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public PopisHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.opt);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onZaposlenikClick(getItem(position));
                }
            });
        }
    }

    public interface onZaposlenikClickListener {
        void onZaposlenikClick(Prodavac prodavac);
    }

    public void setOnZaposlenikClickListener(PopisAdapterZaposlenik.onZaposlenikClickListener listener) {
        this.listener = listener;
    }
}
