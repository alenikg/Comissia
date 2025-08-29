/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comissiatask;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author elenagoncarova
 */
public class Publisher {
    private List<Subscriber> subscribers;

    public Publisher() {
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void unsubscribeAll() {
        subscribers.clear();
    }

    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            subscriber.handleEvent();
        }
    }
}
