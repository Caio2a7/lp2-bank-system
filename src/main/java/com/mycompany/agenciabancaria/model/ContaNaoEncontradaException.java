package com.mycompany.agenciabancaria.model;

public class ContaNaoEncontradaException extends BancoException {
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
