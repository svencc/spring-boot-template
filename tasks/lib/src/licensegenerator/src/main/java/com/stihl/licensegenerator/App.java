package com.stihl.licensegenerator;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.stream.Collectors;

class App {

    public static void main(String[] args) {
        run();
    }

    @SneakyThrows
    private static void run() {
        final Path path = FileSystems.getDefault().getPath("target", "generated-resources", "licenses.xml");

        final Document document = provideDocument(path);
        final NodeList dependencies = document.getElementsByTagName("dependency");
        final FileWriter writer = provideFileWriter();

        for (int i = 0; i < dependencies.getLength(); i++) {
            final Node dependencyNode = dependencies.item(i);
            final Element dependencyElement = (Element) dependencyNode;
            final String groupId = dependencyElement.getElementsByTagName("groupId").item(0).getTextContent();
            final String artifactId = dependencyElement.getElementsByTagName("artifactId").item(0).getTextContent();
            final String licenseLineHeader = groupId + "/" + artifactId + "\n";

            writer.write(licenseLineHeader);

            final NodeList licenses = dependencyElement.getElementsByTagName("licenses");
            final String licenseText = getLicenseText(licenses);

            writer.write(licenseText);

            writer.write("\n\n");
            System.out.println("-> " + licenseLineHeader);
        }

        writer.flush();
        writer.close();
    }

    @NonNull
    private static Document provideDocument(@NonNull Path path) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(path.toFile());
        return document;
    }

    @NonNull
    private static FileWriter provideFileWriter() throws IOException {
        File generatedLicenseFile = new File("gen-license-file.txt");

        if (generatedLicenseFile.exists()) {
            System.out.println("File already exists. Try to delete");
            generatedLicenseFile.delete();
        }
        generatedLicenseFile.createNewFile();
        System.out.println("File created: " + generatedLicenseFile.getName());

        FileWriter writer = new FileWriter(generatedLicenseFile);
        return writer;
    }

    @NonNull
    private static String getLicenseText(@NonNull NodeList licenseNode) {
        String licenseText = "";

        for (int i = 0; i < licenseNode.getLength(); i++) {
            final Element license = (Element) licenseNode.item(i);
            try {
                final String name = license.getElementsByTagName("name").item(0).getTextContent();
                if (license.getElementsByTagName("url").item(0) != null) {
                    try {
                        URL url = new URL(filterUrl(name, license.getElementsByTagName("url").item(0).getTextContent()));
                        licenseText += read(url) + "\n";
                    } catch (MalformedURLException mue) {
                        licenseText += name + "\n";
                    } catch (Exception e) {
                        licenseText += name + "\n";
                    }
                } else {
                    licenseText += name + "\n";
                }
            } catch (NullPointerException npe) {
                // otherwise element does not have any license defined...
                licenseText += "<-- No license information available! -->" + "\n";
            }
        }

        return licenseText;
    }

    private static String filterUrl(@NonNull String name, @NonNull String url) throws Exception {
        if (url.endsWith(".html")) {
            throw new Exception("We dont want HTML");
        }

        if (url.contentEquals("http://www.apache.org/licenses/LICENSE-2.0.html‎")) {
            return "http://www.apache.org/licenses/LICENSE-2.0.txt";
        } else {
            return url;
        }
    }

    @SneakyThrows
    public static String read(@NonNull URL url) {
        final InputStream inputStream = url.openStream();

        final String response = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        if (response.contains("<HTML") || response.contains("<html")) {
            throw new Exception("We dont want HTML");
        }

        return response;
    }

}
