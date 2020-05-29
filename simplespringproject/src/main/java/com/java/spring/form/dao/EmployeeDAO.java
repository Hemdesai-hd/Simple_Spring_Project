package com.java.spring.form.dao;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.java.spring.form.dao.IndexPdf.ContentEvent;
import com.java.spring.form.dao.IndexPdf.IndexEvent;
import com.java.spring.form.model.Employee;

@Repository
public class EmployeeDAO {

	private JdbcTemplate template;
	private JdbcTemplate template2;

	@Autowired
	public EmployeeDAO(@Qualifier("mysql-database") DataSource dataSource,
			@Qualifier("oracle-database") DataSource dataSource2) {
		template = new JdbcTemplate(dataSource);
		template2 = new JdbcTemplate(dataSource2);
	}

	public void saveEmployee(Employee e) {

		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection().prepareStatement(
					"insert into employee_mst(firstName,lastName,freePasses,postalCode,email) values(?,?,?,?,?)");
			st.setString(1, e.getFirstName());
			st.setString(2, e.getLastName());
			st.setInt(3, e.getFreePasses());
			st.setString(4, e.getPostalCode());
			st.setString(5, e.getEmail());

			int i = st.executeUpdate();
			template.getDataSource().getConnection().setAutoCommit(false);
			if (i > 1) {
				template.getDataSource().getConnection().commit();
			} else {
				template.getDataSource().getConnection().rollback();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//				String sql="insert into employee_mst(firstName,lastName,freePasses,postalCode,email) values('"+e.getFirstName()+"','"+e.getLastName()+"','"+e.getFreePasses()+"','"+e.getPostalCode()+"','"+e.getEmail()+"')";
//				System.out.println(sql);
//			    template.update(sql);
	}

	public List<Employee> getAllEmployees() {
		List<Employee> list = new ArrayList<Employee>();
		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection()
					.prepareStatement("select id,firstName,lastName,freePasses,postalCode,email from employee_mst");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt(1));
				e.setFirstName(rs.getString(2));
				e.setLastName(rs.getString(3));
				e.setFreePasses(rs.getInt(4));
				e.setPostalCode(rs.getString(5));
				e.setEmail(rs.getString(6));
				list.add(e);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;

	}

	public Employee getEmployeeById(int empId) {
		return template.query(
				"select id,firstName,lastName,freePasses,postalCode,email from employee_mst where id='" + empId + "'",
				new ResultSetExtractor<Employee>() {

					public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {

						Employee e = new Employee();
						while (rs.next()) {
							e.setId(rs.getInt(1));
							e.setFirstName(rs.getString(2));
							e.setLastName(rs.getString(3));
							e.setFreePasses(rs.getInt(4));
							e.setPostalCode(rs.getString(5));
							e.setEmail(rs.getString(6));
						}
						return e;
					}
				});

	}

	public void deleteEmployee(int empId) {
//		String sql = "delete from employee_mst where id=?";
//		return template.update(sql, empId) > 0;
		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection()
					.prepareStatement("delete from employee_mst where id=?");
			st.setInt(1, empId);

			int i = st.executeUpdate();
			template.getDataSource().getConnection().setAutoCommit(false);
			if (i > 1) {
				template.getDataSource().getConnection().commit();
			} else {
				template.getDataSource().getConnection().rollback();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void updateEmployee(Employee e) {
//		String sql = "update employee_mst set firstName='"+e.getFirstName()+"',lastName='"+e.getLastName()+"',freePasses='"+e.getFreePasses()+"',email='"+e.getEmail()+"',postalCode='"+e.getPostalCode()+"' where id='"+e.getId()+"'";
//		template.update(sql);
		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection().prepareStatement(
					"update employee_mst set firstName=?,lastName=?,freePasses=?,email=?,postalCode=? where id=?");
			st.setString(1, e.getFirstName());
			st.setString(2, e.getLastName());
			st.setInt(3, e.getFreePasses());
			st.setString(4, e.getPostalCode());
			st.setString(5, e.getEmail());
			st.setInt(6, e.getId());

			int i = st.executeUpdate();
			template.getDataSource().getConnection().setAutoCommit(false);
			if (i > 1) {
				template.getDataSource().getConnection().commit();
			} else {
				template.getDataSource().getConnection().rollback();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void generatePdf(List<Employee> list) throws FileNotFoundException, DocumentException {
		Font f2 = new Font();
		f2.setSize(12);
		f2.setStyle(f2.BOLD);
		f2.setFamily("Arial");

		Font f3 = new Font();
		f3.setSize(14);
		f3.setStyle(f3.BOLD);
		f3.setFamily("Arial");

		Font f4 = new Font();
		f4.setSize(10);
		f4.setFamily("Arial");

		Document document = new Document(com.itextpdf.text.PageSize.A4, 50, 50, 50, 50);

//		String USER_PASSWORD = "password";
//		String OWNER_PASSWORD = "hem";
		try {
			PdfWriter contentWriter = PdfWriter.getInstance(document, new ByteArrayOutputStream());
			ContentEvent event1 = new ContentEvent();
			contentWriter.setPageEvent(event1);
			document.open();
			for (int i = 1; i <= 10; i++) {
				Chunk chapTitle = new Chunk("Chapter" + i);
				Chapter chapter = new Chapter(new Paragraph(chapTitle), i);
				chapTitle.setLocalDestination(chapter.getTitle().getContent());

//			for (int j = 0; j < i; j++) {
//				Chunk secTitle = new Chunk("Section" + (j+1));
//				Section section = chapter.addSection(new Paragraph(secTitle));
//				secTitle.setLocalDestination(section.getTitle().getContent());
//				section.setIndentationLeft(10);
//				section.add(new Paragraph("sentense sentense\nsentense sentense"));
//			}
				document.add(chapter);
//				chapterList.add(chapter);
			}
			document.close();

			Document document1 = new Document(com.itextpdf.text.PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document1, new FileOutputStream("C://pdf//employeereport.pdf"));
			HeaderFooterPageEvents event = new HeaderFooterPageEvents();
			writer.setPageEvent(event);
			Rectangle rect = new Rectangle(60, 30, 550, 800);
			writer.setBoxSize("art", rect);
//			IndexEvent indexEvent = new IndexEvent();
//			writer.setPageEvent(indexEvent);

			// password protected file
//		writer.setEncryption(USER_PASSWORD.getBytes(),
//                OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING,
//                PdfWriter.ENCRYPTION_AES_128); 
			PdfCopy copy = new PdfCopy(document1, new FileOutputStream("C://pdf//merged_pdf.pdf"));
			document1.open();
			document1.addAuthor("Hem Desai");
			document1.addCreationDate();
			document1.addCreator("hem.java.examples");
			document1.addTitle("Employee Report");

			Chapter indexChapter = new Chapter("Index", -1);
			indexChapter.setNumberDepth(-1); // not show number style
			PdfPTable table = new PdfPTable(2);
			for (Map.Entry<String, Integer> index : event1.index.entrySet()) {
				PdfPCell left = new PdfPCell(new Phrase(index.getKey()));
				left.setBorder(Rectangle.NO_BORDER);

				Chunk pageno = new Chunk(index.getValue() + "");
				pageno.setLocalGoto(index.getKey());
				PdfPCell right = new PdfPCell(new Phrase(pageno));
				right.setHorizontalAlignment(Element.ALIGN_RIGHT);
				right.setBorder(Rectangle.NO_BORDER);

				table.addCell(left);
				table.addCell(right);
			}
			indexChapter.add(table);
			document1.add(indexChapter);
			// add content chapter
//			for (Chapter c : chapterList) {
//				document1.add(c);
//				indexEvent.body = true;
//			}
			document1.newPage();
			PdfPTable table1 = new PdfPTable(6);
			table1.setWidthPercentage(80);
			table1.setSpacingBefore(0);
			table1.setSpacingAfter(0);

			float[] columnWidths = { 100f, 100f, 100f, 100f, 100f, 100f };
			table1.setWidths(columnWidths);

			Paragraph paragraph2 = new Paragraph("ID", f2);
			PdfPCell cell1 = new PdfPCell(paragraph2);
			cell1.setBorderColor(BaseColor.BLACK);
			cell1.setPaddingLeft(10);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell1);

			Paragraph paragraph3 = new Paragraph("First Name", f2);
			PdfPCell cell2 = new PdfPCell(paragraph3);
			cell2.setBorderColor(BaseColor.BLACK);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell2);

			Paragraph paragraph4 = new Paragraph("Last Name", f2);
			PdfPCell cell3 = new PdfPCell(paragraph4);
			cell3.setBorderColor(BaseColor.BLACK);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell3);

			Paragraph paragraph5 = new Paragraph("Free Passes", f2);
			PdfPCell cell4 = new PdfPCell(paragraph5);
			cell4.setBorderColor(BaseColor.BLACK);
			cell4.setPaddingLeft(10);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell4);

			Paragraph paragraph6 = new Paragraph("Email", f2);
			PdfPCell cell5 = new PdfPCell(paragraph6);
			cell5.setBorderColor(BaseColor.BLACK);
			cell5.setPaddingLeft(10);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell5);

			Paragraph paragraph7 = new Paragraph("Postal Code", f2);
			PdfPCell cell6 = new PdfPCell(paragraph7);
			cell6.setBorderColor(BaseColor.BLACK);
			cell6.setPaddingLeft(10);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell6);

			for (Employee employee : list) {
				PdfPCell cell11 = new PdfPCell();
				cell11.setPaddingLeft(0);
				cell11.setPaddingRight(0);
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell11.setPhrase(new Phrase(String.valueOf(employee.getId()), f4));
				table1.addCell(cell11);

				PdfPCell cell12 = new PdfPCell();
				cell12.setPaddingLeft(0);
				cell12.setPaddingRight(0);
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell12.setPhrase(new Phrase(employee.getFirstName(), f4));
				table1.addCell(cell12);

				PdfPCell cell13 = new PdfPCell();
				cell13.setPaddingLeft(0);
				cell13.setPaddingRight(0);
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell13.setPhrase(new Phrase(employee.getLastName(), f4));
				table1.addCell(cell13);

				PdfPCell cell14 = new PdfPCell();
				cell14.setPaddingLeft(0);
				cell14.setPaddingRight(0);
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell14.setPhrase(new Phrase(String.valueOf(employee.getFreePasses()), f4));
				table1.addCell(cell14);

				PdfPCell cell15 = new PdfPCell();
				cell15.setPaddingLeft(0);
				cell15.setPaddingRight(0);
				cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell15.setPhrase(new Phrase(employee.getEmail(), f4));
				table1.addCell(cell15);

				PdfPCell cell16 = new PdfPCell();
				cell16.setPaddingLeft(0);
				cell16.setPaddingRight(0);
				cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell16.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell16.setPhrase(new Phrase(employee.getPostalCode(), f4));
				table1.addCell(cell16);

			}

			document1.add(table1);
			document1.newPage();
			
			PdfPTable table2 = new PdfPTable(5);
			table2.setWidths(new int[] { 1, 2, 2, 2, 1 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase("S/N"));
			cell.setRowspan(2);
			table2.addCell(cell);
			cell = new PdfPCell(new Phrase("Name"));
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new PdfPCell(new Phrase("Age"));
			cell.setRowspan(2);
			table2.addCell(cell);
			table2.addCell("SURNAME");
			table2.addCell("FIRST NAME");
			table2.addCell("MIDDLE NAME");
			table2.addCell("1");
			table2.addCell("James");
			table2.addCell("Fish");
			table2.addCell("Stone");
			table2.addCell("17");
			document1.add(table2);

			// Adding Image
			document1.newPage();
			Image image = Image.getInstance("C://image//Koala.jpg");
			image.setAbsolutePosition(100f, 350f);
			image.scaleAbsolute(200, 200);
			document1.add(image);
			List<String> files = new ArrayList<String>(
					Arrays.asList("C://pdf//demo.pdf", "C://pdf//Transitional Expressions.pdf"));
			for (String file : files) {
				PdfReader reader = new PdfReader(file);
				copy.addDocument(reader);
				copy.freeReader(reader);
				reader.close();
			}
			document1.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generatePdf1() throws FileNotFoundException, DocumentException, MalformedURLException {
		String dest = "C://pdf//demo.pdf";
		com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(dest);
		PdfDocument pdf = new PdfDocument(writer);
		com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);
		AreaBreak ab = new AreaBreak();
		float[] pointColumnWidths = { 150f, 150f };
		Table table = new Table(pointColumnWidths);

		// Populating row 1 and adding it to the table
		Cell cell1 = new Cell();
		cell1.add("Employee ID");
		table.addCell(cell1);

		Cell cell2 = new Cell();
		cell2.add("1");
		table.addCell(cell2);

		Cell cell3 = new Cell();
		cell3.add("Employee Name");
		table.addCell(cell3);

		Cell cell4 = new Cell();
		cell4.add("Hem Desai");
		table.addCell(cell4);

		Cell cell5 = new Cell();
		cell5.add("Position");
		table.addCell(cell5);

		Cell cell6 = new Cell();
		cell6.add("Sr.Executive");
		table.addCell(cell6);

		Cell cell7 = new Cell();
		cell7.add("Joining date");
		table.addCell(cell7);

		Cell cell8 = new Cell();
		cell8.add("2016-07-06");
		table.addCell(cell8);

		Cell cell9 = new Cell();
		cell9.add("Employee Image");
		table.addCell(cell9);

		Cell cell10 = new Cell();

		String imageFile = "C://image//IMG_20180819_213918_821.jpg";
		ImageData data = ImageDataFactory.create(imageFile);

		com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(data);
		cell10.add(img.setAutoScale(true));
		table.addCell(cell10);

		// Adding Table to document
		document.add(table);
		document.add(ab);
		document.close();
	}

	public void generatePdf2() throws DocumentException, IOException {
		String dest = "C://pdf//demo1.pdf";
		com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(dest);
		PdfDocument pdf = new PdfDocument(writer);
		com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);
		document.setMargins(20, 20, 20, 20);
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
	}

}
