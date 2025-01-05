package edu.brunobandeira.GerenciadorDeNotas.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    private final ResumeService resumeService;

    public PdfService(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    public ResponseEntity<byte[]> exportResume(Long id) {
        try {
            Resume resume = resumeService.findById(id).orElseThrow(() -> new RuntimeException("Resume not found."));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, output);
            document.open();

            Font font = new Font(Font.HELVETICA, 18, Font.BOLD);

            Paragraph title = new Paragraph();
            title.setAlignment(Element.ALIGN_CENTER);
            title.add(new Chunk(resume.getTitle(), font));
            title.add(new Paragraph(" "));
            title.add(new Paragraph(" "));
            document.add(title);

            Paragraph time = new Paragraph();
            time.setAlignment(Element.ALIGN_LEFT);
            font.setSize(10);
            time.add(new Chunk(resume.dataFormatter(), font));
            time.add(new Paragraph(" "));
            time.add(new Paragraph(" "));
            document.add(time);

            font.setSize(12);
            font.setStyle(Font.NORMAL);
            document.add(new Paragraph(resume.getContent(), font));

            document.close();

            // CONFIGURANDO RESPOSTA
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", resume.getTitle() + ".pdf");

            return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}