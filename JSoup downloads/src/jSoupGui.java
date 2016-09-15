import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class jSoupGui implements ActionListener {
	private static final String[] elements = {"num: ","List (google, bing, imagenet, a): ","Manual Url: ","Query: " };
	private static List<JTextField> inputLabels =  null;
	private static JFrame frame = null;
	private static JFrame downloadAgain = null;
	
	public static void main(String[]args){
		createjSoupGui();
	}

	private static void createjSoupGui(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				new jSoupGui();

			}
		});
	}
	private jSoupGui() {

		frame = new JFrame("Download Manager");
		frame.setLayout(new GridLayout(0,2));

		for(int i =0;i<elements.length;i++){
			inputLabels = new ArrayList<JTextField>();
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
				String num = inputLabels.get(0).getText();
				String url = inputLabels.get(1).getText();
				String manUrl = inputLabels.get(2).getText();
				String query = inputLabels.get(3).getText();
				String[] args = {num,url,manUrl,query};
				imageDownload.main(args);
				downloadAgain();

			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
			frame.dispose();
		}

		//cancel
		else if(e.getActionCommand().equals("cancel")){
			frame.dispose();
		}
		else if(e.getActionCommand().equals("yes")){
			downloadAgain.dispose();
			createjSoupGui();
		}
		else{
			downloadAgain.dispose();
			frame.dispose();
		}
	}

	private void downloadAgain() {
		downloadAgain = new JFrame("Would you like to download again?");
		downloadAgain.setLayout(new GridLayout(0,2));
		
		JButton yes = new JButton("Yes");
		downloadAgain.add(yes);
		yes.addActionListener(this);
		yes.setActionCommand("yes");

		//Add the cancel button
		JButton no = new JButton("no");
		downloadAgain.add(no);
		no.addActionListener(this);
		no.setActionCommand("no");


		downloadAgain.pack();
		downloadAgain.setVisible(true);
		
		
	}
}
