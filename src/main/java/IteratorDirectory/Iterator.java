package IteratorDirectory;

import java.io.File;

/**
 * Интерфейс Итератора
 * Автор Евкина Дарья
 */
public interface Iterator {
    public boolean hasNext();
    public File next();
    public File previous();
}