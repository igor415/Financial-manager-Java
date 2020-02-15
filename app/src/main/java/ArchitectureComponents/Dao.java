package ArchitectureComponents;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import tablice.Kupac;
import tablice.Mjesto;
import tablice.Poslovnica;
import tablice.Prodavac;
import tablice.Proizvod;
import tablice.ProizvodiNaRacunu;
import tablice.Racun;
import tablice.Zupanija;

@android.arch.persistence.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void unesiKupca(Kupac kupac);

    @Insert
    void unesiZupaniju(Zupanija zupanija);

    @Insert
    void unesiMjesto(Mjesto mjesto);

    @Delete
    void obrisiKupca(Kupac kupac);

    @Update
    void promijeniPodatkeKupca(Kupac kupac);

    @Query("SELECT * FROM Kupac ORDER BY prezimeKupac ASC")
    LiveData<List<Kupac>> prikaziSveKupce();

    @Query("SELECT pbrMjesto FROM Mjesto ORDER BY pbrMjesto ASC")
    List<Integer> vratiSvePostanskeBrojeve();

    @Query("SELECT nazivProizvod FROM Proizvod")
    List<String> vratiSveProizvode();

    @Query("SELECT nazivZupanija FROM Zupanija")
    List<String> vratiSveZupanije();

    @Query("SELECT id || \"#\" || imeKupac  || \" \" || prezimeKupac FROM Kupac")
    List<String> vratiSveKupce();

    @Query("SELECT id || \"#\" || imeProdavac  || \" \" || prezimeProdavac FROM Prodavac")
    List<String> vratiSveZaposlenike();

    @Insert
    void unesiPoslovnicu(Poslovnica poslovnica);

    @Update
    void promijeniPodatkePoslovnice(Poslovnica poslovnica);

    @Query("SELECT id || \"#\" || nazivPoslovnica FROM Poslovnica")
    List<String> prikaziSvePoslovniceLista();

    @Query("SELECT * FROM Poslovnica")
    LiveData<List<Poslovnica>> prikaziSvePoslovnice();

    @Insert
    void unesiZaposlenika(Prodavac prodavac);

    @Query("SELECT * FROM Prodavac")
    LiveData<List<Prodavac>> prikaziSveZaposlenike();

    @Update
    void promijeniPodatkeZaposlenika(Prodavac prodavac);

    @Delete
    void obrisiZaposlenika(Prodavac prodavac);

    @Insert
    void unesiProizvod(Proizvod proizvod);

    @Query("SELECT * FROM Proizvod")
    LiveData<List<Proizvod>> prikaziSveProizvode();

    @Delete
    void obrisiProizvod(Proizvod proizvod);

    @Update
    void promijeniPodatkeProizvoda(Proizvod proizvod);

    @Insert
    void unesiRacun(Racun racun);

    @Insert
    void unesiProizvodiNaRacunu(ProizvodiNaRacunu proizvodiNaRacunu);

    @Query("select max(brojRacuna) from Racun ")
    int vratiZadnjiBrojRacuna();

    @Query("SELECT id from Proizvod " +
            "where nazivProizvod = :naziv ;")
    int vratiIdProizvoda(String naziv);

    @Query("SELECT SUM(pnr.kolicina*p.cijena) as ukupno " +
            "FROM Proizvod p JOIN ProizvodiNaRacunu pnr ON p.id = pnr.idProizvod " +
            "JOIN Racun r ON r.brojRacuna = pnr.idRacun where strftime(\"%m\",r.datum) = :month " +
            "and strftime(\"%Y\",r.datum) = :year ")
    String ukupnaZaradaPoMjesecu(String month, String year);

    @Query("SELECT x.imeProdavac || \" \" || x.prezimeProdavac || \" je uprihodio\\la \" || CAST(suma as TEXT) FROM(SELECT p.id,p.imeProdavac,p.prezimeProdavac,SUM(pnr.kolicina*pr.cijena) AS suma " +
            "FROM Prodavac p JOIN Racun r ON p.id = r.idProdavac JOIN ProizvodiNaRacunu pnr " +
            "ON pnr.idRacun = r.brojRacuna JOIN Proizvod pr ON pr.id = pnr.idProizvod " +
            "WHERE strftime('%m',r.datum)= :month AND strftime('%Y',r.datum)= :year  " +
            "GROUP BY p.id,p.imeProdavac,p.prezimeProdavac " +
            "ORDER BY SUM(pnr.kolicina*pr.cijena) DESC LIMIT 1) AS x;")
    String prodavacSaNajvecimPrihodomPoMjesecu(String month, String year);

    @Query("SELECT x.imeProdavac || \" \" || x.prezimeProdavac || \" je izdao/la \" || CAST(broj AS TEXT) FROM " +
            "(SELECT p.id,p.imeProdavac,p.prezimeProdavac,count(r.brojRacuna) as broj " +
            "FROM Racun r JOIN Prodavac p ON r.idProdavac = p.id " +
            "WHERE strftime(\"%m\",r.datum) = :month and strftime(\"%Y\",r.datum) = :year " +
            "GROUP BY p.id,p.imeProdavac,p.prezimeProdavac " +
            "ORDER BY count(r.brojRacuna) DESC LIMIT 1) AS x;")
    String prodavacSaNajviseIzdanihRacuna(String month, String year);

    @Query("select x.imeProdavac || \" \" || x.prezimeProdavac || \" je ostvario/la najveći prihod u \"  FROM " +
            "(SELECT p.id, p.imeProdavac, p.prezimeProdavac, p.pbrMjesto, z.nazivZupanija, " +
            "SUM(pnr.kolicina*pr.cijena) as suma " +
            "FROM Prodavac p JOIN Poslovnica pos ON p.idPoslovnica = pos.id JOIN Racun r ON p.id = r.idProdavac JOIN ProizvodiNaRacunu pnr ON r.brojRacuna = pnr.idRacun JOIN Proizvod pr ON pr.id " +
            "=pnr.idProizvod JOIN Mjesto m ON m.pbrMjesto = pos.pbrMjesto JOIN Zupanija z ON z.sifZupanija = m.sifZupanija " +
            "WHERE strftime(\"%m\",r.datum)= :month and strftime(\"%Y\",r.datum) = :year and z.nazivZupanija = :zupanija " +
            "GROUP BY p.id,p.imeProdavac,p.prezimeProdavac,z.nazivZupanija,p.pbrMjesto " +
            "ORDER BY SUM(pnr.kolicina*pr.cijena) DESC LIMIT 1) AS x;")
    String prodavacPoMjesecuPoZupaniji(String month, String year, String zupanija);

    @Query("select x.imeProdavac || \" \" || x.prezimeProdavac || \" je prodao/la u \" || x.broj  FROM " +
            "(SELECT p.id, p.imeProdavac, p.prezimeProdavac, SUM(pnr.kolicina) as broj " +
            "            FROM Prodavac p JOIN Racun r ON p.id = r.idProdavac JOIN ProizvodiNaRacunu pnr ON r.brojRacuna = pnr.idRacun JOIN Proizvod pr ON pr.id =pnr.idProizvod " +
            "            WHERE strftime(\"%m\",r.datum)= :month and strftime(\"%Y\",r.datum) = :year and pr.nazivProizvod = :proizvod " +
            "\t\t\tGROUP BY p.id,p.imeProdavac,p.prezimeProdavac " +
            "            ORDER BY SUM(pnr.kolicina) DESC LIMIT 1) AS x;")
    String prodavacNajviseProizvodaPomjesecu(String month,String year,String proizvod);

    @Query("SELECT x.nazivProizvod || \" je prodan u \" || x.kolicina FROM " +
            "(SELECT p.id,p.nazivProizvod,SUM(pnr.kolicina) as kolicina " +
            "FROM Proizvod p JOIN ProizvodiNaRacunu pnr ON p.id = pnr.idProizvod " +
            "JOIN Racun r ON r.brojRacuna = pnr.idRacun where strftime(\"%m\",r.datum) = :month " +
            "and strftime(\"%Y\",r.datum) = :year " +
            "GROUP BY p.id,p.nazivProizvod ORDER BY SUM(pnr.kolicina) DESC LIMIT 1) as x;")
    String najprodavanijiProizvod(String month, String year);

    @Query("SELECT x.nazivProizvod || \" je ostvario ukupan profit od \" || x.profit FROM " +
            "(SELECT p.id,p.nazivProizvod,SUM(pnr.kolicina*P.cijena) as profit " +
            "FROM Proizvod p JOIN ProizvodiNaRacunu pnr ON p.id = pnr.idProizvod " +
            "JOIN Racun r ON r.brojRacuna = pnr.idRacun  JOIN " +
            "Poslovnica pos ON pos.id = r.idPoslovnica JOIN Mjesto m " +
            "ON m.pbrMjesto = pos.pbrMjesto  JOIN Zupanija z ON z.sifZupanija = m.sifZupanija " +
            "where strftime(\"%m\",r.datum) = :month " +
            "and strftime(\"%Y\",r.datum) = :year and z.nazivZupanija = :zupanija " +
            "GROUP BY p.id,p.nazivProizvod ORDER BY SUM(pnr.kolicina) DESC LIMIT 1) as x;")
    String najprodavanijiProizvodProfitMjesecZupanija(String month,String year,String zupanija);

    @Query("SELECT x.imeProdavac || \" \" || x.prezimeProdavac || \" je prodao/la u \" || x.kolicina  FROM " +
            "(SELECT prod.imeProdavac,prod.prezimeProdavac,SUM(pnr.kolicina) as kolicina " +
            "FROM Proizvod p JOIN ProizvodiNaRacunu pnr ON p.id = pnr.idProizvod " +
            "JOIN Racun r ON r.brojRacuna = pnr.idRacun JOIN Prodavac prod ON prod.id = r.idProdavac where strftime(\"%m\",r.datum) = :month " +
            "and strftime(\"%Y\",r.datum) = :year and p.nazivProizvod = :proizvod " +
            "GROUP BY prod.imeProdavac,prod.prezimeProdavac ORDER BY SUM(pnr.kolicina) DESC LIMIT 1) as x;")
    String prodavacProdaoProizvodNaAkciji(String month, String year, String proizvod);

    @Query("SELECT x.nazivProizvod || \"#\" || x.ukupno FROM " +
            "(SELECT p.id,p.nazivProizvod,SUM(pnr.kolicina*p.cijena) as ukupno " +
            "FROM Proizvod p JOIN ProizvodiNaRacunu pnr ON p.id = pnr.idProizvod " +
            "JOIN Racun r ON r.brojRacuna = pnr.idRacun where strftime(\"%m\",r.datum) = :month " +
            "and strftime(\"%Y\",r.datum) = :year " +
            "GROUP BY p.id,p.nazivProizvod ORDER BY SUM(pnr.kolicina*p.cijena) LIMIT 1 ) as x;")
    String proizvodNajmanjiUdio(String month, String year);

    @Query("SELECT x.imeKupac || \" \" || x.prezimeKupac || \" je potrošio/la \" || x.profit FROM " +
            "(SELECT k.id,k.imeKupac,k.prezimeKupac, SUM(pnr.kolicina*p.cijena) as profit " +
            "FROM Racun r JOIN ProizvodiNaRacunu pnr ON r.brojRacuna = pnr.idRacun " +
            "JOIN Proizvod p ON p.id = pnr.idProizvod JOIN Kupac k ON k.id = r.idKupac " +
            "WHERE strftime(\"%m\",r.datum) = :month and strftime(\"%Y\",r.datum) = :year " +
            "GROUP BY k.id,k.imeKupac,k.prezimeKupac ORDER BY SUM(pnr.kolicina*p.cijena) DESC LIMIT 1) as x;")
    String kupacKojiJePotrosioNajviseUMjesecu(String month, String year);

    @Query("SELECT x.nazivPoslovnica || \" poslovnica je uprihodila \" || x.profit FROM " +
            "(SELECT pos.id,pos.nazivPoslovnica, SUM(pnr.kolicina*p.cijena) as profit " +
            "FROM Racun r JOIN ProizvodiNaRacunu pnr ON r.brojRacuna = pnr.idRacun " +
            "JOIN Proizvod p ON p.id = pnr.idProizvod JOIN Poslovnica pos ON pos.id = r.idPoslovnica " +
            "WHERE strftime(\"%m\",r.datum) = :month and strftime(\"%Y\",r.datum) = :year " +
            "GROUP BY pos.id,pos.nazivPoslovnica ORDER BY SUM(pnr.kolicina*p.cijena) DESC LIMIT 1) as x;")
    String poslovnicaZaradaPoMjesecu(String month, String year);

    @Query("SELECT SUM(pnr.kolicina*p.cijena) as profit " +
            "FROM Racun r JOIN ProizvodiNaRacunu pnr ON r.brojRacuna = pnr.idRacun " +
            "JOIN Proizvod p ON p.id = pnr.idProizvod JOIN Poslovnica pos ON pos.id = r.idPoslovnica " +
            "WHERE strftime(\"%m\",r.datum) = :month and strftime(\"%Y\",r.datum) = :year and pos.nazivPoslovnica = :poslovnica ;")
    String zaradaOdredenePoslovniceUdio(String month, String year,String poslovnica);

    @Query("SELECT x.brojRacuna || \" je izdan u poslovnici \" || x.nazivPoslovnica || \" sa ukupnom svotom novca od \" || x.ukupnoNaRacunu " +
            "FROM " +
            "(SELECT r.brojRacuna,pos.nazivPoslovnica,SUM(pnr.kolicina*p.cijena) as ukupnoNaRacunu " +
            "FROM Racun r JOIN ProizvodiNaRacunu pnr ON r.brojRacuna = pnr.idRacun JOIN Proizvod p ON p.id = pnr.idProizvod " +
            "JOIN Poslovnica pos ON pos.id = r.idPoslovnica " +
            "WHERE strftime(\"%m\",r.datum)=:month and strftime(\"%Y\",r.datum)= :year " +
            "GROUP BY r.brojRacuna,pos.nazivPoslovnica ORDER BY SUM(pnr.kolicina*p.cijena) DESC LIMIT 1) AS x;")
    String najvecaSvotaNaRacunu(String month, String year);

    @Query("SELECT x.nazivZupanija || \"#\" || x.suma || \"#\" || SUM(suma)  FROM " +
            "(SELECT z.nazivZupanija,SUM(pnr.kolicina) as suma " +
            "FROM Racun r JOIN ProizvodiNaRacunu pnr ON r.brojRacuna = pnr.idRacun JOIN Proizvod p ON p.id = pnr.idProizvod " +
            "JOIN Poslovnica pos ON pos.id = r.idPoslovnica JOIN Mjesto m ON m.pbrMjesto = pos.pbrMjesto JOIN " +
            "Zupanija z ON z.sifZupanija = m.sifZupanija " +
            "WHERE strftime(\"%m\",r.datum)= :month and strftime(\"%Y\",r.datum)= :year and p.nazivProizvod = :proizvod " +
            "GROUP BY z.nazivZupanija ORDER BY SUM(pnr.kolicina) DESC) AS x;")
    String ZupanijaNajboljaProdajaProizvoda(String month, String year,String proizvod);

    @Query("select x.imePrezime || \" je ostvario/la niz od \" || max(x.zbroj) from " +
            "(SELECT prod.id,prod.imeProdavac || \" \" || prezimeProdavac as imePrezime, " +
            "strftime(\"%d\",r.datum)-(SELECT COUNT(*) FROM Racun p where p.idProdavac = prod.id and p.datum <= r.datum ) AS grupa, " +
            "COUNT(strftime(\"%d\",r.datum)-(SELECT COUNT(*) FROM Racun p where p.idProdavac = prod.id and p.datum <= r.datum)) as zbroj " +
            "FROM Racun r JOIN Prodavac prod ON r.idProdavac = prod.id WHERE strftime(\"%m\",r.datum)= :month and strftime(\"%Y\",r.datum) = :year " +
            " group by prod.id,prod.imeProdavac || \" \" || prezimeProdavac,grupa) as x;")
    String prodavacIzdaoRacunZaredom(String month, String year);

    @Query("select x.nazivPoslovnica || \" je ostvarila profit od \" || x.profit from " +
            "(select pos.nazivPoslovnica, SUM(pnr.kolicina*p.cijena) as profit " +
            "from Racun r join Poslovnica pos on r.idPoslovnica = pos.id join ProizvodiNaRacunu " +
            "pnr on pnr.idRacun = r.brojRacuna join Proizvod p on p.id = pnr.idProizvod " +
            "where p.nazivProizvod = :proizvod and strftime(\"%m\",r.datum)=:month " +
            "and strftime(\"%Y\",r.datum) = :year " +
            "group by pos.nazivPoslovnica order by SUM(pnr.kolicina*p.cijena) desc limit 1) as x;")
    String poslovnicaKojaNajboljeProdajeProizvod(String month, String year, String proizvod);

    @Query("select pos.nazivPoslovnica, SUM(pnr.kolicina*p.cijena) as profit " +
            "from Racun r join Poslovnica pos on r.idPoslovnica = pos.id join ProizvodiNaRacunu " +
            "pnr on pnr.idRacun = r.brojRacuna join Proizvod p on p.id = pnr.idProizvod " +
            "where strftime(\"%m\",r.datum)= :month and strftime(\"%Y\",r.datum) = :year " +
            "group by pos.nazivPoslovnica having SUM(pnr.kolicina*p.cijena) > :cilj " +
            "order by SUM(pnr.kolicina*p.cijena) desc")
    Cursor poslovniceIznadMjesecnogCilja(String month,String year,int cilj);

    @Query("select x.imePrezime || \" je ostvario/la niz od \" || max(x.zbroj) from " +
            "(SELECT kup.id,kup.imekupac || \" \" || kup.prezimeKupac as imePrezime, " +
            "strftime(\"%d\",r.datum)-(SELECT COUNT(*) FROM Racun p where p.idKupac = kup.id and p.datum <= r.datum ) AS grupa, " +
            "COUNT(strftime(\"%d\",r.datum)-(SELECT COUNT(*) FROM Racun p where p.idKupac = kup.id and p.datum <= r.datum)) as zbroj " +
            "FROM Racun r JOIN Kupac kup ON r.idKupac = kup.id WHERE strftime(\"%m\",r.datum)= :month and strftime(\"%Y\",r.datum) = :year " +
            "group by kup.id,kup.imekupac || \" \" || kup.prezimeKupac,grupa) as x;")
    String kupacNajviseDanaZAaredomKupovao(String month,String year);

    @Query("select x.imePrezime || \" je kupio/la \" || x.suma  from " +
            "(SELECT kup.id,kup.imekupac || \" \" || kup.prezimeKupac as imePrezime, SUM(pnr.kolicina) as suma " +
            "FROM Racun r JOIN Kupac kup ON r.idKupac = kup.id join ProizvodiNaRacunu pnr on pnr.idRacun = r.brojRacuna " +
            "join Poslovnica pos on pos.id = r.idPoslovnica where strftime(\"%m\",r.datum) = :month and strftime(\"%Y\",r.datum) = :year " +
            "and pos.nazivPoslovnica = :poslovnica GROUP by kup.id,kup.imekupac || \" \" || kup.prezimeKupac " +
            "order by SUM(pnr.kolicina) desc limit 1) as x ;")
    String kupacNajviseProizvodaPoMjesecu(String month, String year, String poslovnica);

    @Query("select \" kupuje najčešće u poslovnici \" || x.nazivPoslovnica  from " +
            "(SELECT pos.id,pos.nazivPoslovnica, count(r.brojRacuna) as zbroj " +
            "FROM Racun r JOIN Kupac kup ON r.idKupac = kup.id join ProizvodiNaRacunu pnr on pnr.idRacun = r.brojRacuna " +
            "join Poslovnica pos on pos.id = r.idPoslovnica where strftime(\"%m\",r.datum) = :month and strftime(\"%Y\",r.datum) = :year " +
            "and kup.id= :id GROUP by pos.id,pos.nazivPoslovnica " +
            "order by count(r.brojRacuna) desc limit 1 ) as x ;")
    String kupacKupujeNajcesceUPoslovnici(String month, String year,int id);

    @Query("SELECT count(distinct idKupac) " +
            "from Poslovnica pos join Racun r on r.idPoslovnica = pos.id " +
            "where strftime(\"%m\",r.datum) = :month and strftime(\"%Y\",r.datum) = :year " +
            "and pos.nazivPoslovnica = :poslovnica; ")
    String brojKupacaKojiSuPosjetiliPoslovnicu(String month, String year, String poslovnica);

    @Query("select x.kvartal,x.nazivProizvod,cast(max(x.zbroj) as text) as broj from " +
            "(select case when strftime(\"%m\",r.datum) between '01' and '03' then 'prvo' " +
            "when strftime(\"%m\",r.datum) between '04' and '06' then 'drugo' " +
            "when strftime(\"%m\",r.datum) between '07' and '09' then 'trece' " +
            "else 'cetvrto' end as kvartal " +
            ", p.nazivProizvod,SUM(pnr.kolicina) as zbroj " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "join Proizvod p on p.id = pnr.idProizvod where strftime(\"%Y\",r.datum) = :year " +
            "group by kvartal,p.nazivProizvod) as x " +
            "group by kvartal;")
    Cursor proizvodPoKvartalimaGodine(String year);

    @Query("select x.kvartal,x.imePrezime,cast(max(x.zbroj) as text) as zbroj,cast(sum(x.zbroj) as text) as suma from " +
            "(select case when strftime(\"%m\",r.datum) between '01' and '03' then 'prvo' " +
            "when strftime(\"%m\",r.datum) between '04' and '06' then 'drugo' " +
            "when strftime(\"%m\",r.datum) between '07' and '09' then 'trece' " +
            "else 'cetvrto' end as kvartal " +
            ", prod.id, prod.imeProdavac || \" \" || prod.prezimeProdavac as imePrezime ,SUM(pnr.kolicina*p.cijena) as zbroj " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "join Proizvod p on p.id = pnr.idProizvod " +
            "join Prodavac prod on prod.id = r.idProdavac " +
            "where strftime(\"%Y\",r.datum) = :year " +
            "group by kvartal,p.nazivProizvod) as x " +
            "group by kvartal;")
    Cursor prodavacPoKvartalimaPostotak(String year);

    @Query("select x.kvartal,x.imePrezime,max(x.zbroj) as potrosnja from " +
            "(select case when strftime(\"%m\",r.datum) between '01' and '03' then 'prvo' " +
            "when strftime(\"%m\",r.datum) between '04' and '06' then 'drugo' " +
            "when strftime(\"%m\",r.datum) between '07' and '09' then 'trece' " +
            "else 'cetvrto' end as kvartal " +
            ", k.id,k.imeKupac || \" \" || k.prezimeKupac as imePrezime,SUM(pnr.kolicina*p.cijena) as zbroj " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "join Proizvod p on p.id = pnr.idProizvod join Kupac k on k.id = r.idKupac " +
            "where strftime(\"%Y\",r.datum) = :year " +
            "group by kvartal,k.id,k.imeKupac || \" \" || k.prezimeKupac) as x " +
            "group by kvartal;")
    Cursor kupciPoKvartalimaGodine(String year);

    @Query("select x.kvartal,MAX(x.zbroj) as broj from " +
            "(select case when strftime(\"%m\",r.datum) between '01' and '06' then 'prvo' " +
            "when strftime(\"%m\",r.datum) between '07' and '12' then 'drugo' " +
            "end as kvartal " +
            ",SUM(pnr.kolicina*p.cijena) as zbroj " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun join " +
            "Proizvod p on p.id = pnr.idProizvod join Poslovnica pos on pos.id = r.idPoslovnica " +
            "where strftime(\"%Y\",r.datum) = :year and pos.nazivPoslovnica = :poslovnica " +
            "group by kvartal) as x")
    Cursor poslovnicaPrvoDrugoRazdoblje(String year,String poslovnica);

    @Query("select p.id,p.imeProdavac || \" \" || p.prezimeProdavac as imePrezime, sum(pnr.kolicina*pr.cijena) as zarada " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "join Prodavac p on p.id = r.idProdavac join Proizvod pr on pr.id = pnr.idProizvod " +
            "where strftime(\"%Y\",r.datum) = :year " +
            "group by  p.id,p.imeProdavac || \" \" || p.prezimeProdavac " +
            "having sum(pnr.kolicina*pr.cijena) > :cifra ;")
    Cursor prodavacPrekoOdredeneCifre(String year,int cifra);

    @Query("select x.imePrezime || \" je izdao/la račun u \" || x.broj from " +
            "(select p.id,p.imeProdavac || \" \" || p.prezimeProdavac as imePrezime, COUNT(distinct r.datum) as broj from " +
            "Racun r join Prodavac p on r.idProdavac = p.id " +
            "where strftime(\"%Y\",r.datum) = :year " +
            "group by p.id,p.imeProdavac || \" \" || p.prezimeProdavac " +
            "order by COUNT(distinct r.datum) desc limit 1) as x;")
    String prodavacIzdaoRacunaUGodini(String year);

    @Query("select x.kvartal,cast(x.brojRacuna as text) as sifra,cast(max(x.zbroj) as text) as kolicina from " +
            "(select case when strftime(\"%m\",r.datum) between '01' and '03' then 'prvo' " +
            "when strftime(\"%m\",r.datum) between '04' and '06' then 'drugo' " +
            "when strftime(\"%m\",r.datum) between '07' and '09' then 'trece' " +
            "else 'cetvrto' end as kvartal " +
            ", r.brojracuna,count(pnr.idRacun) as zbroj " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "where strftime(\"%Y\",r.datum) = :year " +
            "group by kvartal,r.brojracuna) as x " +
            "group by kvartal;")
    Cursor brojRacunaPoKvartalima(String year);

    @Query("select \"Račun broj \" || x.brojRacuna || \" je izdan u poslovnici \" || x.nazivPoslovnica || \" sa \" || x.broj from " +
            "(select r.brojRacuna,pos.id,pos.nazivPoslovnica,count(pnr.idRacun) as broj " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "join Poslovnica pos on pos.id = r.idPoslovnica " +
            "where strftime(\"%Y\",r.datum)=:year " +
            "group by r.brojRacuna,pos.id,pos.nazivPoslovnica " +
            "order by count(pnr.idRacun) desc limit 1) as x;")
    String najveciBrojStavkiNaRacunuPoGodini(String year);

    @Query("select \"broj računa: \" || x.brojRacuna || \", iznos: \" || x.novci || \" kn\" ||\", \ndatum: \" || x.datum  from " +
            "(select r.brojRacuna,r.datum, sum(pnr.kolicina*p.cijena) as novci " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "join Proizvod p on p.id = pnr.idProizvod " +
            "where strftime(\"%m\",r.datum)=:month and strftime(\"%Y\",r.datum)=:year " +
            "group by r.brojRacuna,r.datum order by r.brojRacuna asc) as x;")
    LiveData<List<String>> popisSvihRacunaPomjesecuPoGodini(String month,String year);

    @Query("select x.dijelovi, cast(sum(x.cifra) as text) as zarada from " +
            "(SELECT case when strftime(\"%d\",r.datum) between '01' and '15' then 'prvi_dio' " +
            "when strftime(\"%d\",r.datum) between '16' and '31' then 'drugi_dio' " +
            "end as dijelovi,r.brojRacuna,sum(pnr.kolicina*p.cijena) as cifra " +
            "from Racun r join ProizvodiNaRacunu pnr on r.brojRacuna = pnr.idRacun " +
            "join Proizvod p on p.id = pnr.idProizvod " +
            "where strftime(\"%Y\",r.datum)=:year " +
            "group by dijelovi,r.brojRacuna) as x " +
            "GROUP by dijelovi")
    Cursor zaradaPoPrvomDrugomDijeluMjeseca(String year);

}

