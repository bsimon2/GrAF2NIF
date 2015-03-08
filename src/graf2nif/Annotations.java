package graf2nif;
import java.util.HashMap;
import java.util.Map;


public class Annotations {
	
	private Map<String, Annotation> aspace = new HashMap<String, Annotation>();
	protected Map<String, Annotation> annotationById = new HashMap<String, Annotation>();
	private DefaultAnnotation def = new DefaultAnnotation();
	
	public void addAnnotation(String as, String label, Annotation annotation) {
		aspace.put(as+label, annotation);
	}
	
	public void addAnnotation(String id, Annotation annotation) {
		annotationById.put(id, annotation);
	}
	
	public Annotation getAnnotation(String as, String label, String id) {
		if (aspace.containsKey(as+label)) return aspace.get(as+label);
		if (annotationById.containsKey(id)) return annotationById.get(id);
		return def;		
	}

}
