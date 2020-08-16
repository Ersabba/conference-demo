package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getOne(id);
    }

    // Mapping della HTTP POST: @RequestBody  inietta il payload json nel session del metodo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker){
        // Salva e committa (Flush)
        return speakerRepository.saveAndFlush(speaker);
    }

    // Mapping della HTTP DELETE con un ID specifico: @PathVariable  inietta l'ID del HTTP nel metodo
    @RequestMapping(value= "{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable Long id){
        // Controllare i figli del record prima di cancellare: compito per casa !!!
        // Come fare a cancellare i record figli??
        speakerRepository.deleteById(id);
    }

    // Possiamo usare HTTP PATCH (se vogliamo aggiornare parte degli attributi di Session)
    // oppure HTTP PUT (se vogliamo aggiornare tutti gli attributi della sessione)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
        Speaker existingSpeaker = speakerRepository.getOne(id);
        // copia session in existingSession, non copiamo il session_id in quanto è null e provocherebbe errore nella
        // scrittura del DB
        BeanUtils.copyProperties(speaker,existingSpeaker,"session_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

}
