package application;



public class TextInputQuestion extends Question {
    private String prompt;

    public TextInputQuestion(String questionText, String prompt) {
        super(questionText);
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }
}

