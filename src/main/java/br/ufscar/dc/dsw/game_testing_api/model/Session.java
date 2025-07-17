package br.ufscar.dc.dsw.game_testing_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Projeto project;

    @ManyToOne
    @JoinColumn(name = "strategy_id", nullable = false)
    private Estrategia strategy;

    @Column(name = "time_minutes", nullable = false)
    @JsonProperty("time_minutes")
    private int timeMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionState status;

    @Column(name = "created_at", updatable = false)
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    @JsonProperty("started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    @JsonProperty("ended_at")
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bug> bugs = new ArrayList<>();

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Projeto getProject() {
        return project;
    }

    public void setProject(Projeto project) {
        this.project = project;
    }

    public Estrategia getStrategy() {
        return strategy;
    }

    public void setStrategy(Estrategia strategy) {
        this.strategy = strategy;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public SessionState getStatus() {
        return status;
    }

    public void setStatus(SessionState status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
}