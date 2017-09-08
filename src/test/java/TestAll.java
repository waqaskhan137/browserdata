import org.junit.Assert;
import org.junit.Test;

public class TestAll {
    @Test
    public void testFileName(){
        Main mObj = new Main();
        Assert.assertEquals("File names are equal ", "BrowserHistory.json", mObj.fileNames()[0].getName());
    }

    @Test
    public void testDataExtract() throws Exception {
        Main mObj = new Main();
//        Assert.assertEquals("Data Check", "Null", mObj.dataExtract(mObj.fileNames()));
    }
}
