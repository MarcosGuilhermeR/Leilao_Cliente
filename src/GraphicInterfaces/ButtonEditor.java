/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterfaces;


/**
 *
 * @author stephany
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    public long codProduct;
    public String product;
    public String description;
    public double currentHighestBid;
    public String sellerName;
  
 
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          fireEditingStopped();      
        }
      });
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else{
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value ==null) ? "" : value.toString();
        button.setText( label );
        button.setText( "Visualizar");
        isPushed = true;
    
        this.codProduct = (long) table.getValueAt(row, 0);
        this.product = (String) table.getValueAt(row, 1);
        this.description = (String) table.getValueAt(row, 2);
        this.currentHighestBid = (double) table.getValueAt(row, 3);
        this.sellerName = (String) table.getValueAt(row, 5);
        
        return button;
    }
 
  @Override
  public Object getCellEditorValue() {
    if (isPushed)  {
        
        IntDarLance lance = new IntDarLance(this.codProduct, this.product, this.description, this.currentHighestBid, this.sellerName);
        lance.show();
    }
    isPushed = false;
    return label ;
    
  }
   
  @Override
  public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
  }
 
  @Override
  protected void fireEditingStopped() {
        super.fireEditingStopped();
  }
  
}
