package com.paymentsystem.atto.service;

import com.paymentsystem.atto.HibernateUtil;
import com.paymentsystem.atto.exception.BadRequest;
import com.paymentsystem.atto.model.Card;
import com.paymentsystem.atto.model.Terminal;
import com.paymentsystem.atto.model.Transaction;
import lombok.Getter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TransactionService {
    @Autowired
    CardService cardService;
    @Autowired
    TerminalService terminalService;
    @Getter
    private Double faire;

    public String setFaire(Double faire){
        this.faire = faire;
        return "The price is set";
    }

    public String createTransaction(Integer cardId, Integer terminalId) {
        Card card = cardService.getCard(cardId);
        Terminal terminal = terminalService.getTerminal(terminalId);
        if(card.getAmount()<getFaire()) throw new BadRequest("Insufficient funds on the card!");

        Date lastDate = lastTransaction(card.getNumber());
        if(lastDate!=null) {
            Calendar date = Calendar.getInstance();
            date.setTime(lastDate);
            date.add(Calendar.MINUTE, 1);
            Calendar now = Calendar.getInstance();
            if (date.after(now)) throw new BadRequest("Please, pay few minutes later!");
        }

        Transaction transaction = new Transaction();
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();


        transaction.setCardNumber(card.getNumber());
        card.setAmount(card.getAmount()-getFaire());
        session.update(card);

        transaction.setTerminalNumber(terminal.getNumber());
        terminal.setAmount(terminal.getAmount()+getFaire());
        session.update(terminal);

        transaction.setTransactionTime(new Date());
        transaction.setFaire(getFaire());
        session.save(transaction);

        session.getTransaction().commit();
        session.close();
        return "Transaction completed!";
    }

    public Transaction getTransaction(Integer id) {
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        Transaction transaction = session.get(Transaction.class, id);
        if(transaction==null) throw new BadRequest("Transaction not found!");
        session.getTransaction().commit();
        session.close();
        return transaction;
    }

    private Date lastTransaction(String cardNumber){
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Transaction where cardNumber=:cardNumber order by transactionTime desc");
        query.setParameter("cardNumber", cardNumber);
        query.setMaxResults(1);
        List<Transaction> transactions = query.getResultList();
        session.getTransaction().commit();
        if (transactions.size()>0) {
            session.close();
            return transactions.get(0).getTransactionTime();
        }
        session.close();
        return null;
    }

    public String deleteTransaction(Integer id) {
        Transaction transaction = getTransaction(id);
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        session.delete(transaction);
        session.getTransaction().commit();
        session.close();
        return "Transaction deleted!";
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> transactions;
        Session session = HibernateUtil.getFactory().openSession();
        Query query = session.createQuery("from Transaction ");
        transactions = query.getResultList();
        if(transactions.isEmpty()) throw new BadRequest("Transaction List is empty");
        return transactions;
    }

    public List<Transaction> getByCard(Integer id){
        Session session = HibernateUtil.getFactory().openSession();
        String cardNumber = cardService.getCard(id).getNumber();
        Query query = session.createQuery("from Transaction where cardNumber=:cardNumber");
        query.setParameter("cardNumber", cardNumber);
        List<Transaction> transactions = query.getResultList();
        if(transactions.isEmpty()) throw new BadRequest("Transaction List is empty");
        return transactions;
    }

    public List<Transaction> getByTerminal(Integer id){
        String terminalNumber = terminalService.getTerminal(id).getNumber();
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Transaction where terminalNumber =: number");
        query.setParameter("number", terminalNumber);
        List<Transaction> transactions = query.getResultList();
        if(transactions.isEmpty()) throw new BadRequest("Transaction List is empty!");
        return transactions;
    }

    public List<Transaction> getByDate(String date){
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();

        LocalDateTime localDateTime = LocalDate.parse(date).atStartOfDay();

        String str ="from Transaction where transactionTime  > :before and transactionTime < :after";
        Query query = session.createQuery(str);
        query.setParameter("before", Timestamp.valueOf(localDateTime));
        query.setParameter("after",Timestamp.valueOf(localDateTime.plusDays(1)));
        List<Transaction> transactions = query.getResultList();
        session.close();
        if(transactions.isEmpty()) throw new BadRequest("Transaction list empty on this date!");
        return transactions;
    }

    public Double totalFaire(){
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("select sum(faire) from Transaction");
        double sum = (double) query.getSingleResult();
        session.close();
        return sum;
    }

    public Double getFaireByDate(String date){
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        LocalDateTime localDate = LocalDate.parse(date).atStartOfDay();
        Query query = session.createQuery("select sum(faire) from Transaction where transactionTime > :before and transactionTime < :after");
        query.setParameter("before", Timestamp.valueOf(localDate));
        query.setParameter("after", Timestamp.valueOf(localDate.plusDays(1)));
        if(query.getSingleResult()==null) throw new BadRequest("Please, enter another data!");
        double sum =(double) query.getSingleResult();
        session.close();
        return sum;
    }

    public Long countAllTransactions(){
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Transaction");
        long quantity = (long) query.getSingleResult();
        session.close();
        return quantity;
    }

    public Long countTransactionsByDate(String date) {
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        LocalDateTime localDate = LocalDate.parse(date).atStartOfDay();
        Query query = session.createQuery("select count(*) from Transaction where transactionTime > :before and transactionTime < :after");
        query.setParameter("before", Timestamp.valueOf(localDate));
        query.setParameter("after", Timestamp.valueOf(localDate.plusDays(1)));
        if(query.getSingleResult()==null) throw new BadRequest("Please, enter another data!");
        long quantity = (long) query.getSingleResult();
        session.close();
        return quantity;
    }
}
