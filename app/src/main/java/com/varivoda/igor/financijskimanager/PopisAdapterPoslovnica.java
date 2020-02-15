package com.varivoda.igor.financijskimanager;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.recyclerview.extensions.ListAdapter;

import tablice.Poslovnica;

public class PopisAdapterPoslovnica extends ListAdapter<Poslovnica, PopisAdapterPoslovnica.PopisHolder> {
private PopisAdapterPoslovnica.onPoslovnicaClickListener listener;
private static final DiffUtil.ItemCallback<Poslovnica> DIFF_CALLBACK = new DiffUtil.ItemCallback<Poslovnica>() {
@Override
public boolean areItemsTheSame(@NonNull Poslovnica oldKupac, @NonNull Poslovnica newKupac) {
        return oldKupac.getId() == newKupac.getId();
        }

@Override
public boolean areContentsTheSame(@NonNull Poslovnica oldKupac, @NonNull Poslovnica newKupac) {
        return oldKupac.getNazivPoslovnica().equals(newKupac.getNazivPoslovnica()) &&  oldKupac.getAdresa().equals(newKupac.getAdresa()) &&
                oldKupac.getPbrMjesto() == newKupac.getPbrMjesto() ;
        }
        };

public PopisAdapterPoslovnica() {
        super(DIFF_CALLBACK);
        }

@NonNull
@Override
public PopisAdapterPoslovnica.PopisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.option_item, viewGroup, false);
        return new PopisAdapterPoslovnica.PopisHolder(itemview);
        }

@Override
public void onBindViewHolder(@NonNull PopisAdapterPoslovnica.PopisHolder popisHolder, int i) {
        Poslovnica poslovnica = getItem(i);
        popisHolder.setIsRecyclable(false);
        String prikaz_poslovnice = popisHolder.itemView.getContext().getString(R.string.prikaz_poslovnice,poslovnica.getNazivPoslovnica(),String.valueOf(poslovnica.getPbrMjesto()));
        popisHolder.textView.setText(prikaz_poslovnice);
        }



/*public Poslovnica getItemAt(int pos) {
        return getItem(pos);
        }
*/
class PopisHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public PopisHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.opt);
        itemView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onPoslovnicaClick(getItem(position));
            }
        });
    }
}

public interface onPoslovnicaClickListener {
    void onPoslovnicaClick(Poslovnica poslovnica);
}

    public void setOnPoslovnicaClickListener(PopisAdapterPoslovnica.onPoslovnicaClickListener listener) {
        this.listener = listener;
    }
}


