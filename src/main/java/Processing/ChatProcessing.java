package Processing;

import com.thinkaurelius.titan.core.TitanGraph;
import models.Chat;
import models.Message;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import other.MyProperty;

public class ChatProcessing {
    private TitanGraph graph;
    private String nameVertexChat;
    private String nameEdgeUserToChat;
    private String nameVertexMessage;
    private String nameEdgeMessageToChat;

    public ChatProcessing(TitanGraph graph){
        this.graph = graph;
        this.nameVertexChat = MyProperty.getInstance().getValue("name_v_chat");
        this.nameEdgeUserToChat = MyProperty.getInstance().getValue("name_e_user_to_chat");
        this.nameVertexMessage = MyProperty.getInstance().getValue("name_v_message");
        this.nameEdgeMessageToChat = MyProperty.getInstance().getValue("name_e_message_to_chat");
    }

    public String setChat(Chat chat){
        Chat tmp = getChatById(chat.getIdChat());
        if(tmp == null){
            return "insert error";
        }
        else {
            Object objects[] = chat.getDataVertex();
            Vertex curVertex = graph.addVertex(nameVertexChat);
            for (int i = 0; i < objects.length; i += 2) {
                curVertex.property((String) objects[i], objects[i + 1]);
            }
            return "successful";
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
                    resNode.value("id_creatoe")
                    );
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
        }catch (Exception ex){}
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
                // TODO привязать всех людей к данному сообщению которые привязаны к чату
            }catch (Exception e){}
        }

    }



}
