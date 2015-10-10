/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weather;

import com.cdyne.ws.weatherws.WeatherReturn;

/**
 *
 * @author kgb
 */
public class Weather {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        WeatherReturn weather = getCityWeatherByZIP("13069");
        System.out.println("Temperature: " + weather.getTemperature() + " F");
        
    }

    private static WeatherReturn getCityWeatherByZIP(java.lang.String zip) {
        com.cdyne.ws.weatherws.Weather service = new com.cdyne.ws.weatherws.Weather();
        com.cdyne.ws.weatherws.WeatherSoap port = service.getWeatherSoap12();
        return port.getCityWeatherByZIP(zip);
    }
    
}
