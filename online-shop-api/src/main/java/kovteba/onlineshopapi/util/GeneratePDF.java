package kovteba.onlineshopapi.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import kovteba.onlineshopapi.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class GeneratePDF {

    private static Long totalPrice = 0L;

    @Value("${online.out.storage.pdf}")
    public String out;

    public String generateDPF(Map<ProductEntity, String> basket, String email){
        Document document = new Document();
        String fileName = email + "Receipt.pdf";
        try {
            try {
                PdfWriter.getInstance(document, new FileOutputStream(out + fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.open();
        PdfPTable table = new PdfPTable(6);
        addTableHeader(table);
        addRows(table, basket);
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        PdfPTable tableToTalPrice = new PdfPTable(6);
        addTotalPrice(tableToTalPrice);
        try {
            document.add(tableToTalPrice);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        return fileName;
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("#", "Title", "EAN", "Price", "Count", "Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table, Map<ProductEntity, String> basket) {
        int index = 0;
        for (Map.Entry<ProductEntity, String> entry : basket.entrySet()){
            ProductEntity productEntity = entry.getKey();
            table.addCell(String.valueOf(++index));
            table.addCell(productEntity.getBrand() + " " + productEntity.getModel());
            table.addCell(productEntity.getEan());
            table.addCell(String.valueOf(productEntity.getPrice()));
            table.addCell(entry.getValue());
            Long price = Long.parseLong(entry.getValue()) * productEntity.getPrice();
            totalPrice += price;
            table.addCell(String.valueOf(price));
        }
    }

    private static void addTotalPrice(PdfPTable table){
        Stream.of("Total Price", String.valueOf(totalPrice))
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setColspan(5);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

}
