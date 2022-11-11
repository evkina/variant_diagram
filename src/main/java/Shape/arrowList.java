package Shape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Список стрелок
 */
public class ArrowList implements Serializable {
    public List<Arrow> _arrowList;
    public ArrowList()
    {
        _arrowList = new ArrayList<>();
    }
}
