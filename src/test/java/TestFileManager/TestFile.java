package TestFileManager;

import com.analysis.file.manager.File;
import org.junit.Assert;
import org.junit.Test;

public class TestFile {

    File obj = new File("/Users/rmw/IdeaProjects/browserdata/src/main/java/data/Takeout/Chrome");

    @Test
    public void getListOfFiles() throws Exception {
        Assert.assertEquals("File names are equal ", "BrowserHistory.json", obj.getListOfFiles());
    }
}
