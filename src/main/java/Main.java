import java.io.File;

public class Main {
    public static void main(String arg[]) {
        System.out.println("Hello World");
        Main mObj = new Main();
        mObj.fileNames();
    }

    public void fileNames() {
        File folder = new File("D:\\Data\\takeout-20170908T164507Z-001\\Takeout\\Chrome");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            }else{
                System.out.println("There is no file in this directory");
            }
        }
    }

    public void dataExtract() {

    }
}
