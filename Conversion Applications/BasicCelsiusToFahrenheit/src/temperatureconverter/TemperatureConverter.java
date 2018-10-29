/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureconverter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author cstuser
 */
public class TemperatureConverter extends JFrame {

    private JLabel celsiusLabel;
    private JTextField celsiusValueTextField;
    private JLabel fahrenheitLabel;
    private JLabel fahrenheitValueLabel;
    private JButton convertButton;
    private JButton closeButton;

    public TemperatureConverter(String title, int width, int height) {
        super(title);
        setSize(width, height);

        celsiusValueTextField = new JTextField();
        celsiusLabel = new JLabel("C");

        fahrenheitValueLabel = new JLabel("", SwingConstants.RIGHT);
        fahrenheitLabel = new JLabel("F");

        convertButton = new JButton("Convert");
        closeButton = new JButton("Close");

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    float celsius = Float.parseFloat(celsiusValueTextField.getText());
                float fahrenheit = celsius * 1.8f + 32.0f;
                fahrenheitValueLabel.setText(String.format("%.2f", fahrenheit));
                }
                catch(Exception e){
                    
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispatchEvent(new WindowEvent(TemperatureConverter.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(3, 2));

        pane.add(celsiusValueTextField);
        pane.add(celsiusLabel);
        pane.add(fahrenheitValueLabel);
        pane.add(fahrenheitLabel);
        pane.add(convertButton);
        pane.add(closeButton);

    }

    public static void main(String[] args) {
        TemperatureConverter window = new TemperatureConverter("Hello SWING", 300, 120);
        window.setVisible(true);
    }
}
