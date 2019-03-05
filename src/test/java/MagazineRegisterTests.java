import com.bookstore.DuplicateEntryException;
import com.bookstore.MagazineRegister;
import com.bookstore.readables.Magazine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MagazineRegisterTests {

    private static MagazineRegister magazineRegister;
    private static Magazine magazineProper;
    private static Magazine magazineProper2;
    private static Magazine magazineProper3;

    @BeforeAll
    public static void setUp() {
        magazineProper = new Magazine("Magazine", "Publisher", 2);
        magazineProper2 = new Magazine("Magazine2", "Publisher2", 2);
        magazineProper3 = new Magazine("Magazine3", "Publisher3", 2);
    }

    @BeforeEach
    public void beforeEach() {
        magazineRegister = new MagazineRegister();
    }

    @Test
    public void shouldReturnTrueWhenAddingAMagazineToRegister() {
        assertDoesNotThrow(() -> magazineRegister.addMagazine(magazineProper));
        assertTrue(magazineRegister.addMagazine(magazineProper2));
    }

    @Test
    public void canNotAddDuplicateMagazineToRegister() {
        magazineRegister.addMagazine(magazineProper);
        assertThrows(DuplicateEntryException.class, () -> magazineRegister.addMagazine(magazineProper));
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfNullIsPassedWhenAddingMagazine() {
        assertThrows(IllegalArgumentException.class, () -> magazineRegister.addMagazine(null));
    }

    @Test
    public void shouldReturnTrueWhenDeletingMagazineWhichExistsInTheRegistry() {
        magazineRegister.addMagazine(magazineProper);
        assertTrue(magazineRegister.deleteMagazine(magazineProper));
    }

    @Test
    public void shouldReturnFalseWhenDeletingMagazineWhichDoesNotExistInRegistry() {
        assertFalse(magazineRegister.deleteMagazine(magazineProper));
    }

    @Test
    public void shouldReturnAnEmptyIteratorWhenSearchingByTitleAndNoTitlesMatch() {
        assertFalse(magazineRegister.searchMagazinesTitles("Magazine").hasNext());
    }

    @Test
    public void shouldReturnNonEmptyIteratorOfMagazinesWhenSearchingByTitleAndTitlesMatch() {
        magazineRegister.addMagazine(magazineProper);
        magazineRegister.addMagazine(magazineProper2);
        magazineRegister.addMagazine(magazineProper3);
        assertTrue(magazineRegister.searchMagazinesTitles("Maga").hasNext());
    }

    @Test
    public void searchShouldReturnNonEmptyIteratorOfMagazinesWhenSearchingByTitleEvenIfCaseIsWrong() {
        magazineRegister.addMagazine(magazineProper);
        magazineRegister.addMagazine(magazineProper2);
        magazineRegister.addMagazine(magazineProper3);
        assertTrue(magazineRegister.searchMagazinesTitles("mAgA").hasNext());
    }

    @Test
    public void shouldReturnNonEmptyIteratorOfMagazinesWhenGettingMagazinesByPublisherOnMatch() {
        magazineRegister.addMagazine(magazineProper);
        magazineRegister.addMagazine(magazineProper2);
        magazineRegister.addMagazine(magazineProper3);
        assertTrue(magazineRegister.searchMagazinesByPublisher("Pub").hasNext());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenSearchingForNull(){
        assertThrows(IllegalArgumentException.class, ()->magazineRegister.searchMagazinesByPublisher(null));
    }

    @Test
    public void shouldReturnEmptyIteratorWhenGettingMagazinesByPublisherAndNoPublisherMatch() {
        assertFalse(magazineRegister.searchMagazinesByPublisher("not existing in here").hasNext());
    }

    @Test
    public void shouldReturnNonEmptyIteratorOfMagazinesWhenGettingMagazinesByPublisherEvenIfCaseIsWrong() {
        magazineRegister.addMagazine(magazineProper);
        magazineRegister.addMagazine(magazineProper2);
        magazineRegister.addMagazine(magazineProper3);
        assertTrue(magazineRegister.searchMagazinesByPublisher("puBli").hasNext());
    }

    @Test
    public void shouldReturnIteratorOfAllElementsInRegister(){
        assertNotNull(magazineRegister.getAllMagazines());
    }

}
