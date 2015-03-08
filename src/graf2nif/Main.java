package graf2nif;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;

import org.xces.graf.api.GrafException;
import org.xces.graf.api.IAnnotationSpace;
import org.xces.graf.api.IGraph;
import org.xces.graf.io.GrafLoader;
import org.xces.graf.io.dom.ResourceHeader;
import org.xml.sax.SAXException;


public class Main {
	
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.exit(0);
		}
		System.out.println(args[1]);
		IGraph graph = null;	
		
		try {
			
			GrafLoader loader = new org.xces.graf.io.GrafLoader();			
			loader.setTypes(new String[] {"f.seg", "f.penn", "f.s", "f.nc", "f.vc", "f.bn", "f.logical", "f.mpqa", "f.ne", "f.cb", "f.event", "f.ptb", "f.ptbtok", "f.fn", "f.framenet", "f.fntok"});	
			ResourceHeader header = new org.xces.graf.io.dom.ResourceHeader(new File (args[0]));
						
			IAnnotationSpace[] spaces = header.getAnnotationSpaces().toArray(new IAnnotationSpace[header.getAnnotationSpaces().size()]);
			loader.addAnnotationSpaces(spaces);
			graph = loader.load(args[1]);
			String nif = new GrAF2NIF().convert(graph, graph.getContent().toString(), args[3]);
			
			File dir = new File(new File(args[2]).getParent());
			dir.mkdirs();
			Files.write(Paths.get(args[2]), nif.getBytes());
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (GrafException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
}

