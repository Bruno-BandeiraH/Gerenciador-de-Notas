package edu.brunobandeira.GerenciadorDeNotas.controller;

import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import edu.brunobandeira.GerenciadorDeNotas.service.ResumeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // REQUESTS

    @PostMapping
    public Resume create(Resume resume) {
        return resumeService.createResume(resume);
    }

    @GetMapping
    public List<Resume> findAll() {
        return resumeService.findAll();
    }

    @GetMapping("/{id}")
    public Resume findById(@PathVariable Long id) {
        return resumeService.findById(id)
            .orElseThrow(() -> new RuntimeException("Resume not found."));
    }

    @GetMapping("/find-by-tag/{tag}")
    public List<Resume> findByTagsContaining(@PathVariable String tag) {
        return resumeService.findByTagsContaining(tag);
    }

    @GetMapping("/find-by-keyword-title/{keyword}")
    public List<Resume> findByTitleContainingIgnoreCase(String keyword) {
        return resumeService.findByTitleContainingIgnoreCase(keyword);
    }

    @PutMapping("/{id}")
    public Resume update(@PathVariable Long id, @RequestBody Resume resume) {
        return resumeService.updateResume(id, resume);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        resumeService.deleteResume(id);
    }
}
