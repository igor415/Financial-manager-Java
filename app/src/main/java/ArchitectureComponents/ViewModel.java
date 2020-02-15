package ArchitectureComponents;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import tablice.Kupac;
import tablice.Poslovnica;
import tablice.Prodavac;
import tablice.Proizvod;
import tablice.ProizvodiNaRacunu;
import tablice.Racun;

public class ViewModel extends AndroidViewModel {
    private Repozitorij repozitorij;
    private LiveData<List<Kupac>> kupci;
    private LiveData<List<Proizvod>> proizvodi;
    private LiveData<List<Prodavac>> zaposlenici;
    private LiveData<List<Poslovnica>> poslovnice;

    public ViewModel(@NonNull Application application) {
        super(application);
        repozitorij = new Repozitorij(application);
        kupci = repozitorij.prikaziSveKupce();
        zaposlenici = repozitorij.prikaziSveZaposlenike();
        proizvodi = repozitorij.prikaziSveProizvode();
        poslovnice = repozitorij.prikaziSvePoslovniceLive();
    }

    public void unesiKupca(Kupac kupac) {
        repozitorij.unesiKupca(kupac);

    }

    public void obrisiKupca(Kupac kupac) {
        repozitorij.obrisiKupca(kupac);

    }


    public void unesiZaposlenika(Prodavac prodavac) {
        repozitorij.unesiZaposlenika(prodavac);
    }

    public LiveData<List<Prodavac>> prikaziSveZaposlenike() {
        return zaposlenici;
    }

    public void promijeniPodatkeKupca(Kupac kupac) {
        repozitorij.promijeniPodatkeKupca(kupac);
    }


    public LiveData<List<Kupac>> prikaziSveKupce() {
        return kupci;
    }

    public void promijeniPodatkeZaposlenika(Prodavac prodavac) {
        repozitorij.promijeniPodatkeZaposlenika(prodavac);
    }

    public void obrisiZaposlenika(Prodavac prodavac) {
        repozitorij.obrisiZaposlenika(prodavac);
    }

    public void unesiProizvod(Proizvod proizvod) {
        repozitorij.unesiProizvod(proizvod);
    }

    public LiveData<List<Proizvod>> prikaziSveProizvode() {
        return proizvodi;
    }

    public void promijeniPodatkePoslovnice(Poslovnica poslovnica) {
        repozitorij.promijeniPodatkePoslovnice(poslovnica);
    }

    public List<String> prikaziSvePoslovnice() {
        return repozitorij.prikaziSvePoslovnice();
    }

    public void obrisiProizvod(Proizvod proizvod) {
        repozitorij.obrisiProizvod(proizvod);
    }

    public void promijeniPodatkeProizvoda(Proizvod proizvod) {
        repozitorij.promijeniPodatkeProizvoda(proizvod);
    }

    public String prodavacSaNajvecimPrihodomPoMjesecu(String month, String year) {
        return repozitorij.prodavacSaNajvecimPrihodomPoMjesecu(month, year);
    }

    public void unesiRacun(Racun racun) {
        repozitorij.unesiRacun(racun);
    }

    public LiveData<List<Poslovnica>> prikaziSvePoslovniceLive() {
        return poslovnice;
    }

    public void unesiProizvodiNaRacunu(ProizvodiNaRacunu proizvodiNaRacunu) {
        repozitorij.unesiProizvodiNaRacunu(proizvodiNaRacunu);
    }

    public String prodavacSaNajviseIzdanihRacuna(String month, String year) {
        return repozitorij.prodavacSaNajviseIzdanihRacuna(month, year);
    }

    public String prodavacPoMjesecuPoZupaniji(String month, String year, String zupanija) {
        return repozitorij.prodavacPoMjesecuPoZupaniji(month, year, zupanija);
    }

    public String prodavacNajviseProizvodaPomjesecu(String month, String year, String proizvod) {
        return repozitorij.prodavacNajviseProizvodaPomjesecu(month, year, proizvod);
    }

    public List<String> vratiSveZupanije() {
        return repozitorij.vratiSveZupanije();
    }

    public List<String> vratiSveZaposlenike() {
        return repozitorij.vratiSveZaposlenike();
    }

    public int vratiZadnjiBrojRacuna() {
        return repozitorij.vratiZadnjiBrojRacuna();
    }

    public int vratiIdProizvoda(String naziv) {
        return repozitorij.vratiIdProizvoda(naziv);
    }

    public List<String> vratiSveProizvode() {
        return repozitorij.vratiSveProizvode();
    }

    public String najprodavanijiProizvod(String month, String year) {
        return repozitorij.najprodavanijiProizvod(month, year);
    }

    public String najprodavanijiProizvodProfitMjesecZupanija(String month, String year, String zupanija) {
        Log.d("TAG", "najprodavanijiProizvodProfitMjesecZupanija: " + repozitorij.najprodavanijiProizvodProfitMjesecZupanija(month, year, zupanija));
        return repozitorij.najprodavanijiProizvodProfitMjesecZupanija(month, year, zupanija);
    }

    public String prodavacProdaoProizvodNaAkciji(String month, String year, String proizvod) {
        return repozitorij.prodavacProdaoProizvodNaAkciji(month, year, proizvod);
    }

    public String proizvodNajmanjiUdio(String month, String year) {
        return repozitorij.proizvodNajmanjiUdio(month, year);
    }

    public String ukupnaZaradaPoMjesecu(String month, String year) {
        return repozitorij.ukupnaZaradaPoMjesecu(month, year);
    }

    public String kupacKojiJePotrosioNajviseUMjesecu(String month, String year) {
        return repozitorij.kupacKojiJePotrosioNajviseUMjesecu(month, year);
    }

    public String poslovnicaZaradaPoMjesecu(String month, String year) {
        return repozitorij.poslovnicaZaradaPoMjesecu(month, year);
    }

    public String zaradaOdredenePoslovniceUdio(String month, String year, String poslovnica) {
        return repozitorij.zaradaOdredenePoslovniceUdio(month, year, poslovnica);
    }

    public String najvecaSvotaNaRacunu(String month, String year) {
        return repozitorij.najvecaSvotaNaRacunu(month, year);
    }

    public String ZupanijaNajboljaProdajaProizvoda(String month, String year, String proizvod) {
        return repozitorij.ZupanijaNajboljaProdajaProizvoda(month, year, proizvod);
    }

    public String prodavacIzdaoRacunZaredom(String month, String year) {
        return repozitorij.prodavacIzdaoRacunZaredom(month, year);
    }

    public String poslovnicaKojaNajboljeProdajeProizvod(String month, String year, String proizvod) {
        return repozitorij.poslovnicaKojaNajboljeProdajeProizvod(month, year, proizvod);
    }

    public Cursor poslovniceIznadMjesecnogCilja(String month, String year, String cilj) {
        return repozitorij.poslovniceIznadMjesecnogCilja(month, year, cilj);
    }

    public String kupacNajviseDanaZAaredomKupovao(String month, String year) {
        return repozitorij.kupacNajviseDanaZAaredomKupovao(month, year);
    }

    public String kupacNajviseProizvodaPoMjesecu(String month, String year, String poslovnica) {
        return repozitorij.kupacNajviseProizvodaPoMjesecu(month, year, poslovnica);
    }

    public List<String> vratiSveKupce() {
        return repozitorij.vratiSveKupce();
    }

    public String kupacKupujeNajcesceUPoslovnici(String month, String year, String id) {
        return repozitorij.kupacKupujeNajcesceUPoslovnici(month, year, id);
    }

    public String brojKupacaKojiSuPosjetiliPoslovnicu(String month, String year, String poslovnica) {
        return repozitorij.brojKupacaKojiSuPosjetiliPoslovnicu(month, year, poslovnica);
    }

    public Cursor proizvodPoKvartalimaGodine(String year) {
        return repozitorij.proizvodPoKvartalimaGodine(year);
    }

    public Cursor prodavacPoKvartalimaPostotak(String year) {
        return repozitorij.prodavacPoKvartalimaPostotak(year);
    }

    public Cursor kupciPoKvartalimaGodine(String year) {
        return repozitorij.kupciPoKvartalimaGodine(year);
    }

    public Cursor poslovnicaPrvoDrugoRazdoblje(String year, String poslovnica) {
        return repozitorij.poslovnicaPrvoDrugoRazdoblje(year, poslovnica);
    }

    public Cursor prodavacPrekoOdredeneCifre(String year, String cifra) {
        return repozitorij.prodavacPrekoOdredeneCifre(year, cifra);
    }

    public String prodavacIzdaoRacunaUGodini(String year) {
        return repozitorij.prodavacIzdaoRacunaUGodini(year);
    }

    public Cursor brojRacunaPoKvartalima(String year) {
        return repozitorij.brojRacunaPoKvartalima(year);
    }

    public String najveciBrojStavkiNaRacunuPoGodini(String year) {
        return repozitorij.najveciBrojStavkiNaRacunuPoGodini(year);
    }

    public LiveData<List<String>> popisSvihRacunaPomjesecuPoGodini(String month, String year) {
        return repozitorij.popisSvihRacunaPomjesecuPoGodini(month, year);
    }

    public Cursor zaradaPoPrvomDrugomDijeluMjeseca(String year) {
        return repozitorij.zaradaPoPrvomDrugomDijeluMjeseca(year);
    }

}
