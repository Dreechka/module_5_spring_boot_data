package org.example.booking;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.example.booking.dto.ReservationDtoRs;
import org.example.booking.entities.Client;
import org.example.booking.entities.Reservation;
import org.example.booking.entities.Room;
import org.example.booking.entities.RoomType;
import org.example.booking.repo.ClientDao;
import org.example.booking.repo.ReservationDao;
import org.example.booking.repo.RoomDao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationControllerTest {
    private final ReservationDao reservationDao;
    private final ClientDao clientDao;
    private final RoomDao roomDao;
    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        baseURI = "http://localhost:" + port;
    }

    @PostConstruct
    public void fillDb() {
        Client bobby = clientDao.save(new Client("Bobby", "bob@ya.ru"));
        Room room = roomDao.save(new Room("222", RoomType.ECONOMY));
        reservationDao.save(new Reservation(LocalDate.of(2022, 12, 01),
                LocalDate.of(2022, 12, 05), bobby, room));
    }

    @Nested
    public class CreateReservationTest {

        @Test
        @DisplayName("Успешное создание брони")
        void createReservationIT() {
            String inputBody = "{\n" +
                    "  \"roomName\": \"222\",\n" +
                    "  \"checkIn\":\"2022-12-10\",\n" +
                    "  \"checkOut\":\"2022-12-15\",\n" +
                    "  \"clientDtoRq\":  {\n" +
                    "      \"name\": \"Sam\",\n" +
                    "       \"email\": \"sam@ya.ru\"\n" +
                    "    }\n" +
                    "}";
            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(inputBody)
                    .when()
                    .post("/reservation")
                    .then()
                    .extract()
                    .response();
            Assertions.assertEquals(200, response.statusCode());
            Optional<Reservation> byId = reservationDao.findById(2L);
            String expected = byId.get().getResNumber();
            String actual = response.body().asString();
            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Неуспешное создание брони")
        void createReservationFailIT() {
            String inputBody = "{\n" +
                    "  \"roomName\": \"222\",\n" +
                    "  \"checkIn\":\"2022-12-01\",\n" +
                    "  \"checkOut\":\"2022-12-05\",\n" +
                    "  \"clientDtoRq\":  {\n" +
                    "      \"name\": \"Sam\",\n" +
                    "       \"email\": \"sam@ya.ru\"\n" +
                    "    }\n" +
                    "}";
            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(inputBody)
                    .when()
                    .post("/reservation")
                    .then()
                    .extract()
                    .response();
            Assertions.assertEquals(400, response.statusCode());
            String expected = "Выбранный номер на указанные даты уже забронирован";
            String actual = response.body().asString();
            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    public class GetReservationTest {

        @Test()
        @DisplayName("Успешное получение бронирований по email")
        public void getReservationByCustomerEmailIT() {
            String currentResNumber = reservationDao.findById(1L).get().getResNumber();
            ReservationDtoRs expected = ReservationDtoRs.builder()
                    .resNumber(currentResNumber)
                    .checkIn(LocalDate.of(2022, 12, 01))
                    .checkOut(LocalDate.of(2022, 12, 05))
                    .clientName("Bobby")
                    .roomName("222")
                    .build();
            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/reservation?email=bob@ya.ru")
                    .then()
                    .extract()
                    .response();
            List<ReservationDtoRs> bookingDtoRs = response.body().as(new TypeRef<>() {
            });
            Assertions.assertEquals(1, bookingDtoRs.size());
            Assertions.assertEquals(expected, bookingDtoRs.get(0));
        }

        @Test()
        @DisplayName("Неуспешное получение бронирований по email")
        public void getReservationByCustomerEmailFailIT() {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/reservation?customerEmail=failtest@ya.ru")
                    .then()
                    .extract()
                    .response();
            Assertions.assertEquals(400, response.statusCode());
        }

        @Test()
        @DisplayName("Успешное получение бронирований по номеру брони")
        public void getReservationByNumberIT() {
            String currentResNumber = reservationDao.findById(1L).get().getResNumber();
            ReservationDtoRs expected = ReservationDtoRs.builder()
                    .resNumber(currentResNumber)
                    .checkIn(LocalDate.of(2022, 12, 01))
                    .checkOut(LocalDate.of(2022, 12, 05))
                    .clientName("Bobby")
                    .roomName("222")
                    .build();
            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/reservation?resNumber=" + currentResNumber)
                    .then()
                    .extract()
                    .response();
            ReservationDtoRs actual = response.body().as(ReservationDtoRs.class);
            Assertions.assertEquals(expected, actual);
        }

        @Test()
        @DisplayName("Неуспешное получение бронирований по номеру брони")
        public void getReservationByNumberFailIT() {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/reservation?resNumber=failNumber")
                    .then()
                    .extract()
                    .response();
            Assertions.assertEquals(400, response.statusCode());
        }
    }

    @Nested
    public class DeleteReservationTest {

        @Test
        @DisplayName("Успешное удаление брони по номеру")
        public void deleteReservationIT() {
            Client victor = clientDao.save(new Client("Victor", "victor@ya.ru"));
            Room room = roomDao.save(new Room("555", RoomType.ECONOMY));
            Reservation reservation = reservationDao.save(new Reservation(LocalDate.of(2022, 12, 06),
                    LocalDate.of(2022, 12, 07), victor, room));
            String currentResNumber = reservation.getResNumber();
            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete("/reservation/" + currentResNumber)
                    .then()
                    .extract()
                    .response();
            Assertions.assertEquals(200, response.statusCode());
            Optional<Reservation> actual = reservationDao.findFirstByResNumber(currentResNumber);
            Assertions.assertTrue(actual.isEmpty());
        }

        @Test
        @DisplayName("Неуспешное удаление брони по номеру")
        public void deleteReservationFailIT() {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete("/reservation/aaaaa")
                    .then()
                    .extract()
                    .response();
            Assertions.assertEquals(400, response.statusCode());
        }
    }
}
