package TestingUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MapController implements Initializable {
    @FXML
    private ImageView alaska, alberta, argentina, brazil, britain, centralAmerica, china, congo, eastAfrica,
            easternAustralia, easternUS, egypt, greenland, iceland, india, indonesia, irkutsk, japan, kamchatka,
            kazakhstan, madagascar, middleEast, mongolia, northAfrica, northernEurope, northWestTerritory, ontario,
            papuaNewGuinea, peru, quebec, scandinavia, siam, siberia, southAfrica, southernEurope, ukraine, ural,
            venezuela, westernAustralia, westernEurope, westernUS, yakutsk;

    private HashMap<String, ImageView> colorReference = new HashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colorReference.put("0x0d0d0dff", alaska);
        colorReference.put("0x1a1a1aff", northWestTerritory);
        colorReference.put("0x262626ff", greenland);
        colorReference.put("0x333333ff", alberta);
        colorReference.put("0x404040ff", ontario);
        colorReference.put("0x4d4d4dff", quebec);
        colorReference.put("0x595959ff", westernUS);
        colorReference.put("0x666666ff", easternUS);
        colorReference.put("0x737373ff", centralAmerica);
        colorReference.put("0x808080ff", venezuela);
        colorReference.put("0x8c8c8cff", peru);
        colorReference.put("0x999999ff", brazil);
        colorReference.put("0xa6a6a6ff", argentina);
        colorReference.put("0xb3b3b3ff", iceland);
        colorReference.put("0xbfbfbfff", scandinavia);
        colorReference.put("0xccccccff", ukraine);
        colorReference.put("0xd9d9d9ff", britain);
        colorReference.put("0xe6e6e6ff", northernEurope);
        colorReference.put("0xf2f2f2ff", westernEurope);
        colorReference.put("0xffffffff", southernEurope);
        colorReference.put("0xe680e6ff", northAfrica);
        colorReference.put("0xf280f2ff", egypt);
        colorReference.put("0xff80ffff", eastAfrica);
        colorReference.put("0x0d0d80ff", congo);
        colorReference.put("0x1a1a80ff", southAfrica);
        colorReference.put("0x262680ff", madagascar);
        colorReference.put("0x0d800dff", ural);
        colorReference.put("0x1a801aff", siberia);
        colorReference.put("0x268026ff", yakutsk);
        colorReference.put("0x338033ff", kamchatka);
        colorReference.put("0x408040ff", irkutsk);
        colorReference.put("0x4d804dff", kazakhstan);
        colorReference.put("0x598059ff", china);
        colorReference.put("0x668066ff", mongolia);
        colorReference.put("0x738073ff", japan);
        colorReference.put("0x8c808cff", middleEast);
        colorReference.put("0x998099ff", india);
        colorReference.put("0xa680a6ff", siam);
        colorReference.put("0xb380b3ff", indonesia);
        colorReference.put("0xbf80bfff", papuaNewGuinea);
        colorReference.put("0xcc80ccff", westernAustralia);
        colorReference.put("0xd980d9ff", easternAustralia);
    }

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
        ImageView country = (ImageView) event.getSource();
        Color color = country.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());
        country = colorReference.get(color.toString());
        Lighting colorSettings = new Lighting();
        colorSettings.setLight(new Light.Distant(45, 45, Color.AQUA));
        country.setEffect(colorSettings);
        country.setCache(true);
        country.setCacheHint(CacheHint.SPEED);
    }
}