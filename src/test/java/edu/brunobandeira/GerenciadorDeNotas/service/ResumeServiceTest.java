package edu.brunobandeira.GerenciadorDeNotas.service;

import edu.brunobandeira.GerenciadorDeNotas.model.Resume;
import edu.brunobandeira.GerenciadorDeNotas.repository.ResumeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

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


}
