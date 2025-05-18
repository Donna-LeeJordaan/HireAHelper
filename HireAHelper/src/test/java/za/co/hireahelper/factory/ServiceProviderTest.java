/* ServiceProviderFactoryTest.java

   Author: MT Osman (230599125)

   Date: 18 May 2025 */

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceProviderTest {

    private static  ServiceType gardener = new ServiceType.Builder().setTypeId("S1").setTypeName("Gardener").build();
    private static  ServiceType painter = new ServiceType.Builder().setTypeId("S2").setTypeName("Painter").build();
    private static  ServiceType plumber = new ServiceType.Builder().setTypeId("S3").setTypeName("Plumber").build();
    private static List<Booking> bookings = new ArrayList<>();
    private static List<Message> messages = new ArrayList<>();


    private static ServiceProvider sp1 = ServiceProviderFactory.createServiceProvider("SP1", "Tauriq Osman", "moegamattauriqosman@gmail.com", "Tauriq04", "0611234567", "tauriq.jpeg", "Skilled Gardener with 15 years experience", 350, gardener, bookings, messages );
    private static ServiceProvider sp2 = ServiceProviderFactory.createServiceProvider("SP2", "Saadiqah Hendricks", "saadiqahhendricks@gmail.com", "Saadiqah02", "0681234567", "saadiqah.jpeg", "Skilled Gardener with 10 years experience", 450, painter, bookings, messages );
    private static ServiceProvider sp3 = ServiceProviderFactory.createServiceProvider("SP3", "Gabriel Kiewiets", "gabrielkiewiets@gmail.com", "Gabriel04", "0671234567", "gabriel.jpeg", "Skilled Plumber with 20 years experience", 250, plumber, bookings, messages );


    @Test
    @Order(1)
    public void testCreateServiceProvider() {
        assertNotNull(sp1);
        System.out.println(sp1);
    }

    @Test
    @Order(2)
    public void testCreateServiceProviderWithAllAttributes() {
        assertNotNull(sp2);
        System.out.println(sp2);
    }

    @Test
    @Order(3)
    public void testCreateServiceProviderWithInvalidEmail() {
        ServiceProvider sp4 = ServiceProviderFactory.createServiceProvider(
                "SP4", "Ameer Arai", "ameeraraigmail.com", "ameer04", "06423456789","ameer.jpeg", "Skilled Gardener 7 years experience",250, gardener ,bookings, messages);
        assertNull(sp4);
        System.out.println(sp4);
    }

    @Test
    @Order(4)
    public void testCreateServiceProviderWithInvalidMobile() {
        ServiceProvider sp5 = ServiceProviderFactory.createServiceProvider(
                "SP4", "Donna Lee", "donnalee@gmail.com", "donna04", "065ttc785","donna.jpeg", "Skilled Painter 9 years experience",550, painter ,bookings, messages);
        assertNull(sp5);
        System.out.println(sp5);
    }

    @Test
    @Order(5)
    public void testCreateServiceProviderWithNullFields() {
        ServiceProvider sp6 = ServiceProviderFactory.createServiceProvider(
                "", "", "NaidooK@gmail.com", "", "0631234567","naidoo.jpeg", "Skilled plumber 35 years experience",700, plumber,bookings, messages);
        assertNull(sp6);
        System.out.println(sp6);
    }
}
