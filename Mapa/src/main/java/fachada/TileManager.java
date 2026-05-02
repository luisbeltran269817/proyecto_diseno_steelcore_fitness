/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * configurar el proveedor de tiles del mapa.
 * Inyecta el User-Agent para que CartoDB no bloquee las peticiones.
 * 
 * @author julian izaguirre
 */
public class TileManager {
private final DefaultTileFactory tileFactory;

    public TileManager() {
        TileFactoryInfo info = new TileFactoryInfo(
                1, 17, 17, 256, true, true,
                "https://a.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}.png",
                "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                int z = 17 - zoom;
                return "https://a.basemaps.cartocdn.com/dark_all/"
                    + z + "/" + x + "/" + y + ".png";
            }
        };

        tileFactory = new DefaultTileFactory(info) {
            protected BufferedImage downloadTile(TileFactoryInfo inf,
                    int x, int y, int zoom) throws Exception {
                HttpURLConnection con = (HttpURLConnection)
                    new URL(inf.getTileUrl(x, y, zoom)).openConnection();
                con.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/124.0");
                con.setConnectTimeout(8000);
                con.setReadTimeout(8000);
                return ImageIO.read(con.getInputStream());
            }
        };
        tileFactory.setThreadPoolSize(6);
    }

    public DefaultTileFactory getFactory() { 
        return tileFactory; 
    }

}
