public class Main {
    public static void main(String[] args) {

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   STUDENT TRACKER - STARTING UP        ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.println("Testing database connection...");

        if (DBConnection.testConnection()) {
            System.out.println("\n✓ Database connection successful!");
            System.out.println("✓ Database: student_db");
            System.out.println("✓ Launching application...\n");

            // Run GUI safely
            javax.swing.SwingUtilities.invokeLater(() -> {
                new LoginGUI();
            });

        } else {
            System.out.println("\n✗ Database connection FAILED!");
            System.out.println("\nPlease check:");
            System.out.println("1. MySQL server is running");
            System.out.println("2. Database 'student_db' exists");
            System.out.println("3. Run schema.sql to create database");
            System.out.println("4. mysql-connector-j JAR is in classpath");
        }
    }
}