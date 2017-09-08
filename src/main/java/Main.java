import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

public class Main {
    String filePath = "/Users/rmw/IdeaProjects/browserdata/Takeout/Chrome";

    public static void main(String args[]){
        Main mObj = new Main();
        mObj.dataExtract(mObj.fileNames());
    }

    public File[] fileNames() {

        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            return listOfFiles;
        }
        return null;
    }

    public void dataExtract(File[] files) {
        for (int i = 0; i < files.length; i++) {
            parseJson(files[i].getPath());
        }
    }

    public void parseJson(String filePath){

        JSONParser parser = new JSONParser();

        try {

            Object obj;
            obj = parser.parse(new FileReader(filePath));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.size());

            for (int i = 0; i < jsonObject.size(); i++) {
                String title = (String) jsonObject.get("title");
                String url = (String) jsonObject.get("url");
                System.out.println("Title : "+title);
                System.out.println("URL : "+url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
