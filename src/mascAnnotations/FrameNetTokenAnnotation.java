package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class FrameNetTokenAnnotation extends DefaultAnnotation {
	
	public FrameNetTokenAnnotation() {
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		addType(NameSpaces.lider+"FNToken"); 
		addFeature("msd", NameSpaces.lider + "fnMsd", true);		
	}

}
