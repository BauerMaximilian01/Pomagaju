package swe4.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private Connection connection       = null;
  private String     connectionString = null;
  private String     pwd              = null;
  private String     user             = null;

  public Database (String connectionString, String user, String pwd) {
    this.connectionString = connectionString;
    this.user             = user;
    this.pwd              = pwd;
  }

  private void openConnection () {
    try {
      connection = DriverManager.getConnection(connectionString, user, pwd);
    } catch (SQLException x) {
      throw new DataAccessException ("Can't establish connection to database.");
    }
  }

  public Connection getConnection () {
    if (connection == null)
      openConnection();

    return connection;
  }

  public void disposeConnection () {
    try {
      if (connection != null)
        connection.close();

      connection = null;
    } catch (SQLException x) {
      throw new DataAccessException ("Can't close database connection.");
    }
  }
}
