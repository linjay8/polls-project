import java.sql.*;
import java.util.ArrayList;
import java.io.*;
public class DatabaseUtil {
	static String db = "jdbc:mysql://localhost:3306/FinalProject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Los_Angeles";
	static String user = "root";
	static String pwd = "password";
	
	// Adds a new class into the database
	public static void addNewClass(Class c, String userId)
	{
		String classCode = c.getClassCode();
		String className = c.getClassName();
		String sql = "INSERT INTO Class (classCode, className, instructorID) "
				+ "VALUES (?, ?, ?)";
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, classCode);
			ps.setString(2, className);
			ps.setString(3, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Adds a new user into the database
	public static void addNewUser(User u)
	{
		String[] names = u.getFullName().split(" ");
		String fname = names[0];
		String lname = names[1];
		String sql = "INSERT INTO UserInfo (userID, firstname, lastname, email, accountlevel) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, u.getUserId());
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setString(4, u.getEmail());
			ps.setInt(5, u.getAccountLevel());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Checks if a user already exists in the database
	public static boolean userExists(String userId)
	{
		String sql = "SELECT COUNT(*) FROM UserInfo u WHERE u.userID = ?;";
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next())
			{
				count = rs.getInt(1);
			}
			if(count > 0)
			{
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Checks if a classcode already exists in the database
	public static boolean classCodeExists(String classCode)
	{
		String sql = "SELECT COUNT(*) FROM Class c WHERE c.classCode = ?;";
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, classCode);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next())
			{
				count = rs.getInt(1);
			}
			if(count > 0)
			{
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Checks if a student is already enrolled in a class
	public static boolean alreadyEnrolled(String classCode, String userId)
	{
		String sql = "SELECT COUNT(*) FROM ClassMember c WHERE c.classCode = ? AND c.studentID = ?;";
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, classCode);
			ps.setString(2, userId);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next())
			{
				count = rs.getInt(1);
			}
			if(count > 0)
			{
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Returns a student from the database, null if they do not exist
	public static Student getStudent(String userId)
	{
		String sql = "SELECT s.* FROM UserInfo s WHERE s.userID = ?";
		Student s = null;
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("firstname") + " " + rs.getString("lastname");
				String email = rs.getString("email");
				String id = rs.getString("userID");
				s = new Student(name, email, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	// Returns an instructor from the database, null if they do not exist
	public static Instructor getInstructor(String userId)
	{
		String sql = "SELECT i.* FROM UserInfo i WHERE i.userID = ?";
		Instructor i = null;
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("firstname") + " " + rs.getString("lastname");
				String email = rs.getString("email");
				String id = rs.getString("userID");
				i = new Instructor(name, email, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	// Returns a class from the database, null if it does not exist
	public static Class getClass(String classCode)
	{
		String sql = "SELECT i.*, c.* FROM UserInfo i, Class c WHERE c.classcode = ? AND c.instructorID = i.userID";
		Class c = null;
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, classCode);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("firstname") + " " + rs.getString("lastname");
				String email = rs.getString("email");
				String id = rs.getString("userID");
				Instructor i = new Instructor(name, email, id);
				String className = rs.getString("classname");
				c = new Class(i, className, classCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	// Adds a student to a class
	public static void addStudentToClass(String classCode, String userId)
	{
		String sql = "INSERT INTO ClassMember (studentID, classcode) "
				+ "VALUES (?, ?)";
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, userId);
			ps.setString(2, classCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Returns the instructor of a class
	public static Instructor getInstructorFromClass(String classCode)
	{
		String sql = "SELECT i.* FROM UserInfo i, Class c WHERE c.classcode = ? AND c.studentID = i.userID";
		Instructor i = null;
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, classCode);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("firstname") + " " + rs.getString("lastname");
				String email = rs.getString("email");
				String id = rs.getString("userID");
				i = new Instructor(name, email, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	// Returns all of the students enrolled in a class
	public static ArrayList<Student> getStudentsFromClass(String classCode)
	{
		String sql = "SELECT s.* FROM UserInfo s, ClassMember c WHERE c.classcode = ? AND c.studentID = s.userID";
		ArrayList<Student> students = new ArrayList<Student>();
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, classCode);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("firstname") + " " + rs.getString("lastname");
				String email = rs.getString("email");
				String id = rs.getString("userID");
				Student s = new Student(name, email, id);
				students.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	// Returns the list of classes an instructor has created
	public static ArrayList<Class> getClassesFromInstructor(Instructor i)
	{
		String instructorId = i.getUserId();
		String sql = "SELECT c.* FROM UserInfo i, Class c WHERE c.instructorID = ?";
		ArrayList<Class> classes = new ArrayList<Class>();
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, instructorId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("classname");
				String classcode = rs.getString("classcode");
				Class c = new Class(i, name, classcode);
				classes.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	// Returns the list of classes a student is enrolled in
	public static ArrayList<Class> getClassesFromStudent(Student s)
	{
		String studentId = s.getUserId();
		String sql = "SELECT c.classcode, c.classname, i.* FROM UserInfo i, ClassMember cm, Class c "
				+ "WHERE cm.studentID = ? "
				+ "AND cm.classcode = c.classcode AND c.instructorID = i.userID";
		ArrayList<Class> classes = new ArrayList<Class>();
		try(Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);)
		{
			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String className = rs.getString("classname");
				String classCode = rs.getString("classcode");
				String name = rs.getString("firstname") + " " + rs.getString("lastname");
				String email = rs.getString("email");
				String userId = rs.getString("userID");
				Instructor i = new Instructor(name, email, userId);
				Class c = new Class(i, className, classCode);
				classes.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classes;
	}
}
