package com.paymentsystem.atto.service;

import com.paymentsystem.atto.HibernateUtil;
import com.paymentsystem.atto.exception.BadRequest;
import com.paymentsystem.atto.model.Card;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CardService {
    public String createCard(Card card){
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        card.setStatus(true);
        card.setAmount(5000.0);
        card.setCreatedDate(new Date());
        session.save(card);
        session.getTransaction().commit();
        session.close();
        return "Card created!";
    }
    public Card getCard(Integer id){
        Session session = HibernateUtil.getFactory().openSession();
        Card card = session.get(Card.class, id);
        if(card==null) throw new BadRequest("Card not found!");
        return card;
    }
    public String updateCard(Card card, Integer id){
        Card oldCard = getCard(id);
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        oldCard.setName(card.getName());
        oldCard.setNumber(card.getNumber());
        session.update(oldCard);
        session.getTransaction().commit();
        session.close();
        return "Card updated!";
    }
    public String deleteCard(Integer id) {
        Card card = getCard(id);
        Session session = HibernateUtil.getFactory().openSession();
        session.delete(card);
        session.getTransaction().commit();
        session.close();
        return "Card deleted!";
    }

    public Card getCardByNumber(String number){
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        Query query =  session.createQuery("FROM Card where number=:number");
        query.setParameter("number", number);
        Card card  = (Card) query.getSingleResult();
        if(card==null) throw new BadRequest("Card not found!");
        return card;
    }

    public String addCash(Integer cardId, Double cash){
        Card card = getCard(cardId);
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        card.setAmount(card.getAmount()+cash);
        session.update(card);
        session.getTransaction().commit();
        session.close();
        return "Money added to card!";
    }

    public List<Card> getCardList(){
        List<Card> cardList;
        Session session = HibernateUtil.getFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Card");
        cardList = query.getResultList();
        if(cardList.isEmpty()) throw new BadRequest("Card List is empty!");
        return cardList;
    }
}
