package mascAnnotations;
import graf2nif.DefaultAnnotation;
import graf2nif.NameSpaces;





public class LogicalAnnotation extends DefaultAnnotation {
	
	public LogicalAnnotation() {
		addType(NameSpaces.nifCoreNS+"RFC5147String");
		setGenericType(true);
		addFeature("id", NameSpaces.lider + "xcesLogicalSegmentId", true);
		addFeature("who", NameSpaces.lider + "xcesLogicalSegmentWho", true);	
		addFeature("type", NameSpaces.lider + "xcesLogicalSegmentType", true);
		addFeature("feature", NameSpaces.lider + "xcesLogicalSegmentFeature", true);
		
		setTypePrefix(NameSpaces.lider + "XCESLogicalSegment");
		setFeaturePrefix(NameSpaces.lider + "xcesLogicalSegment");
	}
	



}
