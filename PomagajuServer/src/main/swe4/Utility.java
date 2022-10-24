//       $Id: Utility.java 43737 2021-05-24 09:21:15Z p20068 $
//      $URL: https://svn01.fh-hagenberg.at/bin/cepheiden/Inhalt/Java/RMI/PC-template/buffer/src/main/swe4/Utility.java $
// $Revision: 43737 $
//     $Date: 2021-05-24 11:21:15 +0200 (Mo., 24 Mai 2021) $
//   Creator: peter.kulczycki<AT>fh-hagenberg.at
//   $Author: p20068 $

package swe4;

import java.rmi.registry.Registry;
import java.util.Random;

public class Utility {
  static       Random rnd   = new Random ();
  static final long   start = startTiming ();

// -----------------------------------------------

  public static boolean equalsWithNulls (Object a, Object b) {
    if (a == b)
      return true;

    else if ((a == null) || (b == null))
      return false;

    return a.equals (b);
  }

// -----------------------------------------------

  public static double elapsedTime () {   // [s]
    return stopTiming (start);
  }

// -----------------------------------------------

  public static long startTiming () {
    return System.nanoTime ();
  }

  public static double stopTiming (final long start) {   // [s]
    return (System.nanoTime () - start) / 1000000000.0;
  }

  public static double stopTiming (final long start, final int reps) {   // [s]
    return stopTiming (start) / Math.max (1, reps);
  }

// -----------------------------------------------

  public static double timedRun (Runnable target) {   // [s]
    return timedRun (1, target);
  }

  public static double timedRun (int reps, Runnable target) {   // [s]
    reps = Math.max (1, reps);

    final long start = startTiming ();

    for (int r = 0; r < reps; ++r)
      target.run ();

    return stopTiming (start, reps);
  }

// -----------------------------------------------

  public static int nextInt (int bound) {
    return rnd.nextInt (bound);
  }

  public static char toUpperChar (int i) {
    return (char) ('A' + (i % 26));
  }

// -----------------------------------------------

  public static void sleep () {   // sleep for a spell
    sleep (50);
  }

  public static void sleep (int millis) {
    try {
      Thread.sleep (millis);
    } catch (InterruptedException x) {
    }
  }

  public static void sleepRandom (int millis) {
    sleep (nextInt (millis));
  }

// -----------------------------------------------

  public static void joinAll (Thread... threads) {
    for (Thread t : threads)
      try {
        t.join ();
      } catch (InterruptedException x) {
        x.printStackTrace ();
      }

    System.out.println ("joined " + threads.length + " threads ...");
  }

  public static void wait (Object obj) {
    try {
      obj.wait ();
    } catch (InterruptedException x) {
    }
  }

// -----------------------------------------------

  public static String parseHost (String[] args, String defaultHost) {   // host[:port] in args[0]
    String host = defaultHost;

    if ((args.length > 0) && !args[0].isEmpty ()) {
      int e = args[0].lastIndexOf (':');

      host = args[0].substring (0, ((e == -1) ? args[0].length () : e) /*- 1*/);
    }

    return host;
  }

  public static int parsePort (String[] args, int defaultPort) {   // host[:port] in args[0]
    int port = defaultPort;

    if (args.length > 0) {
      int b = args[0].lastIndexOf (':');

      if (b != -1)
        port = Integer.parseInt (args[0].substring (b + 1));
    }

    return port;
  }

// -----------------------------------------------

  public static String getRMIRegistryURL (String host, int port, String serviceName) {
    return "rmi://" + host + ":" + port + "/" + serviceName;
  }

  public static String getRMIRegistryURL (String[] args, String serviceName) {
    return getRMIRegistryURL (parseHost (args, "localhost"),   //
        parsePort (args, Registry.REGISTRY_PORT),          // default port for RMI registry: 1099
        serviceName                                        //
    );
  }
}
