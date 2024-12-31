package edu.brunobandeira.GerenciadorDeNotas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime madeIn;
    @ElementCollection
    private List<String> tags;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(LocalDateTime madeIn) {
        this.madeIn = madeIn;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Resume(Long id, String title, String content, LocalDateTime madeIn, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.madeIn = madeIn;
        this.tags = tags;
    }

    public Resume() {
    }
}