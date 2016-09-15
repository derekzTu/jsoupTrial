import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class jSoupGui implements ActionListener {
	private static final String[] elements = {"num: ","Url: ","Manual Url: ",};
	private static List<JTextField> inputLabels =  null;
	private static JFrame frame = null;
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
				
				
				imageDownload.main(null);
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.dispose();
		}
		
		//cancel
		else{
			
			frame.dispose();
		}
	}
}
