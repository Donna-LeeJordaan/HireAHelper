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

    private static Area area = new Area.Builder().setAreaId("A1").setName("Cape Town").build();

    private static List<Booking> bookings = new ArrayList<>();
    private static List<Message> messages = new ArrayList<>();

    private static ServiceProvider sp1 = ServiceProviderFactory.createServiceProvider("SP1", "Tauriq Osman", "moegamattauriqosman@gmail.com", "Tauriq04", "0611234567", area ,"tauriq.jpeg","Skilled Gardener with 15 years experience", 350.0, gardener, bookings, messages );
    private static ServiceProvider sp2 = ServiceProviderFactory.createServiceProvider("SP2", "Saadiqah Hendricks", "saadiqahhendricks@gmail.com", "Saadiqah02", "0681234567", area ,"saadiqah.jpeg", "Skilled Painter with 10 years experience", 450.0, painter, bookings, messages );

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
        ServiceProvider sp3 = ServiceProviderFactory.createServiceProvider(
                "SP3", "Ameer Arai", "ameeraraigmail.com", "ameer04", "06423456789",area ,"ameer.jpeg", "Skilled Gardener 7 years experience",250.0, gardener ,bookings, messages);
        assertNull(sp3);
        System.out.println(sp3);
    }

    @Test
    @Order(4)
    public void testCreateServiceProviderWithInvalidMobile() {
        ServiceProvider sp4 = ServiceProviderFactory.createServiceProvider(
                "SP4", "Donna Lee", "donnalee@gmail.com", "donna04", "065ttc785",area, "donna.jpeg", "Skilled Painter 9 years experience",550.0, painter ,bookings, messages);
        assertNull(sp4);
        System.out.println(sp4);
    }

    @Test
    @Order(5)
    public void testCreateServiceProviderWithNullFields() {
        ServiceProvider sp5 = ServiceProviderFactory.createServiceProvider(
                null, null, "NaidooK@gmail.com", null, "0631234567",area,"naidoo.jpeg", "Skilled plumber 35 years experience",700.0, plumber,bookings, messages);
        assertNull(sp5);
        System.out.println(sp5);
    }
}
