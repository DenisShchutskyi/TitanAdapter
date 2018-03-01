import Processing.UserProcessing;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import models.DefaultInitAdapter;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;



public class TitanAdapter {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TitanAdapter.class);

    private static TitanAdapter titanAdapter = new TitanAdapter(new DefaultInitAdapter().getPathFile());

    private TitanGraph graph;
    private UserProcessing userProcessing;

    private TitanAdapter(String dataDir){
        this.graph = TitanFactory.open("berkeleyje:" + dataDir);
        this.userProcessing = new UserProcessing(this.graph);
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

    public void setUser(User user){
        userProcessing.setUser(user);
    }


    public static void main(String[] args) {
        TitanAdapter titanAdapter = TitanAdapter
                .getInstance(new DefaultInitAdapter("./test_data"));
        User user = new User("Deniss",
                23,
                "/fsadassdfsdf",
                "380979491075");
        User user1 = new User("Deniss",
                24,
                "/fsadassdfsdf",
                "380979491075");
        //titanAdapter.setUser(user);
        //titanAdapter.setUser(user1);
        /*titanAdapter.getUserById(21);
        System.out.println("______");
        titanAdapter.getUserById(22);
        System.out.println("______");
        */
        //titanAdapter.getUserById(23);
        System.out.println("______");
        //titanAdapter.getUserById(24);
        titanAdapter.getUsers();
        System.exit(0);
    }


}
