package com.example.e3.mybills;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Created by EBRHEEM on 7/10/2017.
 */

public class PdfCreator extends AppCompatActivity {
    int direction;
    Font font;
    String f = "";
    private BaseFont bfBold;
    ArrayList<String> dataHeader = new ArrayList();
    float[] columnWidths;
    Font font_h;
    private BaseFont bfBold_h;
    double d_Value = 0.0D;
    double Disc_bill = 0.0D;
    double totalAmount = 0.0D;
    ArrayList<ArrayList<String>> listdata = new ArrayList();
    ArrayList<String> localArrayList = new ArrayList();
    public void initializeFonts() {
        try {        this.f = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/tahoma.ttf");
            this.font = FontFactory.getFont(this.f, "Identity-H", true, 12.0F, 1, BaseColor.BLACK);
            BaseFont.createFont().correctArabicAdvance();
            this.bfBold = this.font.getBaseFont();
            this.font_h = FontFactory.getFont(this.f, "Identity-H", true, 12.0F, 1, BaseColor.WHITE);
            BaseFont.createFont().correctArabicAdvance();
            this.bfBold_h = this.font_h.getBaseFont();
            return;
        } catch (DocumentException localDocumentException) {
            localDocumentException.printStackTrace();
            return;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }
   // ShowBill_Info getSq = new ShowBill_Info();
    //DataManager dataManager = new DataManager(PdfCreator.this);
    //معلومات المشتري
    public PdfPTable tabbleOfinfocost(String nameCust) {
        PdfPTable localObject6 = new PdfPTable(2);
        ((PdfPTable) localObject6).setWidthPercentage(100.0F);
        ((PdfPTable) localObject6).setRunDirection(this.direction);
        Object localObject4 = new PdfPCell(new Phrase("فاتورة", this.font));
        ((PdfPCell) localObject4).setHorizontalAlignment(0);
        ((PdfPCell) localObject4).setBorder(0);
        ((PdfPCell) localObject4).setColspan(1);
        Object localObject3 = new PdfPCell(new Paragraph(new Phrase("اسم العميل"+":"+nameCust, this.font)));
        ((PdfPCell) localObject3).setHorizontalAlignment(2);
        ((PdfPCell) localObject3).setBorder(0);
        ((PdfPCell) localObject3).setColspan(1);
        Object localObject2 = new PdfPCell(new Paragraph(new Phrase("التاريخ", this.font)));
        ((PdfPCell) localObject2).setHorizontalAlignment(0);
        ((PdfPCell) localObject2).setBorder(0);
        ((PdfPCell) localObject2).setColspan(1);
        Object localObject8 = new PdfPCell(new Paragraph(new Phrase("التاريخ", this.font)));
        ((PdfPCell) localObject8).setHorizontalAlignment(2);
        ((PdfPCell) localObject8).setBorder(0);
        ((PdfPCell) localObject8).setColspan(2);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject4);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject3);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject2);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject8);
        ((PdfPTable) localObject6).setSpacingAfter(10.0F);
        PdfPTable nrw = new PdfPTable(1);
        nrw.setPaddingTop(3.0F);
        nrw.setWidthPercentage(100.0F);
        nrw.setSpacingAfter(20.0F);
        nrw.setRunDirection(this.direction);
        nrw.addCell(localObject6);
        return nrw;
    }

    //عنولن الفاتورة
    public PdfPTable addres() {
        PdfPTable localPdfPTable2 = new PdfPTable(1);
        localPdfPTable2.setPaddingTop(3.0F);
        localPdfPTable2.setWidthPercentage(98.0F);
        localPdfPTable2.setRunDirection(this.direction);
        localPdfPTable2.getDefaultCell().setBorder(0);
        PdfPCell address = new PdfPCell(new Paragraph(new Phrase("فاتورة مبيعات", this.font)));
        address.setHorizontalAlignment(1);
        //address.setBorder(1);
        address.setColspan(1);
        address.setRunDirection(this.direction);
        localPdfPTable2.addCell(address);
        return localPdfPTable2;
    }

    //معلومات الشركة راس الفاتورة
    public PdfPTable pdfPTableheader() {
        PdfPTable localPdfPTable1 = new PdfPTable(1);
        localPdfPTable1.setHorizontalAlignment(0);
        localPdfPTable1.getDefaultCell().setBorder(0);
        localPdfPTable1.addCell("Company Name: " + "");
        localPdfPTable1.addCell("Branch Name: " + "");
        localPdfPTable1.addCell("Branch Address: " + "");
        localPdfPTable1.addCell("Tele No: " + "");
        localPdfPTable1.addCell("Fax No: " + "");
        localPdfPTable1.addCell("Box No: " + "");

        PdfPTable localObject6 = new PdfPTable(1);
        ((PdfPTable) localObject6).setHorizontalAlignment(0);
        ((PdfPTable) localObject6).getDefaultCell().setBorder(0);
        Object localObject5 = new PdfPCell(new Phrase("الشركه" + ":" + "شركة مبيعات", this.font));
        ((PdfPCell) localObject5).setRunDirection(3);
        ((PdfPCell) localObject5).setBorder(0);
        ((PdfPCell) localObject5).setHorizontalAlignment(0);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject5);
        Object localObject7 = new PdfPCell(new Phrase("الفرع" + ":" + "فرع رقم واحد", this.font));
        ((PdfPCell) localObject7).setBorder(0);
        ((PdfPCell) localObject7).setHorizontalAlignment(0);
        ((PdfPCell) localObject7).setRunDirection(3);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject7);
        Object localObject8 = new PdfPCell(new Phrase("العنوان" + ":" + "المملكة العربية", this.font));
        ((PdfPCell) localObject8).setBorder(0);
        ((PdfPCell) localObject8).setHorizontalAlignment(0);
        ((PdfPCell) localObject8).setRunDirection(3);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject8);
        Object localObject9 = new PdfPCell(new Phrase("الهاتف" + ":" + "05555555555", this.font));
        ((PdfPCell) localObject9).setBorder(0);
        ((PdfPCell) localObject9).setHorizontalAlignment(0);
        ((PdfPCell) localObject9).setRunDirection(3);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject9);
        Object localObject11 = new PdfPCell(new Phrase("الفاكس" + ":" + "25522255", this.font));
        ((PdfPCell) localObject11).setBorder(0);
        ((PdfPCell) localObject11).setHorizontalAlignment(0);
        ((PdfPCell) localObject11).setRunDirection(3);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject11);
        Object localObject10 = new PdfPCell(new Phrase("صندوق الريد" + ":" + "000000000", this.font));
        ((PdfPCell) localObject10).setBorder(0);
        ((PdfPCell) localObject10).setHorizontalAlignment(0);
        ((PdfPCell) localObject10).setRunDirection(3);
        ((PdfPTable) localObject6).addCell((PdfPCell) localObject10);

        PdfPTable nrw = new PdfPTable(2);


        nrw.addCell(localPdfPTable1);
        nrw.addCell(localObject6);
        nrw.setWidthPercentage(100.0F);
        nrw.setSpacingAfter(20.0F);
        nrw.setWidthPercentage(98.0F);

        return nrw;
    }

    public PdfPTable createheaderOftble() {
        this.dataHeader.add("رقم الصنف");
        this.dataHeader.add("اسم الصنف");
        this.dataHeader.add(" الكمية");
        this.dataHeader.add(" السعر");
        this.dataHeader.add(" الاجمالي");
        int i = 0;
        PdfPTable localPdfPTable = new PdfPTable(PdfCreator.this.dataHeader.size());
        localPdfPTable.setRunDirection(3);
        localPdfPTable.setWidthPercentage(100.0F);
        i = 0;
        while (i < PdfCreator.this.dataHeader.size()) {
            PdfPCell localObject2 = new PdfPCell(new Phrase(((String) PdfCreator.this.dataHeader.get(i)), PdfCreator.this.font_h));
            ((PdfPCell) localObject2).setHorizontalAlignment(1);
            ((PdfPCell) localObject2).setBackgroundColor(BaseColor.DARK_GRAY);
            localPdfPTable.addCell((PdfPCell) localObject2);
            i += 1;
        }


        return localPdfPTable;
    }

    public PdfPTable lowertable() throws DocumentException {

        PdfPTable localPdfPTable = new PdfPTable(PdfCreator.this.dataHeader.size());
        localPdfPTable.setRunDirection(3);
        localPdfPTable.setWidthPercentage(100.0F);
        localPdfPTable.addCell(new Phrase("المبلغ", PdfCreator.this.font));
        localPdfPTable.addCell(new Phrase("" + PdfCreator.this.d_Value, PdfCreator.this.font));
        Object localObject2 = new PdfPCell(new Paragraph(""));
        ((PdfPCell) localObject2).setColspan(PdfCreator.this.dataHeader.size() - 2);
        localPdfPTable.addCell((PdfPCell) localObject2);

        // localPdfPTable.setWidths(ShwoBillInfo.this.columnWidths);


        localPdfPTable.addCell(new Phrase("خصم الفاتورة", PdfCreator.this.font));
        localPdfPTable.addCell(new Phrase("" + PdfCreator.this.Disc_bill, PdfCreator.this.font));
        localObject2 = new PdfPCell(new Paragraph(""));
        ((PdfPCell) localObject2).setColspan(PdfCreator.this.dataHeader.size() - 2);
        localPdfPTable.addCell((PdfPCell) localObject2);

        // localPdfPTable.setWidths(ShwoBillInfo.this.columnWidths);


        PdfCreator.this.totalAmount = (PdfCreator.this.d_Value - PdfCreator.this.Disc_bill);
        localPdfPTable.addCell(new Phrase("الاجمالي", PdfCreator.this.font));
        localPdfPTable.addCell(new Phrase("" + PdfCreator.this.totalAmount, PdfCreator.this.font));
        localObject2 = new PdfPCell(new Paragraph(""));

        ((PdfPCell) localObject2).setColspan(PdfCreator.this.dataHeader.size() - 2);
        localPdfPTable.addCell((PdfPCell) localObject2);

        //localPdfPTable.setWidths(ShwoBillInfo.this.columnWidths);
        return localPdfPTable;
    }

    public PdfPTable itemsTable() {
        PdfPTable mainLocalPdfPTable = new PdfPTable(1);
        mainLocalPdfPTable.setRunDirection(3);
        mainLocalPdfPTable.setWidthPercentage(100.0F);
        int j;
        int i;

        for (i = 0; i < 10; i++) {
            PdfPTable localPdfPTable = new PdfPTable(PdfCreator.this.dataHeader.size());
            mainLocalPdfPTable.setRunDirection(3);
            mainLocalPdfPTable.setWidthPercentage(100.0F);
            for (j = 0; j < PdfCreator.this.dataHeader.size(); j++) {
                Object localObject2 = new PdfPCell(new Phrase("rowNo " + String.valueOf(i) + " data :" + String.valueOf(j), PdfCreator.this.font));
                ((PdfPCell) localObject2).setHorizontalAlignment(1);
                localPdfPTable.addCell((PdfPCell) localObject2);
            }
            mainLocalPdfPTable.addCell(localPdfPTable);
        }
        return mainLocalPdfPTable;
    }

}
