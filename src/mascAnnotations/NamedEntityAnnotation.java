package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class NamedEntityAnnotation extends DefaultAnnotation {
	
	public NamedEntityAnnotation() {
		setTypePrefix(NameSpaces.lider + "ANCNamedEntity");
		setFeaturePrefix(NameSpaces.lider + "ancNamedEntity");
	}


}
