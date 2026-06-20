package com.cours.app.iu;

import com.cours.app.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * PARTIE 1 — Interface de connexion construite entièrement en code Java.
 * Les styles sont définis dans resources/com/cours/app/css/app.css
 * et appliqués via getStyleClass().add(...).
 */
public class view {

    private final AuthService authService = new AuthService();

    private TextField usernameField;
    private PasswordField passwordField;
    private TextField passwordVisible;
    private CheckBox showPasswordCheck;
    private Label messageLabel;

    /**
     * Construit et retourne la scène JavaFX.
     * Le fichier CSS est chargé ici via scene.getStylesheets().
     */
    public Scene buildScene(Stage stage) {

        // ── Titre ──────────────────────────────────────────────────────────
        Label titleLabel = new Label("Connexion");
        titleLabel.getStyleClass().add("login-title");

        Label subtitleLabel = new Label("Entrez vos identifiants pour continuer");
        subtitleLabel.getStyleClass().add("login-subtitle");

        VBox header = new VBox(6, titleLabel, subtitleLabel);
        header.setAlignment(Pos.CENTER);

        // ── Champ Email ────────────────────────────────────────────────────
        Label usernameLabel = new Label("Email");
        usernameLabel.getStyleClass().add("field-label");

        usernameField = new TextField();
        usernameField.setPromptText("ex : admin@mail.com");
        usernameField.getStyleClass().add("input-field");
        usernameField.setOnAction(e -> handleLogin());

        VBox usernameBox = new VBox(6, usernameLabel, usernameField);

        // ── Champ Mot de passe ─────────────────────────────────────────────
        Label passwordLabel = new Label("Mot de passe");
        passwordLabel.getStyleClass().add("field-label");

        passwordField = new PasswordField();
        passwordField.setPromptText("••••••••");
        passwordField.getStyleClass().add("input-field");
        passwordField.setOnAction(e -> handleLogin());

        // Champ texte visible (BONUS : afficher le mot de passe)
        passwordVisible = new TextField();
        passwordVisible.setPromptText("••••••••");
        passwordVisible.getStyleClass().add("input-field");
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false);
        passwordVisible.setOnAction(e -> handleLogin());

        // Synchronisation bidirectionnelle entre les deux champs
        passwordField.textProperty().bindBidirectional(passwordVisible.textProperty());

        // BONUS : case à cocher
        showPasswordCheck = new CheckBox("Afficher le mot de passe");
        showPasswordCheck.getStyleClass().add("check-label");
        showPasswordCheck.setOnAction(e -> togglePassword());

        StackPane passwordStack = new StackPane(passwordField, passwordVisible);
        VBox passwordBox = new VBox(6, passwordLabel, passwordStack, showPasswordCheck);

        // ── Bouton Se connecter ────────────────────────────────────────────
        Button loginButton = new Button("Se connecter");
        loginButton.getStyleClass().add("login-btn");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setOnAction(e -> handleLogin());

        // ── Message de retour ──────────────────────────────────────────────
        messageLabel = new Label("");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(320);
        messageLabel.setAlignment(Pos.CENTER);

        // ── Assemblage du formulaire ───────────────────────────────────────
        VBox form = new VBox(18,
                header,
                usernameBox,
                passwordBox,
                loginButton,
                messageLabel
        );
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(420);
        form.getStyleClass().add("login-card");

        StackPane root = new StackPane(form);
        root.getStyleClass().add("app-root");

        // ── Chargement du CSS externe ──────────────────────────────────────
        Scene scene = new Scene(root, 520, 550);
        scene.getStylesheets().add(
        getClass().getResource("/com/cours/app/css/style.css").toExternalForm());
        return scene;
    }

    /** Bascule entre champ masqué et champ visible. */
    private void togglePassword() {
        boolean show = showPasswordCheck.isSelected();
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);
        passwordVisible.setVisible(show);
        passwordVisible.setManaged(show);
    }

    /** Valide les champs et vérifie les identifiants via AuthService. */
    private void handleLogin() {
        String email    = usernameField.getText().trim();
        String password = passwordField.isVisible()
                ? passwordField.getText()
                : passwordVisible.getText();

        // Validation : champs vides
        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Veuillez remplir tous les champs.", false);
            return;
        }

        // Vérification via AuthService (retourne Optional<User>)
        authService.login(email, password).ifPresentOrElse(
            user -> {
                showMessage("Connexion reussie ! Bienvenue, " + user.getUsername() + ".", true);
                openDashboard(user.getUsername());
            },
            () -> {
                showMessage("Identifiants incorrects. Reessayez.", false);
                passwordField.clear();
            }
        );
    }

    /** Affiche un message de succès ou d'erreur via les classes CSS. */
    private void showMessage(String text, boolean success) {
        messageLabel.setText(text);
        messageLabel.getStyleClass().removeAll("message-success", "message-error");
        messageLabel.getStyleClass().add(success ? "message-success" : "message-error");
    }

    /** BONUS : ouvre une fenêtre tableau de bord après connexion réussie. */
    private void openDashboard(String username) {
        Stage dash = new Stage();
        dash.setTitle("Tableau de bord");

        Label welcome = new Label("Bienvenue, " + username + " !");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcome.setTextFill(Color.web("#1e293b"));

        Label info = new Label("Connexion reussie (Partie 1 - Code Java).");
        info.setFont(Font.font("Arial", 13));
        info.setTextFill(Color.web("#64748b"));

        VBox box = new VBox(14, welcome, info);
        box.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(box);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #dbeafe, #f0fdf4);");

        dash.setScene(new Scene(root, 420, 220));
        dash.show();
    }
}