package br.ufscar.dc.dsw.game_testing_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class SessionRequestDTO {
    @JsonProperty("project_id")
    @NotNull
    private Long projectId;
    @JsonProperty("strategy_id")
    @NotNull
    private Long strategyId;
    @JsonProperty("time_minutes")
    private int timeMinutes;

    // Getters e Setters
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }
}
