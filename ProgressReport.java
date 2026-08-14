class ProgressReport {

    Subject subject;

    ProgressReport(Subject subject) {
        this.subject = subject;
    }

    double calculateCompletionPercentage() {
        int completed = 0;

        for (int i = 0; i < subject.getTopicCount(); i++) {
            if (subject.getTopics()[i].isCompleted()) {
                completed++;
            }
        }

        if (subject.getTopicCount() == 0) return 0;

        return (completed * 100.0) / subject.getTopicCount();
    }

    double averageConfidenceLevel() {
        int sum = 0;

        for (int i = 0; i < subject.getTopicCount(); i++) {
            sum += subject.getTopics()[i].getConfidenceLevel();
        }

        if (subject.getTopicCount() == 0) return 0;

        return (double) sum / subject.getTopicCount();
    }

    void generateSummary() {
        System.out.println("Subject: " + subject.getSubjectName());
        System.out.printf("Completion: %.2f%%%n", calculateCompletionPercentage());
        System.out.printf("Avg Confidence: %.2f%n", averageConfidenceLevel());
    }

    int getCompletedTopics() {
        int count = 0;

        for (int i = 0; i < subject.getTopicCount(); i++) {
            if (subject.getTopics()[i] != null &&
                subject.getTopics()[i].isCompleted()) {
                count++;
            }
        }

        return count;
    }
}
