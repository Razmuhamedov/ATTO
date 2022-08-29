package com.paymentsystem.atto.controller;

import com.paymentsystem.atto.model.Card;
import com.paymentsystem.atto.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody Card card){
        String result = cardService.createCard(card);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCard(@PathVariable("id") Integer id){
        Card result = cardService.getCard(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCard(@PathVariable("id") Integer id,
                                        @RequestBody Card card){
        String result = cardService.updateCard(card, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable("id") Integer id){
        String result = cardService.deleteCard(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<?> getByNumber(@RequestParam("number") String number){
        Card result = cardService.getCardByNumber(number);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/addCash/{id}")
    public ResponseEntity<?> addCash(@PathVariable("id") Integer id,
                                     @RequestParam("cash") Double cash){
        String result = cardService.addCash(id, cash);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> getAllCards(){
        List<Card> result = cardService.getCardList();
        return ResponseEntity.ok(result);
    }

}
