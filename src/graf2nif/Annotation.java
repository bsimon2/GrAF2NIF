package graf2nif;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Annotation {
	
	protected Set<String> types = new HashSet<String>();
	
	protected boolean genericType = false;
	
	protected Map<String, String> fprops = new HashMap<String, String>();
	protected Map<String, Boolean> flits = new HashMap<String, Boolean>();
	protected String typePrefix = "";
	protected String featurePrefix = "";
	
	
	public void setGenericType(boolean genericType) {
		this.genericType = genericType;
	}
	
	public boolean hasGenericType() {
		return genericType;
	}
	
	public String getGenericType(String label) {
		return typePrefix+Character.toString(label.charAt(0)).toUpperCase()+label.substring(1).replaceAll("\\s+", "_");
	}

	public Iterable<String> getTypes() {
		return types;
	}
	public void addType(String objectURI) {
		types.add(objectURI);
	}
	
	public void setTypePrefix(String typePrefix) {
		this.typePrefix = typePrefix;
	}
	
	public void setFeaturePrefix(String featurePrefix) {
		this.featurePrefix = featurePrefix;
	}

	public void addFeature(String name, String propertyURI, boolean literal) {
		if (propertyURI != null) fprops.put(name, propertyURI);
		flits.put(name, literal);
	}

	public boolean hasFeature(String feature) {
		return fprops.containsKey(feature);
	}

	public boolean isLiteral(String feature) {
		if (flits.containsKey(feature)) return flits.get(feature);		
		return true;
	}

	public String getFeatureProperty(String feature) {
		if(fprops.containsKey(feature)) return fprops.get(feature);
		return featurePrefix+Character.toString(feature.charAt(0)).toUpperCase()+feature.substring(1).replaceAll("\\s+", "_");
	}	
	
	public String getFeatureObject(String feature, String value) {
		return value;
	}

}
