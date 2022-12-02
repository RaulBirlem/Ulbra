package com.example.notas.controller;

import com.example.notas.model.nota;
import com.example.notas.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NotaController {
    @Autowired
    NotaRepository notaRepository;
    // Recuperar todas as notas
    @GetMapping("/notes") // mapea as notas e retorna todas elas
    public List<nota> getAllNotes(){
        return notaRepository.findAll();
    }

    @GetMapping("/notes/{id}") //localhost:8080/api/notes/id
    public Optional<nota> getById(@PathVariable(value = "id")Long id){
        return notaRepository.findById(id);
    }


    @DeleteMapping("/notes/{id}")
    public ResponseEntity deleteNote(@PathVariable(value = "id")Long id){
        Optional<nota> note = notaRepository.findById(id);
        notaRepository.delete(note.get());
        return ResponseEntity.ok().build(); //retorna ok se foi deletado
    }
    // Note -> nota
    // post - CRIAR
    // put -- atualizar
    // para atualizar usa o id (http)
    // titulo e conteudo
    @PutMapping("/notes/{id}")
    public nota updateNote(@PathVariable(value = "id")Long id,
                           @Valid @RequestBody nota noteDetails){
        Optional<nota> note = notaRepository.findById(id);
        note.get().setTitle(noteDetails.getTitle());
        note.get().setContent(noteDetails.getContent());
        //salvar

        nota noteResponse = notaRepository.save(note.get());

        return noteResponse;

    }


    @PostMapping("/notes")
    public nota createNote(@Valid @RequestBody nota Nota){ // verifica se o que recebe é valido e na classe certa(nome,conteudo...)
        return notaRepository.save(Nota); // se tudo certo ele salva no repository
    }


}
