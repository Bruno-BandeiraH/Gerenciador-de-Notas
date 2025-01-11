package edu.brunobandeira.GerenciadorDeNotas.service;

import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import edu.brunobandeira.GerenciadorDeNotas.repository.ResumeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResumeServiceTest {

    @Mock
    private ResumeRepository resumeRepository;

    @InjectMocks
    private ResumeService resumeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateResume() {
        Resume resume = new Resume(null, "Teste", "Esse é um resumo de teste unitário", null, Arrays.asList("testes", "testes unitários"));
        when(resumeRepository.save(any(Resume.class))).thenReturn(resume);

        Resume created = resumeService.createResume(resume);

        assertNotNull(created);
        assertEquals("Teste", created.getTitle());
        verify(resumeRepository, times(1)).save(resume);
    }

    @Test
    void testFindById() {
        Resume resume = new Resume(1L, "Teste", "Esse é um resumo de teste unitário", LocalDateTime.now(), Arrays.asList("teste_tag1", "teste 2"));
        when(resumeRepository.findById(1L)).thenReturn(Optional.of(resume));

        Optional<Resume> found = resumeService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Teste", found.get().getTitle());
        verify(resumeRepository, times(1)).findById(1L);
    }
}
