class Topic {
    
    private int id;
    private String topicName;
    private boolean completed;
    private int confidenceLevel;

  

    public Topic( String topicName) {

        this.topicName = topicName;
        this.confidenceLevel = 1;
	this.completed=false;
    }

    public Topic(int id, String name, boolean completed, int confidenceLevel) {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void markIncomplete() {
        this.completed = false;
    }

    public void setCompleted(boolean c) {
        this.completed = c;
    }

    public void setConfidenceLevel(int level) {
        if (level >= 1 && level <= 5) {
            this.confidenceLevel = level;
        }
    }

    public int getConfidenceLevel() {
        return this.confidenceLevel;
    }

    public boolean isCompleted() {
        return this.completed;
    }
}
