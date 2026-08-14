class Subject {

    private int id;
    private String subjectName;
    private int totalTopics;
    private Topic topics[];
    private Deadline deadlines[];
    private int topicCount;
    private int deadlineCount;


    Subject(int id, String subject) {
        this.id = id;
        this.subjectName = subject;
        this.totalTopics = 50;
        this.topics = new Topic[totalTopics];
        this.deadlines = new Deadline[50];
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getSubjectName() {
        return this.subjectName;
    }

    void addTopic(Topic topic) {
        if (topicCount < topics.length) {
            topics[topicCount] = topic;
            topicCount++;
        }
        System.out.println("Topic added successfully");
    }

    void removeTopic(String remove) {
        for (int i = 0; i < topicCount; i++) {
            if (topics[i] != null &&
                topics[i].getTopicName().equalsIgnoreCase(remove)) {
                for (int j = i; j < topicCount - 1; j++) {
                    topics[j] = topics[j + 1];
                }
                topicCount--;
                System.out.println("Topic removed successfully");
                return;
            }
        }
        System.out.println("Topic is not found");
    }

    Topic[] getTopics() {
        Topic result[] = new Topic[topicCount];
        for (int i = 0; i < topicCount; i++) {
            result[i] = topics[i];
        }
        return result;
    }

    void addDeadline(Deadline d) {
        if (deadlineCount < deadlines.length) {
            deadlines[deadlineCount] = d;
            deadlineCount++;
        }
    }

    void removeDeadline(String title) {
        for (int i = 0; i < deadlineCount; i++) {
            if (deadlines[i] != null &&
                deadlines[i].getTitle().equalsIgnoreCase(title)) {
                for (int j = i; j < deadlineCount - 1; j++) {
                    deadlines[j] = deadlines[j + 1];
                }
                deadlineCount--;
                return;
            }
        }
    }

    public Deadline[] getDeadlines() {
        Deadline result[] = new Deadline[deadlineCount];
        for (int i = 0; i < deadlineCount; i++) {
            result[i] = deadlines[i];
        }
        return result;
    }

    int getTopicCount() {
        return this.topicCount;
    }

    int getDeadlineCount() {
        return this.deadlineCount;
    }
}
