package kmitl.natcha58070069.com.libreria.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nacha on 18-Nov-17.
 */
@Entity
public class LibreriaInfo implements Parcelable {

    public LibreriaInfo() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "COMMENT")
    private String comment;

    //Location of Lib
    @ColumnInfo(name = "LOCATION")
    private String location;

    @ColumnInfo(name = "LATLNG")
    private String latlng;

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    protected LibreriaInfo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        comment = in.readString();
        location = in.readString();
        latlng = in.readString();
    }

    public static final Creator<LibreriaInfo> CREATOR = new Creator<LibreriaInfo>() {
        @Override
        public LibreriaInfo createFromParcel(Parcel in) {
            return new LibreriaInfo(in);
        }

        @Override
        public LibreriaInfo[] newArray(int size) {
            return new LibreriaInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(comment);
        dest.writeString(location);
        dest.writeString(latlng);
    }
}
