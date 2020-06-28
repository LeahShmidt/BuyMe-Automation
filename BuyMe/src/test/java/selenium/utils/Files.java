package selenium.utils;


import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Files {

    public  String readFile(String tagName) throws IOException, ParserConfigurationException, org.xml.sax.SAXException {


        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\lshmidt\\IdeaProjects\\BuyMe\\src\\test\\resources\\params.xml"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            System.out.println(everything);
        } finally

        {
            br.close();

        }
        String param = getParamByTagName(tagName) ;
//        driver.get(URL);
//
//        driver = new ChromeDriver();

        return param;

    }

    private  String getParamByTagName(String tag) throws ParserConfigurationException, IOException, org.xml.sax.SAXException {

        File configXmlFile = new File("C:\\Users\\lshmidt\\IdeaProjects\\BuyMe\\src\\test\\resources\\params.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configXmlFile);

        if (doc != null) {
            doc.getDocumentElement().normalize();
        }
        assert doc != null;
        return doc.getElementsByTagName(tag).item(0).getTextContent();
    }

}
