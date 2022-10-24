package swe4.rmi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoriesDatabase {
  private static final List<String> cat = new ArrayList<>();

  public static void initializeCategories() {
    cat.add("Hygiene");
    cat.add("Lebensmittel");
    cat.add("Kleidung");
    cat.add("Elektronik");
  }

  public static Collection<String> getCats() {
    return cat;
  }
}
