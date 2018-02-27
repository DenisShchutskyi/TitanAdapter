package models;

/**
 * Created by denis on 27.02.18.
 */
public class DefaultInitAdapter {
    private String pathFile;
    public DefaultInitAdapter(){
        this.pathFile = "./data";
    }
    public DefaultInitAdapter(String s){
        this.pathFile = s;
    }

    public String getPathFile() {
        return pathFile;
    }
}
