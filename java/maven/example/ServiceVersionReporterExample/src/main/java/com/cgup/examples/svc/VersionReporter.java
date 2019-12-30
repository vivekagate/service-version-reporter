package com.cgup.examples.svc;
public class VersionReporter {

    private static final String VERSION="heads/master";
    public static void reportVersion(){
        System.out.println("Posting version to VersionViewer: " + VERSION);
    }
}