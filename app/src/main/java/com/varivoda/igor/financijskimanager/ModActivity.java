package com.varivoda.igor.financijskimanager;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.varivoda.igor.financijskimanager.databinding.ActivityModBinding;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ArchitectureComponents.ViewModel;


public class ModActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int mjesec = 0, godina = 0, check = 0;
    private ViewModel viewModel;
    private List<String> listaIndexa = new ArrayList<>();
    private String kupacImePrezime;
    private ActivityModBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mod);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String naslovnica = getIntent().getStringExtra(ListAdapter.TAG);
        if (getSupportActionBar() != null) {
            int naslov = getIntent().getIntExtra(MainActivity.INTENT_VALUE, 0);
            if (naslov >= 10 && naslov < 20) {
                getSupportActionBar().setTitle("Statistika");
            } else if (naslov >= 20 && naslov < 30) {
                getSupportActionBar().setTitle("Računi - statistika");
            } else if (naslov >= 30 && naslov < 40) {
                getSupportActionBar().setTitle("Kupci - statistika");
            } else if (naslov >= 40 && naslov < 50) {
                getSupportActionBar().setTitle("Poslovnica - statistika");
            } else if (naslov >= 50 && naslov < 60) {
                getSupportActionBar().setTitle("Proizvodi - statistika");
            } else if (naslov >= 60 && naslov < 70) {
                getSupportActionBar().setTitle("Zaposlenici - statistika");
            } else {
                getSupportActionBar().setTitle("Greška");
            }

            MainActivity.changeToolbarFont(toolbar, this);
        }
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        List<String> lista = new ArrayList<>();
        int provjera = getIntent().getIntExtra(MainActivity.INTENT_VALUE, 0);
        if (provjera == 64 || provjera == 52) {
            lista = viewModel.vratiSveZupanije();
        } else if (provjera == 65 || provjera == 53 || provjera == 55 || provjera == 44) {
            binding.zup.setText(R.string.izaberite_proizvod_res);
            lista = viewModel.vratiSveProizvode();
        } else if (provjera == 42 || provjera == 33 || provjera == 35 || provjera == 12) {
            binding.zup.setText(getResources().getString(R.string.izaberite_poslovnicu_res));
            List<String> tempLista = viewModel.prikaziSvePoslovnice();
            lista = new ArrayList<>();
            for (int i = 0; i < tempLista.size(); i++) {
                String[] dijelovi = tempLista.get(i).split("#", 2);
                lista.add(dijelovi[1]);
            }
        } else if (provjera == 45 || provjera == 14) {
            binding.zup.setText(R.string.mj_cilj_res);
            lista = Arrays.asList(getResources().getStringArray(R.array.listaCiljeva));
        } else if (provjera == 34) {
            binding.zup.setText(R.string.odaberite_kupca_res);
            List<String> temp = viewModel.vratiSveKupce();
            for (String s : temp) {
                String[] parts = s.split("#", 2);
                lista.add(parts[1]);
                listaIndexa.add(parts[0]);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(ModActivity.this);
        binding.rjesenje.setText("");
        TextView naslov = findViewById(R.id.naslov);
        naslov.setText(naslovnica);
        Button monthPicker = findViewById(R.id.month_picker);
        int[] prikaziSamoGodinu = {10, 11, 12, 13, 14, 15, 23, 24, 25};
        if (contains(prikaziSamoGodinu, provjera)) {
            monthPicker.setText(R.string.odaberite_godinu_res);
        }
        monthPicker.setOnClickListener(view -> {
            if (contains(prikaziSamoGodinu, provjera)) {
                izaberiSamoGodinu();
            } else {
                izaberiMjesecGodinu();
            }
        });

    }

    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    private void izaberiSamoGodinu() {
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ModActivity.this, (selectedMonth, selectedYear) -> {
            godina = selectedYear;
            int[] prikazuGodinuIDodatno = {12, 14};
            int provjera = getIntent().getIntExtra(MainActivity.INTENT_VALUE, 0);
            if (contains(prikazuGodinuIDodatno, provjera)) {
                binding.zup.setVisibility(View.VISIBLE);
                binding.spinner.setVisibility(View.VISIBLE);
                check++;
            } else {
                executeQuery(null, String.valueOf(godina), null);
            }
        }, 2020, 0);

        builder.showYearOnly()
                .setYearRange(1990, 2030)
                .build()
                .show();
    }


    private void executeQuery(String month, String year, String param) {
        String prodavac;
        String proizvod;
        String kupac;
        String primjerak;
        String poslovnica;
        String udio;
        String racun;
        StringBuilder builder = new StringBuilder();
        switch (getIntent().getIntExtra(MainActivity.INTENT_VALUE, 0)) {
            case 10:
                StringBuilder stringBuilder = new StringBuilder();
                Cursor c = viewModel.proizvodPoKvartalimaGodine(year);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    if (c.getString(c.getColumnIndex("kvartal")).equals("prvo")) {
                        stringBuilder.append("I: '");
                        stringBuilder.append(c.getString(c.getColumnIndex("nazivProizvod")));
                        stringBuilder.append("': ");
                        stringBuilder.append(c.getString(c.getColumnIndex("broj")));
                    }
                }
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    if (c.getString(c.getColumnIndex("kvartal")).equals("drugo")) {
                        stringBuilder.append("\nII: '");
                        stringBuilder.append(c.getString(c.getColumnIndex("nazivProizvod")));
                        stringBuilder.append("': ");
                        stringBuilder.append(c.getString(c.getColumnIndex("broj")));
                    }
                }
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    if (c.getString(c.getColumnIndex("kvartal")).equals("trece")) {
                        stringBuilder.append("\nIII: '");
                        stringBuilder.append(c.getString(c.getColumnIndex("nazivProizvod")));
                        stringBuilder.append("': ");
                        stringBuilder.append(c.getString(c.getColumnIndex("broj")));
                    }
                }
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                    if (c.getString(c.getColumnIndex("kvartal")).equals("cetvrto")) {
                        stringBuilder.append("\nIV: '");
                        stringBuilder.append(c.getString(c.getColumnIndex("nazivProizvod")));
                        stringBuilder.append("': ");
                        stringBuilder.append(c.getString(c.getColumnIndex("broj")));
                    }
                }


                if (!stringBuilder.toString().equals("")) {
                    binding.rjesenje.setText(stringBuilder.toString());
                } else {
                    binding.rjesenje.setText(R.string.nema_rezultata_godine);
                }
                break;
            case 11:
                StringBuilder stringBuilder1 = new StringBuilder();
                Cursor c1 = viewModel.prodavacPoKvartalimaPostotak(year);
                float zbroj, suma;
                for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                    if (c1.getString(c1.getColumnIndex("kvartal")).equals("prvo")) {
                        stringBuilder1.append("I: '");
                        stringBuilder1.append(c1.getString(c1.getColumnIndex("imePrezime")));
                        stringBuilder1.append("': ");
                        zbroj = Float.parseFloat(c1.getString(c1.getColumnIndex("zbroj")));
                        suma = Float.parseFloat(c1.getString(c1.getColumnIndex("suma")));
                        stringBuilder1.append(String.format(Locale.getDefault(), "%.2f", zbroj / suma * 100));
                        stringBuilder1.append(" %\n");

                    }
                }
                for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                    if (c1.getString(c1.getColumnIndex("kvartal")).equals("drugo")) {
                        stringBuilder1.append("II: '");
                        stringBuilder1.append(c1.getString(c1.getColumnIndex("imePrezime")));
                        stringBuilder1.append("': ");
                        zbroj = Float.parseFloat(c1.getString(c1.getColumnIndex("zbroj")));
                        suma = Float.parseFloat(c1.getString(c1.getColumnIndex("suma")));
                        stringBuilder1.append(String.format(Locale.getDefault(), "%.2f", zbroj / suma * 100));
                        stringBuilder1.append(" %\n");

                    }
                }
                for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                    if (c1.getString(c1.getColumnIndex("kvartal")).equals("trece")) {
                        stringBuilder1.append("III: '");
                        stringBuilder1.append(c1.getString(c1.getColumnIndex("imePrezime")));
                        stringBuilder1.append("': ");
                        zbroj = Float.parseFloat(c1.getString(c1.getColumnIndex("zbroj")));
                        suma = Float.parseFloat(c1.getString(c1.getColumnIndex("suma")));
                        stringBuilder1.append(String.format(Locale.getDefault(), "%.2f", zbroj / suma * 100));
                        stringBuilder1.append(" %\n");

                    }
                }
                for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
                    if (c1.getString(c1.getColumnIndex("kvartal")).equals("cetvrto")) {
                        stringBuilder1.append("IV: '");
                        stringBuilder1.append(c1.getString(c1.getColumnIndex("imePrezime")));
                        stringBuilder1.append("': ");
                        zbroj = Float.parseFloat(c1.getString(c1.getColumnIndex("zbroj")));
                        suma = Float.parseFloat(c1.getString(c1.getColumnIndex("suma")));
                        stringBuilder1.append(String.format(Locale.getDefault(), "%.2f", zbroj / suma * 100));
                        stringBuilder1.append(" %\n");
                    }
                }


                if (!stringBuilder1.toString().equals("")) {
                    binding.rjesenje.setText(stringBuilder1.toString());
                } else {
                    binding.rjesenje.setText(R.string.nema_rezultata_godine);
                }
                break;
            case 12:
                Cursor kur = viewModel.poslovnicaPrvoDrugoRazdoblje(year, param);
                float prviBroj = 0, drugiBroj = 0;

                for (kur.moveToFirst(); !kur.isAfterLast(); kur.moveToNext()) {
                    if (kur.getString(kur.getColumnIndex("kvartal")) != null) {
                        if (kur.getString(kur.getColumnIndex("kvartal")).equals("prvo")) {
                            prviBroj = Float.valueOf(kur.getString(kur.getColumnIndex("broj")));
                        } else if (kur.getString(kur.getColumnIndex("kvartal")).equals("drugo")) {
                            drugiBroj = Float.valueOf(kur.getString(kur.getColumnIndex("broj")));
                        }
                    }
                }

                Log.d("TAG", "executeQuery: " + prviBroj + " " + drugiBroj);
                if (prviBroj == 0 || drugiBroj == 0) {
                    binding.rjesenje.setText(R.string.prvi_drugi_dio_god_nema_rezultata_res);
                } else {
                    float konacno;
                    if (prviBroj > drugiBroj) {
                        float rj = drugiBroj / prviBroj;
                        konacno = -(1 - rj) * 100;
                        binding.rjesenje.setText(getString(R.string.string12pad, param, year, String.valueOf(konacno)));
                    } else {
                        float rj = drugiBroj / prviBroj;
                        konacno = (rj - 1) * 100;
                        binding.rjesenje.setText(getString(R.string.string12, param, year, String.valueOf(konacno)));
                    }


                }

                break;
            case 13:
                StringBuilder stringBuilder2 = new StringBuilder();
                Cursor c2 = viewModel.kupciPoKvartalimaGodine(year);
                for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
                    if (c2.getString(c2.getColumnIndex("kvartal")).equals("prvo")) {
                        stringBuilder2.append("I: '");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("imePrezime")));
                        stringBuilder2.append("': ");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("potrosnja")));
                        stringBuilder2.append(" kn");

                    }
                }
                for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
                    if (c2.getString(c2.getColumnIndex("kvartal")).equals("drugo")) {
                        stringBuilder2.append("\nII: '");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("imePrezime")));
                        stringBuilder2.append("': ");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("potrosnja")));
                        stringBuilder2.append(" kn");

                    }
                }
                for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
                    if (c2.getString(c2.getColumnIndex("kvartal")).equals("trece")) {
                        stringBuilder2.append("\nIII: '");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("imePrezime")));
                        stringBuilder2.append("': ");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("potrosnja")));
                        stringBuilder2.append(" kn");

                    }
                }
                for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {

                    if (c2.getString(c2.getColumnIndex("kvartal")).equals("cetvrto")) {
                        stringBuilder2.append("\nIV: '");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("imePrezime")));
                        stringBuilder2.append("': ");
                        stringBuilder2.append(c2.getString(c2.getColumnIndex("potrosnja")));
                        stringBuilder2.append(" kn");
                    }
                }

                if (!stringBuilder2.toString().equals("")) {
                    binding.rjesenje.setText(stringBuilder2.toString());
                } else {
                    binding.rjesenje.setText(R.string.nema_rezultata_godine);
                }
                break;
            case 14:
                Cursor kur1 = viewModel.prodavacPrekoOdredeneCifre(year, param);
                StringBuilder builder1 = new StringBuilder();
                for (kur1.moveToFirst(); !kur1.isAfterLast(); kur1.moveToNext()) {
                    builder1.append(kur1.getString(kur1.getColumnIndex("imePrezime")));
                    builder1.append(", ");
                }

                if (builder1.toString().equals("")) {
                    binding.rjesenje.setText(R.string.nema_rezultata_prodavaci_godina);
                } else {
                    binding.rjesenje.setText(builder1.toString().substring(0, builder1.length() - 2));
                }
                break;
            case 15:
                prodavac = viewModel.prodavacIzdaoRacunaUGodini(year);

                if (prodavac != null) {
                    binding.rjesenje.setText(getString(R.string.string15, prodavac, year));
                } else {
                    binding.rjesenje.setText(R.string.nema_rezultata_godine);
                }
                break;
            case 20:
                Intent i = new Intent(ModActivity.this, Popis.class);
                i.putExtra(MainActivity.INTENT_VALUE, 20);
                i.putExtra("month", month);
                i.putExtra("year", year);
                i.putExtra(ListAdapter.NASLOV, "Popis računa");
                startActivity(i);
                finish();
                break;
            case 22:
                racun = viewModel.najvecaSvotaNaRacunu(month, year);
                if (racun != null) {
                    binding.rjesenje.setText(getString(R.string.string22, racun, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 23:
                StringBuilder stringBuilder23 = new StringBuilder();
                Cursor c23 = viewModel.brojRacunaPoKvartalima(year);
                for (c23.moveToFirst(); !c23.isAfterLast(); c23.moveToNext()) {
                    if (c23.getString(c23.getColumnIndex("kvartal")).equals("prvo")) {
                        stringBuilder23.append("I: šifra računa: '");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("sifra")));
                        stringBuilder23.append("' količina: ");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("kolicina")));

                    }
                }
                for (c23.moveToFirst(); !c23.isAfterLast(); c23.moveToNext()) {
                    if (c23.getString(c23.getColumnIndex("kvartal")).equals("drugo")) {
                        stringBuilder23.append("\nII: šifra računa: '");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("sifra")));
                        stringBuilder23.append("' količina: ");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("kolicina")));

                    }
                }
                for (c23.moveToFirst(); !c23.isAfterLast(); c23.moveToNext()) {
                    if (c23.getString(c23.getColumnIndex("kvartal")).equals("trece")) {
                        stringBuilder23.append("\nIII: šifra računa: '");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("sifra")));
                        stringBuilder23.append("' količina: ");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("kolicina")));

                    }
                }
                for (c23.moveToFirst(); !c23.isAfterLast(); c23.moveToNext()) {
                    if (c23.getString(c23.getColumnIndex("kvartal")).equals("cetvrto")) {
                        stringBuilder23.append("\nIV: šifra računa: '");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("sifra")));
                        stringBuilder23.append("' ,količina: ");
                        stringBuilder23.append(c23.getString(c23.getColumnIndex("kolicina")));

                    }
                }


                if (!stringBuilder23.toString().equals("")) {
                    binding.rjesenje.setText(stringBuilder23.toString());
                } else {
                    binding.rjesenje.setText(R.string.nema_rezultata_godine);
                }
                break;
            case 24:
                racun = viewModel.najveciBrojStavkiNaRacunuPoGodini(year);
                if (racun != null) {
                    binding.rjesenje.setText(getString(R.string.string24, racun, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }

                break;
            case 25:
                Cursor cursor25 = viewModel.zaradaPoPrvomDrugomDijeluMjeseca(year);
                for (cursor25.moveToFirst(); !cursor25.isAfterLast(); cursor25.moveToNext()) {
                    if (cursor25.getString(cursor25.getColumnIndex("dijelovi")).equals("prvi_dio")) {
                        builder.append("Prvi dio po mjesecima: ");
                        builder.append(cursor25.getString(cursor25.getColumnIndex("zarada")));
                        builder.append(" kn\n");

                    }
                }
                for (cursor25.moveToFirst(); !cursor25.isAfterLast(); cursor25.moveToNext()) {
                    if (cursor25.getString(cursor25.getColumnIndex("dijelovi")).equals("drugi_dio")) {
                        builder.append("Drugi dio po mjesecima: ");
                        builder.append(cursor25.getString(cursor25.getColumnIndex("zarada")));
                        builder.append(" kn\n");

                    }
                }

                if (builder.toString().length() > 0) {
                    binding.rjesenje.setText(builder.toString());
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }

                break;
            case 31:
                kupac = viewModel.kupacKojiJePotrosioNajviseUMjesecu(month, year);

                if (kupac != null) {
                    binding.rjesenje.setText(getString(R.string.string31, kupac, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 32:
                kupac = viewModel.kupacNajviseDanaZAaredomKupovao(month, year);

                if (kupac != null) {
                    binding.rjesenje.setText(getString(R.string.string32, kupac, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 33:
                kupac = viewModel.kupacNajviseProizvodaPoMjesecu(month, year, param);

                if (kupac != null) {
                    binding.rjesenje.setText(getString(R.string.string33, kupac, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 34:
                String q = listaIndexa.get(Integer.parseInt(param));
                kupac = viewModel.kupacKupujeNajcesceUPoslovnici(month, year, q);

                if (kupac != null) {
                    binding.rjesenje.setText(getString(R.string.string34, kupacImePrezime, kupac, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_kupaca_res));
                }
                break;
            case 35:
                kupac = viewModel.brojKupacaKojiSuPosjetiliPoslovnicu(month, year, param);

                if (kupac != null) {
                    if (!kupac.equals("0")) {
                        binding.rjesenje.setText(getString(R.string.string35, param, kupac, month, year));
                    } else {
                        binding.rjesenje.setText(getString(R.string.string35else, param, month, year));
                    }
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 41:
                poslovnica = viewModel.poslovnicaZaradaPoMjesecu(month, year);

                if (poslovnica != null) {
                    binding.rjesenje.setText(getString(R.string.string41, poslovnica, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 42:
                poslovnica = viewModel.zaradaOdredenePoslovniceUdio(month, year, param);
                udio = viewModel.ukupnaZaradaPoMjesecu(month, year);

                if (poslovnica != null) {
                    udio = String.format(Locale.getDefault(), "%.2f", Float.valueOf(poslovnica) / Float.valueOf(udio) * 100);
                    binding.rjesenje.setText("Poslovnica " + param + " je uprihodila udio od " + udio + " % unutar " + month + ". mjeseca i godine " + year + ".");
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 44:
                poslovnica = viewModel.poslovnicaKojaNajboljeProdajeProizvod(month, year, param);
                if (poslovnica != null) {
                    binding.rjesenje.setText(getString(R.string.string44, poslovnica, param, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka_proizvod));
                }
                break;
            case 45:
                if (builder.length() > 0) {
                    builder.setLength(0);
                }

                Cursor cursor = viewModel.poslovniceIznadMjesecnogCilja(month, year, param);
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    builder.append(cursor.getString(cursor.getColumnIndex("nazivPoslovnica")));
                    builder.append(", ");
                }

                if (builder.toString().length() != 0) {
                    String temp = builder.toString();
                    temp = temp.substring(0, temp.length() - 2);
                    binding.rjesenje.setText(getString(R.string.string45, temp));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 51:
                proizvod = viewModel.najprodavanijiProizvod(month, year);

                if (proizvod != null) {
                    if (proizvod.substring(proizvod.length() - 1).equals("1")) {
                        primjerak = "primjerku";
                    } else if (proizvod.substring(proizvod.length() - 1).equals("2") || proizvod.substring(proizvod.length() - 1).equals("3") || proizvod.substring(proizvod.length() - 1).equals("4")) {
                        primjerak = "primjerka";
                    } else {
                        primjerak = "primjeraka";
                    }
                    binding.rjesenje.setText(getString(R.string.string51, proizvod, primjerak, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 52:
                proizvod = viewModel.najprodavanijiProizvodProfitMjesecZupanija(month, year, param);

                if (proizvod != null) {
                    binding.rjesenje.setText(getString(R.string.string52, proizvod, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 53:
                proizvod = viewModel.prodavacProdaoProizvodNaAkciji(month, year, param);

                if (proizvod != null) {
                    if (proizvod.substring(proizvod.length() - 1).equals("1")) {
                        primjerak = "primjerku";
                    } else if (proizvod.substring(proizvod.length() - 1).equals("2") || proizvod.substring(proizvod.length() - 1).equals("3") || proizvod.substring(proizvod.length() - 1).equals("4")) {
                        primjerak = "primjerka";
                    } else {
                        primjerak = "primjeraka";
                    }
                    binding.rjesenje.setText(getString(R.string.string53, proizvod, primjerak, param, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 54:
                proizvod = viewModel.proizvodNajmanjiUdio(month, year);
                udio = viewModel.ukupnaZaradaPoMjesecu(month, year);
                if (proizvod != null) {
                    String[] dijelovi = proizvod.split("#", 2);
                    String ss = String.format(Locale.getDefault(), "%.2f", Float.valueOf(dijelovi[1]) / Float.valueOf(udio) * 100);
                    binding.rjesenje.setText("Proizvod " + dijelovi[0] + " je doprinio ukupnoj prodaji sa " + ss + " % unutar " + month + ". mjeseca i godine " + year + ".");
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 55:
                proizvod = viewModel.ZupanijaNajboljaProdajaProizvoda(month, year, param);
                if (proizvod != null) {
                    String[] dijelovi = proizvod.split("#", 3);
                    String ss = String.format(Locale.getDefault(), "%.2f", Float.valueOf(dijelovi[1]) / Float.valueOf(dijelovi[2]) * 100);
                    binding.rjesenje.setText("Proizvod " + param + " je ostvario " + ss + " % prodaje u županiji " + dijelovi[0] + " unutar " + month + ". mjeseca i godine " + year + ".");

                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 61:
                prodavac = viewModel.prodavacSaNajvecimPrihodomPoMjesecu(month, year);
                if (prodavac != null) {
                    binding.rjesenje.setText(getString(R.string.string61, prodavac, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 62:
                prodavac = viewModel.prodavacSaNajviseIzdanihRacuna(month, year);
                if (prodavac != null) {
                    binding.rjesenje.setText(getString(R.string.string62, prodavac, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 63:
                prodavac = viewModel.prodavacIzdaoRacunZaredom(month, year);
                if (prodavac != null) {
                    binding.rjesenje.setText(getString(R.string.string63, prodavac, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 64:
                prodavac = viewModel.prodavacPoMjesecuPoZupaniji(month, year, param);
                if (prodavac != null) {
                    binding.rjesenje.setText(getString(R.string.string64, prodavac, param, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka));
                }
                break;
            case 65:
                prodavac = viewModel.prodavacNajviseProizvodaPomjesecu(month, year, param);

                if (prodavac != null) {
                    if (prodavac.substring(prodavac.length() - 1).equals("1")) {
                        primjerak = "primjerku";
                    } else if (prodavac.substring(prodavac.length() - 1).equals("2") || prodavac.substring(prodavac.length() - 1).equals("3") || prodavac.substring(prodavac.length() - 1).equals("4")) {
                        primjerak = "primjerka";
                    } else {
                        primjerak = "primjeraka";
                    }
                    binding.rjesenje.setText(getString(R.string.string65, prodavac, primjerak, param, month, year));
                } else {
                    binding.rjesenje.setText(getResources().getString(R.string.nema_rezultata_poruka_proizvod));
                }
                break;
        }

    }


    private void izaberiMjesecGodinu() {
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ModActivity.this, (selectedMonth, selectedYear) -> {
            mjesec = selectedMonth + 1;
            godina = selectedYear;
            int[] prikaziGodinuIMJesec = {64, 65, 52, 53, 42, 55, 44, 45, 33, 34, 35, 12};
            if (mjesec != 0 && godina != 0) {
                binding.rjesenje.setText("");
                int provjera = getIntent().getIntExtra(MainActivity.INTENT_VALUE, 0);
                if (contains(prikaziGodinuIMJesec, provjera)) {
                    binding.zup.setVisibility(View.VISIBLE);
                    binding.spinner.setVisibility(View.VISIBLE);
                    check++;
                } else {
                    if (mjesec < 10) {
                        executeQuery("0" + mjesec, String.valueOf(godina), null);
                    } else {
                        executeQuery(String.valueOf(mjesec), String.valueOf(godina), null);

                    }
                }

            }

        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(1990)
                .setActivatedYear(2020)
                .setMaxYear(2030)
                .setMinMonth(Calendar.FEBRUARY)
                .setTitle("Izaberite mjesec i godinu: ")
                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                .setOnMonthChangedListener(selectedMonth -> Log.d("TAG", "Selected month : " + selectedMonth))
                .setOnYearChangedListener(selectedYear -> Log.d("TAG", "Selected year : " + selectedYear))
                .build()
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (check > 0) {
            if (getIntent().getIntExtra(MainActivity.INTENT_VALUE, 0) == 34) {
                kupacImePrezime = String.valueOf(parent.getItemAtPosition(position));
                if (mjesec < 10) {
                    executeQuery("0" + mjesec, String.valueOf(godina), String.valueOf(position));
                } else {
                    executeQuery(String.valueOf(mjesec), String.valueOf(godina), String.valueOf(position));
                }
            } else {
                if (mjesec < 10) {
                    executeQuery("0" + mjesec, String.valueOf(godina), parent.getItemAtPosition(position).toString());
                } else {
                    executeQuery(String.valueOf(mjesec), String.valueOf(godina), parent.getItemAtPosition(position).toString());
                }
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
