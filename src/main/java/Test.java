import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws InvalidFormatException, IOException {

        String str = "miz";
        String rep = "MIZ";

        XWPFDocument doc = new XWPFDocument(OPCPackage.open("bir_nimala.docx"));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(str)) {
                        text = text.replace(str, rep);
                        r.setText(text, 0);
                    }
                }
            }
        }

        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains(str)) {
                                text = text.replace(str, rep);
                                r.setText(text,0);
                            }
                        }
                    }
                }
            }
        }


        doc.write(new FileOutputStream("Result.docx"));
    }
}
