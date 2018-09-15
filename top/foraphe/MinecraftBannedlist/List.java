package top.foraphe.MinecraftBannedlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class List extends JFrame implements Runnable,ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FlowLayout flo;
	JLabel playerspre;
	JTextField players;
	JLabel uuid;
	JTextField json;
	JLabel jsonpre;
	JTextField jsonsuf;
	JTextArea output;
	JButton run;
	Thread go;
	public List(){
		//frame
		super("MinecraftBannedList");
		//setLookAndFeel();
		setSize(768,512);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		flo = new FlowLayout();
		playerspre = new JLabel("players.json location:");
		players = new JTextField("https://stats.craft.moe/static/data/players.json",55);
		jsonpre = new JLabel("*.json location:");
		json = new JTextField("https://stats.craft.moe/static/data/",20);
		uuid = new JLabel("(UUID)");
		jsonsuf = new JTextField("/stats.json",23);
		output = new JTextArea("Logs will be displayed here.",15,64);
		run = new JButton("run");
		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		run.addActionListener(this);
		add(playerspre);
		add(players);
		add(jsonpre);
		add(json);
		add(uuid);
		add(jsonsuf);
		add(run);
		JScrollPane outputscroll = new JScrollPane(output,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(outputscroll);
		setLayout(flo);
		setVisible(true);
		
		//Thread
		
		//HTTP GET players.json
		//json get player list
		//HTTP GET %UUID%.json
		//check banned
		//output
		
		//[Thread]
	}
	
	/*
	private void setLookAndFeel(){
		try{
			UIManager.setLookAndFeel(
				"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
			);
		}
		catch(Exception e){
			//ignore
		}
	}
	*/
	
	public void run() {
		final boolean DEBUG=false;
		Thread now = go.currentThread();
		while(now==go) {
			output.setText(null);
			String playersjson = players.getText();
			output.setText("Receiving players.json on "+playersjson+"...");
			String json1 = null;
			try {
				json1 = HttpGet.getplayers(playersjson);
			}
			catch(Exception e) {
				ThrowExceptionAndStop(e);
			}
		if(DEBUG)output.setText(output.getText()+"\n"+json1); //for debugging only
		int playersCount = Jsonresolve.totPlayerCount(json1);
		
		output.setText(output.getText()+"\nTot players count:"+playersCount+"\nTrying to retrieve players.json file...");
		String[] uuidList = new String[playersCount];
		uuidList = Jsonresolve.getUUID(json1,playersCount);
		String[] idList = new String[playersCount];
		idList = Jsonresolve.getID(json1,playersCount);
		boolean[] bannedList = new boolean[playersCount];
		output.setText(output.getText()+"\nretrieve success\nChecking if players are banned...\n(The speed depend on player count and your network. It could take a few minutes.)");
		for(int i=0;i<playersCount;i++) {
			String curJson=HttpGet.get(json.getText(),uuidList[i],jsonsuf.getText());
			//while(curJson=="err") {
			//	curJson=HttpGet.get(json.getText(),uuidList[i],jsonsuf.getText());
			//	output.setText(output.getText()+".");
			//}
			//output.setText(output.getText()+"\nnum:"+i+"cur json:\n"+curJson);
			int tmp=Jsonresolve.getbanned(curJson);
			if(tmp==1) {
				bannedList[i]=true;
			}
			else {
				if(tmp==0)bannedList[i]=false;
				else bannedList[i]=true;
			}
		}
		if(DEBUG) {  //for debugging only
			for(int i=0;i<playersCount;i++) {
				output.setText(output.getText()+"\n"+"ID:"+idList[i]+ " UUID:"+uuidList[i]+" banned:"+bannedList[i]);
			}
		}
		output.setText(output.getText()+"\nBanned list array successfully created");
		output.setText(output.getText()+"\nBanned player(s):\n");
		for(int i=0;i<playersCount;i++) {
			if(bannedList[i])output.setText(output.getText()+idList[i]+" ");
		}
		
		//TODO: File IO
		//output.setText(output.getText()+"\nGenerating banned list file...");
		
		//Do when thread finishes.
		run.setEnabled(true);
		go=null;
		}
	}
	public void actionPerformed(ActionEvent event) {
		run.setEnabled(false);
		if(go==null) {
			go = new Thread(this);
			go.start();
		}
	}
	public void ThrowExceptionAndStop(Exception e) {
		output.setText(output.getText()+"\n\n===Error ccured===\nMessage:\n"+e.getMessage()+"\nThread is stopping...\n");
		go=null;
		run.setEnabled(true);
	}
}