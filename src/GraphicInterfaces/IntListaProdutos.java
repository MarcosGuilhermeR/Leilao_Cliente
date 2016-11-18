/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicInterfaces;

import Controller.Product;
import Controller.Serializer;
import Leilao.Cliente;
import Leilao.ClienteImpl;
import Leilao.InterfaceServ;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


  
/**
 *
 * @author stephany
 */
public class IntListaProdutos  {
    
    int teste;
    
    public static JFrame wrapframe =  new JFrame("Leilões Ativos");
    
    /**
     * Recarrega o painel para mostrar respectivas mensagens advindas do servidor
     * @param serverMsg 
     */
    public static void showNotif(String serverMsg)
    {
        JFrame contentPane = IntListaProdutos.wrapframe;
        MainWin mainPanel = new MainWin(serverMsg);
        contentPane.getContentPane().removeAll();
        contentPane.getContentPane().add(mainPanel);
        contentPane.getContentPane().revalidate(); 
        contentPane.getContentPane().repaint();
    }
    
    /**
     * Cria Frame e adiciona painel com componentes dentro
     */
    static void createAndShowGui() {
       
        MainWin mainPanel = new MainWin("");

        IntListaProdutos.wrapframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IntListaProdutos.wrapframe.getContentPane().add(mainPanel);
        IntListaProdutos.wrapframe.pack();
        IntListaProdutos.wrapframe.setLocationByPlatform(true);
        IntListaProdutos.wrapframe.setSize(900, 650);
        IntListaProdutos.wrapframe.setLocationRelativeTo(null);
        IntListaProdutos.wrapframe.setVisible(true);
        IntListaProdutos.wrapframe.setBackground(Color.LIGHT_GRAY);
   }

   
   /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if(Cliente.mynameis == null)
        {
            try {
                // Comunicação RMI
                InterfaceServ startRMIChannel = Cliente.startRMIChannel();
            
                if(startRMIChannel != null)
                {
                    ClienteImpl refCli = new ClienteImpl();
                    boolean cadOK = false;
                    while(!cadOK)
                    {
                        // Cadastra nome do usuário no primeiro acesso e envia sua respectiva referencia de objeto ao servidor.
                        String nome = JOptionPane.showInputDialog("Para realizar seu primeiro acesso, cadastre seu nome:");
                        boolean cadastrarRefCli = startRMIChannel.cadastrarRefCli(nome, refCli);
                        if(!cadastrarRefCli)
                        {
                            JOptionPane.showMessageDialog(new JFrame(), "Nome de usuário já foi escolhido. Escolha outro.", "Resposta Cadastro Cliente", JOptionPane.ERROR_MESSAGE);
                        }else
                        {
                            cadOK = true;
                            Cliente.mynameis = nome;
                            createAndShowGui();
                        }
                    }
                    
                }else
                {
                    System.out.println("Não foi possível abrir canal de comunicação");
                    JOptionPane.showMessageDialog(new JFrame(), "Não foi possível abrir canal de comunicação com a plataforma de leilão, tente mais tarde.", "Plataforma Leilão", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(IntCadProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


class MainWin extends JPanel {
    
    
   private final String[] COL_NAMES = { "Código", "Produto", "Lance Atual", "Lance por", "Vendedor", "Participar" };
   private final DefaultTableModel model = new DefaultTableModel(COL_NAMES, 0);
   
   /**
    * Carrega os produtos no JTable 
    * Componentes inferiores (botões controladores + mensagem ao usuário)
    * @param msgS 
    */
   public MainWin(String msgS) {
       
        final JTable table = new JTable(model);
        JPanel btnPanel = new JPanel();
        btnPanel.add(new JButton(new AbstractAction("Atualizar Produtos") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addRows(model);
                table.getColumn("Participar").setCellRenderer(new ButtonRenderer());
                table.getColumn("Participar").setCellEditor(new ButtonEditor(new JCheckBox()));
            }
        }));
        
        JPanel btncadPanel = new JPanel();
        btncadPanel.add(new JButton(new AbstractAction("Cadastrar Produtos") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                IntCadProduto icad = new IntCadProduto();
                icad.show();
            }
        }));
        
        /* Listagem de produtos e adiçao de botões orientados a evento (dar lance) */
        this.addRows(model);
        table.getColumn("Participar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Participar").setCellEditor(new ButtonEditor(new JCheckBox()));
        add(new JScrollPane(table));
        setLayout(new BorderLayout());
        table.getTableHeader().setReorderingAllowed(false);
        add(table.getTableHeader(), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        
        
        /* Botões e mensagem ao usuário */
        JPanel bottomComponent = new JPanel(new BorderLayout());
        JPanel bottomControllers = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel serverController = new JPanel(new  FlowLayout(FlowLayout.LEFT));
        
        bottomControllers.add(new Label("Usuário: " + Cliente.mynameis));
        bottomControllers.add(btnPanel);
        bottomControllers.add(btncadPanel);
        
        
        serverController.add(new Label("Notificação: "));
        // Negrito
        Label label = new Label(msgS);
        Font font = new Font("Courier", Font.BOLD,12);
        label.setFont(font);
        
        serverController.add(label);
        
        bottomComponent.add(serverController, BorderLayout.NORTH);
        bottomComponent.add(bottomControllers, BorderLayout.CENTER);
        
        
        add(bottomComponent, BorderLayout.SOUTH);

   }

    private void addRows(DefaultTableModel model) {
        try {
            // Comunicação RMI
            InterfaceServ startRMIChannel = Cliente.startRMIChannel();
            
            if(startRMIChannel != null)
            {
                byte[] listaProdutosAtivos = startRMIChannel.listaProdutosAtivos();
                if(listaProdutosAtivos != null)
                {
                    Map deserializeHashMap = Serializer.deserializeHashMap(listaProdutosAtivos);
                    int size = deserializeHashMap.size();
                    
                    if(size > 0)
                    {
                        Set<Long> chaves = deserializeHashMap.keySet();  
                        int lines = 0;
                        Object[][] rows = new Object[size][7];
                        for (Long chave : chaves)  
                        {  
                            if(chave != null)  
                            {
                                Product objeto = (Product) deserializeHashMap.get(chave);
                                
                                rows[lines][0] = objeto.getCodProduct();
                                rows[lines][1] = objeto.getNameProduct();
                                rows[lines][2] = objeto.getDescriptionProduct();
                                rows[lines][3] = objeto.gethighestBid(); 
                                rows[lines][4] = objeto.getWinnerName();
                                rows[lines][5] = objeto.getSellerName();
                                rows[lines][6] = "VISUALIZAR"; 
                                lines++;
                                
                            }
                        }
                        
                        model.setDataVector(rows, COL_NAMES);
                        
                    }else
                    {
                        JOptionPane.showMessageDialog(new JFrame(), "Não existe nenhum leilão ativo.", "Leilões Ativos", JOptionPane.ERROR_MESSAGE);
                        model.setDataVector(null, COL_NAMES);
                    }
                    
                }else
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Não existe nenhum leilão ativo.", "Leilões Ativos", JOptionPane.ERROR_MESSAGE);
                    model.setDataVector(null, COL_NAMES);
                }
                
            }else
            {
                System.out.println("Não foi possível abrir canal de comunicação");
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(IntCadProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}