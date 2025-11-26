import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {

    // Change for MySQL or PostgreSQL
    static final String URL = "jdbc:mysql://localhost:3306/employeedb";
    // static final String URL = "jdbc:postgresql://localhost:5432/employeedb";

    static final String USER = "root";         // your DB username
    static final String PASS = "password";     // your DB password

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            System.out.println("Connected Successfully!");

            while (true) {
                System.out.println("\n====== EMPLOYEE MENU ======");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addEmployee(con, sc);
                        break;
                    case 2:
                        viewEmployees(con);
                        break;
                    case 3:
                        updateEmployee(con, sc);
                        break;
                    case 4:
                        deleteEmployee(con, sc);
                        break;
                    case 5:
                        System.out.println("Thank you!");
                        return;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ADD EMPLOYEE
    private static void addEmployee(Connection con, Scanner sc) throws SQLException {
        sc.nextLine(); // clear buffer

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        sc.nextLine(); 
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        String sql = "INSERT INTO employees (name, salary, department) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setDouble(2, salary);
        ps.setString(3, dept);

        int rows = ps.executeUpdate();
        System.out.println(rows + " Employee Added!");
    }

    // VIEW EMPLOYEES
    private static void viewEmployees(Connection con) throws SQLException {
        String sql = "SELECT * FROM employees";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- All Employees ---");
        while (rs.next()) {
            System.out.println(
                rs.getInt("id") + " | " +
                rs.getString("name") + " | " +
                rs.getDouble("salary") + " | " +
                rs.getString("department")
            );
        }
    }

    // UPDATE EMPLOYEE
    private static void updateEmployee(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to Update Salary: ");
        int id = sc.nextInt();

        System.out.print("Enter New Salary: ");
        double salary = sc.nextDouble();

        String sql = "UPDATE employees SET salary=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, salary);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();
        System.out.println(rows + " Employee Updated!");
    }

    // DELETE EMPLOYEE
    private static void deleteEmployee(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to Delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM employees WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        int rows = ps.executeUpdate();
        System.out.println(rows + " Employee Deleted!");
    }
}
