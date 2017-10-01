package com.analysis.file.manager;

import java.util.ArrayList;

/**
 * Creating this class to separate the files from Main
 */
public class File {

    public ArrayList<java.io.File> listofAllFiles;
    java.io.File folder;
    private String fileSeedPath = "/Users/rmw/IdeaProjects/browserdata/src/main/java/data/Takeout/Chrome";


    public File(String fileSeedPath) {
        this.fileSeedPath = fileSeedPath;
        folder = new java.io.File(fileSeedPath);
        listofAllFiles = new ArrayList<>();
    }

    public ArrayList<java.io.File> getListOfFiles() {
        getFilesRecursive(folder);
        return listofAllFiles;
    }

    private void getFilesRecursive(java.io.File pFile) {
        for (java.io.File files : pFile.listFiles()) {
            if (files.isDirectory()) {
                getFilesRecursive(files);
            } else {
                listofAllFiles.add(files);
            }
        }
    }
}
