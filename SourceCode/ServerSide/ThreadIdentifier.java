package ServerSide;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

 class ThreadIdentifier {
	ObjectOutputStream outStream;
	String Name;
	
	 ThreadIdentifier(ObjectOutputStream outstream){
		outStream = outstream;
	}
	 
	 void addName(String name){
		 Name = name;
	 }
	 
	 void sendMessage(Vector<String> message){
		 try {
			outStream.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
