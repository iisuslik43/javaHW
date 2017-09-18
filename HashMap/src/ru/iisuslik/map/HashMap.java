package ru.iisuslik.map;

import ru.iisuslik.list.List;

/**
 * Хэш таблица на массиве списков с перехэшированием
 * (при слишком большом количестве ключей увеличивает массив в 2 раза)
 */
public class HashMap {

    /** хранит массив списков*/
    private List[] listArray;

    /**размер массива списков*/
    private int arraySize = 2;

    /**сколько ключей находится в хэш таблице в данный момент*/
    private int keyCount = 0;

    public static void main(String[] args) {
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

    private int getHash(String key){
        return (key.hashCode() ^ 2 + 7) % arraySize;
    }

    public HashMap() {
        listArray = new List[arraySize];
        for(int i = 0; i < arraySize; i++) {
            listArray[i] = new List();
        }
    }

    /**количество ключей в хеш-таблице*/
    public int size(){
        return keyCount;
    }

    /** true, если данный ключ содержится в хеш-таблице, и false иначе*/
     public boolean contains(String key) {
         String result = get(key);
         if(result != null) {
            return true;
         }
         return false;
    }

    /** возвращает значение по ключу, или null, если такого значения нет
     *
     * @param key ключ, по которому нужно найти значение
     **/
    public String get(String key) {
        int index = getHash(key);
        String found = listArray[index].find_key(key);
        if(found == null) {
            return null;
        }
        return found;
    }

    /** положить в хеш-таблицу значение value по ключу key и вернуть то,
     * что было по этому ключу раньше, либо null, если ничего не было
     *
     * @param key ключ, который нужно добавить
     * @param value значение, которое нужно добавить по ключу
     */
    public String put(String key, String value) {
        int index = getHash(key);
        String oldValue = listArray[index].add(key, value);
        if(oldValue == null) {
            keyCount++;
            if(keyCount > arraySize) {
                rehash();
            }
        }
        return oldValue;
    }

    /** удалить значение по заданному ключу из хеш-таблицы
     * и вернуть удалённое значение, либо null, если такого значения не было
     *
     * @param key ключ, который нужно удалить
     */
    public String remove(String key) {
        int index = getHash(key);
        String delValue = listArray[index].delete(key);
        if(delValue != null) {
            keyCount--;
        }
        return delValue;
    }

    /** очистить хеш-таблицу*/
    public void clear() {
        for(List list : listArray) {
            list.clear();
        }
        keyCount = 0;

    }

    private void rehash() {
        List[] oldArray = listArray;
        listArray = new List[arraySize *= 2];
        keyCount = 0;
        for(int i = 0; i < arraySize; i++) {
            listArray[i] = new List();
        }
        for(List list : oldArray) {
            while(list.getHead() != null) {
                this.put(list.getHead().key, list.getHead().value);
                list.delete(list.getHead().key);
            }
        }

    }

}
