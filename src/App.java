import java.io.IOException;

public class App {
	public static void main(String []args ) throws IOException {
		MFrame frame=new MFrame();
//		frame.fun();
		ReadingFiles files=new ReadingFiles();
		files.Reads();
	}

}
