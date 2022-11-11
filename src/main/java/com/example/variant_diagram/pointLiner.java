package com.example.variant_diagram;

/** Класс для хранения информации о координатах оптимального начала/конца стрелок
 * @param <X> coordinate
 * @param <Y> coordinate
 */
public class PointLiner<X,Y> {
    public X _item1;
    public Y _item2;

    public PointLiner(X _item1, Y _item2) {
        this._item1 = _item1;
        this._item2 = _item2;
    }
}
