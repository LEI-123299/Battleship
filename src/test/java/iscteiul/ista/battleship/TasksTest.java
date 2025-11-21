package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Integração (Correção de Input)")
class TasksTest {

    private final InputStream originalIn = System.in;
    private ByteArrayInputStream testIn;

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    void restoreSystemInput() {
        System.setIn(originalIn);
    }


    private void fixGameInstance(IGame gameInterface) {
        if (gameInterface instanceof Game) {
            Game game = (Game) gameInterface;
            try {
                setPrivateField(game, "countHits", 0);
                setPrivateField(game, "countSinks", 0);
                setPrivateField(game, "countInvalidShots", 0);
                setPrivateField(game, "countRepeatedShots", 0);
            } catch (Exception e) {
                System.err.println("Reflexão falhou.");
            }
        }
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.get(target) == null) {
                field.set(target, value);
            }
        } catch (NoSuchFieldException e) {
        }
    }

     private String generateValidShipsInput() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int row = 0; row < 10; row += 2) {
            for (int col = 0; col < 10; col += 2) {
                if (count < 11) {
                    sb.append("barca ").append(row).append(" ").append(col).append(" n ");
                    count++;
                }
            }
        }
        return sb.toString();
    }


    @Test
    @DisplayName("Deve ler linha e coluna")
    void readPosition() {
        String input = "5 7";
        Scanner scanner = new Scanner(input);
        Position p = Tasks.readPosition(scanner);
        assertAll(
                () -> assertEquals(5, p.getRow()),
                () -> assertEquals(7, p.getColumn())
        );
    }

    @Test
    @DisplayName("Deve ler dados e criar Navio")
    void readShip() {
        String input = "barca 1 1 n";
        Scanner scanner = new Scanner(input);
        IShip ship = Tasks.readShip(scanner);
        assertNotNull(ship);
    }

    @Test
    @DisplayName("Deve ler navios até completar a frota (Input Espaçado)")
    void buildFleet() {
        String input = generateValidShipsInput();

        Scanner scanner = new Scanner(input);
        Fleet fleet = Tasks.buildFleet(scanner);

        assertNotNull(fleet);
        assertFalse(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("Deve realizar disparos")
    void firingRound() {
        Fleet fleet = new Fleet();
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));
        Game game = new Game(fleet);
        fixGameInstance(game);

        String input = "0 0 5 5 9 9";
        Scanner scanner = new Scanner(input);

        Tasks.firingRound(scanner, game);

        assertEquals(3, game.getShots().size());
    }

    @Test
    @DisplayName("Leitura de navios e posições")
    void taskA() {
        String input = "barca 1 1 n 1 1 2 2 3 3 ";
        provideInput(input);
        assertDoesNotThrow(() -> Tasks.taskA());
    }

    @Test
    @DisplayName("Comandos nova/estado/desisto")
    void taskB() {
        StringBuilder sb = new StringBuilder();
        sb.append("nova ");
        sb.append(generateValidShipsInput());
        sb.append("estado desisto");

        provideInput(sb.toString());
        assertDoesNotThrow(() -> Tasks.taskB());
    }

    @Test
    @DisplayName("Comandos incluindo mapa")
    void taskC() {
        StringBuilder sb = new StringBuilder();
        sb.append("nova ");
        sb.append(generateValidShipsInput());
        sb.append("mapa desisto");

        provideInput(sb.toString());
        assertDoesNotThrow(() -> Tasks.taskC());
    }

    @Test
    @DisplayName("Jogo completo (Status/Batota)")
    void taskD() {
        StringBuilder sb = new StringBuilder();
        sb.append("nova ");
        sb.append(generateValidShipsInput());
        sb.append("batota ");
        sb.append("desisto");

        provideInput(sb.toString());
        assertDoesNotThrow(() -> Tasks.taskD());
    }
}