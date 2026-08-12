package com.itb.escola.pizzaria.controller.dtorequest;

import java.util.List;

public class PedidoRequest {

    private Long clienteId;
    private List<ItemPedidoRequest> itemPedidos;

    // Getters e Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequest> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(List<ItemPedidoRequest> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }
}

