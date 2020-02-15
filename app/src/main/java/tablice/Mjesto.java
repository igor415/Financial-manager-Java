package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "Mjesto",foreignKeys = @ForeignKey(entity = Zupanija.class,
        parentColumns = "sifZupanija",
        childColumns = "sifZupanija",
        onDelete = CASCADE),indices = @Index("sifZupanija"))
public class Mjesto {
    @PrimaryKey
    private int pbrMjesto;
    private String nazivMjesto;
    private int sifZupanija;

    public Mjesto(int pbrMjesto, String nazivMjesto, int sifZupanija) {
        this.pbrMjesto = pbrMjesto;
        this.nazivMjesto = nazivMjesto;
        this.sifZupanija = sifZupanija;
    }

    public int getPbrMjesto() {
        return pbrMjesto;
    }

    public String getNazivMjesto() {
        return nazivMjesto;
    }

    public int getSifZupanija() {
        return sifZupanija;
    }

}
