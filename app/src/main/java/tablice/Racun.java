package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "Racun",foreignKeys = {@ForeignKey(entity = Prodavac.class,
        parentColumns = "id",
        childColumns = "idProdavac",
        onDelete = CASCADE),@ForeignKey(entity = Poslovnica.class,
        parentColumns = "id",
        childColumns = "idPoslovnica",
        onDelete = CASCADE),@ForeignKey(entity = Kupac.class,
        parentColumns = "id",
        childColumns = "idKupac",
        onDelete = CASCADE) },indices = {@Index("idProdavac"),@Index("idPoslovnica"),@Index("idKupac")})
public class Racun {
    @PrimaryKey (autoGenerate = true)
    private int brojRacuna;
    private int idProdavac;
    private int idPoslovnica;
    private int idKupac;
    private String datum;

    public Racun(int idProdavac, int idPoslovnica, int idKupac, String datum) {
        this.idProdavac = idProdavac;
        this.idPoslovnica = idPoslovnica;
        this.idKupac = idKupac;
        this.datum = datum;
    }

    public int getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(int brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public int getIdProdavac() {
        return idProdavac;
    }

    public int getIdPoslovnica() {
        return idPoslovnica;
    }

    public int getIdKupac() {
        return idKupac;
    }

    public String getDatum() {
        return datum;
    }

}
