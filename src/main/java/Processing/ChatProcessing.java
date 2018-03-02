package Processing;

import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanVertex;
import models.Chat;
import models.Message;
import models.User;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import other.MyProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatProcessing {
    private TitanGraph graph;
    private String nameVertexChat;
    private String nameEdgeUserToChat;
    private String nameVertexMessage;
    private String nameEdgeMessageToChat;
    private String nameEdgeMessageToUser;
    private String nameEdgeMessageSender;

    public ChatProcessing(TitanGraph graph){
        this.graph = graph;
        this.nameVertexChat = MyProperty.getInstance().getValue("name_v_chat");
        this.nameEdgeUserToChat = MyProperty.getInstance().getValue("name_e_user_to_chat");
        this.nameVertexMessage = MyProperty.getInstance().getValue("name_v_message");
        this.nameEdgeMessageToChat = MyProperty.getInstance().getValue("name_e_message_to_chat");
        this.nameEdgeMessageToUser = MyProperty.getInstance().getValue("name_e_message_to_user");
        this.nameEdgeMessageSender = MyProperty.getInstance().getValue("name_e_message_sender");
    }

    public Vertex setChat(Chat chat){
        Vertex tmp = getVertexChatById(chat.getIdChat());
        if(tmp != null){
            return tmp;
        }
        else {
            Object objects[] = chat.getDataVertex();
            Vertex curVertex = graph.addVertex(nameVertexChat);
            for (int i = 0; i < objects.length; i += 2) {
                curVertex.property((String) objects[i], objects[i + 1]);
            }
            return curVertex;
        }


    }

    public Chat getChatById(int idChat){
        Chat chat = null;
        GraphTraversalSource g = graph.traversal();
        try {
            Vertex resNode = g.V().has("id_chat", idChat).next();
            chat = new Chat(resNode.value("id_chat"),
                    resNode.value("title_chat"),
                    resNode.value("path_to_img"),
                    resNode.value("is_main"),
                    resNode.value("is_chat"),
                    resNode.value("id_creatoe"));
        }catch (Exception ex){}
        return chat;
    }

    public Vertex getVertexChatById(int idChat){
        GraphTraversalSource g = graph.traversal();
        try {
            return g.V().has("id_chat", idChat).next();
        }catch (Exception ex){}
        return null;
    }

    public String setEdgeChatToUser(Vertex chat, Vertex user){
        try {
            chat.addEdge(this.nameEdgeUserToChat,
                    user,
                    "connect", (System.currentTimeMillis() / 1000L),
                    "isBan", false);
            return "successful";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "error";
    }

    public void setMessageToChat(Message message){
        Vertex chat = getVertexChatById(message.getIdChat());
        if(chat != null){
            try {
                Object objects[] = message.getDataVertex();
                Vertex curVertex = graph.addVertex(nameVertexMessage);
                for (int i = 0; i < objects.length; i += 2) {
                    curVertex.property((String) objects[i], objects[i + 1]);
                }
                chat.addEdge(this.nameEdgeMessageToChat, curVertex, "created", (System.currentTimeMillis() / 1000L));
                ArrayList vertex = getVertexUsersInChat(chat);
                for(Object a: vertex) {
                    Vertex tmpUser = (Vertex) a;
                    tmpUser.addEdge(nameEdgeMessageToUser, curVertex, "created", message.getCreatedOn(), "is_view", true);
                    int id_user = Integer.parseInt(tmpUser.value("id_user"));

                    if (id_user == (int) message.getIdSender()) {
                        tmpUser.addEdge(nameEdgeMessageSender, curVertex);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList getVertexUsersInChat(int idChat){
        GraphTraversalSource g = graph.traversal();
        Vertex v = getVertexChatById(idChat);
        return getVertexUsersInChat(v,g);
    }

    public ArrayList getVertexUsersInChat(Vertex chat){
        GraphTraversalSource g = graph.traversal();
        return getVertexUsersInChat(chat,g);
    }

    public ArrayList getVertexUsersInChat(Vertex chat, GraphTraversalSource g){
        try {
            ArrayList list = new ArrayList();
            g.V(chat).out(nameEdgeUserToChat).fill(list);
            return list;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public ArrayList<User> getUsersInChat(int idChat){
        ArrayList list = getVertexUsersInChat(idChat);
        if(list != null) {
            try {
                ArrayList<User> listUser = new ArrayList<>();
                for (Object o : list) {
                    listUser.add(new User((Vertex) o));
                }
                return listUser;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Message> getMessagesChat(int page, int idChat){
        Vertex chat = getVertexChatById(idChat);
        GraphTraversalSource g = graph.traversal();
        ArrayList<Vertex> listVertexMessage = new ArrayList<>();

        //Iterable<TitanVertex> vertex = graph.query().has(nameEdgeMessageToChat).orderBy("created", Order.decr).limit(20).vertices();
        ArrayList<Message> listMessage = new ArrayList<>();
        /*for (Vertex v: vertex){
            listMessage.add(new Message(v));
        }*/
        g.V(chat).out(nameEdgeMessageToChat).order().limit(20).fill(listVertexMessage);
        for(Vertex v: listVertexMessage){
            listMessage.add(new Message(v));
        }
        return listMessage;
    }



}
