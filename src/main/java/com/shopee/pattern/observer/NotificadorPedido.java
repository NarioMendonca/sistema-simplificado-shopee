package com.shopee.pattern.observer;

import java.util.ArrayList;
import java.util.List;

import com.shopee.model.Pedido;
import com.shopee.util.Logger;

// classe publicadora no padrão Observer.
// irá alertar os incritos (observadores) quando ocorrer um evento
public class NotificadorPedido {
    // inscritos são os objetos que desejam ser notificados quando ocorrer um evento
    private List<Observador> inscritos = new ArrayList<>();
    private static NotificadorPedido instance;

    private NotificadorPedido() {}

    // singleton para evitar duplicações
    public static NotificadorPedido getInstance() {
    if (instance == null) {
        instance = new NotificadorPedido();
    }
        return instance;
    }

    public void adicionarInscrito(Observador obs) {
        inscritos.add(obs);
    }

    public void removerInscrito(Observador obs) {
        inscritos.remove(obs);
    }
    
    // notifica todos os inscritos dessa classe que houve uma alteração
    public void notificar(Pedido pedido, String evento) {
        Logger.getInstance().logInfo("Notificando inscritos: " + evento);
        for (Observador inscrito : inscritos) {
            inscrito.atualizar(pedido, evento);
        }
    }
}