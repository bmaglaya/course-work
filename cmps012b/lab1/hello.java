
// $Id: hello.java,v 1.3 2012-10-02 12:10:50-07 bmaglaya - $
//
// Blase Maglaya bmaglaya@unix.ic.ucsc.edu
//	 hello - a verbose version of the classical 'Hellow World' program
//
// SYNOPSIS
//	 hello
//
// DESCRIPTION
//	 Prints the message 'Hello World' to stdout, and otherwise introduces it
//	 introduces itself and its environment
//

import java.util.ArrayList;
import static java.lang.System.*;

class hello {

	static void printfprop (String format, String... props) {
		ArrayList<Object> propvals = new ArrayList<Object>();
		for (String prop : props) propvals.add (getProperty (prop));
		out.printf (format, propvals.toArray());
	}

	public static void main (String[] args) {
		double freemem = Runtime.getRuntime().freeMemory();
	  	long now = currentTimeMillis();
		out.printf ("Hello, World!%n");
		printfprop ("Class path is %s%n", "java.class.path");
		printfprop ("Operating system is %s-bit %s-endian %s %s %s%n",
	              "sun.arch.data.model", "sun.cpu.endian",
		      "os.arch", "os.name", "os.version");
	  	printfprop ("Runtime is %s (%s)%n", "java.runtime.name",
	              "java.runtime.version");
	  	printfprop ("Virtual machine is %s (%s)%n", "java.nm.name",
		      "java.vm.version");
	  	printfprop ("home is %s (%s)%n", "java.home", "java.version");
	  	out.printf ("there are %.0f bytes = %.2f Kbytes = %.4f Mbytes"
			+ " of free memory%n",
			freemem, freemem / (1<<10), freemem / (1<<20));
	  	out.printf ("Time is now %.3f seconds = %tc%n", now / 1e3, now);
	}

}	

