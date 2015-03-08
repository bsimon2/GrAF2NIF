import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.xces.graf.api.IGraph;
import org.xml.sax.SAXException;


public class Main {
	
	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.exit(0);
		}
		
		GrafParser parser = null;		
		IGraph graph = null;		
		String text = null;		
				
		try {
			text = new String(Files.readAllBytes(Paths.get(args[0])));
			parser = new GrafParser();
			graph = parser.parse(args[1]);
			String nif = new GrAF2NIF().convert(graph, text, args[1]);
			Files.write(Paths.get(args[1] + ".ttl"), nif.getBytes());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
	
	}
	
}
