package com.example.variant_diagram;

import Shape.shapes;


/**
 * SubClass for storage of Path for arrows
 */
public class paths {
    public shapes[] _arr;

    public String get_type() {
        return _type;
    }

    public String _type;
    public paths(shapes _from, shapes _to, String _type)
    {
        this._type = _type;
        _arr = new shapes[2];
        _arr[0]= _from;
        _arr[1] = _to;
    }
}
