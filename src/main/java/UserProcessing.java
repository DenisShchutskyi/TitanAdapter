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

/**
 * Created by denis on 28.02.18.
 */


public class UserProcessing {

    private TitanGraph graph;
    private String nameVertex;

    UserProcessing(TitanGraph graph){
        this.graph = graph;
        MyProperty property = MyProperty.getInstance();
        this.nameVertex = property.getValue("name_v_user");
    }


    public ArrayList<User> getUsers(){
        System.out.println("23");
        GraphTraversalSource g = graph.traversal();
       /* ArrayList list = new ArrayList();
        g.V().has("id_user", ra).fill(list);
        for(Object o: list) System.out.println(o);
        System.out.println(56);*/
        return null;
    }

    public void setUser(User user) {
        Object objects[] = user.getDataVartex();
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

    public void getUserById(int idUser){
        GraphTraversalSource g = graph.traversal();
        Vertex fromNode = g.V().has("id_user", idUser).next();
        for(String key: fromNode.keys()){
            System.out.println(key +':' + fromNode.value(key));
        }
    }

}
