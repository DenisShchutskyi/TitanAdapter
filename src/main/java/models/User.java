package models;
import com.google.gson.*;

import java.util.Objects;

/**
 * Created by denis on 28.02.18.
 */
public class User {
    private String fullName;
    private int idUser;
    private String pathToImg;
    private String phone;

    public User(String fullName, int idUser, String pathToImg, String phone){
        this.fullName = fullName;
        this.idUser = idUser;
        this.pathToImg = pathToImg;
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", idUser=" + idUser +
                ", pathToImg='" + pathToImg + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getPathToImg() {
        return pathToImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public JsonObject getUser(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("id_user", new JsonPrimitive(this.idUser));
        jsonObject.add("full_name", new JsonPrimitive(this.fullName));
        jsonObject.add("path_to_img", new JsonPrimitive(this.pathToImg));
        jsonObject.add("phone", new JsonPrimitive(this.phone));
        return jsonObject;
    }

    public Object[] getDataVartex(){
        Object objects[] = new Object[8];
        objects[0] = "full_name";
        objects[1] = this.fullName;
        objects[2] = "id_user";
        objects[3] = this.idUser;
        objects[4] = "path_img";
        objects[5] = this.pathToImg;
        objects[6] = "phone";
        objects[7] = this.phone;
        return objects;
    }
}
