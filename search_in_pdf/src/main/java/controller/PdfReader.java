package controller;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PdfReader {
	private String readPdf(File file) {
		try (PDDocument document = PDDocument.load(file)) {
			document.getClass();
			if (!document.isEncrypted()) {
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper tStripper = new PDFTextStripper();
				String pdfFileInText = tStripper.getText(document);
				return pdfFileInText;
			}
		} catch (Exception e) {
		}
		return "";
	}

	public boolean searchStringInPdf(File file, String searchStr) {
		String pdfText = readPdf(file);
		return pdfText.contains(searchStr);
	}

	public static void main(String[] args) throws IOException {
		PdfReader r = new PdfReader();
		boolean result = r.searchStringInPdf(new File("./pdf/포트폴리오.pdf"), "워드");
		System.out.println(result);
	}
}
