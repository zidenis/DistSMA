package br.unb.sma.entities;

/**
 * Created by zidenis.
 * 17-03-2016
 */
public interface AgentEntity {

    String getAgentName();

    String getClassName();

    String getStatus();

    void setStatus(String status);
}
