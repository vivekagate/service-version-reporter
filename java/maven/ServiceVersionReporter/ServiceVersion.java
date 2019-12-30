package com.sample;
public class VersionReporter {

    private static final String VERSION="heads/master";
    public static void reportVersion(String version){
        System.out.println("Posting version to VersionViewer: " + VERSION);
    }
}