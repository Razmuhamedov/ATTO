package com.paymentsystem.atto.controller;

import com.paymentsystem.atto.model.Card;
import com.paymentsystem.atto.model.Terminal;
import com.paymentsystem.atto.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminal")
public class TerminalController {
    @Autowired
    TerminalService terminalService;

    @PostMapping
    public ResponseEntity<?> createdTerminal(@RequestBody Terminal terminal){
        String result = terminalService.createTerminal(terminal);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getTerminal(@PathVariable("id") Integer id){
        Terminal result = terminalService.getTerminal(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTerminal(@PathVariable("id") Integer id,
                                        @RequestBody Terminal terminal){
        String result = terminalService.updateTerminal(terminal, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTerminal(@PathVariable("id") Integer id){
        String result = terminalService.deleteTerminal(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getByNumber")
    public ResponseEntity<?> getTerminalByNumber(@RequestParam("number") String number){
        Terminal result = terminalService.getTerminalNUmber(number);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> getAllTerminals(){
        List<Terminal> result = terminalService.getAllTerminals();
        return ResponseEntity.ok(result);
    }
}
