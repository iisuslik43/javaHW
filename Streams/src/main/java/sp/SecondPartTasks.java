package sp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {

        return paths.stream().flatMap(path-> {
            Stream<String> str = null;
            try{
            str = Files.lines(Paths.get(path));
            } catch (Exception ignored) {}
            return str;
        }). filter(s->s.contains(sequence)).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Random rnd = new Random();
        int count = 100000;
        DoubleStream res = rnd.doubles(count,-0.5, 0.5);
        return (double) res.filter(d->{
            double d2 = rnd.doubles(1,-0.5,0.5).findAny().getAsDouble();
            return (d * d + d2 * d2 <= 0.25);
        }).count() / count ;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet().stream().max(
                Comparator.comparing(e -> e.getValue().stream().reduce("", (s1,l)-> s1.length() + l))
        ).get().getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream().flatMap(m->m.entrySet().stream()).collect(Collectors.groupingBy(
                Map.Entry::getKey,
                Collectors.summingInt(Map.Entry::getValue)
        ));
    }
}