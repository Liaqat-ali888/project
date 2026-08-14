class Student {

    private int id;
    private String name;
    private Subject subjects[] = new Subject[20];
    private int totalSubjects;


    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getName() {
        return this.name;
    }

    void addSubject(Subject sub) {
        if (totalSubjects < subjects.length) {
            subjects[totalSubjects] = sub;
            totalSubjects++;
        }
    }

    void removeSubject(String sub) {
        for (int i = 0; i < totalSubjects; i++) {
            if (subjects[i] != null &&
                subjects[i].getSubjectName().equalsIgnoreCase(sub)) {
                for (int j = i; j < totalSubjects - 1; j++) {
                    subjects[j] = subjects[j + 1];
                }
                totalSubjects--;
                System.out.println("Subject removed successfully");
                return;
            }
        }
    }

    Subject[] getSubjects() {
        Subject[] result = new Subject[totalSubjects];
        for (int i = 0; i < totalSubjects; i++) {
            result[i] = subjects[i];
        }
        return result;
    }

    void findSubject(String sub) {
        for (int i = 0; i < totalSubjects; i++) {
            if (subjects[i] != null &&
                subjects[i].getSubjectName().equalsIgnoreCase(sub)) {
                System.out.println("Subject found at index: " + i);
                return;
            }
        }
        System.out.println("Subject not found");
    }

    int getSubjectCount() {
        return this.totalSubjects;
    }
}
