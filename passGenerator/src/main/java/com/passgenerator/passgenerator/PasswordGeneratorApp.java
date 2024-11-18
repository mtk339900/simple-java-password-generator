package com.passgenerator.passgenerator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

public class PasswordGeneratorApp extends Application {

    private TextField lengthField;
    private CheckBox uppercaseCheck;
    private CheckBox lowercaseCheck;
    private CheckBox numbersCheck;
    private CheckBox specialCharsCheck;
    private TextField generatedPasswordField;
    private ProgressBar strengthBar;
    private Label strengthLabel;

    @Override
    public void start(Stage primaryStage) {
        // Main Container
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("""
            -fx-background-color: #f3f4f6;
            -fx-font-family: 'Segoe UI', Arial, sans-serif;
        """);

        // Header
        Label title = createStyledLabel("Password generator", "heading");

        // Main Content Container
        VBox contentBox = new VBox(20);
        contentBox.setStyle("""
            -fx-background-color: white;
            -fx-padding: 24px;
            -fx-background-radius: 12px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);
        """);

        // Options Section
        VBox optionsSection = createOptionsSection();

        // Password Generation Section
        VBox passwordSection = createPasswordSection();

        contentBox.getChildren().addAll(optionsSection, passwordSection);
        mainContainer.getChildren().addAll(title, contentBox);

        // Scene
        Scene scene = new Scene(mainContainer, 600, 700);
        primaryStage.setTitle("Password generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createOptionsSection() {
        VBox optionsBox = new VBox(15);

        Label sectionTitle = createStyledLabel("Password settings", "section");

        // Password Length Container
        HBox lengthContainer = new HBox(10);
        lengthContainer.setAlignment(Pos.CENTER_LEFT);

        Label lengthLabel = createStyledLabel("Long:", "field");
        lengthField = createStyledTextField("12");
        lengthField.setPrefWidth(80);

        lengthContainer.getChildren().addAll(lengthLabel, lengthField);

        // Checkboxes with modern styling
        uppercaseCheck = createStyledCheckBox("Uppercase letters (A-Z)");
        lowercaseCheck = createStyledCheckBox("Lowercase letters (a-z)");
        numbersCheck = createStyledCheckBox("Numbers (0-9)");
        specialCharsCheck = createStyledCheckBox("Special characters (@#$!)");

        // Default selections
        uppercaseCheck.setSelected(true);
        lowercaseCheck.setSelected(true);
        numbersCheck.setSelected(true);
        specialCharsCheck.setSelected(true);

        optionsBox.getChildren().addAll(
                sectionTitle,
                lengthContainer,
                uppercaseCheck,
                lowercaseCheck,
                numbersCheck,
                specialCharsCheck
        );

        return optionsBox;
    }

    private VBox createPasswordSection() {
        VBox passwordSection = new VBox(15);

        Label sectionTitle = createStyledLabel("Generated password", "section");

        // Password Field
        generatedPasswordField = createStyledTextField("");
        generatedPasswordField.setEditable(false);
        generatedPasswordField.setStyle(generatedPasswordField.getStyle() +
                "-fx-font-family: 'Courier New'; -fx-font-size: 16px;");

        // Buttons Container
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        Button generateButton = createStyledButton("Generate password", "primary");
        Button copyButton = createStyledButton("Copy", "secondary");

        buttonBox.getChildren().addAll(generateButton, copyButton);

        // Strength Indicator
        strengthLabel = createStyledLabel("Password strength: ", "field");
        strengthBar = new ProgressBar(0);
        strengthBar.setPrefWidth(Double.MAX_VALUE);
        strengthBar.setStyle("""
            -fx-accent: #10B981;
            -fx-control-inner-background: #E5E7EB;
        """);

        // Event Handlers
        generateButton.setOnAction(e -> generatePassword());
        copyButton.setOnAction(e -> copyPassword());

        passwordSection.getChildren().addAll(
                sectionTitle,
                generatedPasswordField,
                buttonBox,
                strengthLabel,
                strengthBar
        );

        return passwordSection;
    }

    private Label createStyledLabel(String text, String type) {
        Label label = new Label(text);
        switch (type) {
            case "heading":
                label.setStyle("""
                    -fx-font-size: 24px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #111827;
                    -fx-padding: 0 0 10 0;
                """);
                break;
            case "section":
                label.setStyle("""
                    -fx-font-size: 18px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #374151;
                    -fx-padding: 0 0 5 0;
                """);
                break;
            case "field":
                label.setStyle("""
                    -fx-font-size: 14px;
                    -fx-text-fill: #4B5563;
                """);
                break;
        }
        return label;
    }

    private TextField createStyledTextField(String defaultText) {
        TextField textField = new TextField(defaultText);
        textField.setStyle("""
            -fx-background-color: #F9FAFB;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 6px;
            -fx-background-radius: 6px;
            -fx-padding: 8px;
            -fx-font-size: 14px;
        """);
        return textField;
    }

    private CheckBox createStyledCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setStyle("""
            -fx-font-size: 14px;
            -fx-text-fill: #4B5563;
            -fx-padding: 5px;
        """);
        return checkBox;
    }

    private Button createStyledButton(String text, String type) {
        Button button = new Button(text);
        String baseStyle = """
            -fx-font-size: 14px;
            -fx-padding: 8px 16px;
            -fx-background-radius: 6px;
            -fx-cursor: hand;
        """;

        if (type.equals("primary")) {
            button.setStyle(baseStyle + """
                -fx-background-color: #2563EB;
                -fx-text-fill: white;
                -fx-font-weight: bold;
            """);
        } else {
            button.setStyle(baseStyle + """
                -fx-background-color: #6B7280;
                -fx-text-fill: white;
            """);
        }

        // Hover effect
        button.setOnMouseEntered(e -> {
            if (type.equals("primary")) {
                button.setStyle(button.getStyle() + "-fx-background-color: #1D4ED8;");
            } else {
                button.setStyle(button.getStyle() + "-fx-background-color: #4B5563;");
            }
        });

        button.setOnMouseExited(e -> {
            if (type.equals("primary")) {
                button.setStyle(button.getStyle() + "-fx-background-color: #2563EB;");
            } else {
                button.setStyle(button.getStyle() + "-fx-background-color: #6B7280;");
            }
        });

        return button;
    }

    private void generatePassword() {
        try {
            int length = Integer.parseInt(lengthField.getText());
            if (length <= 0) {
                showAlert("Error", "The password length must be greater than zero");
                return;
            }

            StringBuilder chars = new StringBuilder();
            if (uppercaseCheck.isSelected()) chars.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            if (lowercaseCheck.isSelected()) chars.append("abcdefghijklmnopqrstuvwxyz");
            if (numbersCheck.isSelected()) chars.append("0123456789");
            if (specialCharsCheck.isSelected()) chars.append("!@#$%^&*()_+-=[]{}|;:,.<>?");

            if (chars.length() == 0) {
                showAlert("Error", "Please select at least one type of character");
                return;
            }

            StringBuilder password = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = (int) (Math.random() * chars.length());
                password.append(chars.charAt(index));
            }

            generatedPasswordField.setText(password.toString());
            updatePasswordStrength(password.toString());
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for the password length");
        }
    }

    private void copyPassword() {
        String password = generatedPasswordField.getText();
        if (!password.isEmpty()) {
            javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(password);
            clipboard.setContent(content);
            showAlert("success", "The password has been copied to the clipboard");
        }
    }

    private void updatePasswordStrength(String password) {
        double strength = calculatePasswordStrength(password);
        strengthBar.setProgress(strength);

        String strengthText;
        String barColor;
        if (strength < 0.3) {
            strengthText = "Weak";
            barColor = "#EF4444"; // red
        } else if (strength < 0.6) {
            strengthText = "Medium";
            barColor = "#F59E0B"; // yellow
        } else {
            strengthText = "Strong";
            barColor = "#10B981"; // green
        }

        strengthBar.setStyle("-fx-accent: " + barColor + ";");
        strengthLabel.setText("Password strength: " + strengthText);
    }

    private double calculatePasswordStrength(String password) {
        int length = password.length();
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        int criteriaCount = 0;
        if (hasUpper) criteriaCount++;
        if (hasLower) criteriaCount++;
        if (hasDigit) criteriaCount++;
        if (hasSpecial) criteriaCount++;

        return Math.min(1.0, (length / 20.0 * 0.5) + (criteriaCount / 4.0 * 0.5));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Style the alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("""
            -fx-background-color: white;
            -fx-padding: 20px;
        """);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}