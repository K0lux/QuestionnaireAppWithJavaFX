# Documentation du Code JavaFX pour le Questionnaire

Ce projet JavaFX met en œuvre une interface graphique pour un questionnaire interactif. Le projet est structuré en utilisant plusieurs classes pour gérer différents types de questions ainsi que les aspects visuels de l'interface utilisateur.

## 1. Classe Principale : `Main`

### Description
La classe `Main` est le point d'entrée de l'application JavaFX. Elle configure et lance l'interface graphique en chargeant le fichier FXML et en appliquant les styles définis dans le fichier CSS.

### Méthodes
- **`start(Stage primaryStage)`** : Cette méthode est appelée au démarrage de l'application. Elle charge l'interface utilisateur depuis le fichier `MainViews.fxml`, applique le fichier de styles `Styles.css`, et affiche la fenêtre principale.
- **`main(String[] args)`** : Méthode principale qui lance l'application.

## 2. Classe Abstraite : `Question`

### Description
`Question` est une classe abstraite représentant une question dans le questionnaire. Elle définit l'attribut commun `questionText`, qui stocke le texte de la question.

### Rôle
Cette classe sert de base pour toutes les questions, garantissant que chaque type de question a au moins un texte descriptif.

### Attributs
- **`questionText`** : Texte de la question.

### Méthodes
- **`getQuestionText()`** : Retourne le texte de la question.

## 3. Sous-classe : `TextInputQuestion`

### Description
`TextInputQuestion` est une sous-classe de `Question`, représentant une question à réponse libre où l'utilisateur doit entrer un texte.

### Rôle
Cette classe est utilisée pour des questions qui nécessitent une réponse sous forme de texte. Un attribut `prompt` est ajouté pour fournir des instructions ou un contexte supplémentaire.

### Attributs
- **`prompt`** : Texte d'invite qui donne des instructions supplémentaires à l'utilisateur.

### Méthodes
- **`getPrompt()`** : Retourne le texte d'invite associé à la question.

## 4. Sous-classe : `MultipleChoiceQuestion`

### Description
`MultipleChoiceQuestion` est une sous-classe de `Question`, représentant une question à choix multiple.

### Rôle
Cette classe est utilisée pour des questions où l'utilisateur doit choisir parmi plusieurs options. Les options sont stockées dans une liste.

### Attributs
- **`options`** : Liste des options disponibles pour la question.

### Méthodes
- **`getOptions()`** : Retourne la liste des options.

## 5. Sous-classe : `SingleChoiceQuestion`

### Description
`SingleChoiceQuestion` est une sous-classe de `Question`, représentant une question à choix unique.

### Rôle
Cette classe est utilisée pour des questions où l'utilisateur doit sélectionner une seule option parmi plusieurs. Elle est semblable à `MultipleChoiceQuestion`, mais ne permet qu'une seule sélection.

### Attributs
- **`options`** : Liste des options disponibles pour la question.

### Méthodes
- **`getOptions()`** : Retourne la liste des options.

## 6. Fichier de Style : `Styles.css`

### Description
Ce fichier CSS contient les styles pour l'interface utilisateur de l'application. Il est appliqué aux différents éléments de l'interface pour améliorer l'apparence et la convivialité.

### Styles Principaux
- **`.root`** : Définit la police et la couleur de fond de l'application.
- **`.header`** : Style pour l'entête du questionnaire, avec un fond sombre et des coins arrondis.
- **`.header-title`** : Style pour le titre de l'entête, avec une taille de police plus grande et un texte en gras.
- **`.content-area`** : Style pour la zone de contenu, avec un fond blanc et des bordures arrondies.
- **`.question-label`** : Style pour les labels des questions, avec une police en gras et une taille légèrement augmentée.
- **`.button`** : Style pour les boutons avec un fond bleu, un texte blanc, et des coins arrondis. Des effets de survol sont également définis.
- **`#submitButton`** : Style spécifique pour le bouton de soumission, avec un fond vert et des effets de survol.
- **`.progress-bar`** : Style pour la barre de progression, avec une largeur prédéfinie et une couleur de remplissage bleue.

## 7. Fichier FXML : `MainViews.fxml`

### Description
Ce fichier FXML définit la structure de l'interface utilisateur en utilisant des conteneurs et des contrôles JavaFX.

### Éléments Principaux
- **`VBox`** : Conteneur principal pour organiser verticalement les éléments.
- **`Label`** : Utilisé pour afficher le titre du questionnaire.
- **`ScrollPane`** : Conteneur défilant pour afficher la liste des questions.
- **`HBox`** : Conteneur horizontal pour les boutons et la barre de progression.
- **`ProgressBar`** : Affiche la progression du questionnaire.
- **`Button`** : Boutons pour naviguer entre les questions et soumettre les réponses.

## 8. Contrôleur : `QuestionnaireController`

### Description
Le `QuestionnaireController` est responsable de la gestion de l'interface utilisateur du questionnaire. Il permet de charger les questions, de capturer les réponses des utilisateurs, de naviguer entre les questions, et de soumettre les réponses collectées.

### Attributs
- **`questionnaireBox`** : Conteneur `VBox` pour afficher la question et les options associées.
- **`progressLabel`** : Label pour afficher l'état de progression du questionnaire.
- **`submitButton`** : Bouton pour soumettre les réponses une fois le questionnaire terminé.
- **`previousButton`** : Bouton pour revenir à la question précédente.
- **`nextButton`** : Bouton pour passer à la question suivante.
- **`progressBar`** : Barre de progression qui visualise l'avancement du questionnaire.

### Méthodes
- **`initialize()`** : Initialise le contrôleur, charge les questions et affiche la première question. Met à jour l'état des boutons de navigation et la barre de progression.
- **`loadQuestions()`** : Charge une liste de questions prédéfinies. Ces questions incluent des questions à choix unique, à choix multiple, et des champs de saisie de texte.
- **`renderQuestion(int index)`** : Affiche la question à l'index spécifié et ajoute les options associées (choix unique, choix multiple, ou texte libre). Anime la transition des questions et met à jour l'état des boutons et de la barre de progression.
- **`renderSingleChoiceQuestion(SingleChoiceQuestion question)`** : Affiche les options pour une question à choix unique en utilisant des boutons radio.
- **`renderMultipleChoiceQuestion(MultipleChoiceQuestion question)`** : Affiche les options pour une question à choix multiple en utilisant des cases à cocher.
- **`renderTextInputQuestion(TextInputQuestion question)`** : Affiche un champ de texte pour une question nécessitant une réponse libre.
- **`updateButtonStates()`** : Met à jour l'état des boutons Précédent et Suivant en fonction de l'index de la question actuelle. Le bouton Soumettre est affiché uniquement sur la dernière question.
- **`handleNext()`** : Passe à la question suivante, en enregistrant la réponse actuelle et en mettant à jour la barre de progression.
- **`handlePrevious()`** : Revient à la question précédente, en enregistrant la réponse actuelle et en mettant à jour la barre de progression.
- **`handleSubmit()`** : Enregistre la réponse à la dernière question, affiche un résumé des réponses collectées, et demande confirmation avant la soumission définitive. Si l'utilisateur choisit de modifier une réponse, il peut sélectionner la question à corriger.
- **`handleEditResponses()`** : Permet à l'utilisateur de choisir une question spécifique à modifier après avoir affiché un résumé des réponses.
- **`resetQuestionnaire()`** : Réinitialise le questionnaire en ramenant l'utilisateur à la première question et en effaçant les réponses précédemment enregistrées.
- **`saveCurrentResponse()`** : Enregistre la réponse de l'utilisateur pour la question actuelle dans une Map, en fonction du type de question (choix unique, choix multiple ou texte libre).
- **`collectResponses()`** : Collecte et formate toutes les réponses de l'utilisateur sous forme de texte pour affichage ou soumission.
- **`collectSingleChoiceAnswer(SingleChoiceQuestion question)`** : Récupère la réponse sélectionnée pour une question à choix unique.
- **`collectMultipleChoiceAnswers(MultipleChoiceQuestion question)`** : Récupère les réponses sélectionnées pour une question à choix multiple.
- **`collectTextInputAnswer(TextInputQuestion question)`** : Récupère la réponse saisie dans le champ de texte pour une question à réponse libre.
- **`animateQuestionTransition(Node node)`** : Anime la transition entre les questions pour une expérience utilisateur plus fluide.
- **`updateProgressBar()`** : Met à jour la barre de progression et le label en fonction de la progression dans le questionnaire.
- **`updateSubmitButtonVisibility()`** : Gère la visibilité du bouton de soumission en fonction de la position dans le questionnaire.
- **`showSummary(String responses)`** : Affiche un résumé des réponses dans une boîte de dialogue après la soumission du questionnaire.

## Conclusion

L'application de questionnaire est bien structurée en séparant la logique des questions (avec des classes abstraites et leurs sous-classes) et l'interface utilisateur (avec FXML et CSS). Cette approche modulaire facilite la gestion des différents types de questions et permet de personnaliser l'apparence de l'application à l'aide de styles CSS.

---

Pour toute question ou contribution, n'hésitez pas à ouvrir une issue ou à soumettre une pull request sur le dépôt GitHub du projet.
