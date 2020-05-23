import org.osgeo.proj4j.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class Main {



    public static void main(String[] args) {
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CRSFactory csFactory = new CRSFactory();

        String csName = "EPSG:4647";
        final String EPSG4647_PARAM = "+proj=tmerc +lat_0=0 +lon_0=9 +k=0.9996 +x_0=32500000 +y_0=0 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs ";
        CoordinateReferenceSystem crs = csFactory.createFromParameters(csName, EPSG4647_PARAM);

        final String WGS84_PARAM = "+title=long/lat:WGS84 +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees";
        CoordinateReferenceSystem WGS84 = csFactory.createFromParameters("WGS84", WGS84_PARAM);

        CoordinateTransform trans = ctFactory.createTransform(WGS84, crs);

        String[] toConvert = {
                "51.743994, 13.983766",
                "51.745402, 13.981223",
                "51.744512, 13.981405",
                "53.860388, 10.668646"
        };

        for(String s : toConvert) {
            String[] coords = s.split(",");
            ProjCoordinate p = new ProjCoordinate();
            ProjCoordinate p2 = new ProjCoordinate(); //the result of the projection will be saved here

            //assign the coordinates to be transformed
            p.y = Double.valueOf(coords[0].trim());
            p.x = Double.valueOf(coords[1].trim());

            trans.transform(p, p2);
            System.out.printf("%.0f", p2.x);
            System.out.printf(" %.0f\n", p2.y);
        }
    }
}
