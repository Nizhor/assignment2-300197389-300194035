// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  ChatIF clientUI; 
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    

  public void handleMessageFromServer
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from the server  ");
    this.sendToAllClients(msg);
  }
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      (" stopped listening for connections.");

  }

  protected void serverClosed(){
    quit();
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }

  

  public void handleMessageFromClientUI(String message)
  {
    try
    {
      // sendToServer(message);send to server and all clients
      sendToAllClients("SERVER MSG>"+message);
      String[] messageSplit = message.split(" ");
      if (messageSplit[0].startsWith("#")){
        if(messageSplit[0].equals("#quit")){
          serverStopped();
          serverClosed();
          //
        }
        else if (messageSplit[0].equals("#stop")){
          serverStopped();
        }
        else if (messageSplit[0].equals("#close")){
          serverStopped();
        }
        else if (messageSplit[0].equals("#setport")){
            int newport = Integer.parseInt(messageSplit[1]);
            
            if((this.isListening() == false) && this.getNumberOfClients() < 1){
              setPort(Integer.parseInt( messageSplit[1]));
              System.out.println("Port has been set to " + newport);
            }
            else{
              System.out.println("Port cannot be changed during a server connection. Server must be diconnected");
            }

          // }
        }
          else{
            System.out.println("Error: disconnecting");
           }
        }
        else if (messageSplit[0].equals("#start")){

          if(this.isListening() == false){
            try{
              this.listen();

            }catch (IOException e){
            }
          }
          else{
            System.out.println("Already listening for clients");
          }
          
      }
        else if (messageSplit[0].equals("#getport")){
          clientUI.display(Integer.toString(getPort()));;
        }
        else{
        }
      

          
      }
      
    
    catch(Exception e)
    {
      clientUI.display
        ("Could not send message to client, server has an isue.");
        quit();
        
    }
  }

  public void quit()
  {
    try
    {
      serverStopped();
    }
    catch(Exception e) {}
    System.exit(0);
  }
  public boolean isClosed(){
    return true;
  }

  
  public void clientConnected(ConnectionToClient client){
    System.out.println("client connected");

  }

  public void clientDisconnected(ConnectionToClient client){
    System.out.println("client disconnected");


  }

  synchronized protected void clientException(
    ConnectionToClient client, Throwable exception){
    if(!(exception instanceof NumberFormatException)){
      clientDisconnected(client);
    }


  }
}
//End of EchoServer class
