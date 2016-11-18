/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Leilao;

/**
 *
 * @author stephany
 */

import GraphicInterfaces.IntListaProdutos;

import java.math.MathContext;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



public class ClienteImpl extends UnicastRemoteObject implements InterfaceCli {
    
    public ClienteImpl() throws RemoteException {
        super(0);    
    }

    /**
     * Recebe notificações do servidor e mostra os na class IntListaProdutos;
     * @param msg
     * @throws RemoteException 
     */
    @Override
    public void receberNotificacao(String msg) throws RemoteException {
        if (msg.equals("atualizar")){
            IntListaProdutos listProdutos = new IntListaProdutos();
            
            //MainWin mainWin = new MainWin("");
            
        }else{
            System.out.println("Notificação: " + msg);
            IntListaProdutos.showNotif(msg);
        }
    }
 
    
    
}
