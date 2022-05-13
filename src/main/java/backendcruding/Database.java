package backendcruding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Code below partially recycled from Database lab.
 *
 * Database class instantiates the database and returns/edits it.
 */
public class Database {

  private static Connection conn = null;

  /**
   * Empty constructor for the Database class.
   */
  public Database(){}

  /**
   * Instantiates the database. Copied from the Database lab.
   * @param filename file name of SQLite3 database to open.
   */
  public Database(String filename) {
    try {
      // this line loads the driver manager class, and must be
      // present for everything else to work properly
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
      // these two lines tell the database to enforce foreign keys during operations,
      // and should be present
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
      System.out.println("INFO: Database " + filename + " loaded.");
    } catch (ClassNotFoundException e) {
      System.out.print("ERROR: " + e.getMessage());
    } catch (SQLException e) {
      System.out.print("ERROR: " + e.getMessage());
    }
  }

  /**
   * Method that gets the table names in a database.
   * @return all the table names in the database.
   */
  public List<String> getImage() {
    List<String> imageNames = new ArrayList<>();
    try {
      String sql = "SELECT imagePath FROM imageDictionary ORDER BY RANDOM() LIMIT 1";
      PreparedStatement imageFinder = conn.prepareStatement(sql);
      ResultSet rs = imageFinder.executeQuery();
      // Ensure that we get all the tables in the database with while loop.
      while (rs.next()) {
        // Index based on 1, 2, etc. instead of 0, 1, etc.
        imageNames.add(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
    return imageNames;
  }

  /**
   * Method that gets the table names in a database.
   * @return all the table names in the database.
   */
  public List<String> getWord() {
    List<String> word = new ArrayList<>();
    try {
      String sql = "SELECT words FROM wordsDictionary ORDER BY RANDOM() LIMIT 1";
      PreparedStatement imageFinder = conn.prepareStatement(sql);
      ResultSet rs = imageFinder.executeQuery();
      // Ensure that we get all the tables in the database with while loop.
      while (rs.next()) {
        // Index based on 1, 2, etc. instead of 0, 1, etc.
        word.add(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
    return word;
  }

  /**
   * Method that gets all the words in the word bank
   * @return the word bank for Wordle
   */
  public List<String> getBank() {
    List<String> word = new ArrayList<>();
    try {
      String sql = "SELECT words FROM wordsDictionary WHERE 3 <= LENGTH(words) <= 8";
      PreparedStatement imageFinder = conn.prepareStatement(sql);
      ResultSet rs = imageFinder.executeQuery();
      // Ensure that we get all the tables in the database with while loop.
      while (rs.next()) {
        // Index based on 1, 2, etc. instead of 0, 1, etc.
        word.add(rs.getString(1).toLowerCase());
      }
    } catch (SQLException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
    return word;
  }

}

