import models.Chat;
import models.DefaultInitAdapter;
import models.Message;
import models.User;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Денис Щуцкий on 01.03.2018.
 */


public class RandomGenerateData {
    private TitanAdapter titanAdapter;
    private Random random = new Random();

    public RandomGenerateData(TitanAdapter titanAdapter){
        this.titanAdapter = titanAdapter;
    }

    public HashMap<Vertex, ArrayList<Vertex>> generUserToChat(int quantityChat,
                                int quantityUser){
        ArrayList<Vertex> list = new ArrayList<Vertex>();

        HashMap<Vertex, ArrayList<Vertex>> dictionaryUsersToChat = new HashMap<>();
        Chat chat;
        for(int i = 0; i < quantityChat; i++){
            chat = new Chat(i,
                    "chat_"+i,
                    "/img/"+i,
                    true,
                    true,
                    i);
            list.add(titanAdapter.setChat(chat));
        }
        ArrayList<Vertex> listUser = new ArrayList<>();

        User user;
        for(int i = 0; i < quantityUser; i++){
            user = new User("user_"+i,
                    i,
                    "/img/user/"+i,
                    "3809794910"+i);
            listUser.add(titanAdapter.setUser(user));
        }
        for(int i = 0; i < quantityChat; i++){
            int j = 10+(int)(Math.random() * ((int)(quantityUser/3)));
            ArrayList<Vertex> tmpV = new ArrayList<>();
            for(int k = 0; k < j; k++){
                int id_user = (int)(Math.random() * quantityUser);
                titanAdapter.setUserToChat(list.get(i), listUser.get(id_user));
                tmpV.add(listUser.get(id_user));
            }
            dictionaryUsersToChat.put(list.get(i), tmpV);
        }
        return dictionaryUsersToChat;
    }

    public void generateMessageToChat(HashMap<Vertex, ArrayList<Vertex>> dict,
                                      int minQuantityMessage,
                                      int maxQuantityMessage) {
        int id_message = 0;
        ArrayList<Vertex> keys = new ArrayList<Vertex>(dict.keySet());
        for (Vertex v : keys) {
            ArrayList<Vertex> tmpUsers = dict.get(v);
            int idTmpChat = v.value("id_chat");
            int quantityMessage = minQuantityMessage + (int) (Math.random() * (maxQuantityMessage - minQuantityMessage));
            for (int i = 0; i < quantityMessage; i++) {
                int tmpIdSender = tmpUsers.get(random.nextInt(tmpUsers.size() - 1)).value("id_user");
                Message tmpMessage = new Message(id_message,
                        1,
                        tmpIdSender,
                        idTmpChat,
                        "text message " + tmpIdSender + " " + id_message + " " + idTmpChat);
                id_message++;
                titanAdapter.setMessageToChat(tmpMessage);
            }
        }
    }

    public void getMessage() {
        ArrayList<Message> mes = titanAdapter.getMessageChat(0,
                2);
        //System.out.println(mes);
        for(Message m: mes){
            System.out.println(m);
        }
    }

    public static void main(String[] args) {
        RandomGenerateData randomGenerateData = new RandomGenerateData(TitanAdapter.getInstance(new DefaultInitAdapter("./data_with_message")));
        int quantityChat = 20;
        int quantityUser = 100;
        int minQuantityMessage = 1000;
        int maxQuantityMessage = 4000;
/*
        HashMap<Vertex, ArrayList<Vertex>> users_to_chat = randomGenerateData.generUserToChat(quantityChat, quantityUser);

        randomGenerateData.generateMessageToChat(users_to_chat,
                minQuantityMessage,
                maxQuantityMessage);

*/
        randomGenerateData.getMessage();





        System.exit(0);
    }
}
