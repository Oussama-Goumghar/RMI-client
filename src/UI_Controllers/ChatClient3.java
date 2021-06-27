package UI_Controllers;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Daragh Walshe 	B00064428
 * RMI Assignment 2		 	April 2015
 *
 */
public class ChatClient3  extends UnicastRemoteObject implements ChatClient3IF {
	/**
	 * c
	 */
	private static final long serialVersionUID = 7468891722773409712L;
	ContactsController chatGUI;
	private String hostName = "127.0.0.1";
	private String serviceName = "GroupChatService";
	private String clientServiceName;
	private String name;
	protected ChatServerIF serverIF;
	protected boolean connectionProblem = false;

	
	/**
	 * class constructor,
	 * note may also use an overloaded constructor with 
	 * a port no passed in argument to super
	 * @throws RemoteException
	 */
	public ChatClient3(ContactsController aChatGUI, String userName) throws RemoteException {
		super();
		this.chatGUI = aChatGUI;
		this.name = userName;
		this.clientServiceName = "ClientListenService_" + userName;
	}

	
	/**
	 * Register our own listening service/interface
	 * lookup the server RMI interface, then send our details
	 * @throws RemoteException
	 */
	public void startClient() throws RemoteException {		
		String[] details = {name, hostName, clientServiceName};	

		try {
			Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
			serverIF = (ChatServerIF)Naming.lookup("rmi://" + hostName + "/" + serviceName);
		} catch (ConnectException  e) {
			chatGUI.showAlert("The server seems to be unavailable\\nPlease try later\"");
			connectionProblem = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			connectionProblem = true;
			me.printStackTrace();
		}
		if(!connectionProblem){
			registerWithServer(details);
		}	
		System.out.println("Client Listen RMI Server is running...\n");
	}


	/**
	 * pass our username, hostname and RMI service name to
	 * the server to register out interest in joining the chat
	 * @param details
	 */
	public void registerWithServer(String[] details) {		
		try{
			serverIF.passIDentity(this.ref);//now redundant ??
			serverIF.registerListener(details);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	//=====================================================================
	/**
	 * Receive a string from the chat server
	 * this is the clients RMI method, which will be used by the server 
	 * to send messages to us
	 */
	@Override
	public void messageFromServer(String message) throws RemoteException {
		System.out.println( message );
		CreateMessages SR = new CreateMessages();
		SR.receiveMessage(message, new SimpleDateFormat("hh:mm a").format(new Date()), chatGUI.getMSGS_CONTAINER(), (event) -> {
			System.out.println("1. Clecked :D");
		});
		//make the gui display the last appended text, ie scroll to bottom
		// chatGUI.textArea.setCaretPosition(chatGUI.textArea.getDocument().getLength());
	}

	/**
	 * A method to update the display of users 
	 * currently connected to the server
	 */
	@Override
	public void updateUserList(String[] currentUsers) throws RemoteException {

		if(currentUsers.length < 2){
			// chatGUI.privateMsgButton.setEnabled(false);
		}
		// chatGUI.userPanel.remove(chatGUI.clientPanel);
		// chatGUI.setClientPanel(currentUsers);
		// chatGUI.clientPanel.repaint();
		// chatGUI.clientPanel.revalidate();
	}

}//end class













