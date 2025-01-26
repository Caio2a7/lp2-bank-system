package com.mycompany.agenciabancaria.model;

public class BancoException extends RuntimeException {
    public BancoException(String mensagem) {
        super(mensagem);
    }
}
