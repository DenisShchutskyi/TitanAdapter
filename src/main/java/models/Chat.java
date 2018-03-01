package models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.codehaus.groovy.transform.trait.Traits;

public class Chat implements StructureModelsAllObject {

    private int idChat;
    private String titleChat;
    private String pathToImg;
    private boolean isMain;
    private boolean isChat;
    private int idCreator;

    public Chat(int idChat,
                String titleChat,
                String pathToImg,
                boolean isMain,
                boolean isChat,
                int idCreator){
        this.idChat = idChat;
        this.titleChat = titleChat;
        this.pathToImg = pathToImg;
        this.isMain = isMain;
        this.isChat = isChat;
        this.idCreator = idCreator;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getTitleChat() {
        return titleChat;
    }

    public void setTitleChat(String titleChat) {
        this.titleChat = titleChat;
    }

    public String getPathToImg() {
        return pathToImg;
    }

    public void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isChat() {
        return isChat;
    }

    public void setChat(boolean chat) {
        isChat = chat;
    }

    public int getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(int idCreator) {
        this.idCreator = idCreator;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "idChat=" + idChat +
                ", titleChat='" + titleChat + '\'' +
                ", pathToImg='" + pathToImg + '\'' +
                ", isMain=" + isMain +
                ", isChat=" + isChat +
                ", idCreator=" + idCreator +
                '}';
    }

    @Override
    public JsonObject getJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("id_chat", new JsonPrimitive(this.idChat));
        jsonObject.add("title_chat", new JsonPrimitive(this.titleChat));
        jsonObject.add("path_to_img", new JsonPrimitive(this.pathToImg));
        jsonObject.add("is_main", new JsonPrimitive(this.isMain));
        jsonObject.add("is_chat", new JsonPrimitive(this.isChat));
        jsonObject.add("id_creatoe", new JsonPrimitive(this.idCreator));
        return jsonObject;
    }

    @Override
    public Object[] getDataVertex(){
        Object objects[] = new Object[12];
        objects[0] = "id_chat";
        objects[1] = this.idChat;
        objects[2] = "title_chat";
        objects[3] = this.titleChat;
        objects[4] = "path_to_img";
        objects[5] = this.pathToImg;
        objects[6] = "is_main";
        objects[7] = this.isMain;
        objects[8] = "is_chat";
        objects[9] = this.isChat;
        objects[10] = "id_creatoe";
        objects[11] = this.idCreator;

        return objects;
    }
}
