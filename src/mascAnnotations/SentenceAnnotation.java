package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class SentenceAnnotation extends DefaultAnnotation {
	
	public SentenceAnnotation() {
		
		addType(NameSpaces.nifCoreNS+"Sentence"); 
		setFeaturePrefix(NameSpaces.lider + "ancSentence");		
		
	}


}
