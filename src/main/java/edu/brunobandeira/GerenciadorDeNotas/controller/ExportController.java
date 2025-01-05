package edu.brunobandeira.GerenciadorDeNotas.controller;

import edu.brunobandeira.GerenciadorDeNotas.service.PdfService;
import edu.brunobandeira.GerenciadorDeNotas.service.ResumeService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseEntity<byte[]> exportResume(@PathVariable Long id) {
        return pdfService.exportResume(id);
    }
}
