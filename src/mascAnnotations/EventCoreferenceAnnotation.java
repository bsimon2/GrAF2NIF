package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;


public class EventCoreferenceAnnotation extends DefaultAnnotation {
	
	public EventCoreferenceAnnotation() {
		
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		setGenericType(true);
		
		setTypePrefix(NameSpaces.lider + "Event");
		setFeaturePrefix(NameSpaces.lider + "event");
	}
	
}
