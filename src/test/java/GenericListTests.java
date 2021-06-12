import com.neoterux.tda.list.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenericListTests {

    @Test
    public void testArrayList() {
        genericTest(new ArrayList<>());
    }

    @Test
    public void testSimpleLinkedList(){
        genericTest(new SimpleLinkedList<>());
    }

    @Test
    public void testSimpleDoublyLinkedList() {
        genericTest(new SimpleDoublyLinkedList<>());
    }

    @Test
    public void testLinkedList() {
        genericTest(new DLinkedList<>());
    }

    @Test
    private static void genericTest(List<Integer> lst) {
        lst.clear();

        for (int i = 0; i < 10; i++) {
            lst.addLast(i);
        }
        System.out.println("List: " + lst);
        Assertions.assertEquals(0, lst.removeFirst());
        Assertions.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", lst.toString());

        Assertions.assertEquals(9, lst.removeLast());
        Assertions.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8]", lst.toString());

        Assertions.assertEquals(5, lst.remove(4));
        Assertions.assertEquals("[1, 2, 3, 4, 6, 7, 8]", lst.toString());

        Assertions.assertEquals(6, lst.get(4));

        Assertions.assertEquals(6, lst.set(4, 33));
        Assertions.assertEquals("[1, 2, 3, 4, 33, 7, 8]", lst.toString());

        List<Integer> l2 = new ArrayList<>();
        for (int i = 0; i < 10; i+=2) {
            l2.addLast(i);
        }
        System.out.println("Target intersection list: " + l2);
        List<Integer> find = lst.findAll(33);
        Assertions.assertEquals("[33]", find.toString());

        find = lst.findAll(6, Integer::compareTo);

        Assertions.assertEquals("[]", find.toString());

        var intersection = lst.intersectionWith(l2);

        Assertions.assertEquals("[2, 4, 8]", intersection.toString());

        System.out.println("Intersection: " + intersection);
        System.out.println("List after add/removes: " + lst);

        var iterator = lst.iterator();
        int sum = 0;

        while(iterator.hasNext()){
            sum += iterator.next();
        }
        Assertions.assertEquals(58, sum);
        System.out.println("GENERIC TEST PASSED FOR " + lst.getClass().getCanonicalName());
    }
}
