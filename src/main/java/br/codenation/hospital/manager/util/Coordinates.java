package br.codenation.hospital.manager.util;

public class Coordinates {

    final static Double earthsRadius= 6.371e3;

    //a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
    //c = 2 ⋅ atan2( √a, √(1−a) )
    //distance = R ⋅ c
    //where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
    public static Double calculateDistance(Double lat1,Double lon1,Double lat2,Double lon2) {

        double deltaLat, deltaLon, a, c, distance;

        deltaLat = Math.toRadians(lat2-lat1);
        deltaLon = Math.toRadians(lon2-lon1);
        a = Math.pow(Math.sin(deltaLat/2), 2.0 ) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(deltaLon/2) * Math.sin(deltaLon/2);

        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        distance = earthsRadius * c;

        return distance;
    }

    public static Double convertCoordenateFormatToDouble(String coordinate){

        String splitCoordinate[] = coordinate.split(" ");

        if(splitCoordinate[1].contains("W") || splitCoordinate[1].contains("S"))
            return -Double.valueOf(splitCoordinate[0].replace('°',' '));
        else
            return Double.valueOf(splitCoordinate[0].replace('°',' '));
    }

    public static String convertLatitudeFormatToString(Double coordinate){

        if(coordinate >= 0)
            return coordinate+"° N";
        else
            return Math.abs(coordinate)+"° S";
    }

    public static String convertLongitudeFormatToString(Double coordinate){

        if(coordinate >= 0)
            return coordinate+"° E";
        else
            return Math.abs(coordinate)+"° W";
    }
}
