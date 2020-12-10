package com.rheact.orders.controllers;

import com.rheact.orders.models.Agent;
import com.rheact.orders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentServices agentService;

    @GetMapping(value = "/agent/{id}", produces = "application/json")
    public ResponseEntity<?> getAgent(@PathVariable long id){
        Agent agent = agentService.findbyId(id);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }
}
