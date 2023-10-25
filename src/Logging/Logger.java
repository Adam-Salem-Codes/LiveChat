package Logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.LookAndFeel;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.github.cliftonlabs.json_simple.*;
public class Logger {
    private static File jsonFile = new File("src//Logging//log.json");

    protected static JsonObject jsonObject = new JsonObject();
    protected FileWriter fw;
    //===========Constructor===========//
    public Logger() {
        try {
            fw = new FileWriter(jsonFile, false);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Object get_saved_theme() {
        
         try {
            BufferedReader br = new BufferedReader(new FileReader(jsonFile));
            Object obj = Jsoner.deserialize(br.readLine(), jsonObject).get("theme");
            br.close();
            if (obj == null)
                return new FlatMacDarkLaf().toString();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set_saved_theme(LookAndFeel theme){
        jsonObject.put("theme", theme.getName());
        try {
            fw.flush();
            fw.write(jsonObject.toJson());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }   
    public void dispose(){
        try {
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
