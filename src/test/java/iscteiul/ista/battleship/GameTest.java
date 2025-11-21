package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Lógica de Jogo")
class GameTest {

    private Game game;
    private Fleet fleet;
    private IPosition pos0_0;
    private IPosition pos1_1;
    private IPosition invalidPos11_11;

    private IShip createBargeAt(int row, int col) {
        return new Barge(Compass.NORTH, new Position(row, col));
    }

    private IShip createFrigateAt(int row, int col) {
        return new Frigate(Compass.NORTH, new Position(row, col));
    }

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
        game = new Game(fleet);

        pos0_0 = new Position(0, 0);
        pos1_1 = new Position(1, 1);
        invalidPos11_11 = new Position(11, 11);

        try {
            initializePrivateField(game, "countHits", 0);
            initializePrivateField(game, "countSinks", 0);
            if (getPrivateField(game, "countInvalidShots") == null) initializePrivateField(game, "countInvalidShots", 0);
            if (getPrivateField(game, "countRepeatedShots") == null) initializePrivateField(game, "countRepeatedShots", 0);
        } catch (Exception e) {
            fail("Falha ao inicializar contadores via Reflection: " + e.getMessage());
        }
    }

    private void initializePrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Object getPrivateField(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    @Nested
    @DisplayName("1. Testes de Disparo (fire) e Contadores")
    class FireAndCountersTests {

        @Test
        @DisplayName("1.1. Tiro na Água (Miss)")
        void fire_miss_shouldReturnNull() {
            IShip result = game.fire(pos0_0);

            assertAll("Tiro na Água",
                    () -> assertNull(result),
                    () -> assertEquals(1, game.getShots().size()),
                    () -> assertEquals(0, game.getHits())
            );
        }

        @Test
        @DisplayName("1.2. Tiro de Acerto (Hit)")
        void fire_hit_shouldIncrementHits() {
            IShip ship = createFrigateAt(1, 1);
            fleet.addShip(ship);

            IShip result = game.fire(pos1_1);

            assertAll("Tiro de Acerto",
                    () -> assertNull(result),
                    () -> assertEquals(1, game.getHits(), "Hits deve ser incrementado (agora inicializado via Reflection)."),
                    () -> assertEquals(1, game.getShots().size())
            );
        }

        @Test
        @DisplayName("1.3. Tiro de Afundamento (Sink)")
        void fire_sink_shouldReturnSunkShip() {
            IShip ship = createBargeAt(0, 0);
            fleet.addShip(ship);

            IShip result = game.fire(pos0_0);

            assertAll("Tiro de Afundamento",
                    () -> assertNotNull(result),
                    () -> assertEquals(1, game.getHits()),
                    () -> assertEquals(1, game.getSunkShips()),
                    () -> assertEquals(0, game.getRemainingShips())
            );
        }

        @Test
        @DisplayName("1.4. Tiro Repetido")
        void fire_repeatedShot_shouldIncrementRepeatedCount() {
            game.fire(pos0_0);
            IShip result = game.fire(pos0_0);

            assertAll("Tiro Repetido",
                    () -> assertNull(result),
                    () -> assertEquals(1, game.getRepeatedShots()),
                    () -> assertEquals(1, game.getShots().size())
            );
        }

        @Test
        @DisplayName("1.5. Tiro Inválido")
        void fire_invalidShot_shouldIncrementInvalidCount() {
            IShip result = game.fire(invalidPos11_11);

            assertAll("Tiro Inválido",
                    () -> assertNull(result),
                    () -> assertEquals(1, game.getInvalidShots()),
                    () -> assertEquals(0, game.getShots().size())
            );
        }
    }

    @Test
    @DisplayName("2.1. getShots")
    void getShots() {
        game.fire(pos0_0);
        game.fire(invalidPos11_11);
        game.fire(pos0_0);

        List<IPosition> shots = game.getShots();

        assertEquals(1, shots.size());
        assertTrue(shots.contains(pos0_0));
    }

    @Test
    @DisplayName("2.2. getRemainingShips")
    void getRemainingShips() {
        IShip s1 = createBargeAt(0, 0);
        IShip s2 = createFrigateAt(2, 2);
        fleet.addShip(s1);
        fleet.addShip(s2);

        assertEquals(2, game.getRemainingShips());

        game.fire(pos0_0);

        assertEquals(1, game.getRemainingShips());
    }


    @Test
    @DisplayName("3.1. printBoard")
    void printBoard() {
        fleet.addShip(createBargeAt(1, 1));
        game.fire(pos0_0);
        assertDoesNotThrow(() -> game.printBoard(game.getShots(), 'X'));
    }

    @Test
    @DisplayName("3.2. printValidShots")
    void printValidShots() {
        game.fire(pos0_0);
        assertDoesNotThrow(() -> game.printValidShots());
    }

    @Test
    @DisplayName("3.3. printFleet")
    void printFleet() {
        fleet.addShip(createFrigateAt(1, 1));
        assertDoesNotThrow(() -> game.printFleet());
    }


    @Nested
    @DisplayName("4. Cobertura de Ramos e Limites (Branch Coverage)")
    class BranchCoverageTests {

        @Test
        @DisplayName("validShot: Deve testar todas as condições de fronteira do tabuleiro")
        void testValidShotBoundaries() {
            game.fire(new Position(-1, 5));

            game.fire(new Position(11, 5));

            game.fire(new Position(5, -1));

            game.fire(new Position(5, 11));

            game.fire(new Position(10, 10));

            game.fire(new Position(0, 0));

            assertEquals(4, game.getInvalidShots(), "Devem existir exatamente 4 tiros inválidos neste teste.");
        }
    }
}