package other;

import java.io.*;
import java.util.Properties;

/**
 * Created by denis on 28.02.18.
 */
public class MyProperty {
    private static MyProperty myProperty;
    private Properties prop;
    private String pathToFile = "./src/main/resources/config.properties";


    private MyProperty(){
        prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(pathToFile);
            prop.load(input);

        }catch (IOException io){
            io.printStackTrace();
        }
        finally {
            if(input != null){
                try {
                    input.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static MyProperty getInstance(){
        if(myProperty == null){
            myProperty = new MyProperty();
        }
        return myProperty;
    }

    public String getValue(String key){
        String s = prop.getProperty(key);
        if(s != null) {
            return s;
        }
        setKey(key);
        return key;
    }

    private void setKey(String key) {
        OutputStream output = null;

        try {

            output = new FileOutputStream(pathToFile);

            // set the properties value

            prop.setProperty(key, key);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

}
