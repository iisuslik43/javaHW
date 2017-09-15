package ru.iisuslik.map;

import ru.iisuslik.list.List;

/**
 * Хэш таблица на массиве списков с перехэшированием
 * (при слишком большом количестве ключей увеличивает массив в 2 раза)
 */

public class HashMap {
    /**
     * хранит массив списков
     */
    private List[] listArray;
    /**
     * размер массива списков
     */
    private int arraySize;
    /**
     * сколько ключей находится в хэш таблице в данный момент
     */
    private int keyCount;

    public static void main(String[] args){
        HashMap map = new HashMap();
        map.put("top", "kek");
        map.put("che", "burek");
        map.put("izi", "win");
        System.out.println(map.size() == 3);
        System.out.println(map.get("top") == "kek");
        System.out.println(map.get("fff") == null);
        System.out.println(map.contains("che") == true);
        System.out.println(map.contains("as") == false);
        System.out.println(map.remove("top") == "kek");
        System.out.println(map.remove("cas") == null);
        System.out.println(map.size() == 2);
        map.clear();

    }

    public HashMap(){
        keyCount = 0;
        arraySize = 16;
        listArray = new List[arraySize];
        for(int i = 0; i < arraySize; i++)
            listArray[i] = new List();
    }

    /**количество ключей в хеш-таблице
     *
     */
    public int size(){
        return keyCount;
    }

    /** true, если данный ключ содержится в хеш-таблице, и false иначе
     *
     */
     public boolean contains(String key){
        int index = key.hashCode() % arraySize;
        if(listArray[index].find_key(key) != null){
            return true;
        }
        return false;
    }
    /** возвращает значение по ключу, или null, если такого значения нет
     * @param key ключ, по которому нужно найти значение
     **/
    public String get(String key){
        int index = key.hashCode() % arraySize;
        String found = listArray[index].find_key(key);
        if(found == null){
            return null;
        }
        return found;
    }

    /** положить в хеш-таблицу значение value по ключу key и вернуть то, что было по этому ключу раньше, либо null, если ничего не было
     *
     * @param key ключ, который нужно добавить
     * @param value значение, которое нужно добавить по ключу
     */
    public String put(String key, String value){
        int index = key.hashCode() % arraySize;
        String old_value = listArray[index].add(key, value);
        if(old_value == null){
            keyCount++;
            if(keyCount > arraySize){
                rehash();
            }
        }
        return old_value;
    }

    /** удалить значение по заданному ключу из хеш-таблицы и вернуть удалённое значение, либо null, если такого значения не было
     *
     * @param key ключ, который нужно удалить
     */
    public String remove(String key){
        int index = key.hashCode() % arraySize;
        String del_value = listArray[index].delete(key);
        if(del_value != null){
            keyCount--;
        }
        return del_value;
    }

    /** очистить хеш-таблицу
     *
     */
    public void clear(){
        for(List l : listArray){
            l.clear();
        }
        keyCount = 0;

    }

    private void rehash(){
        List[] oldArray = listArray;
        listArray = new List[arraySize *= 2];
        for(List list : oldArray){
            while(list.head != null){
                this.put(list.head.key, list.head.value);
                list.delete(list.head.key);
            }
        }

    }

}
