package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;

public class QuestionnaireController {
    @FXML private VBox questionnaireBox;
    @FXML private Label progressLabel;
    @FXML private Button submitButton;
    @FXML private Button previousButton;
    @FXML private Button nextButton;
    @FXML private ProgressBar progressBar;

    


    private int currentQuestionIndex = 0;
    private List<Question> questions;
    private double progress = 0.0;
    private Map<Integer, String> responses = new HashMap<>();



    public void initialize() {
        questions = loadQuestions();
        renderQuestion(currentQuestionIndex);

        updateButtonStates();
        updateProgressBar();
    }

   
    private List<Question> loadQuestions() {
        return Arrays.asList(
            new SingleChoiceQuestion("Q01. Sexe", Arrays.asList("Féminin", "Masculin")),
            new TextInputQuestion("Q02. Quelle est votre tranche d'âge ?", "Votre tranche d'âge..."),
            new SingleChoiceQuestion("Q03. Quel est votre plus haut niveau de formation ?", Arrays.asList(
                "Primaire", "Secondaire (sans le bac)", "Secondaire (avec BAC)", 
                "Post-secondaire non supérieur", "Supérieur de cycle court (DUT, DEUG, BTS…)",
                "Licence", "Master", "Doctorat", "Autres"
            )),
            new TextInputQuestion("Q04. Combien d'enfants avez-vous ?", "Nombre d'enfants..."),
            new SingleChoiceQuestion("Q06. Vous habitez KARA?", Arrays.asList("Oui", "Non")),
            new MultipleChoiceQuestion("Q07. Caractéristiques de votre maison", Arrays.asList(
                "Toit en tôle", "Toit en ardoise", "Toit en zinc", "Toit en tuile", "Toit en paille", 
                "Toit en plastique ondulé", "Toit en dalle de béton", "Clôturée", "Mur en bois", 
                "Mur en ciment", "Sol en carreaux", "Sol en ciment", "Toilette et douche interne",
                "Toilette et douche externe", "Toilette moderne", "Douche moderne", 
                "Cuisine interne", "Cuisine externe"
            ))
        );
    }

    private void renderQuestion(int index) {
        questionnaireBox.getChildren().clear();
        Question question = questions.get(index);
        Label label = new Label(question.getQuestionText());
        label.getStyleClass().add("question-label");
        questionnaireBox.getChildren().add(label);

        if (question instanceof SingleChoiceQuestion) {
            renderSingleChoiceQuestion((SingleChoiceQuestion) question);
        } else if (question instanceof MultipleChoiceQuestion) {
            renderMultipleChoiceQuestion((MultipleChoiceQuestion) question);
        } else if (question instanceof TextInputQuestion) {
            renderTextInputQuestion((TextInputQuestion) question);
        }

        animateQuestionTransition(questionnaireBox);
        updateButtonStates();
        updateSubmitButtonVisibility();
        updateProgressBar(); 
    }

    private void renderSingleChoiceQuestion(SingleChoiceQuestion question) {
        ToggleGroup group = new ToggleGroup();
        for (String option : question.getOptions()) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(group);
            rb.getStyleClass().add("option-radio");
            questionnaireBox.getChildren().add(rb);
        }
    }

    private void renderMultipleChoiceQuestion(MultipleChoiceQuestion question) {
        for (String option : question.getOptions()) {
            CheckBox cb = new CheckBox(option);
            cb.getStyleClass().add("option-checkbox");
            questionnaireBox.getChildren().add(cb);
        }
    }

    private void renderTextInputQuestion(TextInputQuestion question) {
        TextField textField = new TextField();
        textField.setPromptText(question.getPrompt());
        textField.getStyleClass().add("option-textfield");
        questionnaireBox.getChildren().add(textField);
    }
    
    private void updateButtonStates() {
        previousButton.setDisable(currentQuestionIndex == 0);
        nextButton.setDisable(currentQuestionIndex == questions.size() - 1);
        submitButton.setVisible(currentQuestionIndex == questions.size() - 1);
    }

    @FXML
    private void handleNext() {
    	saveCurrentResponse();
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            renderQuestion(currentQuestionIndex);
            progress = (double) currentQuestionIndex / (questions.size() - 1); // Calculer la progression en fonction de l'index
            updateProgressBar();
        }
    }

    @FXML
    private void handlePrevious() {
    	saveCurrentResponse();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            renderQuestion(currentQuestionIndex);
            progress = (double) currentQuestionIndex / (questions.size() - 1); // Calculer la progression en fonction de l'index
            updateProgressBar();
        }
    }

    @FXML
    private void handleSubmit() {
    	saveCurrentResponse(); 
        String responses = collectResponses();
        
        System.out.println("Collected Responses:\n" + responses);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Questionnaire Soumis");
        alert.setHeaderText("Responses Collectées");
        alert.setContentText(responses);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
        
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation de soumission");
        confirmAlert.setHeaderText("Êtes-vous sûr de vouloir soumettre le questionnaire ?");
        confirmAlert.setContentText("Assurez-vous d'avoir répondu à toutes les questions.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String responses1 = collectResponses();
            showSummary(responses1);
        }

        // connexion avec la base de donnée 
    }

    private void saveCurrentResponse() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        String response = "";

        if (currentQuestion instanceof SingleChoiceQuestion) {
            response = collectSingleChoiceAnswer((SingleChoiceQuestion) currentQuestion);
        } else if (currentQuestion instanceof MultipleChoiceQuestion) {
            response = String.join(", ", collectMultipleChoiceAnswers((MultipleChoiceQuestion) currentQuestion));
        } else if (currentQuestion instanceof TextInputQuestion) {
            response = collectTextInputAnswer((TextInputQuestion) currentQuestion);
        }

        responses.put(currentQuestionIndex, response);
    }
    private String collectResponses() {
        StringBuilder allResponses = new StringBuilder();
        allResponses.append("Résumé des réponses :\n\n");

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            renderQuestion(i);  
            String response = responses.getOrDefault(i, "Pas de réponse");

            allResponses.append(question.getQuestionText().replaceAll("Q\\d+\\. ", "")).append(" :\n");
            allResponses.append(response).append("\n\n");
        }

       // return responses.toString();
        return allResponses.toString();  
    }

    private String collectSingleChoiceAnswer(SingleChoiceQuestion question) {
        for (Node node : questionnaireBox.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton rb = (RadioButton) node;
                if (rb.isSelected() && question.getOptions().contains(rb.getText())) {
                    return rb.getText();
                }
            }
        }
        return "Pas de réponse";
    }

    private List<String> collectMultipleChoiceAnswers(MultipleChoiceQuestion question) {
        List<String> selectedOptions = new ArrayList<>();
        for (Node node : questionnaireBox.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                if (cb.isSelected() && question.getOptions().contains(cb.getText())) {
                    selectedOptions.add(cb.getText());
                }
            }
        }
        return selectedOptions;
    }

    private String collectTextInputAnswer(TextInputQuestion question) {
        for (Node node : questionnaireBox.getChildren()) {
            if (node instanceof TextField) {
                TextField tf = (TextField) node;
                String text = tf.getText().trim();
                return text.isEmpty() ? "Pas de réponse" : text;
            }
        }
        return "Pas de réponse";
    }
    
    
    
    
   
    private void animateQuestionTransition(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), node);
        tt.setFromX(300);
        tt.setToX(0);
        tt.play();
    }


    private void updateProgressBar() {
    	progressBar.setProgress(progress);
    }

    private void updateSubmitButtonVisibility() {
        submitButton.setVisible(currentQuestionIndex == questions.size() - 1);
    }
    
    private void showSummary(String responses) {
        Alert summaryAlert = new Alert(Alert.AlertType.INFORMATION);
        summaryAlert.setTitle("Résumé du questionnaire");
        summaryAlert.setHeaderText("Merci d'avoir complété le questionnaire !");
        summaryAlert.setContentText("Voici un résumé de vos réponses :");

        TextArea textArea = new TextArea(responses);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(300);

        summaryAlert.getDialogPane().setExpandableContent(textArea);
        summaryAlert.getDialogPane().setExpanded(true);

        summaryAlert.showAndWait();

        
    }
}