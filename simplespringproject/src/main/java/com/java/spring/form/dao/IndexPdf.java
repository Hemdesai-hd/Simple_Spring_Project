package com.java.spring.form.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class IndexPdf {
	public static class ContentEvent extends PdfPageEventHelper {
		private int page;
		Map<String, Integer> index = new LinkedHashMap<String, Integer>();

		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			page++;
		}

		@Override
		public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {

			index.put(title.getContent(), page);
		}

		@Override
		public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth,
				Paragraph title) {
			onChapter(writer, document, paragraphPosition, title);
		}
	}
	public static class IndexEvent extends PdfPageEventHelper {
		private int page;
		boolean body;
		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			// set page number on content
			if(body) {
				page++;
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, 
						new Phrase(page+""), (document.right() + document.left())/2 , document.bottom() - 18, 0);
			}
		}
		
	}
}