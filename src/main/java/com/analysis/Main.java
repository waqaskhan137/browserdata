package com.analysis;

import com.db.DB;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// TODO: 28/09/2017 learn maven in depth

// TODO: 20/09/2017 adding
public class Main {
    DB mysqlConnect = new DB();
    File[] listOfFiles;
    private String filePath = "/Users/rmw/IdeaProjects/browserdata/src/main/java/data/Takeout/Chrome";

    public static void main(String args[]) {
        Main mObj = new Main();
        mObj.dataExtract(mObj.fileNames());
    }

    public File[] getListOfFiles() {
        return listOfFiles;
    }

    // TODO: 28/09/2017 find all files in project of image and json.
    protected File[] fileNames() {

        File folder = new File(filePath);
        listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            return listOfFiles;
        }

        return null;
    }

    private void dataExtract(File[] files) {
        for (File file : files) {
            parseJson(file.getPath());
        }
    }

    private void parseJson(String filePath) {

        JSONParser parser = new JSONParser();

        try {

            Object obj;
            obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            // loop array
            JSONArray browserHistory = (JSONArray) jsonObject.get("history");
            for (JSONObject aBrowserHistory : (Iterable<JSONObject>) browserHistory)
                try {

                    String title = mysql_real_escape_string(aBrowserHistory.get("title").toString());
                    String favicon_url = mysql_real_escape_string(aBrowserHistory.get("favicon_url").toString());
                    String page_transition = mysql_real_escape_string(aBrowserHistory.get("page_transition").toString());
                    String url = mysql_real_escape_string(aBrowserHistory.get("url").toString());
                    String client_id = mysql_real_escape_string(aBrowserHistory.get("client_id").toString());
                    String time_usec = mysql_real_escape_string((aBrowserHistory.get("time_usec")).toString());

                    insertData(favicon_url, page_transition, title, url, client_id, time_usec);
                } catch (NullPointerException e) {
                    e.printStackTrace();

                }
            System.out.println("Data Inserted, Closing MYSql Connection");
            mysqlConnect.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sentDetect(String sentence) throws Exception {

        //Loading sentence detector model
        InputStream inputStream = new FileInputStream("/Users/rmw/IdeaProjects/browserdata/nlp-models/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        //Instantiating the SentenceDetectorME class
        SentenceDetectorME detector = new SentenceDetectorME(model);
        //Detecting the sentence
        String sentences[] = detector.sentDetect(sentence);
        //Printing the sentences
        for (String sent : sentences) {
            insertSentence(sent);
        }
    }

    private void insertSentence(String sent) {


        try {
            String sql = "INSERT INTO `sentDetect` (`sentence`) VALUES ('" + mysql_real_escape_string(sent) + "');";
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertData(String favicon_url, String page_transition, String title, String url, String client_id, String time_usec) {


        try {
            String sql = "INSERT INTO `test_schema`.`c_history` (`favicon_url`, `page_transition`, `title`, `url`, `client_id`, `time_usec`) VALUES ('" + favicon_url + "', '" + page_transition + "', '" + title + "', '" + url + "', '" + client_id + "', '" + time_usec + "');";
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Mysql Utilities
     *
     * @author Ralph Ritoch <rritoch@gmail.com>
     * @copyright Ralph Ritoch 2011 ALL RIGHTS RESERVED
     * @link http://www.vnetpublishing.com
     */


    public String mysql_real_escape_string(String str)
            throws Exception {
        if (str == null) {
            return null;
        }

        if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]", "").length() < 1) {
            return str;
        }

        String clean_string = str;
        clean_string = clean_string.replaceAll("\\\\", "\\\\\\\\");
        clean_string = clean_string.replaceAll("\\n", "\\\\n");
        clean_string = clean_string.replaceAll("\\r", "\\\\r");
        clean_string = clean_string.replaceAll("\\t", "\\\\t");
        clean_string = clean_string.replaceAll("\\00", "\\\\0");
        clean_string = clean_string.replaceAll("'", "\\\\'");
        clean_string = clean_string.replaceAll("\\\"", "\\\\\"");

        if (clean_string.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/?\\\\\"' ]"
                , "").length() < 1) {
            return clean_string;
        }
        return str;
    }

    /**
     * Escape data to protected against SQL Injection
     *
     * @param str
     * @return
     * @throws Exception
     */

    public String quote(String str)
            throws Exception {
        if (str == null) {
            return "NULL";
        }
        return "'" + mysql_real_escape_string(str) + "'";
    }

    /**
     * Escape identifier to protected against SQL Injection
     *
     * @param str
     * @return
     * @throws Exception
     */

    public String nameQuote(String str)
            throws Exception {
        if (str == null) {
            return "NULL";
        }
        return "`" + mysql_real_escape_string(str) + "`";
    }


}
