package com.automation.core.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFile {
	
	DocumentBuilder dBuilder;
	Document doc;
	
	/*
	 * filePath = config/regions.xml
	*/
	public ReadXMLFile(String filePath) {
		
		try {
			File file = new File(filePath);
			
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	/*
	 * Reads and Fetches all XML nodes as per specified envName and passes all values in format of KEY : VALUE Pair
	 * Example:
	 *  KEY			:	VALUE 
	 * 	Username 	: 	tcom_user
	*/
	public Map<String, String> getEnviornmentNodes(String envName) {
		
		Map<String, String> nodes = new HashMap<String, String>();
		envName = envName.toLowerCase();
		String key;
		String value;
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		try {
			String expression = "//entry[@key='Toyota."+envName+"']/*";
			NodeList nList = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			
			
			for (int count = 0; count < nList.getLength(); count++) {
				Node tempNode = nList.item(count);
				
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
					key = tempNode.getNodeName();
					value = tempNode.getTextContent();
					
					nodes.put(key, value);
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return nodes;
		
	}
	
	/*
	 * Reads and fetches Key value as per specified environment and Key
	*/
	public String getKeyValue(String envName, String keyName) {
		
		String value = null;
		envName = envName.toLowerCase();
		keyName = keyName.toLowerCase();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		try {
			
			String expression = "//entry[@key='Toyota."+envName+"']/"+keyName;
			NodeList nList = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			Node tempNode = nList.item(0);
			value = tempNode.getTextContent();
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return value;
		
	}
	
	/*
	 * Get all Keys as per specified envName
	*/
	public ArrayList<String> getRegionKeys(String envName) {
			
		ArrayList<String> keys = new ArrayList<String>();
		envName = envName.toLowerCase();
		
		try {
			String expression = "//entry[@key='Toyota."+envName+"']/*";
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nList = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			
			for (int count = 0; count < nList.getLength(); count++) {
				Node tempNode = nList.item(count);
				
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
					keys.add(tempNode.getNodeName());
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		
		return keys;
	}
	
	/*
	 * To Evaluate passed Xpath
	*/
	/*private List<String> evaluateXPath(String xpathExpression) throws Exception 
    {
        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();
         
        // Create XPath object
        XPath xpath = xpathFactory.newXPath();
 
        List<String> values = new ArrayList<>();
        try
        {
            // Create XPathExpression object
            XPathExpression expr = xpath.compile(xpathExpression);
             
            // Evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
             
            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getNodeValue());
            }
                 
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return values;
    }*/

}
