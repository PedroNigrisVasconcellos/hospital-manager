package br.codenation.hospital.manager.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatesTest {


    @Test
    public void shouldCalculateTheDistanceSameSignals(){

        //Rio Grande - RS
        //32.0395° S, 52.1014° W

        //Pelotas -RS
        //31.7654° S, 52.3376° W

        //Expected distance, approximately: 37.7 Km
        assertEquals(37.7, Coordinates.calculateDistance(-32.0395,-52.1014, -31.7654, -52.3376), 1e-1);

    }

    @Test
    public void shouldCalculateTheDistanceDifferentSignals(){

        //São Paulo - Brasil
        //23.5505° S, 46.6333° W

        //Toronto - Canadá
        //43.6532° N, 79.3832° W

        //Expected distance, approximately: 8.186 km
        assertEquals(8186, Coordinates.calculateDistance(-23.5505,-46.6333, 43.6532, -79.3832), 1e-1);
    }

    @Test
    public void shouldCalculateTheDistanceDifferentSignals2(){

        //Chicago - EUA
        //41.8781° N, 87.6298° W

        //Tóquio - Japão
        //35.6762° N, 139.6503° E

        //Expected distance, approximately: 10.141 km
        assertEquals(10141, Coordinates.calculateDistance(41.8781,-87.6298, 35.6762, 139.6503), 1);
    }



    @Test
    public void shouldConvertStringPositiveCoordinateToDouble(){
        assertEquals(41.8781, Coordinates.convertCoordenateFormatToDouble("41.8781° N"));
        assertEquals(139.6503, Coordinates.convertCoordenateFormatToDouble("139.6503° E"));
    }

    @Test
    public void shouldConvertStringNegativeCoordinateToDouble(){
        assertEquals(-87.6298, Coordinates.convertCoordenateFormatToDouble("87.6298° W"));
        assertEquals(-23.5505, Coordinates.convertCoordenateFormatToDouble("23.5505° S"));
    }

    @Test
    public void shouldConvertPositiveLatitudeToString(){
        assertEquals("41.8781° N", Coordinates.convertLatitudeFormatToString(41.8781));
    }

    @Test
    public void shouldConvertNegativeLatitudeToString(){
        assertEquals("23.5505° S", Coordinates.convertLatitudeFormatToString(-23.5505));
    }

    @Test
    public void shouldConvertPositiveLongitudeToString(){
        assertEquals("139.6503° E", Coordinates.convertLongitudeFormatToString(139.6503));
    }

    @Test
    public void shouldConvertNegativeLongitudeToString(){
        assertEquals("87.6298° W", Coordinates.convertLongitudeFormatToString(-87.6298));
    }

}
