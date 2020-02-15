package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "Proizvod")
public class Proizvod {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String nazivProizvod;
    private double cijena;

    public Proizvod(String nazivProizvod, double cijena) {
        this.nazivProizvod = nazivProizvod;
        this.cijena = cijena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivProizvod() {
        return nazivProizvod;
    }

    public double getCijena() {
        return cijena;
    }

}
