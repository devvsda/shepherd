package com.devsda.platform.shepherd.graphgenerator;

import com.devsda.platform.shepherd.constants.GraphType;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.model.Connection;
import com.devsda.platform.shepherd.model.Graph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class helps to generate @{@link Graph} object from user defined graph xml.
 */
public class DAGGenerator {

    /**
     * This method generate @{@link Graph} object after loading given xml file.
     *
     * @param filePath
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Graph generate(String filePath) throws ParserConfigurationException, SAXException, IOException {

        Graph graph = new Graph();
        List<com.devsda.platform.shepherd.model.Node> nodes = new ArrayList<>();

        File file = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        Node workflowType = doc.getDocumentElement().getElementsByTagName(ShepherdConstants.Graph.TYPE).item(0);
        graph.setGraphType(GraphType.valueOf(workflowType.getTextContent()));

        Node graphRoot = doc.getDocumentElement().getElementsByTagName(ShepherdConstants.Graph.GRAPH).item(0);

        NodeList nodesOfGraph = graphRoot.getChildNodes();

        for (int iterator = 0; iterator < nodesOfGraph.getLength(); iterator++) {

            Node thisDOMNode = nodesOfGraph.item(iterator);

            if (thisDOMNode.getNodeType() == Node.ELEMENT_NODE) {

                com.devsda.platform.shepherd.model.Node thisNode = buildNode((Element) thisDOMNode);
                nodes.add(thisNode);
            }

        }

        graph.setNodes(nodes);
        return graph;
    }

    /**
     * This method generate @{@link Graph} object from srtingified version of xml
     *
     * @param stringifyXML
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Graph generateFromString(String stringifyXML) throws ParserConfigurationException, SAXException, IOException {

        Graph graph = new Graph();
        List<com.devsda.platform.shepherd.model.Node> nodes = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        //Document doc = dBuilder.parse(new StringBufferInputStream(stringifyXML));
        Document doc = dBuilder.parse(new InputSource(new StringReader(stringifyXML)));

        // DocumentBuilder.parse(new StringBufferInputStream(stringifyXML));
        doc.getDocumentElement().normalize();

        Node graphRoot = doc.getDocumentElement().getElementsByTagName(ShepherdConstants.Graph.GRAPH).item(0);

        NodeList nodesOfGraph = graphRoot.getChildNodes();

        for (int iterator = 0; iterator < nodesOfGraph.getLength(); iterator++) {

            Node thisDOMNode = nodesOfGraph.item(iterator);

            if (thisDOMNode.getNodeType() == Node.ELEMENT_NODE) {

                com.devsda.platform.shepherd.model.Node thisNode = buildNode((Element) thisDOMNode);
                nodes.add(thisNode);
            }

        }

        graph.setNodes(nodes);
        return graph;
    }

    /**
     * This method builds @{@link com.devsda.platform.shepherd.model.Node} by extracting data from corresponding xml field.
     *
     * @param element
     * @return
     */
    private com.devsda.platform.shepherd.model.Node buildNode(Element element) {

        com.devsda.platform.shepherd.model.Node node = new com.devsda.platform.shepherd.model.Node();

        NodeList nodeInformation = element.getChildNodes();

        for (int iterator1 = 0; iterator1 < nodeInformation.getLength(); iterator1++) {

            Node thisNodeInformation = nodeInformation.item(iterator1);

            if (thisNodeInformation.getNodeType() == Node.ELEMENT_NODE) {

                if (ShepherdConstants.Graph.CONNECTIONS.equals(thisNodeInformation.getNodeName())) {
                    List<Connection> connections = buildConnections((Element) thisNodeInformation);
                    node.setConnections(connections);
                } else if (ShepherdConstants.Graph.NAME.equals(thisNodeInformation.getNodeName())) {
                    node.setName(thisNodeInformation.getTextContent());
                } else if (ShepherdConstants.Graph.OWNER.equals(thisNodeInformation.getNodeName())) {
                    node.setOwner(thisNodeInformation.getTextContent());
                }
            }
        }

        return node;
    }

    /**
     * This method builds @{@link Connection} by iterating all child nodes.
     *
     * @param element
     * @return
     */
    private List<Connection> buildConnections(Element element) {

        List<Connection> connections = new ArrayList<>();

        NodeList connectionsDOMDetails = element.getChildNodes();

        for (int iter = 0; iter < connectionsDOMDetails.getLength(); iter++) {

            Node thisDOMConnection = connectionsDOMDetails.item(iter);

            if (thisDOMConnection.getNodeType() == Node.ELEMENT_NODE) {
                Connection connection = buildConnection((Element) thisDOMConnection);
                connections.add(connection);
            }
        }
        return connections;
    }

    /**
     * This method builds @{@link Connection} object of every edge between parent and child node
     *
     * @param element
     * @return
     */
    private Connection buildConnection(Element element) {

        Connection connection = new Connection();
        NodeList connectionDOMDetails = element.getChildNodes();

        for (int iter = 0; iter < connectionDOMDetails.getLength(); iter++) {

            Node thisDOMConnection = connectionDOMDetails.item(iter);

            if (thisDOMConnection.getNodeType() == Node.ELEMENT_NODE) {

                Element thisElement = (Element) thisDOMConnection;

                if (ShepherdConstants.Graph.EDGE.equals(thisDOMConnection.getNodeName())) {
                    connection.setEdgeName(thisElement.getTextContent());

                } else if (ShepherdConstants.Graph.NODE.equals(thisDOMConnection.getNodeName())) {
                    connection.setNodeName(thisElement.getTextContent());
                }
            }
        }

        return connection;
    }
}
