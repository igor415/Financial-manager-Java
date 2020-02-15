package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "Zupanija")
public class Zupanija {
    @PrimaryKey (autoGenerate = true)
    private int sifZupanija;
    private String nazivZupanija;

    public Zupanija(String nazivZupanija){

        this.nazivZupanija = nazivZupanija;
    }

    public void setSifZupanija(int sifZupanija) {
        this.sifZupanija = sifZupanija;
    }

    public int getSifZupanija() {
        return sifZupanija;
    }

    public String getNazivZupanija() {
        return nazivZupanija;
    }


}
