import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.python.apache.xerces.impl.io.ASCIIReader;
import org.python.core.PyObject;
import org.python.google.common.base.Ascii;
import org.python.util.PythonInterpreter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class TestPythonInterpreter {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        PythonInterpreter pythonInterpreter = new PythonInterpreter();
        pythonInterpreter.execfile("translate.py");

        XWPFDocument docx = new XWPFDocument(OPCPackage.open("test_tutuq_belgi.docx"));

        List<String> list = Arrays.asList("ʻ","‘","`","ʼ","’","'");
        //‘‘‘`

        char a = (char) 145;
//        char a = (char) Ascii. 226 128 152;
//        System.out.println("------------------------------");
//        System.out.println(a);
//        System.out.println("------------------------------");

        //for pages
        List<XWPFParagraph> paragraphs = docx.getParagraphs();
        for(XWPFParagraph xwpfParagraph: paragraphs) {
            List<XWPFRun> runs = xwpfParagraph.getRuns();
            for(XWPFRun run: runs) {
                String r1 = String.valueOf(run);
                r1.replaceAll(""+a+"", "1");
                if(r1.equals(" ") || r1.equals("\n")) {
                    continue;
                }
                Test2.transfer(pythonInterpreter, run, r1);
            }
        }

        docx.write(new FileOutputStream("Result.docx"));
    }
}
