package IteratorDirectory;

import java.io.File;
import java.util.List;

/**
 * Класс коллекции загруженных диаграм
 */
public class DiagramCollection implements Aggregate {
    private List<File> fileList;
    private File bi;

    /** Инициализация класса
     * @param fileList список файлов диаграмм
     */
    public DiagramCollection(List<File> fileList){
        this.fileList = fileList;
    }

    /**
     * Класс итератора
     */
    private class DiagramIterator implements Iterator {

        //номер отображаемой диаграммы
        private int current = -1;

        /** Метод для проверки существования диаграммы в списке под указанным номером
         *
         */
        @Override
        public boolean hasNext() {
            if(current < fileList.size() && current >= 0){
                bi = fileList.get(current);
                return true;
            } else{
                return false;
            }
        }

        /**
         * Метод перемещения по спику вперед
         */
        @Override
        public File next() {
            current++;
            if(this.hasNext()){
                return bi;
            } else {
                //если достигли конца списка то начинаем сначала
                current = 0;
                this.hasNext();
                return bi;
            }
        }

        /**
         * Метод перемещения по спику назад
         */
        @Override
        public File previous() {
            current--;
            if(this.hasNext()){
                return bi;
            } else {
                //если достигли конца списка то начинаем с последнего файла
                current = fileList.size()-1;
                this.hasNext();
                return bi;
            }
        }
    }

    /**
     * Мето получения экземпляра класса
     */
    @Override
    public Iterator getIterator() {
        return new DiagramIterator();
    }
}
