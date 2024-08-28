package application;


import java.util.List;

public class SingleChoiceQuestion extends Question {
    private List<String> options;

    public SingleChoiceQuestion(String questionText, List<String> options) {
        super(questionText);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }
}

