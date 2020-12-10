package com.rheact.orders.services;

import com.rheact.orders.models.Agent;
import com.rheact.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value="agentService")
public class AgentServicesImplementation implements AgentServices{

    @Autowired
    AgentsRepository agentrepos;

    @Override
    public Agent findbyId(long id) {
        Agent toReturn = agentrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Agent at "+id+" not found."));
        return toReturn;
    }
}
