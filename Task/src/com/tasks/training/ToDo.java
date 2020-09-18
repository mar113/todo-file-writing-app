package com.tasks.training;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class ToDo {
	
	private ArrayList<Task> taskList;
	private JFrame frame;
	private JTextField name;
	private JTextField date;
	private JTextArea desc;
	private JButton create;

	public ToDo(){
		
		//setup ui
		
		frame = new JFrame("to do task");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setting the main panel
		
		JPanel mainPanel = new JPanel();
		
		//setting up the fields
		name = new JTextField();
		name.setPreferredSize(new Dimension(320,45));
		date = new JTextField();
		date.setPreferredSize(new Dimension(320,45));
		desc= new JTextArea(7,25);
		
		// setting up the creation button
		
		create = new JButton("create task");
		taskList = new ArrayList<Task>();
		
		JScrollPane jsp = new JScrollPane(desc);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JLabel taskl = new JLabel("task : ");
		mainPanel.add(taskl);
		mainPanel.add(name);
		JLabel datel = new JLabel("date : ");
		mainPanel.add(datel);
		mainPanel.add(date);
		mainPanel.add(jsp);
		mainPanel.add(create);
		
		JMenuBar menu = new JMenuBar();
		JMenu menuFile = new JMenu("file");
		JMenuItem export = new JMenuItem("export tasks");
		create.addActionListener(new CreateActionListener());
		export.addActionListener(new exportActionListener());
		menuFile.add(export);
		menu.add(menuFile);
		frame.setJMenuBar(menu);
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.setSize(400, 500);
		frame.setVisible(true);
		
		
		
		
	}
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new ToDo();
				
			}
		});

	}
	
	
	class CreateActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			Task task = new Task(name.getText(),desc.getText());
			
			taskList.add(task);
			
			clearTask();
			
		}

		
		
	}
	private void clearTask() {
		name.setText("");
		date.setText("");
		desc.setText("");
		
	}
	
	public class exportActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Create a file dialog with file chooser, all done for us!
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			export(fileSave.getSelectedFile());
			
		}


		
	}
	
	private void export(File selectedFile) {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			Iterator<Task> taskIt = taskList.iterator();
			while(taskIt.hasNext())
			{
				Task tsk =(Task) taskIt.next();
				writer.write("Date: "+tsk.getDate()+"/"+"Task: "+tsk.getName()+"/"+"Description: "+tsk.getDescription()+"\n");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("something went wrong while writing to file!");
		}
		
		
	}
	
}
