package com.paymentsystem.atto.service;

import com.paymentsystem.atto.HibernateUtil;
import com.paymentsystem.atto.exception.BadRequest;
import com.paymentsystem.atto.model.Terminal;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Component
public class TerminalService {
    public String createTerminal(Terminal terminal) {
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        terminal.setStatus(true);
        terminal.setCreatedDate(new Date());
        terminal.setAmount(0.0);
        session.save(terminal);
        session.getTransaction().commit();
        session.close();
        return "Terminal created!";
    }

    public Terminal getTerminal(Integer id) {
        Session session = HibernateUtil.getFactory().openSession();
        Terminal terminal = session.get(Terminal.class, id);
        if(terminal==null) throw new BadRequest("Terminal not found!");
        return terminal;
    }
    public String updateTerminal(Terminal terminal, Integer id) {
        Terminal oldTerminal = getTerminal(id);
        oldTerminal.setName(terminal.getName());
        oldTerminal.setNumber(terminal.getNumber());
        Session session =HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        session.update(oldTerminal);
        session.getTransaction().commit();
        session.close();
        return "Terminal updated!";
    }
    public String deleteTerminal(Integer id) {
        Terminal terminal = getTerminal(id);
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        session.delete(terminal);
        session.getTransaction().commit();
        session.close();
        return "Terminal deleted!";
    }

    public Terminal getTerminalNUmber(String number){
        Session session = HibernateUtil.getFactory().openSession();
        Query query = session.createQuery("from Terminal where number =:number");
        query.setParameter("number", number);
        Terminal terminal = (Terminal) query.getSingleResult();
        if(terminal==null) throw new BadRequest("Terminal not found by number!");
        return terminal;
    }

    public List<Terminal> getAllTerminals() {
        List<Terminal> terminalList;
        Session session = HibernateUtil.getFactory().openSession();
        Query query = session.createQuery("from Terminal");
        terminalList = query.getResultList();
        if(terminalList.isEmpty()) throw new BadRequest("Terminal List is empty!");
        return terminalList;
    }
}
