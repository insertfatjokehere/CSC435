/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculationwa;

/**
 *
 * @author kgb
 */
public class CalculationWA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String value1 = "1";
        String value2 = "23";
        String value3 = "100";
        float check = Float.valueOf(value1) + Float.valueOf(value2) + Float.valueOf(value3);
        // System.out.println("Check: " + check);
        
        String temp1 = addition(value1, value2);
        String result = addition(temp1, value3);
        
        float check2 = Float.valueOf(result);
        if(check == check2) {
            System.out.println(check + " equals " + check2);
        } else {
            System.out.println(check + " not equals " + check2);
        }
    }

    private static String addition(java.lang.String value1, java.lang.String value2) {
        calculationwa.CalWS_Service service = new calculationwa.CalWS_Service();
        calculationwa.CalWS port = service.getCalWSPort();
        return port.addition(value1, value2);
    }
    
}
