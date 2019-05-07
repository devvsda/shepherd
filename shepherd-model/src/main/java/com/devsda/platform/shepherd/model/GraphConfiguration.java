package com.devsda.platform.shepherd.model;

import java.util.List;

public class GraphConfiguration {

    private List<TeamConfiguration> teamConfigurations;


    public List<TeamConfiguration> getTeamConfigurations() {
        return teamConfigurations;
    }

    public void setTeamConfigurations(List<TeamConfiguration> teamConfigurations) {
        this.teamConfigurations = teamConfigurations;
    }

    @Override
    public String toString() {
        return "GraphConfiguration{" +
                "teamConfigurations=" + teamConfigurations +
                '}';
    }
}
