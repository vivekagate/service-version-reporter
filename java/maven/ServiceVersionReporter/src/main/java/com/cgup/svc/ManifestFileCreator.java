package com.cgup.svc;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ManifestFileCreator {

    private File dir;
    private String pkg;
    private String name;
    private String repo;

    public ManifestFileCreator(File dir, String pkg, String name){
        this.dir = dir;
        this.pkg = pkg;
        this.name = name;
    }

    public void write(String servicename, String environmentId, String commit, String repo) throws MojoExecutionException {
        FileWriter w = null;
        try
        {

            String targetFolder = "src/main/resources/META-INF/";
//            Files.createDirectories(Path.of(targetFolder);

            w = new FileWriter( targetFolder + this.name );

            w.write( "service="+servicename+"\n");
            w.write( "commit="+commit+"\n");
            w.write("repo="+repo+"\n");
            w.write( "environment="+environmentId+"\n");
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error creating file " + this.name, e );
        }
        finally
        {
            if ( w != null )
            {
                try
                {
                    w.close();
                }
                catch ( IOException e )
                {
                    // ignore
                }
            }
        }
    }

}
