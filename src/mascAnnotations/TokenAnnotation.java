package mascAnnotations;

import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class TokenAnnotation extends DefaultAnnotation {
	
	public TokenAnnotation() {
		
		 blacklist.add("string");
		
		 addType(NameSpaces.nifCoreNS+"Word");
		 addFeature("base", NameSpaces.lider + "ancPennTokenLemma",true);
		 addFeature("msd", NameSpaces.lider + "ancPennTokenPosTag", true);
		 addFeature("affix", NameSpaces.lider + "ancPennTokenAffix", true);
		 
	}
	


}
