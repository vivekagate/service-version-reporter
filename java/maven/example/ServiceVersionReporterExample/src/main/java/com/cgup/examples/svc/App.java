package com.cgup.examples.svc;

import com.cgup.svc.VersionReporter;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws Exception {
        VersionReporter.reportVersion("1234-41231-12312-131231");
        while(true){
        	System.out.println("Hello World!");
	    	Thread.sleep(1000);
	    }
    }
}
