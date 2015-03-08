package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;


public class CommitedBeliefAnnotation extends DefaultAnnotation {
	public CommitedBeliefAnnotation() {
		
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		setGenericType(true);
		
		setTypePrefix(NameSpaces.lider + "CB");
		setFeaturePrefix(NameSpaces.lider + "cb");
		
	}

}
