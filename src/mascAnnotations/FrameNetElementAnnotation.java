package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class FrameNetElementAnnotation extends DefaultAnnotation {
	
	public FrameNetElementAnnotation() {
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		setGenericType(true);
		
		addFeature("xmlns", "xmlns", false);
		addFeature("xmlns:xsi", "xmlns:xsi", false);
		addFeature("xsi:schemaLocation", "xsi:schemaLocation", true);
		
		setTypePrefix(NameSpaces.lider + "FN");
		setFeaturePrefix(NameSpaces.lider + "fn");
	}

}
