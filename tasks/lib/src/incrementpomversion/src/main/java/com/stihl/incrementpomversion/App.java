package com.stihl.incrementpomversion;

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
import java.util.Arrays;
import java.util.List;

class App {

    /**
     * @param args - Array
     *             argo[0] Absolute Path to project folder
     */
    public static void main(String[] args) {
        final String pathToPom = args[0];
        //final String pathToPom = "/Users/svencarrillocastillo/Documents/Projekte/STIHL/ssc/backend-stihl-release-tools/../backend-stihl-ssc";

        final String absolutePomPath = pathToPom + "/pom.xml";
        run(absolutePomPath);
    }

    private static void run(String pathToPom) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document pom = readPom(pathToPom, factory);

            // Modify POM
            final NodeList versionNode = pom.getDocumentElement().getElementsByTagName("version");
            final String sourceVersion = versionNode.item(0).getTextContent();
            final List<String> semVer = Arrays.asList(sourceVersion.split("\\."));
            if (semVer.size() == 3) {
                int minorVersion = Integer.parseInt(semVer.get(1));
                semVer.set(1, String.valueOf(++minorVersion));
                semVer.set(2, String.valueOf(0) + "-SNAPSHOT");
                String targetVersion = String.join(".", semVer);
                versionNode.item(0).setTextContent(targetVersion);
                writePom(pathToPom, pom);
                System.out.println(targetVersion); // <<< return the new pom version
            } else {
                throw new ArrayIndexOutOfBoundsException("POM Source Version has invalid format; we expect semantic version format X.X.X. Found: " + sourceVersion);
            }
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
