package swe4.database;

public class DataAccessException extends RuntimeException {
   public DataAccessException(String msg) {
      super (msg);
   }
}
