import java.time.LocalDate;

class AssignmentDeadline extends Deadline {

   
    AssignmentDeadline(int id, String title, LocalDate date, String subjectName) {
        super(id, title, date, subjectName);
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return "Assignment";
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Date: " + date + ", Subject: " + subjectName;
    }
}
