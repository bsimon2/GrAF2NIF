import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xces.graf.api.IAnnotation;
import org.xces.graf.api.IGraph;
import org.xces.graf.api.ILink;
import org.xces.graf.api.INode;
import org.xces.graf.api.IRegion;
import org.xces.graf.impl.Factory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class GrafParser extends DefaultHandler {

	private IGraph graph;
	
	private Map<String, IRegion> regions;
	private Map<String, INode> nodes;
	private Map<String, IAnnotation> annotations;
	
	private String currentAnnotation = null;
	private String currentNode = null;
	
	private int annotationCounter = 0;
	
    public void startDocument() throws SAXException {
        regions = new HashMap<String, IRegion>();
        nodes = new HashMap<String, INode>();
        annotations = new HashMap<String, IAnnotation>();
        
        graph = Factory.newGraph();
    }

    public void endDocument() throws SAXException {

    }
    
    public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
    	
    	if (qName.equals("annotationsSpace")) {
    		
    		String name = attributes.getValue("name");
    		String type = attributes.getValue("type");
    		if (name != null && type != null) {
    			graph.addAnnotationSpace(name, type);
    		}
    		
    	} else if (qName.equals("region")) {
    		
    		String rId = "";
    		Long[] anchors = new Long[2];
    		
    		if (attributes.getValue("xml:id") != null) {
    			rId = attributes.getValue("xml:id");
    		} else {
    			
    		}
    		
    		String anchorsString = attributes.getValue("anchors");
    		if (anchorsString != null && anchorsString.split(" ").length == 2) {
    			anchors[0] = Long.valueOf(anchorsString.split(" ")[0]);
    			anchors[1] = Long.valueOf(anchorsString.split(" ")[1]);
    		}
    		
    		IRegion region = Factory.newRegion(rId, anchors[0], anchors[1]);
    		regions.put(rId, region);
    		graph.addRegion(region);
    		
    	} else if (qName.equals("node")) {
    		
    		String nId = "";
    		if (attributes.getValue("xml:id") != null) {
    			nId = attributes.getValue("xml:id");
    			currentNode = nId;
    		} else {
    			
    		}
    		
    		INode node = Factory.newNode(nId);
    		nodes.put(nId, node);
    		graph.addNode(node);
    		
    	} else if (qName.equals("link")) {
    		
    		String[] targets = attributes.getValue("targets").split(" ");
    		for (int i = 0; i < targets.length; i++) {
    			if (currentNode != null && targets[i] != null && regions.containsKey(targets[i])) {
    				ILink link = Factory.newLink();
    				link.addTarget(regions.get(targets[i]));
    				nodes.get(currentNode).addLink(link);
    			}
    		}
    		
    		    		
    	} else if (qName.equals("a")) {
    		String label, ref, annotationSpace, aId;
    		
    		if (attributes.getValue("label") != null ) {
    			label = attributes.getValue("label");
    		} else {
    			label = "";
    		}
    			
    		if (attributes.getValue("ref") != null) {
    			ref = attributes.getValue("ref");
    		} else {
    			ref = "";
    		}
    		
    		if (attributes.getValue("as") != null) {
    			annotationSpace = attributes.getValue("as");
    		} else {
    			annotationSpace = "";
    		}
    		
    		if (attributes.getValue("xml:id") != null) {
    			aId = attributes.getValue("xml:id");
    		} else {
    			aId = "a" + annotationCounter++;
    		}
    		
    		
    		IAnnotation annotation = Factory.newAnnotation(aId, label);
    		if (graph.getAnnotationSpace(annotationSpace) != null) {    			
    			annotation.setAnnotationSpace(Factory.newAnnotationSpace(annotationSpace, graph.getAnnotationSpace(annotationSpace).getType()));
    		}    		
    		if (nodes.containsKey(ref)) {
    			nodes.get(ref).addAnnotation(annotation);
    		}
    		
    		currentAnnotation = aId;
    		annotations.put(aId, annotation);
    			
    	} else if (qName.equals("f")) {
    		if (currentAnnotation != null) {
    			String name = attributes.getValue("name");
    			String value = attributes.getValue("value");
    			if (name != null && value != null) {
    				annotations.get(currentAnnotation).addFeature(name, value);
    				//graph.addFeature(name, value);
    			}
    		}
    	}
    		
    } 
   
    
    public void endElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
    	if (localName.equals("node")) {
    		currentNode = null;
    	} else if (localName.equals("a")) {
    		currentAnnotation = null;
    	}
    }
    
    public IGraph parse(String path) throws SAXException, IOException {
    	
    	FileReader fileReader = new FileReader(path);
        InputSource inputSource = new InputSource(fileReader);
    	XMLReader xmlReader = XMLReaderFactory.createXMLReader();
    	xmlReader.setContentHandler(this);
    	xmlReader.parse(inputSource);
    	
    	return graph;
    }
    
}