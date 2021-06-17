package domain;

public class Question {
    private String id;
    private String wording;
    private String correctAnswer;

    public Question(String id, String wording, String correctAnswer) {
        this.id = id;
        this.wording = wording;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}
