package study;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator extends JFrame{

    private JTextField inputSpace;
    private String num = "";
    private String prev_operation = "";
    private ArrayList<String> equation = new ArrayList<>();
    public Calculator() {

        setLayout(null);

        // 기본 프레임 구현
        inputSpace = new JTextField();
        inputSpace.setEditable(false);
        inputSpace.setBackground(Color.WHITE);
        inputSpace.setHorizontalAlignment(JTextField.RIGHT);
        inputSpace.setFont(new Font("Arial", Font.BOLD, 50));
        inputSpace.setBounds(8, 10, 270, 70);

        // 버튼 구현
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBounds(8, 90, 270, 235);

        String button_names[] = {"C", "÷", "×", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0"};
        JButton buttons[] = new JButton[button_names.length];

        for (int i = 0; i < button_names.length; i++) {
            buttons[i] = new JButton(button_names[i]);
            //글씨체
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            //버튼의 색 지정
//            if (button_names[i] == "C") buttons[i].setBackground(Color.RED);
//            else if ((i >= 4 && i <= 6) || (i >= 8 && i <= 10) || (i >= 12 && i <= 14)) buttons[i].setBackground(Color.BLACK);
//            else buttons[i].setBackground(Color.GRAY);
            //글자색 지정
            buttons[i].setForeground(Color.BLACK);
            //테두리 없앱
            buttons[i].setBorderPainted(false);
            //밑에서 만든 액션리스너를 버튼에 추가
            buttons[i].addActionListener(new PadActionListener());
            //버튼들을 버튼패널에 추가
            buttonPanel.add(buttons[i]);
        }

        add(inputSpace);
        add(buttonPanel);

        setTitle("계산기");
        setVisible(true);
        setSize(300, 370);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class PadActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String operation = e.getActionCommand();

            if (operation.equals("C")) {
                inputSpace.setText("");
            } else if (operation.equals("=")) {
                String result = Double.toString(calculate(inputSpace.getText()));
                inputSpace.setText("" + result);
                num = "";
            } else if (operation.equals("+") || operation.equals("-") || operation.equals("x") || operation.equals("÷")) {
                if (inputSpace.getText().equals("") && operation.equals("-")) {
                    inputSpace.setText(inputSpace.getText() + e.getActionCommand());
                } else if (!inputSpace.getText().equals("") && !prev_operation.equals("+") &&
                        !prev_operation.equals("-") && !prev_operation.equals("×") && !prev_operation.equals("÷")) {
                    inputSpace.setText(inputSpace.getText() + e.getActionCommand());
                }
            } else {
                inputSpace.setText(inputSpace.getText() + e.getActionCommand());
            }
        }
    }

    private void fullTextParsing(String inputText) {
        equation.clear();

        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);
            if(ch == '-' || ch == '+' || ch =='x' || ch == '÷') {
                equation.add(num);
                num = "";
                equation.add(ch + "");
            } else {
                num = num + ch;
            }
        }
        equation.add(num);
    }

    // TODO 우선순위 적용 필요
    public double calculate(String inputText) {
        fullTextParsing(inputText);

        double prev = 0;
        double current = 0;
        String mode = "";

        for (String s : equation) {
            if (s.equals("+")) {
                mode = "add";
            } else if (s.equals("-")) {
                    mode = "sub";
            } else if (s.equals("x")) {
                mode = "mul";
            } else if (s.equals("÷")) {
                mode = "div";
            } else {
                current = Double.parseDouble(s);
                if (mode.equals("add")) {
                    prev += current;
                } else if (mode.equals("sub")) {
                    prev -= current;
                } else if (mode.equals("mul")) {
                    prev *= current;
                } else if (mode.equals("div")) {
                    prev /= current;
                } else {
                    prev = current;
                }
            }
        }
        return prev;
    }
    public static void main(String[] args) {
        new Calculator();
    }
}