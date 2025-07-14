package br.ufscar.dc.dsw.game_testing_api.dto;

import br.ufscar.dc.dsw.game_testing_api.model.Session;
import br.ufscar.dc.dsw.game_testing_api.model.SessionState;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class SessionResponseDTO {
    private Long id;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("project_name")
    private String projectName;
    @JsonProperty("strategy_id")
    private Long strategyId;
    @JsonProperty("strategy_name")
    private String strategyName;
    @JsonProperty("time_minutes")
    private int timeMinutes;
    private SessionState status;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
    @JsonProperty("ended_at")
    private LocalDateTime endedAt;

    public SessionResponseDTO(Session session) {
        this.id = session.getId();
        this.projectId = session.getProject().getId();
        this.projectName = session.getProject().getNome();
        this.strategyId = session.getStrategy().getId();
        this.strategyName = session.getStrategy().getNome();
        this.timeMinutes = session.getTimeMinutes();
        this.status = session.getStatus();
        this.createdAt = session.getCreatedAt();
        this.startedAt = session.getStartedAt();
        this.endedAt = session.getEndedAt();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
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
