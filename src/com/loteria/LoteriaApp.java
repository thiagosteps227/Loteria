package com.loteria;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.loteria.dao.LotteryDAO;
import com.loteria.model.Draw;

@SuppressWarnings("serial")
class LoteriaApp extends JFrame implements ActionListener {

	LotteryDAO lotteryDao = new LotteryDAO();

	// Create some Buttons
	private JButton selectDrawsBtn = new JButton("Ver sorteios");
	private JButton insertDrawsBtn = new JButton("Inserir um sorteio");
	private JButton updateDrawsBtn = new JButton("Modificar um sorteio");
	private JButton deleteDrawBtn = new JButton("Deletar um sorteio");
	// private JButton instructorsButton = new JButton("INSTRUCTORS");
	// private JButton divesitesButton = new JButton("DIVE SITES( Clear | Turbid
	// )");

	// Create Text Fields
	private JTextField lookDiveDates = new JTextField(6);
	private JTextField lookDives = new JTextField(10);
	private JTextField lookDepth = new JTextField(6);

	// Set up the JDBC requirements
	private Connection con = null;
	private Statement stmt = null;

	public LoteriaApp(String str) {
		super(str);

		// Set the layout format (4 Rows * 4 Columns)
		getContentPane().setLayout(new GridLayout(4, 4));

		// Connect to the Database
		initDBConnection();

		// Add the GUI components
		getContentPane().add(selectDrawsBtn);
		getContentPane().add(insertDrawsBtn);
		// getContentPane().add(instructorsButton);
		getContentPane().add(deleteDrawBtn);
		getContentPane().add(updateDrawsBtn);
		// getContentPane().add(divesitesButton);
		getContentPane().add(lookDiveDates);
		getContentPane().add(lookDepth);

		// Add some listeners to monitor for actions (i.e. button presses)
		selectDrawsBtn.addActionListener(this);
		insertDrawsBtn.addActionListener(this);
		updateDrawsBtn.addActionListener(this);
		deleteDrawBtn.addActionListener(this);
		// instructorsButton.addActionListener(this);
		// divesitesButton.addActionListener(this);
		lookDiveDates.addActionListener(this);
		lookDepth.addActionListener(this);

		// Set the Window Size
		setSize(750, 350);
		// Show the Window
		setVisible(true);
	}

	private void initDBConnection() {

		// Connect to the database
		try {

			String url = "jdbc:mysql://localhost:3306/lottery?serverTimezone=" + TimeZone.getDefault().getID();
			con = DriverManager.getConnection(url, "root", "admin");
			stmt = con.createStatement();

		}
		// Report the error and stopping the code
		catch (Exception e) {
			// Updated error
			System.out.println("Failed to initialise DB Connection");
			System.out.println("Error: " + e);
		}
	}

	// Write information to a file
	private void writeToFile(ResultSet rs) {
		try {

			FileWriter outputFile = new FileWriter("CellOutput.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for (int i = 0; i < numColumns; i++) {
				printWriter.print(rsmd.getColumnLabel(i + 1) + ",");
			}
			printWriter.print("\n");

			while (rs.next()) {
				for (int i = 0; i < numColumns; i++) {
					printWriter.print(rs.getString(i + 1) + ",");
				}

				printWriter.print("\n");
				printWriter.flush();
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// When an action is performed
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		ResultSet rs = null;

		ResultSet result = null;
		String cmd = null;

		// When the button is clicked, the SQL command is constructed and stored in CMD
		if (target.equals(selectDrawsBtn)) {

			// initDBConnection();
			try {

				List<Draw> draws = lotteryDao.selectAllDraws();

				for (int i = 0; i < draws.size(); i++) {

					System.out.println(draws.get(i).getDrawNumbers());

				}

			} catch (Exception e1) {

				e1.printStackTrace();
			}

		} else if (target.equals(insertDrawsBtn)) {

			try {

				Draw draws = new Draw();

				draws.setDrawNumbers("12,23,34,45");

				lotteryDao.insertDraws(draws);

				System.out.println(draws.getDrawNumbers());

			} catch (Exception e1) {

				e1.printStackTrace();
			}

		} else if (target.equals(updateDrawsBtn)) {

			try {

				Draw draws = new Draw();

				draws.setDrawNumbers("12,23,34,45,23,12");

				Draw drawSelected = lotteryDao.selectDraw(2);

				drawSelected.setDrawNumbers("12,23,34,45,23,12");

				System.out.println(drawSelected.getDrawNumbers());

				lotteryDao.updateDraw(drawSelected);

				System.out.println(draws.getDrawNumbers());

			} catch (Exception e1) {

				e1.printStackTrace();
			}

		} else if (target.equals(deleteDrawBtn)) {

			try {

				lotteryDao.deleteDraw(4);

			} catch (Exception e1) {

				e1.printStackTrace();
			}

		}
		;

		// call this query on the SQL database
		try {

			// rs = stmt.executeQuery(cmd);
			// writeToFile(rs);
			// System.out.println(rs);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String args[]) {

		// window title
		new LoteriaApp("Loteria App");

	}

}
