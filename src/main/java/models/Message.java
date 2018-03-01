package models;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Message implements StructureModelsAllObject {
    private int idMessage;
    private int idTypeMessage;
    private int idSender;
    private int idChat;
    private long createdOn;
    private String textMessage;

    public Message(int idMessage,
                   int idTypeMessage,
                   int idSender,
                   int idChat,
                   String textMessage){
        this.idMessage = idMessage;
        this.idTypeMessage = idTypeMessage;
        this.idSender = idSender;
        this.idChat = idChat;
        this.createdOn =  (System.currentTimeMillis() / 1000L);
        this.textMessage = textMessage;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getIdTypeMessage() {
        return idTypeMessage;
    }

    public void setIdTypeMessage(int idTypeMessage) {
        this.idTypeMessage = idTypeMessage;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", idTypeMessage=" + idTypeMessage +
                ", idSender=" + idSender +
                ", idChat=" + idChat +
                ", createdOn=" + createdOn +
                ", textMessage='" + textMessage + '\'' +
                '}';
    }

    @Override
    public Object[] getDataVertex() {
        Object objects[] = new Object[10];
        objects[0] = "id_message";
        objects[1] = idMessage;
        objects[2] = "id_type_message";
        objects[3] = idTypeMessage;
        objects[4] = "id_sender";
        objects[5] = idSender;
        objects[6] = "id_chat";
        objects[7] = idChat;
        objects[8] = "id_created_on";
        objects[9] = createdOn;
        objects[10] = "text_message";
        objects[11] = textMessage;
        return objects;
    }

    @Override
    public JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("id_message", new JsonPrimitive(idMessage));
        jsonObject.add("id_type_message", new JsonPrimitive(idTypeMessage));
        jsonObject.add("id_sender", new JsonPrimitive(idSender));
        jsonObject.add("id_chat", new JsonPrimitive(idChat));
        jsonObject.add("id_created_on", new JsonPrimitive(createdOn));
        jsonObject.add("text_message", new JsonPrimitive(textMessage));
        return jsonObject;
    }
}
