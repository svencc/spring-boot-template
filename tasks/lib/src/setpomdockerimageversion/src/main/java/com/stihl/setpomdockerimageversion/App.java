package com.stihl.setpomdockerimageversion;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

class App {

    /**
     * @param args - Array
     *             argo[0] docker image version
     *             argo[1] Absolute Path to project folder
     */
    public static void main(String[] args) {
        final String dockerImageVersion = args[0];
        final String pathToPom = args[1];

        final String absolutePomPath = pathToPom + "/pom.xml";
        run(dockerImageVersion, absolutePomPath);
    }

    private static void run(String dockerImageVersion, String pathToPom) {
        // Prepare release version
        System.out.println("Set to docker image version: " + dockerImageVersion);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document pom = readPom(pathToPom, factory);

            // Modify POM
            final NodeList propertiesNode = pom.getDocumentElement().getElementsByTagName("properties");
            for (int i = 0; i < propertiesNode.item(0).getChildNodes().getLength(); i++) {
                final Node dockerImageVersionNodeCandidate = propertiesNode.item(0).getChildNodes().item(i);
                if (dockerImageVersionNodeCandidate.getNodeName().equalsIgnoreCase("docker.base-image.tag")) {
                    dockerImageVersionNodeCandidate.setTextContent(dockerImageVersion);
                }
            }

            writePom(pathToPom, pom);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void writePom(String pathToPom, Document pom) throws IOException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        FileWriter writer = new FileWriter(new File(pathToPom));
        StreamResult result = new StreamResult(writer);
        DOMSource source = new DOMSource(pom);
        transformer.transform(source, result);
    }

    private static Document readPom(String pathToPom, DocumentBuilderFactory factory) throws SAXException, IOException, ParserConfigurationException {
        InputStream bomInputStream = new FileInputStream(pathToPom);
        Document pom = factory.newDocumentBuilder()
                .parse(bomInputStream);
        bomInputStream.close();

        return pom;
    }

}
