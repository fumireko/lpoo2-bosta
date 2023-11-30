package ca.fubi.main;

public class Bolo {
    private static int numeroBolo = 1;
    private int numero;
    private String descricao;
    private double preco;

    public Bolo(String descricao, double preco) {
        this.descricao = descricao;
        this.numero = numeroBolo++;
        this.preco = preco;
    }
    
    @Override
    public String toString() {
    	return this.numero + ", " + this.descricao;
    }
    
    public int getNumero() {
		return numero;
	}
    
    public void setNumero(int numero) {
		this.numero = numero;
	}
    
    public String getDescricao() {
		return descricao;
	}
    
    public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
    public double getPreco() {
		return preco;
	}
    
    public void setPreco(double preco) {
		this.preco = preco;
	}
}
