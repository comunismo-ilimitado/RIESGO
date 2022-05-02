package TestingUI;

import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class MapController {


    @FXML
    private void countrySelected(MouseEvent event) {
        System.out.println("country");
        ImageView country = (ImageView) event.getSource();
        Lighting blackout = new Lighting();
        blackout.setLight(new Light.Distant(45, 45, Color.AQUA));

        Color color = country.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());
        System.out.println(color.toString());

        country.setEffect(blackout);
        country.setCache(true);
        country.setCacheHint(CacheHint.SPEED);
        System.out.println(country.getId());
    }
    @FXML
    private void mapSelected(MouseEvent event) {
        System.out.println("Map");
        ImageView country = (ImageView) event.getSource();
        System.out.println(country.getImage().getHeight());
        Color color = country.getImage().getPixelReader().getColor((int) event.getX()*3, (int) event.getY()*3);

        System.out.println(event.getX());
        System.out.println(event.getY());
        System.out.println(color.toString());


    }
}