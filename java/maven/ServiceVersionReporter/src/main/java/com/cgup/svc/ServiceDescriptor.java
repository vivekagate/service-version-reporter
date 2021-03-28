package com.cgup.svc;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ServiceDescriptor {

    private String name;
    private String repo;
    private String version;
    private String commit;
    private Properties attributes;

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public void setAttributes(Properties attributes) {
        this.attributes = attributes;
    }

    public String toString(){
        Gson json = new Gson();
        return json.toJson(this);
    }

    public static ServiceDescriptor fromFile(String filename){
        ServiceDescriptor sd = new ServiceDescriptor();
        InputStream is = ClassLoader.getSystemResourceAsStream(filename);
        try {
            List<String> lines = IOUtils.readLines(is, "UTF-8");
            for (String line: lines) {
                String[] kvp = line.split("=");
                String key = kvp[0];
                String val = kvp[1];
                if(key.equalsIgnoreCase("commit")){
                    sd.setCommit(val);
                }else if(key.equalsIgnoreCase("service")){
                    sd.setName(val);
                }else if(key.equalsIgnoreCase("repo")){
                    sd.setRepo(val);
                }
                Properties props = System.getProperties();
                sd.setAttributes(props);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sd;
    }
}
