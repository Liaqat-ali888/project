import java.time.LocalDate;

abstract class Deadline {

    protected int id;
    protected String title;
    protected LocalDate date;
    protected String subjectName;

    
    Deadline(int id, String title, LocalDate date, String subjectName) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.subjectName = subjectName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    abstract String getType();

    public String getSubjectName() {
        return this.subjectName;
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + title + " | " + date;
    }
}
