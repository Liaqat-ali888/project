import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class DashboardGUI extends JFrame {

    StudentDAO dao;

    // State - Track by ID
    int currentStudentId = -1;
    int currentSubjectId = -1;
    int currentTopicId = -1;

    // UI Components
    JList<String> studentListUI;
    DefaultListModel<String> studentModel;
    int[] studentIds = new int[100];
    int studentCount = 0;

    JList<String> subjectListUI;
    DefaultListModel<String> subjectModel;
    int[] subjectIds = new int[100];
    int subjectCount = 0;

    JList<String> topicListUI;
    DefaultListModel<String> topicModel;
    int[] topicIds = new int[100];
    int topicCount = 0;

    JCheckBox completeCheckBox;
    JComboBox<String> confidenceCombo;

    JList<String> deadlineListUI;
    DefaultListModel<String> deadlineModel;
    int[] deadlineIds = new int[100];
    int deadlineCount = 0;

    JTextArea reportArea;

    public DashboardGUI() {
        dao = new StudentDAO();

        setTitle("Student Tracker Dashboard");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // HEADER
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        JLabel headerLabel = new JLabel("STUDENT TRACKER DASHBOARD");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // LEFT PANEL - STUDENTS
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Students"));
        leftPanel.setPreferredSize(new Dimension(200, 0));

        studentModel = new DefaultListModel<>();
        studentListUI = new JList<>(studentModel);
        studentListUI.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onStudentSelected();
            }
        });

        JScrollPane studentScroll = new JScrollPane(studentListUI);
        leftPanel.add(studentScroll, BorderLayout.CENTER);

        JPanel leftBtnPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton addStudentBtn = new JButton("+ Add Student");
        addStudentBtn.addActionListener(e -> handleAddStudent());
        leftBtnPanel.add(addStudentBtn);

        JButton delStudentBtn = new JButton("- Delete");
        delStudentBtn.addActionListener(e -> handleDeleteStudent());
        leftBtnPanel.add(delStudentBtn);

        leftPanel.add(leftBtnPanel, BorderLayout.SOUTH);

        // CENTER PANEL - SUBJECTS + TOPICS
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel subjectPanel = new JPanel(new BorderLayout(10, 10));
        subjectPanel.setBorder(BorderFactory.createTitledBorder("Subjects"));

        subjectModel = new DefaultListModel<>();
        subjectListUI = new JList<>(subjectModel);
        subjectListUI.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onSubjectSelected();
            }
        });

        JScrollPane subjectScroll = new JScrollPane(subjectListUI);
        subjectPanel.add(subjectScroll, BorderLayout.CENTER);

        JPanel subBtnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton addSubBtn = new JButton("+ Add");
        addSubBtn.addActionListener(e -> handleAddSubject());
        subBtnPanel.add(addSubBtn);

        JButton delSubBtn = new JButton("- Delete");
        delSubBtn.addActionListener(e -> handleDeleteSubject());
        subBtnPanel.add(delSubBtn);

        subjectPanel.add(subBtnPanel, BorderLayout.SOUTH);

        JPanel topicPanel = new JPanel(new BorderLayout(10, 10));
        topicPanel.setBorder(BorderFactory.createTitledBorder("Topics"));

        topicModel = new DefaultListModel<>();
        topicListUI = new JList<>(topicModel);
        topicListUI.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onTopicSelected();
            }
        });

        JScrollPane topicScroll = new JScrollPane(topicListUI);
        topicPanel.add(topicScroll, BorderLayout.CENTER);

        JPanel topicControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        completeCheckBox = new JCheckBox("Mark Complete");
        completeCheckBox.addActionListener(e -> handleTopicUpdate());
        topicControlPanel.add(completeCheckBox);

        topicControlPanel.add(new JLabel("Confidence:"));
        String[] levels = {"1", "2", "3", "4", "5"};
        confidenceCombo = new JComboBox<>(levels);
        confidenceCombo.addActionListener(e -> handleTopicUpdate());
        topicControlPanel.add(confidenceCombo);

        topicPanel.add(topicControlPanel, BorderLayout.NORTH);

        JPanel topicBtnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton addTopBtn = new JButton("+ Add");
        addTopBtn.addActionListener(e -> handleAddTopic());
        topicBtnPanel.add(addTopBtn);

        JButton delTopBtn = new JButton("- Delete");
        delTopBtn.addActionListener(e -> handleDeleteTopic());
        topicBtnPanel.add(delTopBtn);

        topicPanel.add(topicBtnPanel, BorderLayout.SOUTH);

        JSplitPane centerSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, subjectPanel, topicPanel);
        centerSplit.setDividerLocation(150);
        centerPanel.add(centerSplit);

        // RIGHT PANEL
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350, 0));

        JPanel deadlinePanel = new JPanel(new BorderLayout(10, 10));
        deadlinePanel.setBorder(BorderFactory.createTitledBorder("Deadlines"));

        deadlineModel = new DefaultListModel<>();
        deadlineListUI = new JList<>(deadlineModel);

        JScrollPane deadlineScroll = new JScrollPane(deadlineListUI);
        deadlinePanel.add(deadlineScroll, BorderLayout.CENTER);

        JPanel deadlineBtnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton addDlBtn = new JButton("+ Add");
        addDlBtn.addActionListener(e -> handleAddDeadline());
        deadlineBtnPanel.add(addDlBtn);

        JButton delDlBtn = new JButton("- Delete");
        delDlBtn.addActionListener(e -> handleDeleteDeadline());
        deadlineBtnPanel.add(delDlBtn);

        deadlinePanel.add(deadlineBtnPanel, BorderLayout.SOUTH);

        JPanel reportPanel = new JPanel(new BorderLayout(10, 10));
        reportPanel.setBorder(BorderFactory.createTitledBorder("Progress Report"));

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        reportArea.setText("Select a subject to view progress");

        JScrollPane reportScroll = new JScrollPane(reportArea);
        reportPanel.add(reportScroll, BorderLayout.CENTER);

        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, deadlinePanel, reportPanel);
        rightSplit.setDividerLocation(180);
        rightPanel.add(rightSplit);

        // Add to frame
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        loadStudents();
        setVisible(true);
    }

    private void onStudentSelected() {
        int index = studentListUI.getSelectedIndex();
        if (index == -1) return;

        currentStudentId = studentIds[index];
        currentSubjectId = -1;
        currentTopicId = -1;

        loadSubjects();
        subjectModel.clear();
        topicModel.clear();
        deadlineModel.clear();
        reportArea.setText("Select a subject to view progress");
    }

    private void onSubjectSelected() {
        int index = subjectListUI.getSelectedIndex();
        if (index == -1 || currentStudentId == -1) return;

        currentSubjectId = subjectIds[index];
        currentTopicId = -1;

        loadTopics();
        loadDeadlines();
        updateProgressReport();
    }

    private void onTopicSelected() {
        int index = topicListUI.getSelectedIndex();
        if (index == -1 || currentSubjectId == -1) return;

        currentTopicId = topicIds[index];

        Topic t = dao.getTopicById(currentTopicId);
        if (t != null) {
            completeCheckBox.setSelected(t.isCompleted());
            confidenceCombo.setSelectedIndex(t.getConfidenceLevel() - 1);
        }
    }

    // ========== ADD HANDLERS ==========

    private void handleAddStudent() {
        String name = JOptionPane.showInputDialog(this, "Enter Student Name:");
        if (name == null || name.trim().isEmpty()) return;

        int id = dao.insertStudent(name.trim());
        if (id > 0) {
            loadStudents();
            JOptionPane.showMessageDialog(this, "Student added!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error adding student!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddSubject() {
        if (currentStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter Subject Name:");
        if (name == null || name.trim().isEmpty()) return;

        int id = dao.insertSubject(currentStudentId, name.trim());
        if (id > 0) {
            loadSubjects();
            JOptionPane.showMessageDialog(this, "Subject added!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error adding subject!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddTopic() {
        if (currentSubjectId == -1) {
            JOptionPane.showMessageDialog(this, "Select a subject first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter Topic Name:");
        if (name == null || name.trim().isEmpty()) return;

        int id = dao.insertTopic(currentSubjectId, name.trim());
        if (id > 0) {
            loadTopics();
            updateProgressReport();
            JOptionPane.showMessageDialog(this, "Topic added!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error adding topic!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddDeadline() {
        if (currentSubjectId == -1) {
            JOptionPane.showMessageDialog(this, "Select a subject first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Subject sub = dao.getSubjectById(currentSubjectId);
        if (sub == null) {
            JOptionPane.showMessageDialog(this, "Error loading subject!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField titleField = new JTextField();
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Assignment", "Exam"});

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Type:"));
        panel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Deadline", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            String title = titleField.getText().trim();
            String date = dateField.getText().trim();
            String type = (String) typeBox.getSelectedItem();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = dao.insertDeadline(currentSubjectId, title, date, sub.getSubjectName(), type);
            if (id > 0) {
                loadDeadlines();
                JOptionPane.showMessageDialog(this, "Deadline added!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error adding deadline!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== DELETE HANDLERS ==========

    private void handleDeleteStudent() {
        if (currentStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this student and all data?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (dao.deleteStudent(currentStudentId)) {
            currentStudentId = -1;
            currentSubjectId = -1;
            currentTopicId = -1;
            loadStudents();
            subjectModel.clear();
            topicModel.clear();
            deadlineModel.clear();
            reportArea.setText("Select a subject to view progress");
            JOptionPane.showMessageDialog(this, "Student deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting student!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteSubject() {
        if (currentSubjectId == -1) {
            JOptionPane.showMessageDialog(this, "Select a subject first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this subject and all topics?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (dao.deleteSubject(currentSubjectId)) {
            currentSubjectId = -1;
            currentTopicId = -1;
            loadSubjects();
            topicModel.clear();
            deadlineModel.clear();
            reportArea.setText("Select a subject to view progress");
            JOptionPane.showMessageDialog(this, "Subject deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting subject!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteTopic() {
        if (currentTopicId == -1) {
            JOptionPane.showMessageDialog(this, "Select a topic first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this topic?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (dao.deleteTopic(currentTopicId)) {
            currentTopicId = -1;
            loadTopics();
            updateProgressReport();
            completeCheckBox.setSelected(false);
            confidenceCombo.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "Topic deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting topic!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteDeadline() {
        if (deadlineListUI.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Select a deadline first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int index = deadlineListUI.getSelectedIndex();
        int deadlineId = deadlineIds[index];

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this deadline?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (dao.deleteDeadline(deadlineId)) {
            loadDeadlines();
            JOptionPane.showMessageDialog(this, "Deadline deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting deadline!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== UPDATE HANDLERS ==========

    private void handleTopicUpdate() {
        if (currentTopicId == -1) {
            JOptionPane.showMessageDialog(this, "Select a topic first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean completed = completeCheckBox.isSelected();
        int confidence = confidenceCombo.getSelectedIndex() + 1;

        if (dao.updateTopic(currentTopicId, completed, confidence)) {
            loadTopics();
            updateProgressReport();
            JOptionPane.showMessageDialog(this, "Topic updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error updating topic!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========== LOAD DATA ==========

    private void loadStudents() {
        studentModel.clear();
        studentCount = 0;

        try {
            ResultSet rs = dao.getAllStudents();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                studentIds[studentCount] = id;
                studentModel.addElement(id + " - " + name);
                studentCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSubjects() {
        subjectModel.clear();
        subjectCount = 0;

        if (currentStudentId == -1) return;

        try {
            ResultSet rs = dao.getSubjectsByStudent(currentStudentId);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                subjectIds[subjectCount] = id;
                subjectModel.addElement(name);
                subjectCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTopics() {
        topicModel.clear();
        topicCount = 0;

        if (currentSubjectId == -1) return;

        try {
            ResultSet rs = dao.getTopicsBySubject(currentSubjectId);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boolean completed = rs.getBoolean("completed");
                int confidence = rs.getInt("confidence_level");

                topicIds[topicCount] = id;
                String status = completed ? "✓" : "✗";
                topicModel.addElement("[" + status + "] " + name + " (Conf: " + confidence + "/5)");
                topicCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDeadlines() {
        deadlineModel.clear();
        deadlineCount = 0;

        if (currentSubjectId == -1) return;

        try {
            ResultSet rs = dao.getDeadlinesBySubject(currentSubjectId);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String date = rs.getString("date");
                String type = rs.getString("type");

                deadlineIds[deadlineCount] = id;
                deadlineModel.addElement("[" + type + "] " + title + " - " + date);
                deadlineCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProgressReport() {
        if (currentSubjectId == -1) {
            reportArea.setText("Select a subject to view progress");
            return;
        }

        try {
            ResultSet rs = dao.getTopicsBySubject(currentSubjectId);

            int total = 0, completed = 0;
            double confidenceSum = 0;

            while (rs.next()) {
                total++;
                if (rs.getBoolean("completed")) {
                    completed++;
                }
                confidenceSum += rs.getInt("confidence_level");
            }

            double completionPct = total == 0 ? 0 : (completed * 100.0) / total;
            double avgConfidence = total == 0 ? 0 : confidenceSum / total;

            Subject sub = dao.getSubjectById(currentSubjectId);
            String subName = sub != null ? sub.getSubjectName() : "Unknown";

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════\n");
            sb.append("SUBJECT: ").append(subName).append("\n");
            sb.append("═══════════════════════════════\n\n");
            sb.append("TOPICS:\n");
            sb.append("  Total: ").append(total).append("\n");
            sb.append("  Completed: ").append(completed).append("\n");
            sb.append("  Pending: ").append(total - completed).append("\n\n");
            sb.append("PROGRESS:\n");
            sb.append("  Completion: ").append(String.format("%.2f%%", completionPct)).append("\n");
            sb.append("  Avg Confidence: ").append(String.format("%.2f", avgConfidence)).append("/5.0\n");

            reportArea.setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
