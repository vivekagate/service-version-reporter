package com.cgup.svc;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class ServiceDescriptor {

    private String name;
    private String version;
    private String commit;
    private String environment;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
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
                if(key.equalsIgnoreCase("tag")){
                    sd.setVersion(val);
                }
                if(key.equalsIgnoreCase("service")){
                    sd.setName(val);
                }
                if(key.equalsIgnoreCase("environment")){
                    Optional opt = Optional.ofNullable(System.getenv(val));
                    sd.setEnvironment(opt.orElse("development"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sd;
    }
}
