package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Prodavac",foreignKeys = {@ForeignKey(entity = Mjesto.class,
        parentColumns = "pbrMjesto",
        childColumns = "pbrMjesto",
        onDelete = CASCADE),@ForeignKey(entity = Poslovnica.class,
        parentColumns = "id",
        childColumns = "idPoslovnica",
        onDelete = CASCADE) },indices = {@Index("pbrMjesto"),@Index("idPoslovnica")})
public class Prodavac{
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String imeProdavac;
    private String prezimeProdavac;
    private String adresa;
    private int idPoslovnica;
    private int pbrMjesto;

    public Prodavac(String imeProdavac, String prezimeProdavac, String adresa, int idPoslovnica, int pbrMjesto) {
        this.imeProdavac = imeProdavac;
        this.prezimeProdavac = prezimeProdavac;
        this.adresa = adresa;
        this.idPoslovnica = idPoslovnica;
        this.pbrMjesto = pbrMjesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImeProdavac() {
        return imeProdavac;
    }

    public String getPrezimeProdavac() {
        return prezimeProdavac;
    }

    public String getAdresa() {
        return adresa;
    }

    public int getIdPoslovnica() {
        return idPoslovnica;
    }

    public int getPbrMjesto() {
        return pbrMjesto;
    }

}
