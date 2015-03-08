package graf2nif;

import java.util.HashSet;
import java.util.Set;

public class DefaultAnnotation extends Annotation {
	
	
	protected Set<String> blacklist = new HashSet<String>();
	
	public DefaultAnnotation() {
		blacklist.add("xmlns");
		blacklist.add("xmlns:xsi");
		blacklist.add("xsi:schemaLocation");
		blacklist.add("graf:id");
		blacklist.add("graf:set");
	}
	
	
	public boolean hasFeature(String feature) {
		return (!blacklist.contains(feature)) || super.hasFeature(feature);
	}

}

