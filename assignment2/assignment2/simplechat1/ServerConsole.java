import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import common.ChatIF;

public class ServerConsole implements ChatIF{


    final public static int DEFAULT_PORT = 5555;


    EchoServer server;


    public ServerConsole(int port){
        
     
        
          try 
          {
            server = new EchoServer(port);
            server.listen();
          } 
          catch(Exception e) 
          {
            System.out.println("Error: Can't setup connection!"
                      + " Terminating client.");
            System.exit(1);
          }
        
    }

    public void accept() 
    {
      try
      {
        BufferedReader fromConsole = 
          new BufferedReader(new InputStreamReader(System.in));
        String message;
  
        while (true) 
        {
          message = fromConsole.readLine();
          server.handleMessageFromClientUI(message);

        }
      } 
      catch (Exception ex) 
      {
        System.out.println
          ("Unexpected error while reading from console!");
      }
    }


  public  void display(String message) 
  {
    System.out.println("SERVER MSG> " + message);
  }

  public void setPort(){
  
    
  }

  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    ServerConsole chat= new ServerConsole(DEFAULT_PORT);
    chat.accept();  //Wait for console data
    
    
  
  }
}
