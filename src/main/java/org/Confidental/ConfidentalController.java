package org.Confidental;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ConfidentalController {

    private final Map<String, PositionUpdate> devices =
            new ConcurrentHashMap<>();

    private final SimpMessagingTemplate messagingTemplate;


    // --------------------------------------------------
    // GPS boundaries for the building
    // --------------------------------------------------

    // Northwest / top-left corner
    private static final double NORTH_LAT = 29.424135;
    private static final double WEST_LON = -98.497296;

    // Southeast / bottom-right corner
    private static final double SOUTH_LAT = 29.423816;
    private static final double EAST_LON = -98.496521;


    public ConfidentalController(
            SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }


    // --------------------------------------------------
    // Device identification
    // --------------------------------------------------

    @MessageMapping("/identify")
    public void identify(@Payload String deviceId) {

        System.out.println(
                "Device identified: " + deviceId
        );
    }


    // --------------------------------------------------
    // Receive GPS position from phone
    // --------------------------------------------------

    @MessageMapping("/position")
    public void position(@Payload PositionUpdate data) {

        String deviceId = data.getDeviceId();

        double latitude = data.getLatitude();
        double longitude = data.getLongitude();


        // --------------------------------------------------
        // Convert GPS longitude → map X
        //
        // West side = 0%
        // East side = 100%
        // --------------------------------------------------

        double x =
                ((longitude - WEST_LON)
                        / (EAST_LON - WEST_LON))
                        * 100.0;


        // --------------------------------------------------
        // Convert GPS latitude → map Y
        //
        // North side = 0%
        // South side = 100%
        //
        // Latitude is reversed because larger latitude
        // means farther north.
        // --------------------------------------------------

        double y =
                ((NORTH_LAT - latitude)
                        / (NORTH_LAT - SOUTH_LAT))
                        * 100.0;


        // --------------------------------------------------
        // Keep coordinates inside the map
        // --------------------------------------------------

        x = Math.max(0, Math.min(100, x));
        y = Math.max(0, Math.min(100, y));


        // Store calculated map position
        data.setX(x);
        data.setY(y);

        devices.put(deviceId, data);


        // --------------------------------------------------
        // Send position to everyone watching the map
        // --------------------------------------------------

        messagingTemplate.convertAndSend(
                "/topic/updates",
                data
        );


        System.out.println(
                "Position update: " +
                        deviceId +
                        " → GPS (" +
                        latitude +
                        ", " +
                        longitude +
                        ") → Map (" +
                        x +
                        "%, " +
                        y +
                        "%)"
        );
    }
}