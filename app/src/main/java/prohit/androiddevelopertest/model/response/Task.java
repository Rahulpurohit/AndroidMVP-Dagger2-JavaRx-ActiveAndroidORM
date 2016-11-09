package prohit.androiddevelopertest.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Created by Rahul Purohit on 11/7/2016.
 */
@Table(name = "Data", id = "_id")
public class Task extends Model implements Parcelable {
    @Column(name = "SID", index = true)
    @SerializedName("id")
    @Expose
    private Integer sId;

    @Column(name = "Name", index = true)
    @SerializedName("name")
    @Expose
    private String name;

    @Column(name = "State")
    @SerializedName("state")
    @Expose
    private Integer state;


    //Local variables
    @Column(name = "IsLocal")
    private boolean isLocal;

    @Column(name = "IsModified")
    private boolean isModified;

    @Column(name = "IsDeleted")
    private boolean isDeleted;

    public Task() {
        super();
    }

    public Task(Integer sId, String name, Integer state, boolean isLocal, boolean isModified) {
        super();
        this.sId = sId;
        this.name = name;
        this.state = state;
        this.isLocal = isLocal;
        this.isModified = isModified;
    }

    /**
     * @param sId The sId
     */
    public void setsId(Integer sId) {
        this.sId = sId;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Task) == false) {
            return false;
        }
        Task rhs = ((Task) other);
        return new EqualsBuilder().append(sId, rhs.sId).append(name, rhs.name).append(state, rhs.state).isEquals();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.sId);
        dest.writeString(this.name);
        dest.writeValue(this.state);
        dest.writeByte(this.isLocal ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isModified ? (byte) 1 : (byte) 0);
        dest.writeValue(this.getId());
    }

    protected Task(Parcel in) {
        this.sId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.state = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isLocal = in.readByte() != 0;
        this.isModified = in.readByte() != 0;

    }

    public Integer getsId() {
        return sId;
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
