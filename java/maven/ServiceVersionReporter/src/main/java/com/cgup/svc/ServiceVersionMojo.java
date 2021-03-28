package com.cgup.svc;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Goal which obtains git commit.
 *
 * @goal versioncommit
 * 
 * @phase process-sources
 */
public class ServiceVersionMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     * @parameter property="project.build.directory"
     * @required
     */
    private File outputDirectory;

    /**
     * @parameter property="project.groupId"
     */
    private String pkg;

    /**
     * @parameter property="project.artifactId"
     */
    private String serviceName;

    /**
     * @parameter property="mainClass" default-value="com.sample.App"
     */
    private String mainClass;

    /**
     * @parameter property="ENVIRONMENT_ID" default-value="ENVIRONMENT"
     */
    private String environmentIdentifier;

    /**
     * @parameter property="versioncommand" default-value="git describe --all"
     */
    private String versionCommand;

    /**
     * @parameter property="commitommand" default-value="git rev-parse HEAD"
     */
    private String commitCommand;

    /**
     * @parameter property="repocommand" git config --get remote.origin.url"
     */
    private String repoCommand;

    private String executeCommand(String command) throws IOException {
        CommandExecutor exec = new CommandExecutor(commitCommand);
        return exec.runCommand();
    }


    public void execute()
        throws MojoExecutionException
    {
        try {
            String commit = executeCommand(commitCommand);
            String repo = executeCommand(repoCommand);
            ManifestFileCreator cc = new ManifestFileCreator(outputDirectory, pkg, "Version.MF");
            cc.write(serviceName, environmentIdentifier, commit, repo);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }
}
