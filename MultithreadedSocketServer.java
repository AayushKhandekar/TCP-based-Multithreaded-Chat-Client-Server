import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.Thread;
import java.net.Socket;

public class MultithreadedSocketServer {
  public static void main(String[] args) throws Exception {
    
    try {

      // Creating Server
      ServerSocket server = new ServerSocket(8888);
      System.out.println("Server Started");

      // Counter is used to keep a track of the total number of clients
      int counter = 0;

      while(true) {

        counter++;

        // Creating an instance of Socket Class and the Server accepts the client connection request
        Socket serverClient = server.accept();  
        System.out.println(" >> " + "Client No :" + counter + " started!");

        // // Sends the request to a separate thread
        ServerClientThread sct = new ServerClientThread(serverClient,counter); 
        sct.start();
      }
    }
    
    catch(Exception e){

      System.out.println(e);
    }
  }
}

class ServerClientThread extends Thread {

  Socket serverClient;
  int clientNo;
  int square;

  ServerClientThread ( Socket inSocket , int counter) {

    serverClient = inSocket;
    clientNo = counter;
  }

  public void run(){

    try{
        
      // Allows primitive data from input stream
      DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
      DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
      String clientMessage="", serverMessage="";

      while ( !clientMessage.equals("bye") ) {
          
        // Unicode Transformation Format used for encoding in unicode
        clientMessage = inStream.readUTF();

        System.out.println("Client " + clientNo + " : " + clientMessage);
        outStream.writeUTF(serverMessage);
        outStream.flush();  
      }

      inStream.close();
      outStream.close();
      serverClient.close();
    }
    
    catch(Exception ex){

        System.out.println(ex);
    }
    
    finally{

        System.out.println("Client " + clientNo + " exit!! ");
    }
  }
}
  