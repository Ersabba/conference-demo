package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    // Mapping della HTTP GET
    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
    }

    // Mapping della HTTP GET con un ID specifico: @PathVariable  inietta l'ID del HTTP nel metodo
    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getOne(id);
    }

    // Mapping della HTTP POST: @RequestBody  inietta il payload json nel session del metodo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        // Salva e committa (Flush)
        return sessionRepository.saveAndFlush(session);
    }

    // Mapping della HTTP DELETE con un ID specifico: @PathVariable  inietta l'ID del HTTP nel metodo
    @RequestMapping(value= "{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable Long id){
        // Controllare i figli del record prima di cancellare: compito per casa !!!
        // Come fare a cancellare i record figli??
        sessionRepository.deleteById(id);
    }

    // Possiamo usare HTTP PATCH (se vogliamo aggiornare parte degli attributi di Session)
    // // oppure HTTP PUT (se vogliamo aggiornare tutti gli attributi della sessione)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session){
        Session existingSession = sessionRepository.getOne(id);
        // copia session in existingSession, non copiamo il session_id in quanto è null e provocherebbe errore nella
        // scrittura del DB
        BeanUtils.copyProperties(session,existingSession,"session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
