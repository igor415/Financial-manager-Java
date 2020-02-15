package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "Kupac",foreignKeys = @ForeignKey(entity = Mjesto.class,
        parentColumns = "pbrMjesto",
        childColumns = "pbrMjesto",
        onDelete = CASCADE),indices = @Index("pbrMjesto"))
public class Kupac {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String imeKupac;
    private String prezimeKupac;
    private String adresa;
    private int stanjeBodova;
    private int pbrMjesto;

    public Kupac( String imeKupac, String prezimeKupac, String adresa, int stanjeBodova, int pbrMjesto) {
        this.imeKupac = imeKupac;
        this.prezimeKupac = prezimeKupac;
        this.adresa = adresa;
        this.stanjeBodova = stanjeBodova;
        this.pbrMjesto = pbrMjesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImeKupac() {
        return imeKupac;
    }


    public String getPrezimeKupac() {
        return prezimeKupac;
    }


    public String getAdresa() {
        return adresa;
    }


    public int getStanjeBodova() {
        return stanjeBodova;
    }

    public int getPbrMjesto() {
        return pbrMjesto;
    }

}
