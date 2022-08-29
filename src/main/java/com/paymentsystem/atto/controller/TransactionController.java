package com.paymentsystem.atto.controller;

import com.paymentsystem.atto.model.Transaction;
import com.paymentsystem.atto.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createdTransaction(@RequestParam("cardId") Integer cardId,
                                                @RequestParam("terminalId") Integer terminalId){
        String result = transactionService.createTransaction(cardId, terminalId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable("id") Integer id){
        Transaction result = transactionService.getTransaction(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<?> setFaire(@RequestParam("faire") Double faire){
        String result = transactionService.setFaire(faire);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") Integer id) {
        String result = transactionService.deleteTransaction(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions(){
        List<Transaction> result = transactionService.getAllTransactions();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getByCard/{id}")
    public ResponseEntity<?> getTransactionsByCard(@PathVariable("id") Integer id){
        List<Transaction> result = transactionService.getByCard(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getByTerminal/{id}")
    public ResponseEntity<?> getTransactionsByTerminal(@PathVariable("id") Integer id){
        List<Transaction> result = transactionService.getByTerminal(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<?> getTransactionByDate(@PathVariable("date") String date){
        List<Transaction> result = transactionService.getByDate(date);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getTotalFaire")
    public ResponseEntity<?> getTotalFaire(){
        Double result = transactionService.totalFaire();
        return ResponseEntity.ok(result);
    }

    @PatchMapping("getFaireByDate/{date}")
    public ResponseEntity<?> getFaireByDate(@PathVariable("date") String date){
        Double result = transactionService.getFaireByDate(date);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/countAll")
    public ResponseEntity<?> countAllTransactions(){
        Long result = transactionService.countAllTransactions();
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/countByDate/{date}")
    public ResponseEntity countByDate(@PathVariable("date") String date){
        Long result = transactionService.countTransactionsByDate(date);
        return ResponseEntity.ok(result);
    }

   /* @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable("id") Integer id,
                                            @RequestBody Transaction transaction){
        String result = transactionService.updateTransaction(transaction, id);
        return ResponseEntity.ok(result);
    }
    */
}
