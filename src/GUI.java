import java.awt.event.*;

import javax.swing.*;

public class GUI extends JFrame{

	private JPanel panel = new JPanel();
	private JLabel label;
	private JTextField textField;
	private JButton button;
	
	public GUI() {
		
		label = new JLabel("Number");
		textField = new JTextField("Please enter a maximum value"); 
		button = new JButton("Press");
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int maxNumber = Integer.parseInt(textField.getText());
			}		
		});
		
		panel.add(label);
		panel.add(textField);
		panel.add(button);
		
		this.setContentPane(panel);	
		this.setSize(500,500);
		this.setTitle("Guess the number");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
