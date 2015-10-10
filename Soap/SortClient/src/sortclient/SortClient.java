/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortclient;

import java.rmi.RemoteException;

/**
 *
 * @author kgb
 */
public class SortClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        // TODO code application logic here
        String key = getKey();
        String results = mergeSort("12 23 45 34 11 5 6", key);
        System.out.println(results);
    }

    private static String getKey() {
        sortclient.Service service = new sortclient.Service();
        sortclient.IService port = service.getBasicHttpBindingIService();
        return port.getKey();
    }

    private static String mergeSort(java.lang.String input, java.lang.String userKey) {
        sortclient.Service service = new sortclient.Service();
        sortclient.IService port = service.getBasicHttpBindingIService();
        return port.mergeSort(input, userKey);
    }
    
}
