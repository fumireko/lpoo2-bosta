package ca.fubi.main;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame {
    private List<Pedido> pedidos;
    private List<Cliente> clientes;
    private List<Bolo> bolos;
    private DefaultTableModel tableModel;

    public Main() {
        pedidos = new ArrayList<>();
        clientes = new ArrayList<>();
        bolos = new ArrayList<>();
        /*for (int i = 1; i <= 10; i++) {
            Cliente cliente = new Cliente("Cliente " + i, "123-456-789" + i);
            clientes.add(cliente);
            Bolo bolo = new Bolo("Bolo " + i);
            bolos.add(bolo);
            pedidos.add(new Pedido(cliente, bolo, new Endereco("Rua " + i, "Cidade " + i, "Estado " + i)));
        }*/

        Cliente joao = new Cliente("João", "123-456-7890");
        Cliente maria = new Cliente("Maria", "987-654-3210");
        Cliente marcos = new Cliente("Marcos", "555-123-4567");
        Cliente joaquina = new Cliente("Joaquina", "999-888-7777");
        Cliente alice = new Cliente("Alice", "444-555-6666");

        clientes.add(joao);
        clientes.add(maria);
        clientes.add(marcos);
        clientes.add(joaquina);
        clientes.add(alice);

        Bolo boloMorango = new Bolo("Bolo de Morango", 19.99);
        Bolo boloChocolate = new Bolo("Bolo de Chocolate", 24.99);

        bolos.add(boloMorango);
        bolos.add(boloChocolate);

        Endereco ruaDasFlores = new Endereco("Rua das Flores", "São Paulo", "SP");
        Endereco ruaXVDeNovembro = new Endereco("Rua XV de Novembro", "Rio de Janeiro", "RJ");
        Endereco avenidaDomPedroII = new Endereco("Avenida Dom Pedro II", "Curitiba", "PR");
        Endereco rodoviaA11 = new Endereco("Rodovia A-11", "Recife", "PE");
        Endereco ruaDosMusicos = new Endereco("Rua dos Músicos", "Salvador", "BA");

        pedidos.add(new Pedido(joao, boloMorango, ruaDasFlores));
        pedidos.add(new Pedido(maria, boloChocolate, ruaXVDeNovembro));
        pedidos.add(new Pedido(marcos, boloMorango, avenidaDomPedroII));
        pedidos.add(new Pedido(joaquina, boloChocolate, rodoviaA11));
        pedidos.add(new Pedido(alice, boloMorango, ruaDosMusicos));
        
        setTitle("Confeitaria App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Cliente", "Data", "Bolo", "Endereço", "Telefone", "Status"}, 0);
        JTable pedidosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pedidosTable);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu pedidosMenu = new JMenu("Pedidos");
        menuBar.add(pedidosMenu);

        JMenuItem novoPedidoItem = new JMenuItem("Novo Pedido");
        JMenuItem consultarPedidoItem = new JMenuItem("Consultar Pedido");

        consultarPedidoItem.addActionListener(e -> {
        	consultarPedido();
        	atualizarPedidosTabela(pedidos);        	
        });
        novoPedidoItem.addActionListener(e -> novoPedido(null, null, null));

        pedidosMenu.add(consultarPedidoItem);
        pedidosMenu.add(novoPedidoItem);

        JMenu clientesMenu = new JMenu("Clientes");
        menuBar.add(clientesMenu);

        JMenuItem novoClienteItem = new JMenuItem("Novo Cliente");
        JMenuItem consultarClienteItem = new JMenuItem("Consultar Cliente");
        
        JMenuItem listarClientesItem = new JMenuItem("Listar Clientes");
        listarClientesItem.addActionListener(e -> {
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> {
                    listarClientes();
                });
            }).start();
        });
        clientesMenu.add(listarClientesItem);

        consultarClienteItem.addActionListener(e -> consultarCliente());
        novoClienteItem.addActionListener(e -> novoCliente(null, null));

        clientesMenu.add(consultarClienteItem);
        clientesMenu.add(novoClienteItem);

        JMenu bolosMenu = new JMenu("Bolos");
        menuBar.add(bolosMenu);

        JMenuItem novoBoloItem = new JMenuItem("Novo Bolo");
        JMenuItem consultarBoloItem = new JMenuItem("Consultar Bolo");
        
        JMenuItem listarBolosItem = new JMenuItem("Listar Bolos");
        listarBolosItem.addActionListener(e -> {
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> {
                    listarBolos();
                });
            }).start();
        });
        bolosMenu.add(listarBolosItem);

        consultarBoloItem.addActionListener(e -> consultarBolo());
        novoBoloItem.addActionListener(e -> novoBolo(null));

        bolosMenu.add(consultarBoloItem);
        bolosMenu.add(novoBoloItem);

        atualizarPedidosTabela(pedidos);

        setVisible(true);
    }

    private void atualizarPedidosTabela(List<Pedido> pedidos2) {
    	tableModel.setRowCount(0);
        pedidos.forEach(pedido -> {
            Object[] rowData = {
                    pedido.getCliente().getNome(),
                    pedido.getData(),
                    pedido.getBolo().getDescricao(),
                    pedido.getEndereco().getRua() + ", " + pedido.getEndereco().getCidade() + ", " + pedido.getEndereco().getEstado(),
                    pedido.getCliente().getTelefone(),
                    pedido.getStatus()
            };
            tableModel.addRow(rowData);
        });
	}

	private void consultarPedido() {
        String numeroPedido = JOptionPane.showInputDialog(this, "Digite o número do pedido:");

        try {
            int numero = Integer.parseInt(numeroPedido);

            Optional<Pedido> pedidoConsultado = pedidos.stream()
                    .filter(pedido -> pedido.getNumero() == numero)
                    .findFirst();

            if (pedidoConsultado.isPresent()) {

                JPanel panel = new JPanel(new GridLayout(0, 2));

                Pedido pedido = pedidoConsultado.get();
                JLabel statusLabel = new JLabel(pedido.getStatus().toString());

                JButton bProcessamento = new JButton("Em processamento");
                bProcessamento.addActionListener(e -> {
                	if(pedido.getStatus() == StatusPedido.FINALIZADO) this.setEnabled(false);
                	pedido.setStatus(StatusPedido.EM_PROCESSAMENTO);
                    statusLabel.setText(pedido.getStatus().toString());
                    panel.revalidate();
                    panel.repaint();
                });

                JButton bFinalizar = new JButton("Finalizar");
                bFinalizar.addActionListener(e -> {
                	pedido.setStatus(StatusPedido.FINALIZADO);
                    statusLabel.setText(pedido.getStatus().toString());
                    bProcessamento.setEnabled(false);
                    panel.revalidate();
                    panel.repaint();
                });

                panel.add(new JLabel("Número do Pedido:"));
                panel.add(new JLabel(String.valueOf(pedido.getNumero())));
                panel.add(new JLabel("Data do Pedido:"));
                panel.add(new JLabel(String.valueOf(pedido.getData())));
                panel.add(new JLabel("Cliente:"));
                panel.add(new JLabel(pedido.getCliente().getNome()));
                panel.add(new JLabel("Bolo:"));
                panel.add(new JLabel(pedido.getBolo().getDescricao()));
                panel.add(new JLabel("Endereço:"));
                panel.add(new JLabel(pedido.getEndereco().getRua() + ", " + pedido.getEndereco().getCidade() + ", " + pedido.getEndereco().getEstado()));
                panel.add(new JLabel("Telefone do Cliente:"));
                panel.add(new JLabel(pedido.getCliente().getTelefone()));
                panel.add(new JLabel("Status do Pedido:"));
                panel.add(statusLabel);
                panel.add(bProcessamento);
                panel.add(bFinalizar);

                JOptionPane.showMessageDialog(this, panel, "Detalhes do Pedido", JOptionPane.PLAIN_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(this, "Pedido não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "Número de pedido inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

	private void novoPedido(Cliente c, Bolo b, Endereco e) {

	    if (c == null || b == null) {

	        JComboBox<Cliente> clientesComboBox = new JComboBox<>(clientes.toArray(new Cliente[0]));
	        JComboBox<Bolo> bolosComboBox = new JComboBox<>(bolos.toArray(new Bolo[0]));

	        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5)); 
	        panel.add(new JLabel("Cliente:"));
	        panel.add(clientesComboBox);
	        panel.add(new JLabel("Bolo:"));
	        panel.add(bolosComboBox);

	        JTextField ruaField = new JTextField();
	        JTextField cidadeField = new JTextField();
	        JTextField estadoField = new JTextField();

	        panel.add(new JLabel("Rua:"));
	        panel.add(ruaField);
	        panel.add(new JLabel("Cidade:"));
	        panel.add(cidadeField);
	        panel.add(new JLabel("Estado:"));
	        panel.add(estadoField);

	        int result = JOptionPane.showConfirmDialog(this, panel, "Novo Pedido", JOptionPane.OK_CANCEL_OPTION);

	        if (result == JOptionPane.OK_OPTION) {

	            Cliente selectedCliente = (Cliente) clientesComboBox.getSelectedItem();
	            Bolo selectedBolo = (Bolo) bolosComboBox.getSelectedItem();
	            String rua = ruaField.getText();
	            String cidade = cidadeField.getText();
	            String estado = estadoField.getText();
	            Endereco novoEndereco = new Endereco(rua, cidade, estado);

	            Pedido novoPedido = new Pedido(selectedCliente, selectedBolo, novoEndereco);
	            pedidos.add(novoPedido);

	            atualizarPedidosTabela(pedidos);
	        }
	    } else {

	        Pedido novoPedido = new Pedido(c, b, e);
	        pedidos.add(novoPedido);

	        atualizarPedidosTabela(pedidos);
	    }
	}

	private void consultarCliente() {
	    String nomeCliente = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");

	    Optional<Cliente> clienteConsultado = clientes.stream()
	            .filter(cliente -> cliente.getNome().equalsIgnoreCase(nomeCliente))
	            .findFirst();

	    if (clienteConsultado.isPresent()) {
	        Cliente cliente = clienteConsultado.get();
	        JOptionPane.showMessageDialog(this, "Nome do Cliente: " + cliente.getNome() + "\nTelefone do Cliente: " + cliente.getTelefone(), "Detalhes do Cliente", JOptionPane.PLAIN_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(this, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void novoCliente(String nome, String telefone) {
	    JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
	    panel.add(new JLabel("Nome do Cliente:"));
	    JTextField nomeField = new JTextField(nome);
	    panel.add(nomeField);
	    panel.add(new JLabel("Telefone do Cliente:"));
	    JTextField telefoneField = new JTextField(telefone);
	    panel.add(telefoneField);

	    int result = JOptionPane.showConfirmDialog(this, panel, "Novo Cliente", JOptionPane.OK_CANCEL_OPTION);

	    if (result == JOptionPane.OK_OPTION) {

	        String novoNome = nomeField.getText();
	        String novoTelefone = telefoneField.getText();

	        Cliente novoCliente = new Cliente(novoNome, novoTelefone);
	        clientes.add(novoCliente);
	    }
	}

    private void consultarBolo() {
        int numeroBolo = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite o número do bolo:"));

        Optional<Bolo> boloConsultado = bolos.stream()
                .filter(bolo -> bolo.getNumero() == (numeroBolo))
                .findFirst();

        try {
	        if (boloConsultado.isPresent()) {
	            Bolo bolo = boloConsultado.get();
	            JOptionPane.showMessageDialog(this, "Numero do Bolo:" + bolo.getNumero() + "\nDescrição do Bolo: " + bolo.getDescricao(), "Detalhes do Bolo", JOptionPane.PLAIN_MESSAGE);
		    } else {
		        JOptionPane.showMessageDialog(this, "Pedido não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		} catch (NumberFormatException e) {
		    JOptionPane.showMessageDialog(this, "Número de pedido inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }

    private void novoBolo(Bolo b) {
        if (b == null) {
            JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
            panel.add(new JLabel("Descrição do Bolo:"));
            JTextField descricaoField = new JTextField();
            panel.add(descricaoField);
            panel.add(new JLabel("Preço do Bolo:"));
            JTextField precoField = new JTextField();
            panel.add(precoField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Novo Bolo", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String descricaoBolo = descricaoField.getText();
                double precoBolo = Double.parseDouble(precoField.getText());

                Bolo novoBolo = new Bolo(descricaoBolo, precoBolo);
                novoBolo.setPreco(precoBolo);

                bolos.add(novoBolo);
            }
        }
    }

    private void listarClientes() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel clientesTableModel = new DefaultTableModel(new String[]{"Nome", "Telefone"}, 0);

            clientes.forEach(cliente -> {
                Object[] rowData = {
                        cliente.getNome(),
                        cliente.getTelefone()
                };
                clientesTableModel.addRow(rowData);
            });

            JTable clientesTable = new JTable(clientesTableModel);
            JFrame frame = new JFrame("Lista de Clientes");
            frame.add(new JScrollPane(clientesTable));
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        });
    }

    private void listarBolos() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel bolosTableModel = new DefaultTableModel(new String[]{"Descrição", "Preço"}, 0);

            bolos.forEach(bolo -> {
                Object[] rowData = {
                        bolo.getDescricao(),
                        bolo.getPreco()
                };
                bolosTableModel.addRow(rowData);
            });

            JTable bolosTable = new JTable(bolosTableModel);
            JFrame frame = new JFrame("Lista de Bolos");
            frame.add(new JScrollPane(bolosTable));
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        });
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}