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
public class ServiceProviderFactoryTest {

    private static List<Booking> bookings = new ArrayList<>();
    private static List<Message> messages = new ArrayList<>();

    private static Area area = new Area.Builder().
            setAreaId("area001")
            .setName("Athlone")
            .build();

    private static  ServiceType gardener = new ServiceType.Builder().setTypeId("type03").setTypeName("Painter").build();
    private static  ServiceType painter = new ServiceType.Builder().setTypeId("type02").setTypeName("Gardner").build();
    private static  ServiceType plumber = new ServiceType.Builder().setTypeId("type01").setTypeName("Plumbing").build();


    private static ServiceProvider sp1 = ServiceProviderFactory.createServiceProvider("user007", "Tauriq Osman", "moegamattauriqosman@example.com", "Tauriq04", "0611234567", area ,"tauriq.jpeg","Skilled Gardener with 15 years experience", 350.0, gardener, bookings, messages);
    private static ServiceProvider sp2 = ServiceProviderFactory.createServiceProvider("user008", "Saadiqah Hendricks", "saadiqahhendricks@example.com", "Saadiqah02", "0681234567", area ,"saadiqah.jpeg", "Skilled Painter with 10 years experience", 450.0, painter, bookings, messages );

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
                "user009", "Ameer Arai", "ameeraraiexample.com", "ameer04", "06423456789",area ,"ameer.jpeg", "Skilled Gardener 7 years experience",250.0, gardener ,bookings, messages);
        assertNull(sp3);
        System.out.println(sp3);
    }

    @Test
    @Order(4)
    public void testCreateServiceProviderWithInvalidMobile() {
        ServiceProvider sp4 = ServiceProviderFactory.createServiceProvider(
                "user010", "Donna Lee", "donnalee@example.com", "donna04", "065ttc785",area, "donna.jpeg", "Skilled Painter 9 years experience",550.0, painter ,bookings, messages);
        assertNull(sp4);
        System.out.println(sp4);
    }

    @Test
    @Order(5)
    public void testCreateServiceProviderWithNullFields() {
        ServiceProvider sp5 = ServiceProviderFactory.createServiceProvider(
                null, null, "NaidooK@example.com", null, "0631234567",area,"naidoo.jpeg", "Skilled plumber 35 years experience",700.0, plumber,bookings, messages);
        assertNull(sp5);
        System.out.println(sp5);
    }
}
