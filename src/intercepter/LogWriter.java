package intercepter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyu on 12/9/16.
 */
public class LogWriter {

    /*
     <?xml version="1.0" encoding="UTF-8"?>
     <log>
        <action>
            <name>login</name>
            <s-time>yyyy-mm-dd hh:mm:ss</s-time>
            <e-time>yyyy-mm-dd hh:mm:ss</e-time>
            <result>success</result>
        </action>
     </log>
     */
    public void log(Map<String, String> args) {

        try {
            File file = new File("output.xml");
            if (!file.exists()) {
                Document doc = DocumentHelper.createDocument();
                Element root = doc.addElement("log");
                write(doc);
                System.out.println(LogWriter.class.getName() + "---new log.xml file created----");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read("output.xml");
            Element root = document.getRootElement();
            Element action;
            if (args.get("flag").equals("before")) {
                action = root.addElement("action");
            } else {
                List<Element> actions = root.elements("action");
                action = actions.get(actions.size() - 1);
            }


            if (args.containsKey("name")) {
                action.addElement("name").addText(args.get("name"));
                System.out.println(LogWriter.class.getName() + "---name:" + args.get("name"));
            }
            if (args.containsKey("s-time")) {
                action.addElement("s-time").addText(args.get("s-time"));
                System.out.println(LogWriter.class.getName() + "---s-time:" + args.get("s-time"));
            }
            if (args.containsKey("e-time")) {
                action.addElement("e-time").addText(args.get("e-time"));
                System.out.println(LogWriter.class.getName() + "---e-time:" + args.get("e-time"));
            }
            if (args.containsKey("result")) {
                action.addElement("result").addText(args.get("result"));
                System.out.println(LogWriter.class.getName() + "---result:" + args.get("result"));
            }
            write(document);
            System.out.println(LogWriter.class.getName() + "---information added----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(Document document) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileOutputStream("output.xml"), format);
        writer.write(document);
        writer.close();

    }
}
