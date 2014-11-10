import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.xces.graf.api.IAnnotation;
import org.xces.graf.api.IFeature;
import org.xces.graf.api.IGraph;
import org.xces.graf.api.ILink;
import org.xces.graf.api.INode;
import org.xces.graf.api.IRegion;
import org.xces.graf.impl.CharacterAnchor;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


public class GrAF2NIF {
	
    private static final String nifCoreNS = "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#";
    private static final String xsd = "http://www.w3.org/2001/XMLSchema#";
    
    private Map<String, String> annotationTypes;
    
    private Model model;    
    
    public GrAF2NIF() {
    	
    	annotationTypes = new HashMap<String,String>();
    	annotationTypes.put("msd", "posTag");
    	annotationTypes.put("base", "stem");
    	
    	model = ModelFactory.createDefaultModel();
    	
    	model.setNsPrefix("nif", nifCoreNS);
    	model.setNsPrefix("xsd", xsd);
    	
    }

	public String convert(IGraph graph, String text, String prefix) {

		model.removeAll();

		Map<String, String> id2uri = new HashMap<String, String>();

		String contextId = prefix + "#char=0,";
		Resource context = model.createResource(contextId);
		context.addProperty(RDF.type, model.createResource(nifCoreNS + "Context"));
		context.addProperty(RDF.type, model.createResource(nifCoreNS + "RFC5147String"));
		context.addLiteral(model.createProperty(nifCoreNS + "beginIndex"), model.createTypedLiteral(0, XSDDatatype.XSDint));
		context.addLiteral(model.createProperty(nifCoreNS + "endIndex"), model.createTypedLiteral(text.length(), XSDDatatype.XSDint));	
		context.addLiteral(model.createProperty(nifCoreNS + "isString"), model.createTypedLiteral(text, XSDDatatype.XSDstring));
		
		for (IRegion r: graph.getRegions()) {

			int a1 = ((CharacterAnchor) r.getAnchor(0)).getOffset().intValue();
			int a2 = ((CharacterAnchor) r.getAnchor(1)).getOffset().intValue();
			String identifier = prefix + "#char=" + a1 + "," + a2;
			Resource res = model.createResource(identifier);
			res.addProperty(RDF.type, model.createResource(nifCoreNS + "Word"));
			res.addProperty(RDF.type, model.createResource(nifCoreNS + "RFC5147String"));
			res.addProperty(model.createProperty(nifCoreNS + "referenceContext"), context);
			res.addLiteral(model.createProperty(nifCoreNS + "beginIndex"), model.createTypedLiteral(a1, XSDDatatype.XSDint));
			res.addLiteral(model.createProperty(nifCoreNS + "endIndex"), model.createTypedLiteral(a2, XSDDatatype.XSDint));			
			String span = text.substring(a1, a2);
			res.addLiteral(model.createProperty(nifCoreNS + "anchorOf"), model.createTypedLiteral(span, XSDDatatype.XSDstring));
			id2uri.put(r.getId(), res.getURI());

		}

		for (INode n: graph.getNodes()) {
			for (ILink l: n.getLinks()) {
				for (IRegion t: l.getRegions()) {					
					if (id2uri.containsKey(t.getId())) {
						for (IAnnotation a: n.annotations()) {							
							for (IFeature f: a.features()) {
								if (annotationTypes.containsKey(f.getName())) {
									Resource r = model.getResource(id2uri.get(t.getId()));
									r.addLiteral(model.createProperty(nifCoreNS + annotationTypes.get(f.getName())), 
											model.createTypedLiteral(f.getValue(), XSDDatatype.XSDstring));
								}
								
								/* This could be better, since it's hardcoded to Penn now,
								 * but its ok for the moment. */
								if("msd".equals(f.getName())) {
									Resource r = model.getResource(id2uri.get(t.getId()));
									Resource oliaTarget = model.createResource(penn + f.getValue());
									r.addProperty(model.createProperty(nifCoreNS + "oliaLink"), oliaTarget);
								}
								
							}
						}
					}
				}
			}
		}
		
		StringWriter out = new StringWriter();
		model.write(out, "TURTLE");
		return out.toString();

	}

}
