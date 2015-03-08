package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;



public class BabelNetAnnotation extends DefaultAnnotation {
	
	public BabelNetAnnotation() {
		addFeature("synset", NameSpaces.lider + "bnsynset", false);
		addFeature("name", NameSpaces.lider + "bnName", true);	
		addFeature("type", NameSpaces.lider + "bnType", true);
	}
	
	public String getFeatureObject(String feature, String value) {
		if (feature.equals("synset")) {
			return "http://babelnet.org/2.0/page/s"+value.split(":")[1];
		}
		return value;
	}
	

}
