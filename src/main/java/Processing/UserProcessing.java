package Processing;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.thinkaurelius.titan.graphdb.database.management.ManagementSystem;
import models.User;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import other.MyProperty;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


public class UserProcessing {

    private TitanGraph graph;
    private String nameVertex;

    public UserProcessing(TitanGraph graph){
        this.graph = graph;
        this.nameVertex = MyProperty.getInstance().getValue("name_v_user");
    }


    public ArrayList<User> getUsers(){
        System.out.println("23");
        GraphTraversalSource g = graph.traversal();

        ArrayList list = new ArrayList();
        g.V().has("id_user").fill(list);
        for(Object o: list){
            Vertex curNode = (Vertex)o;
            for(String key: curNode.keys()){
                System.out.println(key +':' + curNode.value(key));
            }
        }
        System.out.println(56);
        return null;
    }

    public void setUser(User user) {
        Object objects[] = user.getDataVertex();
        Vertex curVertex = graph.addVertex(nameVertex);
        for (int i = 0; i < objects.length; i += 2) {
            curVertex.property((String) objects[i], objects[i + 1]);
        }
        TitanManagement mgmt = graph.openManagement();
        TitanManagement.IndexBuilder indexBuilder = mgmt.buildIndex("byIdUser", Vertex.class);
        if (!mgmt.containsGraphIndex("byIdUser")) {
            PropertyKey indexPropertyKey = mgmt.getOrCreatePropertyKey("id_user");
            indexBuilder.addKey(indexPropertyKey);
            indexBuilder.unique().buildCompositeIndex();
        }

        mgmt.commit();
    }

    public User getUserById(int idUser){
        GraphTraversalSource g = graph.traversal();
        User user = null;
        try {
            Vertex resNode = g.V().has("id_user", idUser).next();
            user = new User(resNode.value("id_user"),
                    resNode.value("full_name"),
                    resNode.value("path_to_img"),
                    resNode.value("phone"));
        }catch (Exception ex){}
        return user;
    }

    public Vertex getVertexUserById(int idUser){
        GraphTraversalSource g = graph.traversal();
        Vertex resNode = null;
        try {
            resNode = g.V().has("id_user", idUser).next();
        }catch (Exception ex){}
        return resNode;
    }

}
