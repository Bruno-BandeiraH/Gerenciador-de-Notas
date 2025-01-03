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

    public ResponseEntity<byte[]> exportPdf(Long id) {
        try {
            Resume resume = resumeService.findById(id).orElseThrow(() -> new RuntimeException("Resume not found."));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, output);
            document.open();

            Font font = new Font(Font.HELVETICA, 18, Font.BOLD);

            Paragraph header = new Paragraph();
            header.setAlignment(Element.ALIGN_CENTER);
            header.add(new Chunk(resume.getTitle(), font));
            header.add(new Paragraph(" "));
            header.setAlignment(Element.ALIGN_RIGHT);
            header.add(new Chunk(resume.dataFormatter()));
            header.add(new Paragraph(" "));
            document.add(header);

            font.setSize(14);
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