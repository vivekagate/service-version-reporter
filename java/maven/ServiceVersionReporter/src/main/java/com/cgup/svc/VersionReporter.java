package com.cgup.svc;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
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
        reportVersion(System.getProperty("NIROPS_TOKEN"));
    }

    public static void reportVersion(String token){
        if (StringUtils.isEmpty(token)) {
            System.out.println("No Token provided. Skip Version Tracking");
            return;
        }
        ServiceDescriptor sd = ServiceDescriptor.fromFile("META-INF/Version.MF");
        System.out.println("Posting version to VersionViewer: " + sd.toString());
        String url = "http://localhost:8080/api/service";
        byte[] postData = sd.toString().getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con = null;
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Basic " + token);

            try (OutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
//        OutputStream os = null;
//        try {
//            URL url = new URL ("http://localhost:8080/api/service");
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Authorization", "Basic " + token);
//            con.setDoOutput(true);
//            byte[] input = sd.toString().getBytes("utf-8");
//            os = con.getOutputStream();
//            os.write(input, 0, input.length);
//        } catch (MalformedURLException e) {
//            System.out.println("Failed to parse URL");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
