package edu.brown.cs.student.main;

import backendcruding.Database;
import com.google.gson.Gson;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.Spark;

import spark.Request;
import spark.Response;
import spark.Route;



//import static spark.route.HttpMethod.get;

/**
 * The Main class of our project. This is where execution begins.
 */

public final class Main {

  private static final int DEFAULT_PORT = 4567;
  private static Database db = null;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;


  private Main(String[] args) {
    this.args = args;
  }

  private void run() {

    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }
  }

  private static void runSparkServer(int port) {
    System.out.println("Starting spark server on port " + port);
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");

    Spark.options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    Gson gson = new Gson();
    // Put Routes Here
    db = new Database("data/image-dictionary.sqlite3");

    Spark.get("/getImage", (req, res) -> {
      return gson.toJson(db.getImage());
    });

    Spark.get("/getWord", (req, res) -> {
      return gson.toJson(db.getWord());
    });

    Spark.get("/getBank", (req, res) -> {
      return gson.toJson(db.getBank());
    });

    Spark.init();

  }

}
