/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Leilao;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 *
 * @author stephany
 */
public class Cliente {
    
    public static String mynameis; // Cliente cadastra seu nome no primeiro acesso
    
    /**
     * Inicializa lookup pela referência de objeto do servidor
     * @return InterfaceServ :: referencia do objeto do servidor
     * @throws RemoteException
     * @throws NotBoundException 
     */
    public static InterfaceServ startRMIChannel() throws RemoteException, NotBoundException
    {
        InterfaceServ servidor = null;
        try 
        {
            Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            servidor = (InterfaceServ) myRegistry.lookup(InterfaceServ.LOOKUP_NAME);
            
        } catch  (RemoteException e){
            System.out.println(e.getMessage());
        }
        
        return servidor;
    }
    
}
