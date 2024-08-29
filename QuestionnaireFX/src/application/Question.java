package application;

public class Question {
    private String questionText;
    private String response;

    public Question(String questionText) {
        this.questionText = questionText;
        this.response = null;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void clearResponse() {
        this.response = null; 
    }
}


