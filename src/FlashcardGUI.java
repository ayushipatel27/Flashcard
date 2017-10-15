
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;

public class FlashcardGUI extends JFrame {

	/**
	 * Data variables
	 */
	AbstractStudyMethod sm;
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JButton dontKnow;
	private JButton know;
	private JButton flip;
	private JButton exit;
	private JButton save;
	private JButton load;

	private JTextField Answer;
	private JTextArea Question;

	/**
	 * Create the frame.
	 */
	public FlashcardGUI(AbstractStudyMethod method) {
		this.sm = method;
		if (sm.hasMoreCards() == true) {

			sm.pickCard();

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

			panel = new JPanel();
			contentPane.add(panel);
			panel.setLayout(new GridLayout(0, 1, 0, 0));

			Question = new JTextArea();
			Question.setBackground(new Color(0, 0, 0));
			Question.setDisabledTextColor(new Color(255, 255, 255));
			Question.setEnabled(false);
			Question.setEditable(false);
			Question.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
			Question.setText("Answer the following question:" + sm.getQuestion()
					+ "\r\n\r\nEnter your answer in the text box below.");
			panel.add(Question);

			panel_1 = new JPanel();
			contentPane.add(panel_1);
			panel_1.setLayout(new GridLayout(0, 1, 0, 0));

			Answer = new JTextField();
			Answer.setFont(new Font("Modern No. 20", Font.PLAIN, 30));
			Answer.setPreferredSize(new Dimension(10, 20));
			Answer.setHorizontalAlignment(SwingConstants.CENTER);
			panel_1.add(Answer);
			Answer.setColumns(20);

			panel_2 = new JPanel();
			panel_2.setBackground(new Color(0, 0, 0));
			FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
			contentPane.add(panel_2);

			dontKnow = new JButton("I Don't Know It");
			dontKnow.setFont(new Font("Modern No. 20", Font.PLAIN, 17));
			dontKnow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean validResponse = sm.testAnswer(Answer.getText());
					sm.moveCard(validResponse);
					JOptionPane.showMessageDialog(null, "The correct answer is: " + sm.getAnswer());
					if (sm.hasMoreCards() == true) {
						update();
					} else {
						dispose();
					}
				}
			});
			panel_2.add(dontKnow);

			know = new JButton("I Know It");
			know.setFont(new Font("Modern No. 20", Font.PLAIN, 17));
			know.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean validResponse = sm.testAnswer(Answer.getText());
					if (Answer.getText().equals(""))
						validResponse = true;
					sm.moveCard(validResponse);
					if (validResponse == false)
						JOptionPane.showMessageDialog(null, "The answer is: " + sm.getAnswer());
					if (sm.hasMoreCards() == true) {
						update();
					} else {
						dispose();
					}
				}

			});
			panel_2.add(know);

			flip = new JButton("Flip");
			flip.setFont(new Font("Modern No. 20", Font.PLAIN, 17));
			flip.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "The back of the card is: " + sm.getAnswer());
				}
			});
			panel_2.add(flip);

			exit = new JButton("Exit");
			exit.setFont(new Font("Modern No. 20", Font.PLAIN, 17));
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			panel_2.add(exit);

			setVisible(true);
		} else {
			dispose();

		}
	}

	/**
	 * Updates the question
	 */

	private void update() {
		sm.pickCard();
		Question.setText("Answer the following question:" + sm.getQuestion()
				+ "\r\n\r\nEnter your answer in the text box below.");
		Answer.setText(null);
		repaint();
		revalidate();
	}

	public String toString() {
		return "";
	}
}
