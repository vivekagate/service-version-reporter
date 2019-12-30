package com.cgup.svc;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ManifestFileCreator {

    private File dir;
    private String pkg;
    private String name;

    public ManifestFileCreator(File dir, String pkg, String name){
        this.dir = dir;
        this.pkg = pkg;
        this.name = name;
    }

    public void write(String servicename, String environmentId, String version) throws MojoExecutionException {
        FileWriter w = null;
        try
        {
            String targetFolder = "src/main/resources/META-INF/";
            w = new FileWriter( targetFolder + this.name );

            w.write( "service="+servicename+"\n");
            w.write( "tag="+version+"\n");
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


    private String readFile(String pkg, String version, String fname) throws IOException {
        return "package " + pkg + ";\n" +
                "public class VersionReporter {\n" +
                "\n" +
                "    private static final String VERSION=\"" + version + "\";" +
                "\n" +
                "    public static void reportVersion(){\n" +
                "        System.out.println(\"Posting version to VersionViewer: \" + VERSION);\n" +
                "    }\n" +
                "}";
    }
}
