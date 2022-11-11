package com.example.variant_diagram;

import Shape.Shapes;


/**
 * Побочный класс для хранения траектории стрелок
 */
public class Paths {
    public Shapes[] _arr;

    public String get_type() {
        return _type;
    }

    public String _type;
    public Paths(Shapes _from, Shapes _to, String _type) {
        this._type = _type;
        _arr = new Shapes[2];
        _arr[0]= _from;
        _arr[1] = _to;
    }
}
