package graf2nif;
import java.io.StringWriter;

import mascAnnotations.MASCAnnotations;

import org.xces.graf.api.IAnnotation;
import org.xces.graf.api.IEdge;
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

	private Model model;    

	public GrAF2NIF() {

		model = ModelFactory.createDefaultModel();

		model.setNsPrefix("nif", NameSpaces.nifCoreNS);
		model.setNsPrefix("xsd", NameSpaces.xsd);
		model.setNsPrefix("lider", NameSpaces.lider);
		model.setNsPrefix("rdfs", NameSpaces.rdfs);
		model.setNsPrefix("lexinfo", "http://www.lexinfo.net/ontology/2.0/lexinfo#");

	}

	public String convert(IGraph graph, String text, String prefix) {

		model.removeAll();

		String contextId = prefix + "#char=0,";		
		addResource(NameSpaces.nifCoreNS + "Context", contextId, "", text, null, 0, text.length());

		MASCAnnotations masc = new MASCAnnotations();

		for (INode n: graph.getNodes()) {
			
			if (n.outDegree() == 0) {
				for (ILink l: n.getLinks()) {
					for (IRegion r: l.getRegions()) {

						int start = ((CharacterAnchor) r.getStart()).getOffset().intValue();
						int end = ((CharacterAnchor) r.getEnd()).getOffset().intValue();
						end = Math.min(end, text.length()-1);
						
						String identifier = prefix + "#char=" + start + "," + end;
						String span = text.substring(start, end);
						Resource res = addResource(NameSpaces.nifCoreNS + "RFC5147String", identifier, contextId, span, prefix, start, end);
						
						addAnnotations(masc, n, res);

					}
				} 
				
			} else {

				Resource phrase = null;
				int pstart = text.length();
				int pend = 0;
				for (IEdge e: n.getOutEdges()) {
					if (e.getTo().getLinks().size() == 0) continue;
					for (ILink to: e.getTo().getLinks()) {
						for (IRegion target: to.getRegions()) {	
							int a1 = ((CharacterAnchor) target.getStart()).getOffset().intValue();
							int a2 = ((CharacterAnchor) target.getEnd()).getOffset().intValue();							
							if (a1 < pstart) pstart = a1;
							if (a2 > pend) pend = a2;		
						}
					}
				}
				
				if (!(pstart == text.length() || pend == 0)) {
					String chunk = text.substring(pstart, pend);					
					phrase = addResource(NameSpaces.nifCoreNS + "RFC5147String", prefix + "#char=" + pstart + "," + pend, contextId, chunk, prefix, pstart, pend);					
				}
				
				if (phrase != null)	addAnnotations(masc, n, phrase);
				
			}
		}


		StringWriter out = new StringWriter();
		model.write(out, "TURTLE");
		return out.toString();

	}
	
	private Resource addResource(String type, String prefix, String context, String text, String label, int start, int end) {
		
		Resource r = model.createResource(prefix);							
		r.addProperty(RDF.type, model.createResource(NameSpaces.nifCoreNS + "RFC5147String"));
		r.addLiteral(model.createProperty(NameSpaces.nifCoreNS + "beginIndex"), model.createTypedLiteral(start, XSDDatatype.XSDint));
		r.addLiteral(model.createProperty(NameSpaces.nifCoreNS + "endIndex"), model.createTypedLiteral(end, XSDDatatype.XSDint));	
		if (label != null) r.addLiteral(model.createProperty(NameSpaces.rdfs + "label"), model.createTypedLiteral(label, XSDDatatype.XSDstring));
		if (type.equals(NameSpaces.nifCoreNS + "Context")) {
			r.addProperty(RDF.type, model.createResource(NameSpaces.nifCoreNS + "Context"));
			r.addLiteral(model.createProperty(NameSpaces.nifCoreNS + "isString"), model.createTypedLiteral(text, XSDDatatype.XSDstring));
		} else {
			r.addProperty(model.createProperty(NameSpaces.nifCoreNS + "referenceContext"), context);
			r.addLiteral(model.createProperty(NameSpaces.nifCoreNS + "anchorOf"), model.createTypedLiteral(text, XSDDatatype.XSDstring));
		}	
		
		return r;
		
	}
	
	private void addAnnotations(Annotations annotations, INode n, Resource r) {
		
		for (IAnnotation a: n.annotations()) {

			Annotation an = annotations.getAnnotation(a.getAnnotationSpace().getName(), a.getLabel(), a.getId().split("-")[0]);			
			if (an == null)	continue;		
			
			for (String obj: an.getTypes())	r.addProperty(model.createProperty(NameSpaces.rdf+"type"), model.createResource(obj));
			
			if (an.hasGenericType()) r.addProperty(model.createProperty(NameSpaces.rdf+"type"), model.createResource(an.getGenericType(a.getLabel())));
			
			for (IFeature f: a.features()) {
				if(an.hasFeature(f.getName())) {
					if(an.isLiteral(f.getName())) {
						r.addLiteral(model.createProperty(an.getFeatureProperty(f.getName())), model.createTypedLiteral(an.getFeatureObject(f.getName(), (String)f.getValue()), XSDDatatype.XSDstring));
					} else {
						r.addProperty(model.createProperty(an.getFeatureProperty(f.getName())), model.createResource(an.getFeatureObject(f.getName(), (String)f.getValue())));
					}
				}
			}
		}
		
	}

}

