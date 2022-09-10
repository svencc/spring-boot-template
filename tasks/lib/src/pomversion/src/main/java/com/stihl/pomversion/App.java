package com.stihl.pomversion;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class App {

    /**
     * @param args - Array
     *             argo[0] Absolute Path to project folder
     */
    public static void main(String[] args) {
        final String pathToPom = args[0];

        final String absolutePomPath = pathToPom + "/pom.xml";
        run(absolutePomPath);
    }

    private static void run(String pathToPom) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document pom = readPom(pathToPom, factory);

            // Read POM Version
            final NodeList versionNode = pom.getDocumentElement().getElementsByTagName("version");
            final String pomVersion = versionNode.item(0).getTextContent();
            System.out.print(pomVersion);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private static Document readPom(String pathToPom, DocumentBuilderFactory factory) throws SAXException, IOException, ParserConfigurationException {
        InputStream bomInputStream = new FileInputStream(pathToPom);
        Document pom = factory.newDocumentBuilder()
                .parse(bomInputStream);
        bomInputStream.close();

        return pom;
    }

}
