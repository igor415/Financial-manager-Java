package com.varivoda.igor.financijskimanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<String> kupci;
    private int switch_value;
    public static final String TAG = "com.varivoda.igor.financijskimanager.TAG";
    public static final String NASLOV = "com.varivoda.igor.financijskimanager.NASLOV";

    public ListAdapter(List<String> listdata,int switch_value) {
        this.kupci = listdata;
        this.switch_value = switch_value;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.option_item, parent, false);
        return new ViewHolder(listItem, context,switch_value);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(kupci.get(position));
    }



    @Override
    public int getItemCount() {
        return kupci.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public Context context;
        private int switch_value;
        public ViewHolder(View itemView,Context context,int switch_value) {
            super(itemView);
            this.context = context;
            this.switch_value = switch_value;
            this.textView = itemView.findViewById(R.id.opt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intentPopis = new Intent(context,Popis.class);
            Intent intentMod = new Intent(context,ModActivity.class);
            switch(switch_value){
                case 1:
                    List<String> lista = Arrays.asList(context.getResources().getStringArray(R.array.statistikaLista));
                    if(getAdapterPosition()==0) {
                        intentMod.putExtra(MainActivity.INTENT_VALUE,10);
                        intentMod.putExtra(TAG,lista.get(0));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==1){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,11);
                        intentMod.putExtra(TAG,lista.get(1));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==2){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,12);
                        intentMod.putExtra(TAG,lista.get(2));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==3){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,13);
                        intentMod.putExtra(TAG,lista.get(3));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==4){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,14);
                        intentMod.putExtra(TAG,lista.get(4));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==5){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,15);
                        intentMod.putExtra(TAG,lista.get(5));
                        context.startActivity(intentMod);
                    }
                    break;
                case 2:
                    lista = Arrays.asList(context.getResources().getStringArray(R.array.racuniLista));
                    if(getAdapterPosition()==0) {
                        Intent unosRacunaIntent = new Intent(context,UnosRacunaActivity.class);
                        context.startActivity(unosRacunaIntent);
                    }else if(getAdapterPosition()==1){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,20);
                        intentMod.putExtra(TAG,lista.get(0));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==2){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,22);
                        intentMod.putExtra(TAG,lista.get(1));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==3){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,23);
                        intentMod.putExtra(TAG,lista.get(2));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==4){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,24);
                        intentMod.putExtra(TAG,lista.get(3));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==5){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,25);
                        intentMod.putExtra(TAG,lista.get(4));
                        context.startActivity(intentMod);
                    }
                    break;
                case 3:
                    lista = Arrays.asList(context.getResources().getStringArray(R.array.kupciLista));
                    if(getAdapterPosition()==0) {
                        intentPopis.putExtra(MainActivity.INTENT_VALUE,30);
                        intentPopis.putExtra(NASLOV,lista.get(0));
                        context.startActivity(intentPopis);
                    }else if(getAdapterPosition()==1){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,31);
                        intentMod.putExtra(TAG,lista.get(1));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==2){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,32);
                        intentMod.putExtra(TAG,lista.get(2));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==3){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,33);
                        intentMod.putExtra(TAG,lista.get(3));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==4){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,34);
                        intentMod.putExtra(TAG,lista.get(4));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==5){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,35);
                        intentMod.putExtra(TAG,lista.get(5));
                        context.startActivity(intentMod);
                    }
                    break;
                case 4:
                    lista = Arrays.asList(context.getResources().getStringArray(R.array.poslovniceLista));
                    if(getAdapterPosition()==0) {
                        intentPopis.putExtra(MainActivity.INTENT_VALUE,40);
                        intentPopis.putExtra(NASLOV,lista.get(0));
                        context.startActivity(intentPopis);
                    }else if(getAdapterPosition()==1){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,41);
                        intentMod.putExtra(TAG,lista.get(1));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==2){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,42);
                        intentMod.putExtra(TAG,lista.get(2));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==3){
                        Intent maps = new Intent(context,MapsActivity.class);
                        context.startActivity(maps);
                    }else if(getAdapterPosition()==4){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,44);
                        intentMod.putExtra(TAG,lista.get(3));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==5){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,45);
                        intentMod.putExtra(TAG,lista.get(4));
                        context.startActivity(intentMod);
                    }
                    break;
                case 5:
                    lista = Arrays.asList(context.getResources().getStringArray(R.array.proizvodiLista));
                    if(getAdapterPosition()==0) {
                        intentPopis.putExtra(MainActivity.INTENT_VALUE,50);
                        intentPopis.putExtra(NASLOV,lista.get(0));
                        context.startActivity(intentPopis);
                    }else if(getAdapterPosition()==1){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,51);
                        intentMod.putExtra(TAG,lista.get(1));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==2){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,52);
                        intentMod.putExtra(TAG,lista.get(2));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==3){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,53);
                        intentMod.putExtra(TAG,lista.get(3));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==4){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,54);
                        intentMod.putExtra(TAG,lista.get(4));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==5){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,55);
                        intentMod.putExtra(TAG,lista.get(5));
                        context.startActivity(intentMod);
                    }
                    break;
                case 6:
                    lista = Arrays.asList(context.getResources().getStringArray(R.array.zaposleniciLista));
                    if(getAdapterPosition()==0) {
                        intentPopis.putExtra(MainActivity.INTENT_VALUE,60);
                        intentPopis.putExtra(NASLOV,lista.get(0));
                        context.startActivity(intentPopis);
                    }else if(getAdapterPosition()==1){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,61);
                        intentMod.putExtra(TAG,lista.get(1));
                        context.startActivity(intentMod);
                    }else if(getAdapterPosition()==2){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,62);
                        intentMod.putExtra(TAG,lista.get(2));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==3){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,63);
                        intentMod.putExtra(TAG,lista.get(3));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==4){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,64);
                        intentMod.putExtra(TAG,lista.get(4));
                        context.startActivity(intentMod);
                    }
                    else if(getAdapterPosition()==5){
                        intentMod.putExtra(MainActivity.INTENT_VALUE,65);
                        intentMod.putExtra(TAG,lista.get(5));
                        context.startActivity(intentMod);
                    }
                    break;

            }

        }

    }

}
