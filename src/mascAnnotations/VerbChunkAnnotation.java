package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class VerbChunkAnnotation extends DefaultAnnotation {
	
	public VerbChunkAnnotation() {
		addType(NameSpaces.nifCoreNS+"Phrase"); 
		addType(NameSpaces.lexinfo+"VerbPhrase");
		addFeature("voice", NameSpaces.lider + "ancVerbChunkVoice", true);
		addFeature("tense", NameSpaces.lider + "ancVerbChunkTense", true);
		addFeature("type", NameSpaces.lider + "ancVerbChunkType", true);
	}

}
