package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CalculatorController {

    private String output = "";
    private Infix2Postfix calculation = new Infix2Postfix();
    private double memory = 0;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label outputLabel;

    @FXML
    private Button mcButton;

    @FXML
    private Button mrButton;

    @FXML
    private Button mPlusButton;

    @FXML
    private Button divideButton;

    @FXML
    private Button cButton;

    @FXML
    private Button ceButton;

    @FXML
    private Button negativeButton;

    @FXML
    private Button multiplyButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button plusButton;

    @FXML
    private Button equalButton;

    @FXML
    private Button decimalButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button zeroButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button sixButton;

    public void initialize() {
        oneButton.setOnAction(e -> buttonPressed('1'));
        twoButton.setOnAction(e -> buttonPressed('2'));
        threeButton.setOnAction(e -> buttonPressed('3'));
        fourButton.setOnAction(e -> buttonPressed('4'));
        fiveButton.setOnAction(e -> buttonPressed('5'));
        sixButton.setOnAction(e -> buttonPressed('6'));
        sevenButton.setOnAction(e -> buttonPressed('7'));
        eightButton.setOnAction(e -> buttonPressed('8'));
        nineButton.setOnAction(e -> buttonPressed('9'));
        zeroButton.setOnAction(e -> buttonPressed('0'));
        decimalButton.setOnAction(e -> buttonPressed('.'));
        plusButton.setOnAction(e -> buttonPressed('+'));
        minusButton.setOnAction(e -> buttonPressed('-'));
        multiplyButton.setOnAction(e -> buttonPressed('*'));
        divideButton.setOnAction(e -> buttonPressed('/'));
        equalButton.setOnAction(e -> buttonPressed('='));
        mPlusButton.setOnAction(e -> buttonPressed('m'));
        mrButton.setOnAction(e -> buttonPressed('r'));
        mcButton.setOnAction(e -> buttonPressed('c'));
        negativeButton.setOnAction(e -> buttonPressed('n'));
        cButton.setOnAction(e -> clear());
        // should handle clearing last entry only, but doesn't
        // would need to implement more complex data structure to handle
        ceButton.setOnAction(e -> clear());
    }

    void buttonPressed( char ch ) {
        String token = Character.toString( ch );
        // wanted to include switch statement rather than if/else chain
        // not sure how to implement that in Java since string comparison
        // is done via .equals() method rather than traditional comparison operators

        if(token.equals("=")) {// calculate equation
            String postFix = calculation.infixToPostfix(output);
            String result = calculation.evaluatePostFix(postFix);
            outputLabel.setText( result );
            output = result;
            return;
        }

        if (token.equals("n")) {// change number to negative (+- button)
            try {
                Integer currentValue = Integer.parseInt(output);
                currentValue = Math.negateExact(currentValue);
                output = String.valueOf(currentValue);
                outputLabel.setText( output );
                return;
            } catch (NumberFormatException | ArithmeticException e) {
                System.err.println("Tried to make a non-number negative: " + e);
            }
        }

        if (token.equals("m")) {// store to memory
            try {
                memory = Double.parseDouble(output);
                return;
            } catch (NumberFormatException e) {
                System.err.println("Tried to store a non-number in memory: " + e);
            }
        } else if (token.equals("c")) {// clear memory
            memory = 0;
            return;
        }

        // add input to textfield
        if(Character.isDigit(ch) || token.equals(".")) {
            output = output.concat(token);
        } else if (token.equals("r")) {
            output = output.concat(String.valueOf(memory));
        } else {
            output = output.concat(" " + token + " ");
        }

        outputLabel.setText( output );
    }

    void clear() {
        output = "";
        outputLabel.setText( "0" );
    }

}

