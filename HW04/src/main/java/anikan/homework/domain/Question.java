package anikan.homework.domain;

public class Question {
    private String id;
    private String wording;

    public void setId(String id) {
        this.id = id;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String correctAnswer;

    public Question(String id, String wording, String correctAnswer) {
        this.id = id;
        this.wording = wording;
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }
    public String getWording() {
        return wording;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Question() {
    }
}
