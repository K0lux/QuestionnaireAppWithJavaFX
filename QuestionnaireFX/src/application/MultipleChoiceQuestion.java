package application;



import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> options;

    public MultipleChoiceQuestion(String questionText, List<String> options) {
        super(questionText);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }
}

