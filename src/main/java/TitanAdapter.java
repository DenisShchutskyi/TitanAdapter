import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import models.DefaultInitAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by denis on 27.02.18.
 */

public class TitanAdapter {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TitanAdapter.class);

    private static TitanGraph graph;
    private static TitanAdapter titanAdapter;

    private TitanAdapter(String dataDir){

        graph = TitanFactory.open("berkeleyje:" + dataDir);


    }

    private TitanAdapter(){
        graph = TitanFactory.open("berkeleyje:"+"./data");
    }

    public static TitanGraph getGraph(DefaultInitAdapter defaultInitAdapter){
        if(graph == null){
            titanAdapter = new TitanAdapter(defaultInitAdapter.getPathFile());
        }
        return graph;
    }

    public static TitanGraph getGraph() {
        return graph;
    }
}
