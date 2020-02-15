package tablice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "ProizvodiNaRacunu",foreignKeys = {@ForeignKey(entity = Racun.class,
        parentColumns = "brojRacuna",
        childColumns = "idRacun",
        onDelete = CASCADE),@ForeignKey(entity = Proizvod.class,
        parentColumns = "id",
        childColumns = "idProizvod",
        onDelete = CASCADE)},indices = {@Index("idRacun"),@Index("idProizvod")})
public class ProizvodiNaRacunu {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private int idRacun;
    private int idProizvod;
    private int kolicina;

    public ProizvodiNaRacunu(int idRacun, int idProizvod, int kolicina) {
        this.idRacun = idRacun;
        this.idProizvod = idProizvod;
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRacun() {
        return idRacun;
    }

    public int getIdProizvod() {
        return idProizvod;
    }

    public int getKolicina() {
        return kolicina;
    }

}
