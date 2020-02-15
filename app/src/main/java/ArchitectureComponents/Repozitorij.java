package ArchitectureComponents;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import tablice.Kupac;
import tablice.Poslovnica;
import tablice.Prodavac;
import tablice.Proizvod;
import tablice.ProizvodiNaRacunu;
import tablice.Racun;

@SuppressWarnings("ALL")
public class Repozitorij {

    private Dao dao;
    private LiveData<List<Kupac>> sviKupci;
    private LiveData<List<Proizvod>> sviProizvodi;
    private LiveData<List<Prodavac>> sviZaposlenici;
    private LiveData<List<Poslovnica>> svePoslovice;
    private LiveData<List<String>> sviRacuni;
    private static List<String> poslovnice;
    private static List<Integer> postanskiBrojevi;
    private static List<String> imenaProizvoda;
    private static List<String> imenaZupanija;
    private static List<String> kupci;
    private static List<String> zaposlenici;
    private static String prod;
    private static String kup;
    private static Cursor kursor;
    private static int zadnjiRacun;

    public Repozitorij(Application application) {
        Financije financije = Financije.getInstance(application);
        dao = financije.dao();
        sviKupci = dao.prikaziSveKupce();
        sviZaposlenici = dao.prikaziSveZaposlenike();
        sviProizvodi = dao.prikaziSveProizvode();
        poslovnice = new ArrayList<>();
        kupci = new ArrayList<>();
        zaposlenici = new ArrayList<>();
        svePoslovice = dao.prikaziSvePoslovnice();
        postanskiBrojevi = new ArrayList<>();
        imenaZupanija = new ArrayList<>();
        imenaProizvoda = new ArrayList<>();
        new vratiSvePostanskeBrojeveAsyncTask(dao).execute();

    }

    public void unesiKupca(Kupac kupac) {
        new unesiKupcaAsyncTask(dao).execute(kupac);

    }

    public void obrisiKupca(Kupac kupac) {
        new obrisiKupcaAsyncTask(dao).execute(kupac);

    }

    public void promijeniPodatkeKupca(Kupac kupac) {
        new promijeniPodatkeKupcaAsyncTask(dao).execute(kupac);
    }

    public LiveData<List<Kupac>> prikaziSveKupce() {
        return sviKupci;
    }

    public List<Integer> vratiSvePostanskeBrojeve() {
        return postanskiBrojevi;
    }

    public LiveData<List<Poslovnica>> prikaziSvePoslovniceLive() {
        return svePoslovice;
    }


    public void unesiZaposlenika(Prodavac prodavac) {
        new unesiZaposlenikaAsyncTask(dao).execute(prodavac);
    }

    LiveData<List<Prodavac>> prikaziSveZaposlenike() {
        return sviZaposlenici;
    }

    public void promijeniPodatkeZaposlenika(Prodavac prodavac) {
        new promijeniPodatkeZaposlenikaAsyncTask(dao).execute(prodavac);
    }

    public void obrisiZaposlenika(Prodavac prodavac) {
        new obrisiZaposlenikaAsyncTask(dao).execute(prodavac);
    }


    public void unesiProizvod(Proizvod proizvod) {
        new unesiProizvodAsyncTask(dao).execute(proizvod);
    }

    public LiveData<List<Proizvod>> prikaziSveProizvode() {
        return sviProizvodi;
    }

    public void obrisiProizvod(Proizvod proizvod) {
        new obrisiProizvodAsyncTask(dao).execute(proizvod);
    }

    public void promijeniPodatkeProizvoda(Proizvod proizvod) {
        new promijeniPodatkeProizvodaAsyncTask(dao).execute(proizvod);
    }

    public void promijeniPodatkePoslovnice(Poslovnica poslovnica) {
        new promijeniPodatkePoslovniceAsyncTask(dao).execute(poslovnica);
    }

    public String prodavacSaNajvecimPrihodomPoMjesecu(String month, String year) {
        prod = null;

        new prodavacSaNajvecimPrihodomPoMjesecuAsyncTask(dao).execute(month, year);
        Log.d("TAG", "prodavacSaNajvecimPrihodomPoMjesecu: " + prod);
        long startTime = System.currentTimeMillis();
        while (prod == null) {

            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 500) {
                break;
            }
        }
        return prod;
    }

    public List<String> prikaziSvePoslovnice() {
        poslovnice.clear();
        new prikaziSvePoslovniceAsyncTask(dao).execute();
        long startTime = System.currentTimeMillis();
        while (1 == 1) {
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                return poslovnice;
            }
        }
    }
    public List<String> vratiSveZaposlenike(){
        zaposlenici.clear();
        new vratiSveZaposlenikeAsyncTask(dao).execute();
        long startTime = System.currentTimeMillis();
        while (1 == 1) {
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                return zaposlenici;
            }
        }
    }

    public void unesiRacun(Racun racun) {
        new unesiRacunAsyncTask(dao).execute(racun);
    }

    public int vratiZadnjiBrojRacuna(){
        new vratiZadnjiBrojRacunaAsyncTask(dao).execute();
        long startTime = System.currentTimeMillis();
        while (1 == 1) {
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 50) {
                return zadnjiRacun;
            }
        }
    }
    public int vratiIdProizvoda(String naziv){
        new vratiIdProizvodaAsyncTask(dao).execute(naziv);
        long startTime = System.currentTimeMillis();
        while (1 == 1) {
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 50) {
                return zadnjiRacun;
            }
        }
    }


    public void unesiProizvodiNaRacunu(ProizvodiNaRacunu proizvodiNaRacunu) {
        new UnesiProizvodiNaRacunuAsyncTask(dao).execute(proizvodiNaRacunu);
    }

    public String prodavacSaNajviseIzdanihRacuna(String month, String year) {
        prod = null;
        new prodavacSaNajviseIzdanihRacunasyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                break;
            }
        }
        return prod;

    }

    public String prodavacPoMjesecuPoZupaniji(String month, String year, String zupanija) {
        prod = null;
        new prodavacPoMjesecuPoZupanijiAsyncTask(dao).execute(month, year, zupanija);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                break;
            }
        }
        return prod;
    }

    public String prodavacNajviseProizvodaPomjesecu(String month, String year, String proizvod) {
        prod = null;
        new prodavacNajviseProizvodaPomjesecuAsyncTask(dao).execute(month, year, proizvod);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                break;
            }
        }
        return prod;
    }

    public List<String> vratiSveProizvode() {
        imenaProizvoda.clear();
        new vratiSveProizvodeAsyncTask(dao).execute();
        long startTime = System.currentTimeMillis();
        while (1 == 1) {
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                return imenaProizvoda;
            }
        }

    }

    public List<String> vratiSveZupanije() {
        imenaZupanija.clear();
        new vratiSveZupanijeAsyncTask(dao).execute();
        long startTime = System.currentTimeMillis();
        while (1 == 1) {

            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                return imenaZupanija;
            }
        }
    }

    public String najprodavanijiProizvod(String month, String year) {
        prod = null;
        new najprodavanijiProizvodAsyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                break;
            }
        }
        return prod;
    }

    public String najprodavanijiProizvodProfitMjesecZupanija(String month, String year, String zupanija) {
        prod = null;
        new najprodavanijiProizvodProfitMjesecZupanijaAsyncTask(dao).execute(month, year, zupanija);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                break;
            }
        }
        return prod;
    }

    public String prodavacProdaoProizvodNaAkciji(String month, String year, String proizvod) {
        prod = null;
        new prodavacProdaoProizvodNaAkcijiAsyncTask(dao).execute(month, year, proizvod);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                break;
            }
        }
        return prod;
    }

    public String proizvodNajmanjiUdio(String month, String year) {
        prod = null;
        String s = null;
        new proizvodNajmanjiUdioAsyncTask(dao).execute(month, year);
        s = ukupnaZaradaPoMjesecu(month, year);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                break;
            }
        }
        return prod;
    }

    public String ukupnaZaradaPoMjesecu(String month, String year) {
        kup = null;
        new ukupnaZaradaPoMjesecuAsyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {


                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public String kupacKojiJePotrosioNajviseUMjesecu(String month, String year) {
        kup = null;
        new kupacKojiJePotrosioNajviseUMjesecuAsyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public String poslovnicaZaradaPoMjesecu(String month, String year) {
        kup = null;
        new poslovnicaZaradaPoMjesecuuAsyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    String zaradaOdredenePoslovniceUdio(String month, String year, String poslovnica) {
        kup = null;
        new zaradaOdredenePoslovniceUdioAsyncTask(dao).execute(month, year, poslovnica);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public String najvecaSvotaNaRacunu(String month, String year) {
        kup = null;
        new najvecaSvotaNaRacunuAsyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public String ZupanijaNajboljaProdajaProizvoda(String month, String year, String proizvod) {
        kup = null;
        new ZupanijaNajboljaProdajaProizvodaAsyncTask(dao).execute(month, year, proizvod);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public String prodavacIzdaoRacunZaredom(String month, String year) {
        prod = null;
        new prodavacIzdaoRacunZaredomAsyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 250) {
                break;
            }
        }
        return prod;
    }

    public String poslovnicaKojaNajboljeProdajeProizvod(String month, String year, String proizvod) {
        prod = null;
        new poslovnicaKojaNajboljeProdajeProizvodAsyncTask(dao).execute(month, year, proizvod);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 250) {
                break;
            }
        }
        return prod;
    }

    public Cursor poslovniceIznadMjesecnogCilja(String month, String year, String cilj) {
        kursor = null;
        new poslovniceIznadMjesecnogCiljaAsyncTask(dao).execute(month, year, cilj);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 250) {
                break;
            }
        }
        return kursor;

    }

    public String kupacNajviseDanaZAaredomKupovao(String month, String year) {
        kup = null;
        new kupacNajviseDanaZAaredomKupovaoAsyncTask(dao).execute(month, year);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public String kupacNajviseProizvodaPoMjesecu(String month, String year, String poslovnica) {
        kup = null;
        new kupacNajviseProizvodaPoMjesecuAsyncTask(dao).execute(month, year, poslovnica);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public List<String> vratiSveKupce() {
        kupci.clear();
        new vratiSveKupceAsyncTask(dao).execute();
        long startTime = System.currentTimeMillis();
        while (1 == 1) {
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 200) {
                return kupci;
            }
        }

    }

    public String kupacKupujeNajcesceUPoslovnici(String month, String year, String id) {
        kup = null;
        new kupacKupujeNajcesceUPoslovniciAsyncTask(dao).execute(month, year, id);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public String brojKupacaKojiSuPosjetiliPoslovnicu(String month, String year, String poslovnica) {
        kup = null;
        new brojKupacaKojiSuPosjetiliPoslovnicuAsyncTask(dao).execute(month, year, poslovnica);
        long startTime = System.currentTimeMillis();
        while (kup == null) {
            if (kup != null) {
                return kup;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kup;
    }

    public Cursor proizvodPoKvartalimaGodine(String year) {
        kursor = null;
        new proizvodPoKvartalimaGodineAsyncTask(dao).execute(year);
        long startTime = System.currentTimeMillis();
        while (kursor == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kursor;
    }

    public Cursor prodavacPoKvartalimaPostotak(String year) {
        kursor = null;
        new prodavacPoKvartalimaPostotakAsyncTask(dao).execute(year);
        long startTime = System.currentTimeMillis();
        while (kursor == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kursor;
    }

    public Cursor kupciPoKvartalimaGodine(String year) {
        kursor = null;
        new kupciPoKvartalimaGodineAsyncTask(dao).execute(year);
        long startTime = System.currentTimeMillis();
        while (kursor == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kursor;
    }

    public Cursor poslovnicaPrvoDrugoRazdoblje(String year, String poslovnica) {
        kursor = null;
        new poslovnicaPrvoDrugoRazdobljeAsyncTask(dao).execute(year, poslovnica);
        long startTime = System.currentTimeMillis();
        while (kursor == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kursor;
    }

    public Cursor prodavacPrekoOdredeneCifre(String year, String cifra) {
        kursor = null;
        new prodavacPrekoOdredeneCifreAsyncTask(dao).execute(year, cifra);
        long startTime = System.currentTimeMillis();
        while (kursor == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kursor;
    }

    public String prodavacIzdaoRacunaUGodini(String year) {
        prod = null;
        new prodavacIzdaoRacunaUGodiniAsyncTask(dao).execute(year);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 250) {
                break;
            }
        }
        return prod;
    }

    public Cursor brojRacunaPoKvartalima(String year) {
        kursor = null;
        new brojRacunaPoKvartalimaAsyncTask(dao).execute(year);
        long startTime = System.currentTimeMillis();
        while (kursor == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kursor;
    }

    public String najveciBrojStavkiNaRacunuPoGodini(String year) {
        prod = null;
        new najveciBrojStavkiNaRacunuPoGodiniAsyncTask(dao).execute(year);
        long startTime = System.currentTimeMillis();
        while (prod == null) {
            if (prod != null) {
                return prod;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 250) {
                break;
            }
        }
        return prod;
    }

    public LiveData<List<String>> popisSvihRacunaPomjesecuPoGodini(String month, String year) {
        sviRacuni = dao.popisSvihRacunaPomjesecuPoGodini(month, year);
        return sviRacuni;
    }

    public Cursor zaradaPoPrvomDrugomDijeluMjeseca(String year) {
        kursor = null;
        new zaradaPoPrvomDrugomDijeluMjesecaAsyncTask(dao).execute(year);
        long startTime = System.currentTimeMillis();
        while (kursor == null) {
            if (kursor != null) {
                return kursor;
            }
            long currTime = System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if (elapsedtime > 150) {
                break;
            }
        }
        return kursor;
    }
   /* public String helpFunction(long time){
        long startTime = System.currentTimeMillis();
        while(prod==null){
            if(prod!=null){
                return prod;
            }
            long currTime=  System.currentTimeMillis();
            long elapsedtime = currTime - startTime;
            if(elapsedtime > time)
            {
                break;
            }
        }
        return null;
    }*/


    private static class unesiKupcaAsyncTask extends AsyncTask<Kupac, Void, Void> {
        private Dao dao;

        private unesiKupcaAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Kupac... kupacs) {
            dao.unesiKupca(kupacs[0]);
            return null;
        }
    }


    private static class obrisiKupcaAsyncTask extends AsyncTask<Kupac, Void, Void> {
        private Dao dao;

        private obrisiKupcaAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Kupac... kupci) {
            dao.obrisiKupca(kupci[0]);
            return null;
        }
    }

    private static class promijeniPodatkeKupcaAsyncTask extends AsyncTask<Kupac, Void, Void> {
        private Dao dao;

        private promijeniPodatkeKupcaAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Kupac... kupci) {
            dao.promijeniPodatkeKupca(kupci[0]);
            return null;
        }
    }

    private static class prikaziSvePoslovniceAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private prikaziSvePoslovniceAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<String> lista = dao.prikaziSvePoslovniceLista();
            poslovnice.addAll(lista);
            return null;
        }

    }

    private static class vratiSvePostanskeBrojeveAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private vratiSvePostanskeBrojeveAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Integer> lista = dao.vratiSvePostanskeBrojeve();
            postanskiBrojevi.addAll(lista);
            return null;
        }

    }


    private static class unesiZaposlenikaAsyncTask extends AsyncTask<Prodavac, Void, Void> {
        private Dao dao;

        private unesiZaposlenikaAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Prodavac... prodavaci) {
            dao.unesiZaposlenika(prodavaci[0]);
            return null;
        }
    }

    private static class promijeniPodatkeZaposlenikaAsyncTask extends AsyncTask<Prodavac, Void, Void> {
        private Dao dao;

        private promijeniPodatkeZaposlenikaAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Prodavac... prodavaci) {
            dao.promijeniPodatkeZaposlenika(prodavaci[0]);
            return null;
        }
    }

    private static class obrisiZaposlenikaAsyncTask extends AsyncTask<Prodavac, Void, Void> {
        private Dao dao;

        private obrisiZaposlenikaAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Prodavac... prodavaci) {
            dao.obrisiZaposlenika(prodavaci[0]);
            return null;
        }
    }

    private static class unesiProizvodAsyncTask extends AsyncTask<Proizvod, Void, Void> {
        private Dao dao;

        private unesiProizvodAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Proizvod... proizvodi) {
            dao.unesiProizvod(proizvodi[0]);
            return null;
        }
    }

    private static class promijeniPodatkeProizvodaAsyncTask extends AsyncTask<Proizvod, Void, Void> {
        private Dao dao;

        private promijeniPodatkeProizvodaAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Proizvod... proizvodi) {
            dao.promijeniPodatkeProizvoda(proizvodi[0]);
            return null;
        }
    }

    private static class obrisiProizvodAsyncTask extends AsyncTask<Proizvod, Void, Void> {
        private Dao dao;

        private obrisiProizvodAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Proizvod... proizvodi) {
            dao.obrisiProizvod(proizvodi[0]);
            return null;
        }
    }

    private static class promijeniPodatkePoslovniceAsyncTask extends AsyncTask<Poslovnica, Void, Void> {
        private Dao dao;

        private promijeniPodatkePoslovniceAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Poslovnica... poslovnice) {
            dao.promijeniPodatkePoslovnice(poslovnice[0]);
            return null;
        }
    }

    private static class prodavacSaNajvecimPrihodomPoMjesecuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacSaNajvecimPrihodomPoMjesecuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.prodavacSaNajvecimPrihodomPoMjesecu(strings[0], strings[1]);
            Log.d("TAG", "doInBackground: " + prod);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //ModActivity.setRjesenjeBackgroundTask(prod);
        }
    }

    private static class UnesiProizvodiNaRacunuAsyncTask extends AsyncTask<ProizvodiNaRacunu, Void, Void> {
        private Dao dao;

        private UnesiProizvodiNaRacunuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(ProizvodiNaRacunu... proizvodiNaRacunu) {
            dao.unesiProizvodiNaRacunu(proizvodiNaRacunu[0]);
            return null;
        }
    }

    private static class vratiZadnjiBrojRacunaAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private vratiZadnjiBrojRacunaAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            zadnjiRacun = dao.vratiZadnjiBrojRacuna();
            return null;
        }
    }

    private static class vratiIdProizvodaAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private vratiIdProizvodaAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            zadnjiRacun = dao.vratiIdProizvoda(strings[0]);
            return null;
        }
    }
    private static class unesiRacunAsyncTask extends AsyncTask<Racun, Void, Void> {
        private Dao dao;

        private unesiRacunAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Racun... racuni) {
            dao.unesiRacun(racuni[0]);
            return null;
        }
    }

    private static class prodavacSaNajviseIzdanihRacunasyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacSaNajviseIzdanihRacunasyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.prodavacSaNajviseIzdanihRacuna(strings[0], strings[1]);
            return null;
        }
    }

    private static class prodavacPoMjesecuPoZupanijiAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacPoMjesecuPoZupanijiAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.prodavacPoMjesecuPoZupaniji(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class prodavacNajviseProizvodaPomjesecuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacNajviseProizvodaPomjesecuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.prodavacNajviseProizvodaPomjesecu(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class vratiSveProizvodeAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private vratiSveProizvodeAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            List<String> lista = dao.vratiSveProizvode();
            imenaProizvoda.addAll(lista);
            return null;
        }
    }

    private static class vratiSveZaposlenikeAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private vratiSveZaposlenikeAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            List<String> lista = dao.vratiSveZaposlenike();
            zaposlenici.addAll(lista);
            return null;
        }
    }

    private static class vratiSveKupceAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private vratiSveKupceAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            List<String> lista = dao.vratiSveKupce();
            kupci.addAll(lista);
            return null;
        }
    }

    private static class vratiSveZupanijeAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private vratiSveZupanijeAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            List<String> lista = dao.vratiSveZupanije();
            imenaZupanija.addAll(lista);
            return null;
        }
    }

    private static class najprodavanijiProizvodAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private najprodavanijiProizvodAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.najprodavanijiProizvod(strings[0], strings[1]);
            return null;
        }
    }

    private static class najprodavanijiProizvodProfitMjesecZupanijaAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private najprodavanijiProizvodProfitMjesecZupanijaAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.najprodavanijiProizvodProfitMjesecZupanija(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class prodavacProdaoProizvodNaAkcijiAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacProdaoProizvodNaAkcijiAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.prodavacProdaoProizvodNaAkciji(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class proizvodNajmanjiUdioAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private proizvodNajmanjiUdioAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.proizvodNajmanjiUdio(strings[0], strings[1]);
            return null;
        }
    }

    private static class ukupnaZaradaPoMjesecuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private ukupnaZaradaPoMjesecuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.ukupnaZaradaPoMjesecu(strings[0], strings[1]);
            return null;
        }
    }

    private static class kupacKojiJePotrosioNajviseUMjesecuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private kupacKojiJePotrosioNajviseUMjesecuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.kupacKojiJePotrosioNajviseUMjesecu(strings[0], strings[1]);
            return null;
        }
    }

    private static class poslovnicaZaradaPoMjesecuuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private poslovnicaZaradaPoMjesecuuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.poslovnicaZaradaPoMjesecu(strings[0], strings[1]);
            return null;
        }
    }

    private static class zaradaOdredenePoslovniceUdioAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private zaradaOdredenePoslovniceUdioAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.zaradaOdredenePoslovniceUdio(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class najvecaSvotaNaRacunuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private najvecaSvotaNaRacunuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.najvecaSvotaNaRacunu(strings[0], strings[1]);
            return null;
        }
    }

    private static class ZupanijaNajboljaProdajaProizvodaAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private ZupanijaNajboljaProdajaProizvodaAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.ZupanijaNajboljaProdajaProizvoda(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class prodavacIzdaoRacunZaredomAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacIzdaoRacunZaredomAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.prodavacIzdaoRacunZaredom(strings[0], strings[1]);
            return null;
        }
    }

    private static class poslovnicaKojaNajboljeProdajeProizvodAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private poslovnicaKojaNajboljeProdajeProizvodAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.poslovnicaKojaNajboljeProdajeProizvod(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class poslovniceIznadMjesecnogCiljaAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private poslovniceIznadMjesecnogCiljaAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.poslovniceIznadMjesecnogCilja(strings[0], strings[1], Integer.valueOf(strings[2]));
            return null;
        }
    }

    private static class kupacNajviseDanaZAaredomKupovaoAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private kupacNajviseDanaZAaredomKupovaoAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.kupacNajviseDanaZAaredomKupovao(strings[0], strings[1]);
            return null;
        }
    }

    private static class kupacNajviseProizvodaPoMjesecuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private kupacNajviseProizvodaPoMjesecuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.kupacNajviseProizvodaPoMjesecu(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class kupacKupujeNajcesceUPoslovniciAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private kupacKupujeNajcesceUPoslovniciAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.kupacKupujeNajcesceUPoslovnici(strings[0], strings[1], Integer.valueOf(strings[2]));
            return null;
        }
    }

    private static class brojKupacaKojiSuPosjetiliPoslovnicuAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private brojKupacaKojiSuPosjetiliPoslovnicuAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kup = dao.brojKupacaKojiSuPosjetiliPoslovnicu(strings[0], strings[1], strings[2]);
            return null;
        }
    }

    private static class proizvodPoKvartalimaGodineAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private proizvodPoKvartalimaGodineAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.proizvodPoKvartalimaGodine(strings[0]);
            return null;
        }
    }

    private static class prodavacPoKvartalimaPostotakAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacPoKvartalimaPostotakAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.prodavacPoKvartalimaPostotak(strings[0]);
            return null;
        }
    }


    private static class kupciPoKvartalimaGodineAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private kupciPoKvartalimaGodineAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.kupciPoKvartalimaGodine(strings[0]);
            return null;
        }
    }


    private static class poslovnicaPrvoDrugoRazdobljeAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private poslovnicaPrvoDrugoRazdobljeAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.poslovnicaPrvoDrugoRazdoblje(strings[0], strings[1]);
            return null;
        }
    }

    private static class prodavacPrekoOdredeneCifreAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacPrekoOdredeneCifreAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.prodavacPrekoOdredeneCifre(strings[0], Integer.valueOf(strings[1]));
            return null;
        }
    }

    private static class prodavacIzdaoRacunaUGodiniAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private prodavacIzdaoRacunaUGodiniAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.prodavacIzdaoRacunaUGodini(strings[0]);
            return null;
        }
    }

    private static class brojRacunaPoKvartalimaAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private brojRacunaPoKvartalimaAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.brojRacunaPoKvartalima(strings[0]);
            return null;
        }
    }

    private static class najveciBrojStavkiNaRacunuPoGodiniAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private najveciBrojStavkiNaRacunuPoGodiniAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            prod = dao.najveciBrojStavkiNaRacunuPoGodini(strings[0]);
            return null;
        }
    }

  /*  private static class popisSvihRacunaPomjesecuPoGodiniAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private popisSvihRacunaPomjesecuPoGodiniAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.popisSvihRacunaPomjesecuPoGodini(strings[0],strings[1]);
            return null;
        }
    }*/

    private static class zaradaPoPrvomDrugomDijeluMjesecaAsyncTask extends AsyncTask<String, Void, Void> {
        private Dao dao;

        private zaradaPoPrvomDrugomDijeluMjesecaAsyncTask(Dao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            kursor = dao.zaradaPoPrvomDrugomDijeluMjeseca(strings[0]);
            return null;
        }
    }
}
