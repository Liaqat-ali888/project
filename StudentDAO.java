import java.sql.*;

public class StudentDAO {

    Connection con;

    public StudentDAO() {
        this.con = DBConnection.getConnection();
    }

    // ========== STUDENT OPERATIONS ==========

    public int insertStudent(String name) {
        try {
            String sql = "INSERT INTO students (name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                ps.close();
                return id;
            }
            ps.close();
            return -1;
        } catch (SQLException e) {
            System.out.println("Insert Student Error: " + e.getMessage());
            return -1;
        }
    }

    public ResultSet getAllStudents() {
        try {
            String sql = "SELECT * FROM students ORDER BY id";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            System.out.println("[DAO] Executing: " + sql);
            return rs;
        } catch (SQLException e) {
            System.out.println("Get Students Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Student getStudentById(int id) {
        try {
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Student s = new Student(rs.getInt("id"), rs.getString("name"));
                ps.close();
                return s;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Get Student Error: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteStudent(int id) {
        try {
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Delete Student Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ========== SUBJECT OPERATIONS ==========

    public int insertSubject(int studentId, String name) {
        try {
            String sql = "INSERT INTO subjects (student_id, name) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, studentId);
            ps.setString(2, name);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                ps.close();
                return id;
            }
            ps.close();
            return -1;
        } catch (SQLException e) {
            System.out.println("Insert Subject Error: " + e.getMessage());
            return -1;
        }
    }

    public ResultSet getSubjectsByStudent(int studentId) {
        try {
            String sql = "SELECT * FROM subjects WHERE student_id = ? ORDER BY id";
            PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            System.out.println("[DAO] Executing: SELECT subjects for student " + studentId);
            return rs;
        } catch (SQLException e) {
            System.out.println("Get Subjects Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteSubject(int subjectId) {
        try {
            // First delete all deadlines for this subject
            String deleteDl = "DELETE FROM deadlines WHERE subject_id = ?";
            PreparedStatement ps1 = con.prepareStatement(deleteDl);
            ps1.setInt(1, subjectId);
            ps1.executeUpdate();
            ps1.close();

            // Then delete all topics for this subject
            String deleteTp = "DELETE FROM topics WHERE subject_id = ?";
            PreparedStatement ps2 = con.prepareStatement(deleteTp);
            ps2.setInt(1, subjectId);
            ps2.executeUpdate();
            ps2.close();

            // Finally delete the subject
            String deleteSub = "DELETE FROM subjects WHERE id = ?";
            PreparedStatement ps3 = con.prepareStatement(deleteSub);
            ps3.setInt(1, subjectId);
            int result = ps3.executeUpdate();
            ps3.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Delete Subject Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Subject getSubjectById(int subjectId) {
        try {
            String sql = "SELECT * FROM subjects WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Subject s = new Subject(rs.getInt("id"), rs.getString("name"));
                ps.close();
                return s;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Get Subject Error: " + e.getMessage());
            return null;
        }
    }

    // ========== TOPIC OPERATIONS ==========

    public int insertTopic(int subjectId, String name) {
        try {
            String sql = "INSERT INTO topics (subject_id, name, completed, confidence_level) VALUES (?, ?, 0, 1)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, subjectId);
            ps.setString(2, name);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                ps.close();
                return id;
            }
            ps.close();
            return -1;
        } catch (SQLException e) {
            System.out.println("Insert Topic Error: " + e.getMessage());
            return -1;
        }
    }

    public ResultSet getTopicsBySubject(int subjectId) {
        try {
            String sql = "SELECT * FROM topics WHERE subject_id = ? ORDER BY id";
            PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();
            System.out.println("[DAO] Executing: SELECT topics for subject " + subjectId);
            return rs;
        } catch (SQLException e) {
            System.out.println("Get Topics Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Topic getTopicById(int topicId) {
        try {
            String sql = "SELECT * FROM topics WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, topicId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Topic t = new Topic(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBoolean("completed"),
                    rs.getInt("confidence_level")
                );
                ps.close();
                return t;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Get Topic Error: " + e.getMessage());
            return null;
        }
    }

    public boolean updateTopic(int topicId, boolean completed, int confidence) {
        try {
            String sql = "UPDATE topics SET completed = ?, confidence_level = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, completed);
            ps.setInt(2, confidence);
            ps.setInt(3, topicId);
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Update Topic Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTopic(int topicId) {
        try {
            String sql = "DELETE FROM topics WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, topicId);
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Delete Topic Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ========== DEADLINE OPERATIONS ==========

    public int insertDeadline(int subjectId, String title, String date, String subjectName, String type) {
        try {
            String sql = "INSERT INTO deadlines (subject_id, title, date, subject_name, type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, subjectId);
            ps.setString(2, title);
            ps.setString(3, date);
            ps.setString(4, subjectName);
            ps.setString(5, type);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                ps.close();
                return id;
            }
            ps.close();
            return -1;
        } catch (SQLException e) {
            System.out.println("Insert Deadline Error: " + e.getMessage());
            return -1;
        }
    }

    public ResultSet getDeadlinesBySubject(int subjectId) {
        try {
            String sql = "SELECT * FROM deadlines WHERE subject_id = ? ORDER BY date";
            PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();
            System.out.println("[DAO] Executing: SELECT deadlines for subject " + subjectId);
            return rs;
        } catch (SQLException e) {
            System.out.println("Get Deadlines Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Deadline getDeadlineById(int deadlineId) {
        try {
            String sql = "SELECT * FROM deadlines WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, deadlineId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String dateStr = rs.getString("date");
                String subName = rs.getString("subject_name");
                String type = rs.getString("type");

                // Parse date string to LocalDate
                java.time.LocalDate date = java.time.LocalDate.parse(dateStr);

                Deadline d;
                if (type.equals("Exam")) {
                    d = new ExamDeadline(rs.getInt("id"), title, date, subName);
                } else {
                    d = new AssignmentDeadline(rs.getInt("id"), title, date, subName);
                }
                ps.close();
                return d;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Get Deadline Error: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteDeadline(int deadlineId) {
        try {
            String sql = "DELETE FROM deadlines WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, deadlineId);
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Delete Deadline Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}