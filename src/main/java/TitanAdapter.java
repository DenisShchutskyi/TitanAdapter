import Processing.ChatProcessing;
import Processing.UserProcessing;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import models.Chat;
import models.DefaultInitAdapter;
import models.Message;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.tinkerpop.gremlin.structure.Vertex;


import java.util.ArrayList;



public class TitanAdapter {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TitanAdapter.class);

    private static TitanAdapter titanAdapter = new TitanAdapter(new DefaultInitAdapter().getPathFile());

    private TitanGraph graph;
    private UserProcessing userProcessing;
    private ChatProcessing chatProcessing;

    private TitanAdapter(String dataDir){
        this.graph = TitanFactory.open("berkeleyje:" + dataDir);
        this.userProcessing = new UserProcessing(this.graph);
        this.chatProcessing = new ChatProcessing(this.graph);
    }

    public static TitanAdapter getInstance(DefaultInitAdapter defaultInitAdapter){
        if(defaultInitAdapter != null){
            titanAdapter = new TitanAdapter(defaultInitAdapter.getPathFile());
        }
        return titanAdapter;
    }

    public ArrayList<User> getUsers(){
        return userProcessing.getUsers();
    }

    public void getUserById(int idUser){
        userProcessing.getUserById(idUser);
    }

    public Vertex setUser(User user){
        return userProcessing.setUser(user);
    }

    public Vertex setChat(Chat chat){ return chatProcessing.setChat(chat);}

    public void setUserToChat(Vertex chat, Vertex user){chatProcessing.setEdgeChatToUser(chat,user);}

    public void getUsersChat(int idChat){chatProcessing.getUsersInChat(idChat);}

    public void setMessageToChat(Message message){chatProcessing.setMessageToChat(message);}

    public ArrayList<Message> getMessageChat(int page,
                                             int idChat){
        return chatProcessing.getMessagesChat(page, idChat);
    }


    public static void main(String[] args) {
        TitanAdapter titanAdapter = TitanAdapter
                .getInstance(new DefaultInitAdapter("./data_"));

        titanAdapter.getUsersChat(1);
        System.exit(0);
    }


}
