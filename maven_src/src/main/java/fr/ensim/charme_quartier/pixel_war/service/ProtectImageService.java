package fr.ensim.charme_quartier.pixel_war.service;

import fr.ensim.charme_quartier.pixel_war.model.AuthentifierToken;
import fr.ensim.charme_quartier.pixel_war.model.CanvasCoords;
import fr.ensim.charme_quartier.pixel_war.model.EUseableColors;
import io.socket.client.IO;
import io.socket.client.Socket;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * aim of this service is to register every drawing, and protect them if needed
 */
@Service
public class ProtectImageService {

    private Map<CanvasCoords, BufferedImage> toProtect = new HashMap<>();

    /**
     * register an image to be protected
     * @param coordTopLeft coordinate of the top left pixel of the image
     * @param imageToProtect BufferedImage containing all colors of pixels
     * @return false if the service is already protecting an image with the given coordTopLeft, else true
     */
    public boolean registerProtectionFor(CanvasCoords coordTopLeft, BufferedImage imageToProtect) {
        if(toProtect.containsKey(coordTopLeft)) {
            // coordTopLeft already in use, not possible
            // TODO: check if no overlaping image
            return false;
        }
        toProtect.put(coordTopLeft, imageToProtect);
        return true;
    }

    /**
     * unregister an image identified by the position of the top left pixel
     * @param coordTopLeft position of the top left pixel of the image
     */
    public void unregisterFromProtection(CanvasCoords coordTopLeft) {
        toProtect.remove(coordTopLeft);
    }

    /**
     * will check if a given pixel is under protection, and will quarantee to be back to the right color is specified
     * newColor doesn't match with the one of the protected image
     * @param coordinatesTriggering coordinates of the pixel to check
     * @param newColor color of the pixel to check
     * @param tokenToUse token to use if changes are needed
     * @param workerIdToUse id of the worker which will be charged to modify the pixel if needed
     * @param canvasNameToWriteOn name of the canvas where you will write if needed
     */
    public void triggerProtection(CanvasCoords coordinatesTriggering, EUseableColors newColor,
                                  String tokenToUse, int workerIdToUse, String canvasNameToWriteOn) {
        int xCoordTriggered = coordinatesTriggering.getAbsoluteCoords()[0];
        int yCoordTriggered = coordinatesTriggering.getAbsoluteCoords()[1];
        int encodedColorModelVal = newColor.getValue().getRGB();
        toProtect.forEach((CanvasCoords topLeftCoordinate, BufferedImage image) -> {
            int startingImageXCoord = topLeftCoordinate.getAbsoluteCoords()[0];
            int endingImageXCoord = startingImageXCoord + image.getWidth();
            int startingImageYCoord = topLeftCoordinate.getAbsoluteCoords()[1];
            int endingImageYCoord = startingImageYCoord + image.getHeight();

            if(xCoordTriggered >= startingImageXCoord && xCoordTriggered <= endingImageXCoord &&
                    yCoordTriggered >= startingImageYCoord && yCoordTriggered <= endingImageYCoord) {
                int encodedNormalColorModelVal =
                        image.getRGB(xCoordTriggered - startingImageXCoord, yCoordTriggered - startingImageYCoord);
                if(encodedColorModelVal != encodedNormalColorModelVal) {
                    System.out.println("PIXEL MODIFIED!!!!");
                    // new value is not equal to the one excepted, have to change, with socket this time
                    URI uri = URI.create("http://149.202.79.34:8085/api/socket");
                    IO.Options options = IO.Options.builder()
                            .setAuth(singletonMap("token", tokenToUse))
                            .build();

                    Socket socket = IO.socket(uri, options);
                    socket.connect();
                    Map<String, Object> body = new HashMap<>();
                    body.put("canvasId", canvasNameToWriteOn);
                    body.put("workerId", workerIdToUse);
                    body.put("x", xCoordTriggered);
                    body.put("y", yCoordTriggered);
                    body.put("color", EUseableColors.getEUseableColors(new Color(encodedNormalColorModelVal)).getKey());
                    socket.emit("updatePixel", body);
                }
            }
        });
    }
}
