package base;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GraphicalInterface extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 150;
	
	void presentFact(String fact) {
		JOptionPane.showMessageDialog(this,
				"<html><body><p style='width: 200px;'>" + fact + "</p></body></html>"
				);
	}
	
	void signalAnError(String errorMessage) {
		JOptionPane.showMessageDialog(this,
				errorMessage,
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	private class GraphicalInterfacePanel extends JPanel{
		private static final long serialVersionUID = 1L;

		GraphicalInterfacePanel(Handler handler){
			JButton printButton = new JButton("Get a fact"),
					updateButton = new JButton("Update fact base");
			
			printButton.setPreferredSize(new Dimension(180, 100));
			updateButton.setPreferredSize(new Dimension(180, 100));
			
			printButton.addActionListener(event -> {
				handler.requestFact();
			});
			
			updateButton.addActionListener(event -> {
				handler.updateFactBase();
			});
			
			add(printButton);
			add(updateButton);
		}
	}
	
	GraphicalInterface(Handler handler){
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 1));
		setResizable(false);
		setTitle("Cat facts on demand");
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(res.width/2 - WIDTH/2, res.height/2 - HEIGHT/2);
		
		GraphicalInterfacePanel panel = new GraphicalInterfacePanel(handler);
		add(panel);
		
		setVisible(true);
	}
}
