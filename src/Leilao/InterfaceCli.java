/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Leilao;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author stephany
 */
public interface InterfaceCli extends Remote  {
    
    void receberNotificacao(String msg) throws RemoteException;
    
}
