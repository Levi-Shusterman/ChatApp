import java.io.ObjectOutputStream;

 class ThreadIdentifier {
	ObjectOutputStream outStream;
	String Name;
	
	 ThreadIdentifier(ObjectOutputStream outstream){
		outStream = outstream;
	}
	 
	 void addName(String name){
		 Name = name;
	 }
}
