package yuan.com.androidarchitecture.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mertsimsek on 19/05/2017.
 */
@Entity(tableName = "jokes")
public class JokeEntity {

    @PrimaryKey
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("data_type")
    private String data_type;  //数据类型
    @SerializedName("name")
    private String name;
    @SerializedName("profile_image")
    private String profile_image;
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
