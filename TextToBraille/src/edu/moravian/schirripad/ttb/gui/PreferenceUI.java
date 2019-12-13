package edu.moravian.schirripad.ttb.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.moravian.edu.ttb.logging.Logger;
import net.miginfocom.swing.MigLayout;

public class PreferenceUI extends JFrame implements WindowListener {

	private JPanel contentPane;
	private JTextField textField;
	private JCheckBox doDebug;
	private MainUI main;
	private Logger log = new Logger("PrefenceUI");

	/**
	 * Create the frame.
	 */
	public PreferenceUI(MainUI main) {
		this.main = main;
		setTitle("Preferences");
		addWindowListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new MigLayout("", "[145px]", "[16px][][][][][]"));

		JLabel lblNewLabel = new JLabel("Character Set Location:");
		panel.add(lblNewLabel, "cell 0 1,alignx left,aligny center");

		JLabel lblDebug = new JLabel("Debug:");
		panel.add(lblDebug, "cell 0 3");

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnSelect = new JButton("Select");
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelect.gridx = 0;
		gbc_btnSelect.gridy = 2;
		panel_1.add(btnSelect, gbc_btnSelect);

		JButton btnApply = new JButton("Apply");
		btnApply.setActionCommand("applySettings");
		btnApply.addActionListener(main);

		doDebug = new JCheckBox("Debug");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 3;
		panel_1.add(doDebug, gbc_chckbxNewCheckBox);
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.gridx = 0;
		gbc_btnApply.gridy = 8;
		panel_1.add(btnApply, gbc_btnApply);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setVisible(true);
	}

	public String getPath() {
		return textField.getText();
	}

	public boolean doDebug() {
		return doDebug.isSelected();
	}
	
	@Override
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		log.debug("Opened preference frame...");
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		main.setFocusableWindowState(true);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		main.setFocusableWindowState(true);
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
