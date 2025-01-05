package edu.brunobandeira.GerenciadorDeNotas.controller;

import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import edu.brunobandeira.GerenciadorDeNotas.service.PdfService;
import edu.brunobandeira.GerenciadorDeNotas.service.ResumeService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exports")
@ApiResponse(responseCode = "200", description = "Exporta PDF", content = @Content(mediaType = "application/pdf"))
public class ExportController {

    private final ResumeService resumeService;
    private  final PdfService pdfService;

    public ExportController(ResumeService resumeService, PdfService pdfService) {
        this.resumeService = resumeService;
        this.pdfService = pdfService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> exportResumeToPdf(@PathVariable Long id) {
        Resume resume = resumeService.findById(id)
            .orElseThrow(() -> new RuntimeException("Resume not found."));

        byte[] export = pdfService.exportResume(id);

        // CONFIGURANDO RESPOSTA
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", resume.getTitle() + ".pdf");

        return new ResponseEntity<>(export, headers, HttpStatus.OK);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<byte[]> exportResumesByTagToPdf(@PathVariable String tag) {
        byte[] export = pdfService.exportPdfByTagName(tag);

        // CONFIGURANDO RESPOSTA
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", tag + ".pdf");

        return new ResponseEntity<>(export, headers, HttpStatus.OK);
    }
}
