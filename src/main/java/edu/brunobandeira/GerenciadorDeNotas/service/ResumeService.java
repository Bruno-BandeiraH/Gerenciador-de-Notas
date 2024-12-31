package edu.brunobandeira.GerenciadorDeNotas.service;

import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import edu.brunobandeira.GerenciadorDeNotas.repository.ResumeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume createResume(Resume resume) {
        resume.setMadeIn(LocalDateTime.now());
        return resumeRepository.save(resume);
    }

    public List<Resume> findAll() {
        return resumeRepository.findAll();
    }

    public Optional<Resume> findById(Long id) {
        return resumeRepository.findById(id);
    }

    public List<Resume> findByTagsContaining(String tag) {
        return resumeRepository.findByTagsContaining(tag);
    }

    public List<Resume> findByTitleContainingIgnoreCase(String keyword) {
        return resumeRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Resume updateResume(Long id, Resume updatedResume) {
        Resume resume = resumeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resume not found."));

        resume.setTitle(updatedResume.getTitle());
        resume.setContent(updatedResume.getContent());
        resume.setTags(updatedResume.getTags());
        return resumeRepository.save(resume);
    }

    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }
}
