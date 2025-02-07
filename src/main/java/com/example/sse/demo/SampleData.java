package com.example.sse.demo;

public class SampleData {
    
    private String texto;
    private int number;

    public SampleData(String texto, int number) {
        this.texto = texto; this.number = number;
    }

    public String getTexto() { return texto; }
    public int getNumber() { return number; }

    public void setTexto(String texto) { this.texto = texto; }
    public void setNumber(int number) { this.number = number; }
}
