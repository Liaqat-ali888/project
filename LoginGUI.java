import javax.swing.*;

public class LoginGUI extends JFrame {

    public LoginGUI() {

        setTitle("Student Tracker Login");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        JLabel titleLabel = new JLabel("📚 STUDENT TRACKER");
        titleLabel.setBounds(80, 30, 250, 40);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
        add(titleLabel);

        // Username
        JLabel u = new JLabel("Username:");
        u.setBounds(50, 90, 100, 25);
        u.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        add(u);

        JTextField user = new JTextField();
        user.setBounds(150, 90, 200, 25);
        add(user);

        // Password
        JLabel p = new JLabel("Password:");
        p.setBounds(50, 140, 100, 25);
        p.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        add(p);

        JPasswordField pass = new JPasswordField();
        pass.setBounds(150, 140, 200, 25);
        add(pass);

        // Login Button
        JButton btn = new JButton("LOGIN");
        btn.setBounds(125, 200, 150, 40);
        btn.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        add(btn);

        // Login Action
        btn.addActionListener(e -> {
            String username = user.getText();
            String password = new String(pass.getPassword());

            if (username.equals("admin") && password.equals("123")) {
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new DashboardGUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                user.setText("");
                pass.setText("");
            }
        });

        setVisible(true);
    }

//    public static void main(String[] args) {
//        new LoginGUI();
//    }
//}
}