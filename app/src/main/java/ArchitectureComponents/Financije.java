package ArchitectureComponents;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import tablice.Kupac;
import tablice.Mjesto;
import tablice.Poslovnica;
import tablice.Prodavac;
import tablice.Proizvod;
import tablice.ProizvodiNaRacunu;
import tablice.Racun;
import tablice.Zupanija;

@android.arch.persistence.room.Database(entities = {Kupac.class, Proizvod.class, Prodavac.class, Mjesto.class, Zupanija.class, Racun.class,
        ProizvodiNaRacunu.class, Poslovnica.class},version = 2,exportSchema = false)
public abstract class Financije extends RoomDatabase {
    private static Financije instance;
    public abstract Dao dao();
    public static synchronized Financije getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), Financije.class,"Financije").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new inicijalnoPopunjavanjeAsyncTask(instance).execute();
        }

    };
    private static class inicijalnoPopunjavanjeAsyncTask extends AsyncTask<Void,Void,Void>{
        private Dao dao;
        private inicijalnoPopunjavanjeAsyncTask(Financije db){
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.unesiZupaniju(new Zupanija("Primorsko-goranska županija"));
            dao.unesiZupaniju(new Zupanija("Grad Zagreb"));
            dao.unesiZupaniju(new Zupanija("Osječko-baranjska županija"));
            dao.unesiMjesto(new Mjesto(10000,"Zagreb",2));
            dao.unesiMjesto(new Mjesto(51000,"Rijeka",1));
            dao.unesiMjesto(new Mjesto(31000,"Osijek",3));
            dao.unesiPoslovnicu(new Poslovnica("Zitnjak","",10000));
            dao.unesiPoslovnicu(new Poslovnica("Jankomir","",10000));
            dao.unesiPoslovnicu(new Poslovnica("Ilica","",10000));
            dao.unesiPoslovnicu(new Poslovnica("Novi Zagreb","",10000));
            dao.unesiZaposlenika(new Prodavac("Nikola","Bacic","",1,10000));
            dao.unesiZaposlenika(new Prodavac("Karlo","Krsnik","",1,10000));
            dao.unesiZaposlenika(new Prodavac("Katarina","Dobrina","",1,10000));
            dao.unesiZaposlenika(new Prodavac("Nikola","Medvedec","",2,10000));
            dao.unesiZaposlenika(new Prodavac("Edita","Domijan","",2,10000));
            dao.unesiZaposlenika(new Prodavac("Iva","Mioc","",2,10000));
            dao.unesiZaposlenika(new Prodavac("Dominik","Hacek","",3,10000));
            dao.unesiZaposlenika(new Prodavac("Denis","Pauk","",3,10000));
            dao.unesiZaposlenika(new Prodavac("Gorana","Bozic","",3,10000));
            dao.unesiZaposlenika(new Prodavac("Petra","Culjak","",4,10000));
            dao.unesiZaposlenika(new Prodavac("Davor","Jurinjak","",4,10000));
            dao.unesiZaposlenika(new Prodavac("Josip","Dukic","",4,10000));
            dao.unesiKupca(new Kupac("Marin","Maric","",0,10000));
            dao.unesiKupca(new Kupac("Luka","Jokic","",0,10000));
            dao.unesiKupca(new Kupac("Stipe","Mutivoda","",0,10000));
            dao.unesiKupca(new Kupac("Stipe","Mesic","",0,10000));
            dao.unesiKupca(new Kupac("Roko","Stoka","",0,10000));
            dao.unesiKupca(new Kupac("Mijo","Jusic","",0,10000));
            dao.unesiKupca(new Kupac("Petar","Zoranic","",0,10000));
            dao.unesiKupca(new Kupac("Vanja","Velic","",0,10000));
            dao.unesiKupca(new Kupac("Katarina","Gavran","",0,10000));
            dao.unesiProizvod(new Proizvod("SSD 120GB WD Green",246));
            dao.unesiProizvod(new Proizvod("SSD 256GB Gigabyte",349));
            dao.unesiProizvod(new Proizvod("SSD 240GB Kingston",381));
            dao.unesiProizvod(new Proizvod("Napajanje NaviaTech 400W",170));
            dao.unesiProizvod(new Proizvod("Napajanje Xilence 500W",251));
            dao.unesiProizvod(new Proizvod("Napajanje AKYGA 700W",299));
            dao.unesiProizvod(new Proizvod("Patriot Signature DDR4 2666Mhz 4GB",199));
            dao.unesiProizvod(new Proizvod("Kingston DDR4 2400MHz 8GB",369));
            dao.unesiProizvod(new Proizvod("Memorija G.Skill Aegis 8GB DDR4 3200MHz",349));
            dao.unesiProizvod(new Proizvod("Kingmax Zeus Gaming 16GB DDR4 3000MHz",466));
            dao.unesiProizvod(new Proizvod("Crucial 16GB DDR4 3200 MT/s",724));
            dao.unesiProizvod(new Proizvod("Procesor AMD Ryzen 3 1200",349));
            dao.unesiProizvod(new Proizvod("Procesor Intel Core i3 9100F ",722));
            dao.unesiProizvod(new Proizvod("Procesor AMD Ryzen 5 1600 AF",799));
            dao.unesiProizvod(new Proizvod("Procesor Intel core i5 9400F",1249));
            dao.unesiProizvod(new Proizvod("Procesor AMD Ryzen Threadripper",1303));
            dao.unesiProizvod(new Proizvod("Procesor Intel core i5 9600K",2029));
            dao.unesiProizvod(new Proizvod("Procesor AMD Ryzen 5 3600",1845));
            dao.unesiRacun(new Racun(1,1,8,"2020-06-01"));
            dao.unesiRacun(new Racun(2,1,8,"2020-06-01"));
            dao.unesiRacun(new Racun(3,1,7,"2020-06-02"));
            dao.unesiRacun(new Racun(4,2,6,"2020-06-03"));
            dao.unesiRacun(new Racun(5,2,4,"2020-06-03"));
            dao.unesiRacun(new Racun(7,3,1,"2020-06-05"));
            dao.unesiRacun(new Racun(8,3,2,"2020-06-05"));
            dao.unesiRacun(new Racun(10,4,5,"2020-06-06"));
            dao.unesiRacun(new Racun(10,4,5,"2020-06-07"));
            dao.unesiRacun(new Racun(10,4,5,"2020-10-10"));
            dao.unesiRacun(new Racun(10,4,5,"2020-11-10"));
            dao.unesiRacun(new Racun(11,4,2,"2020-07-19"));
            dao.unesiRacun(new Racun(11,4,2,"2020-07-10"));
            dao.unesiRacun(new Racun(10,4,1,"2020-07-11"));
            dao.unesiRacun(new Racun(10,4,1,"2020-11-11"));
            dao.unesiRacun(new Racun(10,4,1,"2020-11-12"));
            dao.unesiRacun(new Racun(10,4,1,"2020-11-13"));
            dao.unesiRacun(new Racun(11,4,2,"2020-03-13"));
            dao.unesiRacun(new Racun(11,4,2,"2020-03-14"));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(1,4,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(2,5,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(3,6,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(4,1,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(5,1,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(6,2,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(7,3,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(8,1,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(9,6,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(10,2,2));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(11,2,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(12,3,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(12,5,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(14,8,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(18,1,1));
            dao.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(19,1,1));



            return null;
        }
    }

}
