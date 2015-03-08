package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;




public class PTBAnnotation extends DefaultAnnotation {
	
	public PTBAnnotation() {
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		setGenericType(true);
		
		setTypePrefix(NameSpaces.lider + "PTB");
		setFeaturePrefix(NameSpaces.lider + "ptb");
	}
}
