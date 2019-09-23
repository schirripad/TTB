package edu.moravian.schirripad.ttb.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import edu.moravian.schirripad.ttb.PDFConverter;
import net.miginfocom.swing.MigLayout;

public class MainUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1675702011798656444L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JCheckBox chckbxConvertImages;
	private JProgressBar progressBar = new JProgressBar();
	private JSpinner spinnerWidth, spinnerHeight;
	private File pdf, output;
	private JFileChooser fc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		setTitle("Text to Braille");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		fc = new JFileChooser();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnEdit = new JMenu("Preferences");
		JMenuItem prefEdit = new JMenuItem("Edit");
		prefEdit.setActionCommand("pref");
		prefEdit.addActionListener(this);
		mnEdit.add(prefEdit);
		menuBar.add(mnEdit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel fileSelPanel = new JPanel();
		contentPane.add(fileSelPanel, BorderLayout.NORTH);
		fileSelPanel.setLayout(new BoxLayout(fileSelPanel, BoxLayout.X_AXIS));

		JLabel lblPdf = new JLabel("PDF:");
		fileSelPanel.add(lblPdf);

		textField = new JTextField();
		fileSelPanel.add(textField);
		textField.setColumns(10);

		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(this);
		btnSelect.setActionCommand("selPDF");
		fileSelPanel.add(btnSelect);

		JLabel lblOutput = new JLabel("Output:");
		fileSelPanel.add(lblOutput);

		textField_1 = new JTextField();
		fileSelPanel.add(textField_1);
		textField_1.setColumns(10);

		JButton btnSelect_1 = new JButton("Select");
		btnSelect_1.addActionListener(this);
		btnSelect_1.setActionCommand("selOut");
		fileSelPanel.add(btnSelect_1);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);

		JLabel lblPagePreview = new JLabel("Page Preview") {
			@Override
			public void paint(Graphics g0) {
				Graphics2D g = (Graphics2D) g0;
				// TODO Generate image for preview, however, look into AWT Threading so it
				// doesn't block the main thread
			}
		};

		// TODO Add preview image
		panel_4.add(lblPagePreview);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new MigLayout("", "[][119px,grow]", "[16px][][][][][]"));

		JLabel lblDocumentOptions = new JLabel("Document Options");
		panel_1.add(lblDocumentOptions, "cell 1 0,alignx left,aligny center");

		chckbxConvertImages = new JCheckBox("Convert Images");
		panel_1.add(chckbxConvertImages, "cell 1 1");

		JLabel lblPages = new JLabel("Pages:");
		panel_1.add(lblPages, "cell 0 2,alignx trailing");

		textField_2 = new JTextField();
		panel_1.add(textField_2, "cell 1 2,growx");
		textField_2.setColumns(10);

		JLabel lblOutputDimensions = new JLabel("Output Dimensions");
		panel_1.add(lblOutputDimensions, "cell 1 3");

		JLabel lblWidth = new JLabel("Width:");
		panel_1.add(lblWidth, "cell 0 4");

		spinnerWidth = new JSpinner(
				new SpinnerNumberModel(Integer.valueOf(50), Integer.valueOf(1), null, Integer.valueOf(1)));
		((JSpinner.DefaultEditor) spinnerWidth.getEditor()).getTextField().setColumns(4);
		panel_1.add(spinnerWidth, "cell 1 4");

		JLabel lblHeight = new JLabel("Height:");
		panel_1.add(lblHeight, "cell 0 5");

		spinnerHeight = new JSpinner(
				new SpinnerNumberModel(Integer.valueOf(50), Integer.valueOf(1), null, Integer.valueOf(1)));
		((JSpinner.DefaultEditor) spinnerHeight.getEditor()).getTextField().setColumns(4);
		panel_1.add(spinnerHeight, "cell 1 5");

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(this);
		btnConvert.setActionCommand("convert");
		panel_3.add(btnConvert);

		panel_3.add(progressBar);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(null);
		panel_3.add(panel_6);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Commands:
		// pref
		// selOut
		// selPDF
		// convert
		switch (e.getActionCommand()) {
		case "pref":
			this.setFocusableWindowState(false);
			new PreferenceUI(this);
			break;
		case "selOut":
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int resp = fc.showOpenDialog(this);
			if (resp == JFileChooser.APPROVE_OPTION) {
				output = fc.getSelectedFile();
				textField_1.setText(output.getPath());
			}
			break;
		case "selPDF":
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int resp0 = fc.showOpenDialog(this);
			if (resp0 == JFileChooser.APPROVE_OPTION) {
				pdf = fc.getSelectedFile();
				System.out.println(pdf.getPath());
				textField.setText(pdf.getPath());
			}
			break;
		case "convert":
			// Check if already running
			if (progressBar.isIndeterminate()) {
				break;
			}
			// Check files
			if (!output.exists() || !output.isDirectory()) {
				JOptionPane.showMessageDialog(this, "Output directory does not exist!");
				break;
			}
			if (!pdf.exists()) {
				JOptionPane.showMessageDialog(this, "PDF File not found!");
				break;
			}
			try {
				SwingWorker sw = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						progressBar.setIndeterminate(true);
						PDFConverter.convert(pdf, output, (int) spinnerWidth.getValue(), (int) spinnerHeight.getValue(),
								chckbxConvertImages.isSelected());
						progressBar.setIndeterminate(false);
						JOptionPane.showMessageDialog(null, "Conversion Complete!");
						return null;
					}

				};
				sw.execute();
			} catch (Exception e0) {
				e0.printStackTrace();
				JOptionPane.showMessageDialog(this, "Exception: " + e0.getMessage());
			}
			break;
		}
	}
}
