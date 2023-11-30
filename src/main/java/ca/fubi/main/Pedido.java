package ca.fubi.main;

import java.util.Date;

public class Pedido {
    private static int numeroPedido = 1;
    private int numero;
    private Date data;
    private Cliente cliente;
    private Bolo bolo;
    private Endereco endereco;
    private StatusPedido status;

    public Pedido(Cliente cliente, Bolo bolo, Endereco endereco) {
        this.numero = numeroPedido++;
        this.data = new Date();
        this.cliente = cliente;
        this.bolo = bolo;
        this.endereco = endereco;
        this.status = StatusPedido.PENDENTE;
    }

	public static int getNumeroPedido() {
		return numeroPedido;
	}

	public static void setNumeroPedido(int numeroPedido) {
		Pedido.numeroPedido = numeroPedido;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Bolo getBolo() {
		return bolo;
	}

	public void setBolo(Bolo bolo) {
		this.bolo = bolo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}


}
