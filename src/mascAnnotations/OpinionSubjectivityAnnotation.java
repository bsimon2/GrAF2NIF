package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;


public class OpinionSubjectivityAnnotation extends DefaultAnnotation {
	
	public OpinionSubjectivityAnnotation() {
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		setGenericType(true);
		
		setTypePrefix(NameSpaces.lider + "MPQA");
		setFeaturePrefix(NameSpaces.lider + "mpqa");
	}
	
}
