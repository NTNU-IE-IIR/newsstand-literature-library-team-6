import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.bookstore.readables.Magazine;

public class MagazineTest {

    Magazine magazineProper;
    Magazine magazineNegativePublications;

    @BeforeEach
    public void beforeEach(){
        magazineProper = new Magazine("Title", "Publisher", 2);
        magazineNegativePublications = new Magazine("Title", "Publisher", -2);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTitleIsNull(){
        assertThrows(IllegalArgumentException.class, ()-> new Magazine(null, "weewr", 2));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPublisherIsNull(){
        assertThrows(IllegalArgumentException.class, ()-> new Magazine("ewrw", null, 2));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTitleIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> new Magazine("", "weewr", 2));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPublisherIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> new Magazine("wqeqe", "", 2));
    }

    @Test
    public void magazinePublicationsPerYearShouldReturnAPositivIntEvenWhenAnegativeIsProvided(){
        assertTrue(magazineNegativePublications.getPublicationsPerYear() >= 0);
        assertTrue(magazineProper.getPublicationsPerYear() >= 0);
    }

    @Test
    public void shouldReturnAStringWhenGettingTitle(){
        assertEquals("Title", magazineProper.getTitle());
    }

    @Test
    public void shouldReturnAStringWhenGettingPublisher(){
        assertEquals("Publisher", magazineProper.getPublisher());
    }
    @Test
    public void shouldReturnAnIntWhenGettingPublicationsPerYear(){
        assertEquals(2, magazineProper.getPublicationsPerYear());
    }
}
