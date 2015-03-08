package mascAnnotations;
import graf2nif.Annotations;





public class MASCAnnotations extends Annotations {	
	
	public MASCAnnotations() {
		
			LogicalAnnotation logical = new LogicalAnnotation();		
			addAnnotation("xces", "p", logical);
			addAnnotation("xces", "cesDoc", logical);
			addAnnotation("xces", "head", logical);			
			
			addAnnotation("bn", "sense", new BabelNetAnnotation());			
			addAnnotation("anc", "tok", new TokenAnnotation());
			addAnnotation("anc", "nchunk", new NounChunkAnnotation());
			addAnnotation("anc", "vchunk", new VerbChunkAnnotation());			
			addAnnotation("ba", new BabelNetAnnotation());
			addAnnotation("mpqa", new OpinionSubjectivityAnnotation());
			addAnnotation("logical", new LogicalAnnotation());
			addAnnotation("mpqa", new OpinionSubjectivityAnnotation());
			addAnnotation("event", new EventCoreferenceAnnotation());
			addAnnotation("cb", new CommitedBeliefAnnotation());
			addAnnotation("nc", new NounChunkAnnotation());
			addAnnotation("vc", new VerbChunkAnnotation());
			addAnnotation("s", new SentenceAnnotation());
			addAnnotation("ne", new NamedEntityAnnotation());
			addAnnotation("penn", new TokenAnnotation());
			addAnnotation("ptb", new PTBAnnotation());
			addAnnotation("ptbtok", new PTBTokenAnnotation());
			addAnnotation("fn", new FrameNetElementAnnotation());
			addAnnotation("fntok", new FrameNetTokenAnnotation());			
			
	}
}
