package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class NounChunkAnnotation extends DefaultAnnotation {
	
	public NounChunkAnnotation() {
		addType(NameSpaces.nifCoreNS+"Phrase"); 
		addType(NameSpaces.lexinfo+"NounPhrase");		
	}

}
