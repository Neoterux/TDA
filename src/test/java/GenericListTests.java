import com.neoterux.tda.list.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("A collection of tests for List TDA implementations")
public class GenericListTests {

    public List<Integer> target;
//
//    public List<Integer> simpleLinkedList = new SimpleLinkedList<>();
//
//    public List<Integer> simpleCircularArrayList = new SimpleDoublyLinkedList<>();
//
//    public final static List<Integer> linkedList = new DLinkedList<>();

    @BeforeAll
    @DisplayName("Instanciation")
    void instanceTargetListClass() {
        target = new ArrayList<>();
    }




    @DisplayName("Test for ArrayList")
    @Test
    @Order(1)
    public void testArrayList() {
        genericTest(new ArrayList<>());
//        genericMutableTest(new ArrayList<>());
    }

    @Test
    @DisplayName("Test for Simple LinkedList")
    @Order(2)
    public void testSimpleLinkedList(){
        genericTest(new SimpleLinkedList<>());
        genericMutableTest(new SimpleLinkedList<>());
    }

    @Test
    @DisplayName("Test for Simple Circular LinkedList")
    @Order(3)
    public void testSimpleDoublyLinkedList() {
        genericTest(new SCLinkedList<>());
        genericMutableTest(new SCLinkedList<>());
    }

    @Test
    @DisplayName("Test for Double Circular LinkedList")
    @Order(4)
    public void testLinkedList() {
        genericTest(new LinkedList<>());
        genericMutableTest(new LinkedList<>());
    }

    @Test
    @DisplayName("Test for List class with most of the methods.")
    private static void genericTest(List<Integer> lst) {
        lst.clear();

        for (int i = 0; i < 10; i++) {
            lst.addLast(i);
        }
        System.out.println("List: " + lst);
        System.out.println("Testing removeFirst method...");
        assertEquals(0, lst.removeFirst());
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", lst.toString());
        System.out.println("passed "+ lst);

        System.out.println("Testing removeLast method...");
        assertEquals(9, lst.removeLast());
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8]", lst.toString());
        System.out.println("passed "+ lst);

        System.out.println("Testing remove method...");
        assertEquals(5, lst.remove(4));
        assertEquals("[1, 2, 3, 4, 6, 7, 8]", lst.toString());
        System.out.println("passed "+ lst);

        System.out.println("Testing get method...");
        assertEquals(6, lst.get(4));
        System.out.println("passed "+ lst);

        System.out.println("Testing set method...");
        assertEquals(6, lst.set(4, 33));
        assertEquals("[1, 2, 3, 4, 33, 7, 8]", lst.toString());
        System.out.println("passed "+ lst);

        List<Integer> l2 = new ArrayList<>();
        for (int i = 0; i < 10; i+=2) {
            l2.addLast(i);
        }
        System.out.println("Target intersection list: " + l2);
        List<Integer> find = lst.findAll(33);

        System.out.println("Testing findAll method...");
        assertEquals("[33]", find.toString());
        System.out.println("passed");

        System.out.println("Testing findAll with comparator method...");
        find = lst.findAll(6, Integer::compareTo);
        assertEquals("[]", find.toString());
        System.out.println("passed");

        var intersection = lst.intersectionWith(l2);

        assertEquals("[2, 4, 8]", intersection.toString());

        System.out.println("Intersection: " + intersection);
        System.out.println("List after add/removes: " + lst);

        var iterator = lst.iterator();
        int sum = 0;

        while(iterator.hasNext()){
            sum += iterator.next();
        }
        assertEquals(58, sum);
        System.out.println("GENERIC TEST PASSED FOR " + lst.getClass().getCanonicalName());
    }

    @Test
    @DisplayName("Test for MutableList TDA with most of the methods")
    private static void genericMutableTest(MutableList<Integer> lst){
        lst.clear();
        System.out.println("populatin list...");
        for (int i = 0; i < 10; i++) {
            lst.addLast(i);
        }
        System.out.println("populated " + lst);

        System.out.println("Testing method keep only inside list bounds...");
        lst.keepOnly(2, lst.size() - 1);
        assertEquals("[2, 3, 4, 5, 6, 7, 8, 9]", lst.toString());
        System.out.println("passed");

        System.out.println("Testing keepOnly method out of bounds...");
        lst.keepOnly(1, 20);
        assertEquals("[3, 4, 5, 6, 7, 8, 9]", lst.toString());
        System.out.println("passed " + lst);

        System.out.println("Testing keepOnly method invalid arguments..");
        assertThrows(IllegalArgumentException.class, () -> {
            lst.keepOnly(4, 3);
        });
        System.out.println("Passed");
//        try {
//            lst.keepOnly(4, 3);
//            System.out.println("keepOnly method executed, do you have implemented correctly?");
//        }catch (IllegalArgumentException | IndexOutOfBoundsException ia){
//
//            System.out.println("passed");
//        }catch (Exception e){
//            System.out.println("Unknown exception occur, please read stacktrace");
//            e.printStackTrace();
//        }
        System.out.println(lst);
        System.out.println("Testing detach method inside bounds...");
        lst.detach(3,4);
        assertEquals("[3, 4, 5, 8, 9]", lst.toString());
        System.out.println("passed");

        System.out.println("Testing detach method out of bounds...");
        lst.detach(1, 9);
        assertEquals("[3]", lst.toString());
        System.out.println("passed");

        System.out.println("Testing detach with invalid index: ");
        assertThrows(IllegalArgumentException.class,() -> lst.detach(4, 3));
//        try {
//            lst.detach(4, 3);
//            System.out.println("keepOnly method executed, do you have implemented correctly?");
//        }catch (IllegalArgumentException | IndexOutOfBoundsException ia){
//            System.out.println("passed");
//        }catch (Exception e){
//            System.out.println("Unknown exception occur, please read stacktrace");
//            e.printStackTrace();
//        }

    }
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    public class Generic_List_Test implements interfaces.ListTest {



        @Override
        @Test
        @DisplayName("Testing AddFirst")
        @Order(1)
        public void addFirstTest() {
            System.out.println("before addFirst: " + target);
            for (int i = 0; i < 10; i++) {
                assertTrue(target.addFirst(i));
            }
        }

        @Override
        @Test
        @DisplayName("Testing addLast")
        @Order(2)
        public void addLastTest() {
            System.out.println("before addLast: " + target);
            for (int i = 10; i < 20; i++) {
                assertTrue(target.addLast(i));
            }
        }

        @Override
        @Test
        @DisplayName("Testing add")
        @Order(3)
        public void addTest(){
            System.out.println("before add: " + target);
            target.add(0, 100);
        }

        @Override
        @Test
        @DisplayName("Testing get")
        @Order(4)
        public void getTest(){
            assertEquals(100, target.get(0));
        }

        @Override
        @Test
        @DisplayName("Testing remove")
        @Order(7)
        public void removeTest(){
            int[] expected = {94, 34};
            int[] rm_idx = {1, 9};

            for(int i = 0; i < rm_idx.length; i++){
                assertEquals(expected[i], target.remove(rm_idx[i]));
            }

        }

        @Override
        @Test
        @DisplayName("Testing removeFirst")
        @Order(5)
        public void removeFirstTest(){
            assertEquals(100, target.removeFirst());
        }

        @Override
        @Test
        @DisplayName("Testing removeLast")
        @Order(6)
        public void removeLastTest(){
            int[] expect = {19, 18, 17};
            for (int j : expect) {
                assertEquals(j, target.removeLast());
            }
        }



        @Override
        @Test
        @DisplayName("Testing set")
        @Order(8)
        public void setTest(){


        }

        @Test
        @DisplayName("Testing size")
        @Order(9)
        public void sizeTest(){

        }

        @Test
        @DisplayName("Testing isEmpty")
        @Order(10)
        public void isEmptyTest(){

        }

        @Test
        @DisplayName("Testing findAll")
        @Order(11)
        public void findAllTestEquals(){

        }

        @Test
        @DisplayName("Testing findAll with comparator")
        @Order(12)
        public void findAllTestComparator(){

        }

        @Test
        @DisplayName("Testing intersectionWith")
        @Order(13)
        public void intersectionTest(){

        }

        @Test
        @AfterAll
        @DisplayName("Testing clear")
        @Order(14)
        public void clear(){
            target.clear();
            assertNull(target.get(0));
        }
    }


    @Nested
    @DisplayName("Generic MutableList TDA Test")
    private class TestMutableListMethod{

    }

}
