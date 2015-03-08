package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class PTBTokenAnnotation extends DefaultAnnotation {

	public PTBTokenAnnotation() {
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		addType(NameSpaces.lider+"PTBToken"); 
		addFeature("msd", NameSpaces.lider + "ptbMsd", true);		
	}
}
