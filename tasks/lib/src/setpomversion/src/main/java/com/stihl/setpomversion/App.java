package com.stihl.setpomversion;

import org.w3c.dom.Document;
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
     *  argo[0] major version
     *  argo[1] minor version
     *  argo[2] Absolute Path to project folder
     */
    public static void main(String[] args) {
        final String majorTargetVersion = args[0];
        final String minorTargetVersion = args[1];
        final String pathToPom = args[2];

        final String absolutePomPath = pathToPom + "/pom.xml";
        run(majorTargetVersion, minorTargetVersion, absolutePomPath);
    }

    private static void run(String majorTargetVersion, String minorTargetVersion, String pathToPom) {
        // Prepare release version
        final String releaseVersion = majorTargetVersion + "." + minorTargetVersion + ".0";
        System.out.println("Set to POM version: " + releaseVersion);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document pom = readPom(pathToPom, factory);

            // Modify POM
            final NodeList versionNode = pom.getDocumentElement().getElementsByTagName("version");
            versionNode.item(0).setTextContent(releaseVersion);

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
