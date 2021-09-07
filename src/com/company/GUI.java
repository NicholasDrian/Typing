package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class GUI extends JFrame implements KeyListener, ActionListener {

    int wordCount = 40;
    int minWordLength = 1;
    int maxWordLength = 8;
    int wordsPerLine = 8;
    int typed = 0;
    int errors = 0;
    boolean lastError = false;
    boolean started = false;
    double startTime;
    JTextArea stats;
    boolean nums;
    JButton numsOn;
    boolean lower;
    JButton lowerOn;
    boolean upper;
    JButton upperOn;
    boolean symbol;
    JButton symbolOn;
    String charSet;
    JLabel charSetLabel;
    String text = "";
    JTextArea textArea;
    String textTyped = "";
    JTextArea textTypedArea;
    String wrong = "";
    JTextArea wrongArea;

    GUI(boolean Nums, boolean Lower, boolean Upper, boolean Symbol){
        nums = Nums;
        lower = Lower;
        upper = Upper;
        symbol = Symbol;
        setTitle("LeetTyping!");
        setBounds(100, 100, 820, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        getContentPane().setLayout(null);

        charSet = Typable.getTypable(nums, lower, upper, symbol);
        charSetLabel = new JLabel("Character Set: " + charSet, SwingConstants.CENTER);
        charSetLabel.setForeground(Color.black);
        charSetLabel.setLocation(0,5);
        charSetLabel.setSize(820, 30);
        add(charSetLabel);

        numsOn = new JButton("Numbers");
        numsOn.setLocation(5,35);
        numsOn.setSize(200, 30);
        numsOn.addActionListener(this);
        numsOn.setFocusable(false);
        add(numsOn);

        lowerOn = new JButton("Lower Case");
        lowerOn.setLocation(205,35);
        lowerOn.setSize(200, 30);
        lowerOn.addActionListener(this);
        lowerOn.setFocusable(false);
        add(lowerOn);

        upperOn = new JButton("Upper Case");
        upperOn.setLocation(405,35);
        upperOn.setSize(200, 30);
        upperOn.addActionListener(this);
        upperOn.setFocusable(false);
        add(upperOn);

        symbolOn = new JButton("Symbols");
        symbolOn.setLocation(605,35);
        symbolOn.setSize(200, 30);
        symbolOn.addActionListener(this);
        symbolOn.setFocusable(false);
        add(symbolOn);


        textArea = new JTextArea(text);
        textArea.setFont(new Font("Courier", Font.PLAIN, 22));
        textArea.setLocation(25,130);
        textArea.setSize(800, 150);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        add(textArea);

        textTypedArea = new JTextArea(textTyped);
        textTypedArea.setFont(new Font("Courier", Font.PLAIN, 22));
        textTypedArea.setLocation(25,130);
        textTypedArea.setSize(800, 150);
        textTypedArea.setLineWrap(true);
        textTypedArea.setWrapStyleWord(true);
        textTypedArea.setOpaque(false);
        textTypedArea.setForeground(new Color(100, 200, 100));
        textTypedArea.setFocusable(false);
        add(textTypedArea);

        wrongArea = new JTextArea(textTyped);
        wrongArea.setFont(new Font("Courier", Font.PLAIN, 22));
        wrongArea.setLocation(25,130);
        wrongArea.setSize(800, 150);
        wrongArea.setLineWrap(true);
        wrongArea.setWrapStyleWord(true);
        wrongArea.setOpaque(false);
        wrongArea.setForeground(new Color(200, 100, 100));
        wrongArea.setFocusable(false);
        add(wrongArea);

        stats = new JTextArea();
        stats.setFont(new Font("Courier", Font.BOLD, 30));
        stats.setLocation(45,70);
        stats.setSize(800, 50);
        stats.setOpaque(false);
        stats.setFocusable(false);
        stats.setText(Stats.getStats(startTime, errors, typed));
        add(stats);

        resetText();
        updateButtons();
        setVisible(true);
    }

    private String generateText() {
        String s = "";
        int sizeVariability = maxWordLength - minWordLength;
        for (int i = 0; i < wordCount; i++){
            double random = Math.random();
            random *= sizeVariability;
            random += minWordLength;
            for (int j = 0; j < (int) random; j++){
                s = s + getRandomChar();
            }
            s = s + " ";
        }
        int count = 0;
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == ' '){
                count++;
                if (count % wordsPerLine == 0){
                    s = s.substring(0,i) + "\n" + s.substring(i+1);
                }
            }
        }
        return s;
    }

    private char getRandomChar() {
        double random = Math.random();
        if (charSet.length() != 0) {
            int selection = (int) (random * charSet.length());
            return charSet.charAt(selection);
        } return ' ';
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {
        updateTyped(e.getKeyChar());
    }

    private void updateTyped(char c) {
        if (c == '￿'){
            return;
        }
        if (!started){
            started = true;
            startTime = System.nanoTime();
        }
        if (c == text.charAt(typed)){
            if (c == ' ') {
                c = '_';
            }
            char ch = ' ';
            if (c == '\n') {
                ch = '\n';
            }
            if (lastError) {
                wrong = wrong + c;
                textTyped = textTyped + ch;
                lastError = false;
            } else {
                wrong = wrong + ch;
                textTyped = textTyped + c;
            }
            text = text.substring(0, typed) +
                    ch +
                    text.substring(typed + 1);

            textTypedArea.setText(textTyped);
            textArea.setText(text);
            wrongArea.setText(wrong);
            add(textTypedArea);
            typed++;
            stats.setText(Stats.getStats(startTime, errors, typed));
        } else {
            if (!lastError) {
                errors++;
                lastError = true;
            }
        }
    }

    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == numsOn){nums = !nums;}
        if (e.getSource() == lowerOn){lower = !lower;}
        if (e.getSource() == upperOn){upper = !upper;}
        if (e.getSource() == symbolOn){symbol = !symbol;}
        updateChars();
        updateButtons();
    }

    private void updateButtons() {
        if (nums) {
            numsOn.setForeground(new Color(100, 200, 100));
        } else {
            numsOn.setForeground(Color.black);
        }
        if (lower) {
            lowerOn.setForeground(new Color(100, 200, 100));
        } else {
            lowerOn.setForeground(Color.black);
        }
        if (upper) {
            upperOn.setForeground(new Color(100, 200, 100));
        } else {
            upperOn.setForeground(Color.black);
        }
        if (symbol) {
            symbolOn.setForeground(new Color(100, 200, 100));
        } else {
            symbolOn.setForeground(Color.black);
        }
        resetText();
    }
    public void resetText(){
        text = generateText();
        textArea.setText(text);
        textTyped = "";
        wrong = "";
        typed = 0;
        errors = 0;
        textTypedArea.setText(textTyped);
        wrongArea.setText(wrong);
        started = false;
    }

    private void updateChars() {
        charSet = Typable.getTypable(nums, lower, upper, symbol);
        charSetLabel.setText("Character Set: " + charSet);
    }
}
