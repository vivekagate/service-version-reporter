package com.cgup.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {

    private String command;

    public CommandExecutor(String command){
        this.command = command;
    }

    public String runCommand() throws IOException {
        BufferedReader input = null;
        try {
            String line;
            Process p = Runtime.getRuntime().exec
                    (this.command);
            input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                return line;
            }
        }finally {
            input.close();
        }
        return null;
    }
}
