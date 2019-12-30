package com.cgup.svc;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class VersionReporter {

    private static String VERSION;
    static {
        VERSION = readManifest();
    }

    private static String readManifest() {
        InputStream is = ClassLoader.getSystemResourceAsStream("META-INF/Version.MF");
        try {
            List<String> lines = IOUtils.readLines(is, "UTF-8");
            for (String line: lines) {
                String[] kvp = line.split("=");
                return kvp[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static void reportVersion(){
        ServiceDescriptor sd = ServiceDescriptor.fromFile("META-INF/Version.MF");
        System.out.println("Posting version to VersionViewer: " + sd.toString());
//        reportToUrl();
    }
}
