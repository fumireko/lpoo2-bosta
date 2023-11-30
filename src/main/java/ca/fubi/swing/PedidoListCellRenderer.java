package ca.fubi.swing;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ca.fubi.main.Pedido;

public class PedidoListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Pedido) {
            Pedido pedido = (Pedido) value;
            setText("Pedido #" + pedido.getNumero() + " - Cliente: " + pedido.getCliente().getNome());
        }
        
        return this;
    }
}
