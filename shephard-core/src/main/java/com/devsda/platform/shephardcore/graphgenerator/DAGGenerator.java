package com.devsda.platform.shephardcore.graphgenerator;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.model.Connection;
import com.devsda.platform.shephardcore.model.Graph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;

public class DAGGenerator {

    public Graph generate(String filePath) throws ParserConfigurationException, SAXException, IOException {

        Graph graph = new Graph();
        List<com.devsda.platform.shephardcore.model.Node> nodes = new ArrayList<>();

        File file = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        Node graphRoot = doc.getDocumentElement().getElementsByTagName(ShephardConstants.Graph.GRAPH).item(0);

        NodeList nodesOfGraph = graphRoot.getChildNodes();

        for (int iterator = 0; iterator < nodesOfGraph.getLength(); iterator++) {

            Node thisDOMNode = nodesOfGraph.item(iterator);

            if(thisDOMNode.getNodeType() == Node.ELEMENT_NODE) {

                com.devsda.platform.shephardcore.model.Node thisNode = buildNode((Element) thisDOMNode);
                nodes.add(thisNode);
            }

        }

        graph.setNodes(nodes);
        return graph;
    }

    public Graph generateFromString(String stringifyXML) throws ParserConfigurationException, SAXException, IOException {

        Graph graph = new Graph();
        List<com.devsda.platform.shephardcore.model.Node> nodes = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        //Document doc = dBuilder.parse(new StringBufferInputStream(stringifyXML));
        Document doc = dBuilder.parse(new InputSource(new StringReader(stringifyXML)));

        // DocumentBuilder.parse(new StringBufferInputStream(stringifyXML));
        doc.getDocumentElement().normalize();

        Node graphRoot = doc.getDocumentElement().getElementsByTagName(ShephardConstants.Graph.GRAPH).item(0);

        NodeList nodesOfGraph = graphRoot.getChildNodes();

        for (int iterator = 0; iterator < nodesOfGraph.getLength(); iterator++) {

            Node thisDOMNode = nodesOfGraph.item(iterator);

            if(thisDOMNode.getNodeType() == Node.ELEMENT_NODE) {

                com.devsda.platform.shephardcore.model.Node thisNode = buildNode((Element) thisDOMNode);
                nodes.add(thisNode);
            }

        }

        graph.setNodes(nodes);
        return graph;
    }

    private com.devsda.platform.shephardcore.model.Node buildNode(Element element) {

        com.devsda.platform.shephardcore.model.Node node = new com.devsda.platform.shephardcore.model.Node();

        NodeList nodeInformation = element.getChildNodes();

        for(int iterator1 = 0; iterator1 < nodeInformation.getLength(); iterator1++) {

            Node thisNodeInformation = nodeInformation.item(iterator1);

            if(thisNodeInformation.getNodeType() == Node.ELEMENT_NODE) {

                if(ShephardConstants.Graph.CONNECTIONS.equals(thisNodeInformation.getNodeName())) {
                    List<Connection> connections = buildConnections((Element) thisNodeInformation);
                    node.setConnections(connections);
                } else if(ShephardConstants.Graph.NAME.equals(thisNodeInformation.getNodeName())) {
                    node.setName(thisNodeInformation.getTextContent());
                } else if(ShephardConstants.Graph.HOSTNAME.equals(thisNodeInformation.getNodeName())) {
                    node.setOwner(thisNodeInformation.getTextContent());
                }
            }
        }

        return node;
    }

    private List<Connection> buildConnections(Element element) {

        List<Connection> connections = new ArrayList<>();

        NodeList connectionsDOMDetails = element.getChildNodes();

        for(int iter = 0; iter < connectionsDOMDetails.getLength(); iter++) {

            Node thisDOMConnection = connectionsDOMDetails.item(iter);

            if(thisDOMConnection.getNodeType() == Node.ELEMENT_NODE) {
                Connection connection = buildConnection((Element) thisDOMConnection);
                connections.add(connection);
            }

        }

        return connections;
    }

    private Connection buildConnection(Element element) {

        Connection connection = new Connection();

        NodeList connectionDOMDetails = element.getChildNodes();

        for(int iter = 0; iter < connectionDOMDetails.getLength(); iter++) {

            Node thisDOMConnection = connectionDOMDetails.item(iter);

            if(thisDOMConnection.getNodeType() == Node.ELEMENT_NODE) {

                Element thisElement = (Element) thisDOMConnection;

                if(ShephardConstants.Graph.EDGE.equals(thisDOMConnection.getNodeName())) {
                    connection.setEdgeName(thisElement.getTextContent());
                } else if (ShephardConstants.Graph.NODE.equals(thisDOMConnection.getNodeName())) {
                    connection.setNodeName(thisElement.getTextContent());
                }
            }
        }

        return connection;
    }
}
