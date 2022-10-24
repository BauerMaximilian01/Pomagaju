package swe4.database;

import swe4.pomagajuclasses.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PomagajuDAOImpl implements PomagajuDAO {
  private Database db = null;

  public PomagajuDAOImpl(String connectionString, String user, String pwd) {
    db = new Database(connectionString, user, pwd);
  }

  @Override
  public String changeFacility(String oldName, Facility f, boolean activeness) {
    if (getFacility(oldName) != null) {
      try (Statement statement = db.getConnection().createStatement()) {
        statement.executeUpdate(String.format("UPDATE address SET street = '%s', houseNumber = %d, zipCode = '%s', location = '%s' WHERE id = (SELECT addressId FROM facilities WHERE facilityName = '%s')",
            f.getAddress().getStreet().replaceAll("[0-9]", ""),
            Integer.parseInt(f.getAddress().getStreet().replaceAll("\\D+", "")),
            f.getAddress().getZipCode(), f.getAddress().getLocation(), oldName));


        statement.executeUpdate(String.format("UPDATE facilities SET facilityName = '%s', country = '%s', district = '%s', region = '%s', activeness = '%s' WHERE facilityName = '%s'",
            f.getName(), f.getCountry(), f.getDistrict(), f.getRegion(), activeness?"aktiv":"inaktiv", oldName));

        return getFacility(f.getName()).getName();

      } catch (Exception x) {
        throw new DataAccessException("SQLException: " + x.getMessage());
      }
    }

    return null;
  }

  @Override
  public void removeFacility(String name) {
    try (Statement statement = db.getConnection().createStatement()) {
      statement.executeUpdate(String.format("DELETE FROM facilities WHERE facilityName = '%s'", name));
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }
  }

  @Override
  public Collection<Facility> getFacilitiesWhere(String whereClause) {
    Collection<Facility> result = new ArrayList<>();
    System.out.println(whereClause);
    String sql = String.format("SELECT * FROM facilities INNER JOIN address ON facilities.addressId = address.id %s", whereClause);

    try (Statement statement = db.getConnection().createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
      while (resultSet.next()) {
        result.add(new Facility(resultSet.getString("facilityName"),
            resultSet.getString("country"),
            resultSet.getString("district"),
            new Address(resultSet.getString("street") + " " + resultSet.getInt("houseNumber"),
                resultSet.getString("zipCode"), resultSet.getString("location")),
            resultSet.getString("region"),
            resultSet.getString("activeness")
        ));
      }
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }

    return result;
  }

  @Override
  public boolean addFacility(Facility f) {
    if (getFacility(f.getName()) == null) {
      try (Statement statement = db.getConnection().createStatement()) {
        statement.executeUpdate(String.format("INSERT INTO address (street, houseNumber, zipCode, location) VALUES ('%s', %d, '%s', '%s')",
            f.getAddress().getStreet().replaceAll("[0-9]", ""),
            Integer.parseInt(f.getAddress().getStreet().replaceAll("\\D+", "")),
            f.getAddress().getZipCode(), f.getAddress().getLocation()), Statement.RETURN_GENERATED_KEYS);

        try (ResultSet resultSet = statement.getGeneratedKeys()) {

          if (resultSet != null && resultSet.next()) {
            int id = resultSet.getInt(1);
            statement.executeUpdate(String.format("INSERT INTO facilities (facilityName, country, district, addressId, region, activeness) VALUES ('%s', '%s', '%s', %d, '%s', '%s')",
                f.getName(), f.getCountry(), f.getDistrict(), id, f.getRegion(), f.getActive()));

            return true;
          }

        } catch (SQLException x) {
          throw new DataAccessException("SQLException: " + x.getMessage());
        }

      } catch (Exception x) {
        throw new DataAccessException("SQLException: " + x.getMessage());
      }
    }

    return false;
  }

  @Override
  public Facility getFacility(String name) {
    Facility f = null;

    try (Statement statement = db.getConnection().createStatement();
         ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM facilities INNER JOIN address ON facilities.addressId = address.id WHERE facilityName = '%s'", name))) {
      while (resultSet.next()) {
        f = new Facility(resultSet.getString("facilityName"),
            resultSet.getString("country"),
            resultSet.getString("district"),
            new Address(resultSet.getString("street") + " " + resultSet.getInt("houseNumber"),
                resultSet.getString("zipCode"), resultSet.getString("location")),
            resultSet.getString("region"),
            resultSet.getString("activeness"));
      }
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }

    return f;
  }


  @Override
  public Collection<Facility> getFacilities() {
    return getFacilitiesWhere("");
  }

  @Override
  public Collection<Goods> getGoodsWhere(String whereClause) {
    Collection<Goods> result = new ArrayList<>();
    String sql = String.format("SELECT * FROM goods INNER JOIN facilities ON goods.facilityId = facilities.id INNER JOIN categories ON goods.categoryId = categories.id %s", whereClause);

    try (Statement statement = db.getConnection().createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
      while (resultSet.next()) {
        result.add(new Goods(resultSet.getString("identifier"),
            resultSet.getString("description"),
            resultSet.getString("stateOfGood"),
            resultSet.getString("category"),
            resultSet.getInt("quantity")
        ));
      }
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }

    return result;
  }

  @Override
  public void changeGood(Facility f, String oldIdent, Goods newGood) {
    try (Statement statement = db.getConnection().createStatement()) {
      statement.executeUpdate(String.format("UPDATE goods SET identifier = '%s', description = '%s', stateOfGood = '%s', categoryId = (SELECT id FROM categories WHERE category = '%s'), quantity = %d " +
                                            "WHERE facilityId = (SELECT id FROM facilities WHERE facilityName = '%s') AND identifier = '%s'",
                                            newGood.getIdentifier(), newGood.getDescription(), newGood.getState(), newGood.getCategory(), newGood.getQuantity(), f.getName(), newGood.getIdentifier()));
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }
  }

  @Override
  public void removeGood(Facility f, Goods g) {
    try (Statement statement = db.getConnection().createStatement()) {
      statement.executeUpdate(String.format("DELETE FROM goods WHERE facilityId = (SELECT id FROM facilities WHERE facilityName = '%s') AND identifier = '%s'", f.getName(), g.getIdentifier()));
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }
  }

  private boolean getGood(Facility f, Goods g) {
    try (Statement statement = db.getConnection().createStatement()) {
      statement.executeQuery(String.format("SELECT COUNT(id) as counted FROM goods WHERE facilityId = (SELECT id FROM facilities WHERE facilityName = '%s') AND identifier = '%s'",
                                           f.getName(), g.getIdentifier()));

      ResultSet resultSet = statement.getResultSet();
      resultSet.next();
      if (resultSet.getInt("counted") == 1) {
        return true;
      } else {
        return false;
      }
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }
  }

  @Override
  public boolean addGood(Facility f, Goods g) {
    if (!getGood(f, g)) {
      try (Statement statement = db.getConnection().createStatement()) {
        statement.executeUpdate(String.format("INSERT INTO goods (facilityId, identifier, description, stateOfGood, categoryId, quantity) " +
                                              "VALUES ((SELECT id FROM facilities WHERE facilityName = '%s'), '%s', '%s', '%s', (SELECT id FROM categories WHERE category = '%s'), %d)",
                                              f.getName(), g.getIdentifier(), g.getDescription(), g.getState(), g.getCategory(), g.getQuantity()));

        return true;
      } catch (SQLException x) {
        throw new DataAccessException("SQLException: " + x.getMessage());
      }
    }

    return false;
  }

  @Override
  public Collection<Donations> getDonationsWhere(String whereClause) {
    Collection<Donations> result = new ArrayList<>();
    String sql = String.format("SELECT DISTINCT * FROM donations INNER JOIN facilities ON donations.facilityId = facilities.id INNER JOIN goods ON donations.good = goods.identifier " +
        " INNER JOIN categories ON goods.categoryId = categories.id %s", whereClause);

    try (Statement statement = db.getConnection().createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
      while (resultSet.next()) {
        Date date = new Date(resultSet.getTimestamp("delivery").getTime());
        result.add(new Donations(resultSet.getString("email"),
            new Goods(resultSet.getString("identifier"),
                resultSet.getString("description"),
                resultSet.getString("stateOfGood"),
                resultSet.getString("category"),
                resultSet.getInt("quantity")),
            resultSet.getInt("quantity"),
            LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()))
        );
      }
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }

    return result;
  }

  @Override
  public boolean addDonation(Facility f, Donations d) {
    try (Statement statement = db.getConnection().createStatement()) {
      statement.executeUpdate(String.format("INSERT INTO donations (facilityId, userId, email, good, quantity, delivery, token) " +
              "VALUES ((SELECT id FROM facilities WHERE facilityName = '%s'), (SELECT id FROM users WHERE email = '%s'), '%s', '%s', %d, '%s', '%s')",
          f.getName(), d.getEmail(), d.getEmail(), d.getGood(), d.getQuantity(), d.getTimeStamp(), d.getToken()));

      return true;
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }
  }

  @Override
  public Collection<String> getCategories() {
    Collection<String> result = new ArrayList<>();

    try (Statement statement = db.getConnection().createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT category FROM categories")) {
      while (resultSet.next()) {
        result.add(resultSet.getString("category"));
      }
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }

    return result;
  }

  @Override
  public boolean addUser(User u) {
    if (getUser(u.getUsername()) == null) {
      try (Statement statement = db.getConnection().createStatement()) {
        statement.executeUpdate(String.format("INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES ('%s', '%s', '%s', '%s', '%s')",
                                              u.getFirstName(), u.getLastName(), u.getMail(), u.getUsername(), u.getPassword()));

        return true;
      } catch (SQLException x) {
        throw new DataAccessException("SQLException: " + x.getMessage());
      }
    }

    return false;
  }

  @Override
  public User getUser(String name) {
    User u = null;

    try (Statement statement = db.getConnection().createStatement();
         ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM users WHERE userName = '%s'", name))) {
      while (resultSet.next()) {
        u = new User(resultSet.getString("firstName"),
            resultSet.getString("lastName"),
            resultSet.getString("userName"),
            resultSet.getString("email"),
            resultSet.getString("pwd"));
      }
    } catch (SQLException x) {
      throw new DataAccessException("SQLException: " + x.getMessage());
    }

    return u;
  }

  @Override
  public boolean addUser(String mail, String pw) {
    if (getUser(mail) == null) {
      try (Statement statement = db.getConnection().createStatement()) {
        System.out.println(mail + " " + pw);
        statement.executeUpdate(String.format("INSERT INTO users (email, userName, pwd) VALUES ('%s', '%s', '%s')",
                                              mail, mail, pw));

        return true;
      } catch (SQLException x) {
        throw new DataAccessException("SQLException: " + x.getMessage());
      }
    }

    return false;
  }
}
