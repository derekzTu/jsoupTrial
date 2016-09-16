import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class jSoupGui implements ActionListener {


	private static final String[] elements = {"Directory to save to: ","num: ",
		"List (google, bing, imagenet, a): ","Manual Url: ","Query: " };
	private static List<JTextField> inputLabels =  null;
	private static JFrame frame = null;
	private static JFrame downloadAgain = null;

	//main to run everything
	public static void main(String[]args){
		createjSoupGui();
	}

	//calls the run method
	private static void createjSoupGui(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				new jSoupGui();

			}
		});
	}

	//creates the grid
	private jSoupGui() {
		inputLabels = new ArrayList<JTextField>();
		frame = new JFrame("Download Manager");
		frame.setLayout(new GridLayout(0,2));

		//adds the elements
		for(int i =0;i<elements.length;i++){
			JLabel newLabel = new JLabel(elements[i]);
			frame.add(newLabel);
			JTextField elementToBeAdded = new JTextField();
			frame.add(elementToBeAdded);
			inputLabels.add(elementToBeAdded);
		}

		//Add the done button
		JButton downloadButton = new JButton("Download");
		frame.add(downloadButton);
		downloadButton.addActionListener(this);
		downloadButton.setActionCommand("download");

		//Add the cancel button
		JButton cancelButton = new JButton("Cancel");
		frame.add(cancelButton);
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");


		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//download
		if(e.getActionCommand().equals("download")){
			try {

				//variable to send to main of imageDownload
				String[] args = new String[inputLabels.size()];
				for (int i =0;i<inputLabels.size();i++){
					args[i] = inputLabels.get(i).getText();
				}

				//call the imageDownload main to start downloading
				imageDownload.main(args);

				//java gui spring cleaning
				frame.dispose();
				downloadAgain();

			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		//cancel
		else if(e.getActionCommand().equals("cancel")){
			frame.dispose();
		}

		//download again
		else if(e.getActionCommand().equals("yes")){
			downloadAgain.dispose();
			createjSoupGui();
		}
		//done downloading
		else{
			downloadAgain.dispose();
			frame.dispose();
		}
	}

	//method to creat the window that asks if want to download
	private void downloadAgain() {
		downloadAgain = new JFrame("Would you like to download again?");
		downloadAgain.setLayout(new GridLayout(0,2));

		//labels to ask if want to download
		JLabel label = new JLabel("Would you like to download again?");
		JLabel yesNo = new JLabel(" Please select yes or no");
		downloadAgain.add(label);
		downloadAgain.add(yesNo);

		//yes button
		JButton yes = new JButton("Yes");
		downloadAgain.add(yes);
		yes.addActionListener(this);
		yes.setActionCommand("yes");

		//no button
		JButton no = new JButton("no");
		downloadAgain.add(no);
		no.addActionListener(this);
		no.setActionCommand("no");


		downloadAgain.pack();
		downloadAgain.setVisible(true);


	}
}
