import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PatientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hmo_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM patients *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Patient_instantiates_true() {
    Patient patient = new Patient("Bub", "1986-08-18");
    assertEquals(true, patient instanceof Patient);
  }

  @Test
  public void Patient_gettersWork_true() {
    Patient patient = new Patient("Bub", "1986-08-18");
    assertEquals("Bub", patient.getName());
    assertEquals("1986-08-18", patient.getBirthday());
  }

  @Test
  public void save_returnsPatientIfNamesMatch_true() { // must make all() method save() method, and Override .equals method
    Patient patient = new Patient("Bub", "1986-08-18");
    patient.save();
    assertTrue(Patient.all().get(0).equals(patient));
  }

  @Test
  public void getId_tasksInstantiateWithAnId() {
    Patient pat = new Patient("Bub", "1986-08-18");
    pat.save();
    assertTrue(pat.getId() > 0);
  }

  @Test
  public void find_returnsPatientById_pat2() {
    Patient pat = new Patient("Bub", "1986-08-18");
    pat.save();
    Patient pat2 = new Patient("Blah", "1986-08-14");
    pat2.save();
    assertEquals(Patient.find(pat2.getId()), pat2);
  }
}
