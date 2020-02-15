package com.varivoda.igor.financijskimanager;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tablice.Proizvod;

public class PopisAdapterProizvod extends ListAdapter<Proizvod, PopisAdapterProizvod.PopisHolder> {
    private PopisAdapterProizvod.onProizvodClickListener listener;
    private static final DiffUtil.ItemCallback<Proizvod> DIFF_CALLBACK = new DiffUtil.ItemCallback<Proizvod>() {
        @Override
        public boolean areItemsTheSame(@NonNull Proizvod oldKupac, @NonNull Proizvod newKupac) {
            return oldKupac.getId() == newKupac.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Proizvod oldKupac, @NonNull Proizvod newKupac) {
            return oldKupac.getNazivProizvod().equals(newKupac.getNazivProizvod()) &&  oldKupac.getCijena() == newKupac.getCijena();
        }
    };

    public PopisAdapterProizvod() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PopisAdapterProizvod.PopisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.option_item, viewGroup, false);
        return new PopisAdapterProizvod.PopisHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull PopisAdapterProizvod.PopisHolder popisHolder, int i) {
        Proizvod proizvod = getItem(i);
        popisHolder.setIsRecyclable(false);
        String prikaz_proizvoda = popisHolder.itemView.getContext().getString(R.string.prikaz_proizvoda,proizvod.getNazivProizvod(),String.valueOf(proizvod.getCijena()));
        popisHolder.textView.setText(prikaz_proizvoda);
    }



    public Proizvod getItemAt(int pos) {
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
                    listener.onProizvodClick(getItem(position));
                }
            });
        }
    }

    public interface onProizvodClickListener {
        void onProizvodClick(Proizvod proizvod);
    }

    public void setOnProizvodClickListener(PopisAdapterProizvod.onProizvodClickListener listener) {
        this.listener = listener;
    }
}


