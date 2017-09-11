import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class OpenNLPCategorizer {
    DoccatModel model;

    public void startSenti() {
        OpenNLPCategorizer twitterCategorizer = new OpenNLPCategorizer();
        twitterCategorizer.trainModel();
//        twitterCategorizer.classifyNewTweet("Have a nice day!");
    }

    public void trainModel() {
        InputStream dataIn = null;
        try {
            dataIn = new FileInputStream("input/tweets.txt");
            ObjectStream<String> lineStream = new PlainTextByLineStream((InputStreamFactory) dataIn, "UTF-8");
            ObjectStream<opennlp.tools.doccat.DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            // Specifies the minimum number of times a feature must be seen
            int cutoff = 2;
            int trainingIterations = 30;
//            model = DocumentCategorizerME.train("en", sampleStream, cutoff, trainingIterations);
//            model = DocumentCategorizerME.train("en", sampleStream, cutoff, trainingIterations);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataIn != null) {
                try {
                    dataIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void classifyNewTweet(String[] tweet) {
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        double[] outcomes = myCategorizer.categorize(tweet);
        String category = myCategorizer.getBestCategory(outcomes);

        if (category.equalsIgnoreCase("1")) {
            System.out.println("The tweet is positive :) ");
        } else {
            System.out.println("The tweet is negative :( ");
        }
    }
}