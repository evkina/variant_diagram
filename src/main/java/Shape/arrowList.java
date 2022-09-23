package Shape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * list of arrows
 */
public class arrowList implements Serializable {
    public List<arrow> _arrowList;
    public arrowList()
    {
        _arrowList = new ArrayList<>();
    }
}
