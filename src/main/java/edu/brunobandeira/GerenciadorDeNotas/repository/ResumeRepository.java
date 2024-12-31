package edu.brunobandeira.GerenciadorDeNotas.repository;

import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByTagsContaining(String tag);
    List<Resume> findByTitleContainingIgnoreCase(String keyword);
}
