package edu.brunobandeira.GerenciadorDeNotas.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    private final ResumeService resumeService;

    public PdfService(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    public byte[] exportResume(Long id) {
            Resume resume = resumeService.findById(id).orElseThrow(() -> new RuntimeException("Resume not found."));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, output);
            document.open();

            Font headerFont = createFont(18, Font.BOLD);
            Font dateTimeFont = createFont(10, Font.BOLD);
            Font contentFont = createFont(12, Font.NORMAL);

            // Header title
            Paragraph title = new Paragraph();
            title.setAlignment(Element.ALIGN_CENTER);
            title.add(new Chunk(resume.getTitle(), headerFont));
            title.add(new Paragraph(" "));
            title.add(new Paragraph(" "));
            document.add(title);

            // DateTime
            Paragraph time = new Paragraph();
            time.setAlignment(Element.ALIGN_LEFT);
            time.add(new Chunk(resume.dataFormatter(), dateTimeFont));
            time.add(new Paragraph(" "));
            time.add(new Paragraph(" "));
            document.add(time);

            // Resume content
            document.add(new Paragraph(resume.getContent(), contentFont));

            document.close();

            return output.toByteArray();
    }

    public byte[] exportPdfByTagName(String tag) {
        List<Resume> resumes = resumeService.findByTagsContaining(tag);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();

        Font headerFont = createFont(22, Font.BOLD);
        Font titleFont = createFont(14, Font.BOLD);
        Font dateTimeFont = createFont(10, Font.BOLD);
        Font contentFont = createFont(12, Font.NORMAL);

        // Header title
        Paragraph header = new Paragraph();
        header.setAlignment(Element.ALIGN_CENTER);
        header.add(new Chunk(tag, headerFont));
        document.add(header);
        document.add(new Paragraph(" "));

        for(Resume resume : resumes) {
            Paragraph title = new Paragraph();
            // title.setAlignment(Element.ALIGN_CENTER);
            title.add(new Chunk(resume.getTitle(), titleFont));
            document.add(title);

            // DateTime
            Paragraph time = new Paragraph();
            time.setAlignment(Element.ALIGN_LEFT);
            time.add(new Chunk(resume.dataFormatter(), dateTimeFont));
            document.add(time);

            // Resume content
            document.add(new Paragraph(resume.getContent(), contentFont));
            document.add(new Paragraph(" "));
        }

        document.close();
        return output.toByteArray();
    }

    private Font createFont(int size, int style) {
        return new Font(Font.HELVETICA, size, style);
    }
}