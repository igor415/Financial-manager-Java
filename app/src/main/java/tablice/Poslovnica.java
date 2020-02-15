package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "Poslovnica",foreignKeys = @ForeignKey(entity = Mjesto.class,
        parentColumns = "pbrMjesto",
        childColumns = "pbrMjesto",
        onDelete = CASCADE),indices = @Index("pbrMjesto"))
public class Poslovnica {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String nazivPoslovnica;
    private String adresa;
    private int pbrMjesto;

    public Poslovnica( String nazivPoslovnica, String adresa, int pbrMjesto) {
        this.nazivPoslovnica = nazivPoslovnica;
        this.adresa = adresa;
        this.pbrMjesto = pbrMjesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivPoslovnica() {
        return nazivPoslovnica;
    }

    public String getAdresa() {
        return adresa;
    }

    public int getPbrMjesto() {
        return pbrMjesto;
    }

}
