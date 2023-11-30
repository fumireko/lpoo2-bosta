package ca.fubi.main;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame {
    private List<Pedido> pedidos;
    private DefaultTableModel tableModel;

    public Main() {
        pedidos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Cliente cliente = new Cliente("Cliente " + i, "123-456-789" + i);
            Bolo bolo = new Bolo("Bolo " + i);
            pedidos.add(new Pedido(cliente, bolo, new Endereco("Rua " + i, "Cidade " + i, "Estado " + i)));
        }

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

        JMenuItem consultarPedidoItem = new JMenuItem("Consultar Pedido");
        JMenuItem novoPedidoItem = new JMenuItem("Novo Pedido");

        consultarPedidoItem.addActionListener(e -> {
        	consultarPedido();
        	SwingUtilities.updateComponentTreeUI(pedidosTable);
        	
        });
        novoPedidoItem.addActionListener(e -> novoPedido(null, null, null));

        pedidosMenu.add(consultarPedidoItem);
        pedidosMenu.add(novoPedidoItem);

        JMenu clientesMenu = new JMenu("Clientes");
        menuBar.add(clientesMenu);

        JMenuItem consultarClienteItem = new JMenuItem("Consultar Cliente");
        JMenuItem novoClienteItem = new JMenuItem("Novo Cliente");

        consultarClienteItem.addActionListener(e -> consultarCliente());
        novoClienteItem.addActionListener(e -> novoCliente(null, null));

        clientesMenu.add(consultarClienteItem);
        clientesMenu.add(novoClienteItem);

        // Adicionar os pedidos à tabela
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

        setVisible(true);
    }

    private void consultarPedido() {
        String numeroPedido = JOptionPane.showInputDialog(this, "Digite o número do pedido:");

        try {
            int numero = Integer.parseInt(numeroPedido);

            // Procurar o pedido com o número fornecido usando Stream
            Optional<Pedido> pedidoConsultado = pedidos.stream()
                    .filter(pedido -> pedido.getNumero() == numero)
                    .findFirst();

            if (pedidoConsultado.isPresent()) {
                // Criar um JPanel para exibir os dados do pedido
                JPanel panel = new JPanel(new GridLayout(0, 2));

                Pedido pedido = pedidoConsultado.get();
                JLabel statusLabel = new JLabel(pedido.getStatus().toString());
                
                JButton bProcessamento = new JButton("Em processamento");
                bProcessamento.addActionListener(e -> {
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

                // Mostrar o JPanel com os dados do pedido em uma nova janela
                JOptionPane.showMessageDialog(this, panel, "Detalhes do Pedido", JOptionPane.PLAIN_MESSAGE);
            } else {
                // Mostrar mensagem de erro se o pedido não for encontrado
                JOptionPane.showMessageDialog(this, "Pedido não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Tratar entrada inválida para o número do pedido
            JOptionPane.showMessageDialog(this, "Número de pedido inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void novoPedido(Cliente c, Bolo b, Endereco e) {
        // Implemente a lógica para criar um novo pedido
        Pedido novoPedido = new Pedido(c, b, e);
        pedidos.add(novoPedido);

        // Adicionar novo pedido à tabela
        Object[] rowData = {
                novoPedido.getCliente().getNome(),
                novoPedido.getData(),
                novoPedido.getBolo().getDescricao(),
                novoPedido.getEndereco().getRua() + ", " + novoPedido.getEndereco().getCidade() + ", " + novoPedido.getEndereco().getEstado(),
                novoPedido.getCliente().getTelefone(),
                novoPedido.getStatus()
        };
        tableModel.addRow(rowData);
    }

    private void consultarCliente() {
        String nomeCliente = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        // Implemente a lógica para consultar o cliente com o nome fornecido
    }

    private void novoCliente(String n, String t) {
        // Implemente a lógica para criar um novo cliente
        Cliente novoCliente = new Cliente(n, t);
        // Adicione o novo cliente à sua lista de clientes, se necessário
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
