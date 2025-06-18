package calculator;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GUICalculator extends JFrame implements ActionListener {
    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0, memory = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public GUICalculator() {
        setTitle("Smart Calculator");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Buttons
        String[] buttons = {
            "MC", "MR", "M+", "M-",
            "C", "←", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "=",
            "√", "x²", "1/x", "x^y",
            "π", "e", "!", "log",
            "ln", "sin", "cos", "tan"
        };

        JPanel buttonPanel = new JPanel(new GridLayout(9, 4, 10, 10));
        for (String b : buttons) {
            JButton btn = new JButton(b);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            if (cmd.matches("[0-9]")) {
                if (startNewNumber || display.getText().equals("0")) {
                    display.setText(cmd);
                } else {
                    display.setText(display.getText() + cmd);
                }
                startNewNumber = false;
            } else if (cmd.equals(".")) {
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
            } else if (cmd.equals("C")) {
                display.setText("0");
                num1 = num2 = 0;
                operator = "";
                startNewNumber = true;
            } else if (cmd.equals("←")) {
                String t = display.getText();
                display.setText(t.length() > 1 ? t.substring(0, t.length() - 1) : "0");
            } else if (cmd.equals("+/-")) {
                double val = Double.parseDouble(display.getText());
                display.setText(String.valueOf(-val));
            } else if (cmd.equals("π")) {
                display.setText(String.valueOf(Math.PI));
                startNewNumber = true;
            } else if (cmd.equals("e")) {
                display.setText(String.valueOf(Math.E));
                startNewNumber = true;
            } else if (cmd.equals("√")) {
                double val = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.sqrt(val)));
                startNewNumber = true;
            } else if (cmd.equals("x²")) {
                double val = Double.parseDouble(display.getText());
                display.setText(String.valueOf(val * val));
                startNewNumber = true;
            } else if (cmd.equals("1/x")) {
                double val = Double.parseDouble(display.getText());
                if (val == 0) {
                    showError("Cannot divide by zero!");
                } else {
                    display.setText(String.valueOf(1 / val));
                    startNewNumber = true;
                }
            } else if (cmd.equals("!")) {
                int n = Integer.parseInt(display.getText());
                display.setText(String.valueOf(factorial(n)));
                startNewNumber = true;
            } else if (cmd.equals("log")) {
                display.setText(String.valueOf(Math.log10(Double.parseDouble(display.getText()))));
                startNewNumber = true;
            } else if (cmd.equals("ln")) {
                display.setText(String.valueOf(Math.log(Double.parseDouble(display.getText()))));
                startNewNumber = true;
            } else if (cmd.equals("sin")) {
                display.setText(String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(display.getText())))));
                startNewNumber = true;
            } else if (cmd.equals("cos")) {
                display.setText(String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(display.getText())))));
                startNewNumber = true;
            } else if (cmd.equals("tan")) {
                display.setText(String.valueOf(Math.tan(Math.toRadians(Double.parseDouble(display.getText())))));
                startNewNumber = true;
            } else if (cmd.equals("MC")) {
                memory = 0;
            } else if (cmd.equals("MR")) {
                display.setText(String.valueOf(memory));
            } else if (cmd.equals("M+")) {
                memory += Double.parseDouble(display.getText());
            } else if (cmd.equals("M-")) {
                memory -= Double.parseDouble(display.getText());
            } else if ("+-*/x^y".contains(cmd)) {
                num1 = Double.parseDouble(display.getText());
                operator = cmd;
                startNewNumber = true;
            } else if (cmd.equals("=")) {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/":
                        if (num2 == 0) {
                            showError("Cannot divide by zero");
                            return;
                        }
                        result = num1 / num2; break;
                    case "x^y": result = Math.pow(num1, num2); break;
                }
                display.setText(String.valueOf(result));
                startNewNumber = true;
            }
        } catch (Exception ex) {
            showError("Invalid operation!");
        }
    }

    private int factorial(int n) {
        int f = 1;
        for (int i = 2; i <= n; i++) f *= i;
        return f;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUICalculator::new);
    }
}
