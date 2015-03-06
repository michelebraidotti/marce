package marce.domain;

/**
 * Created by michele on 3/6/15.
 */
public class PostoFactory {

    public static final Posto getPostoIngoto() {
        Posto postoIgnoto = new Posto();
        postoIgnoto.setLocalita("Ignoto");
        postoIgnoto.setZona("Ignoto");
        return postoIgnoto;
    }
}
