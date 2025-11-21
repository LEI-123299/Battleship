package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Caravel (Caravela) - Testes Unitários")
class CaravelTest {

    private final IPosition START_POS = new Position(5, 5);
    private final Integer EXPECTED_SIZE = 2;
    private final String EXPECTED_NAME = "Caravela";

    // --- Secções 1 e 2: Testes de Propriedades e Posicionamento ---

    @Nested
    @DisplayName("1. Propriedades e Criação (Caminho Feliz)")
    class TestFixedPropertiesAndCreation {
        @Test
        @DisplayName("Criação: Tamanho, Nome e Direção corretos")
        void testCaravel() {
            IPosition pos = new Position(1, 1);
            Compass dir = Compass.EAST;
            Caravel ship = new Caravel(dir, pos);

            assertAll("Verificar propriedades da Caravela",
                    () -> assertEquals(EXPECTED_SIZE, ship.getSize()),
                    () -> assertTrue(ship.getCategory().equalsIgnoreCase(EXPECTED_NAME)),
                    () -> assertEquals(dir, ship.getBearing()),
                    () -> assertEquals(EXPECTED_SIZE, ship.getPositions().size())
            );
        }
    }

    @Nested
    @DisplayName("2. Testes de Posicionamento (Coordenadas)")
    class TestPositioning {
        @Nested
        @DisplayName("2.1. Orientação Vertical (NORTH/SOUTH)")
        class TestVerticalPlacement {
            private final IPosition POS_0 = START_POS;
            private final IPosition POS_1 = new Position(START_POS.getRow() + 1, START_POS.getColumn());
            @Test
            @DisplayName("NORTH/SOUTH: Expande a linha (row + 1)")
            void testVerticalExpansion() {
                Caravel northShip = new Caravel(Compass.NORTH, START_POS);
                assertEquals(POS_0, northShip.getPositions().get(0));
                assertEquals(POS_1, northShip.getPositions().get(1));

                Caravel southShip = new Caravel(Compass.SOUTH, START_POS);
                assertEquals(POS_0, southShip.getPositions().get(0));
                assertEquals(POS_1, southShip.getPositions().get(1));
            }
        }

        @Nested
        @DisplayName("2.2. Orientação Horizontal (EAST/WEST)")
        class TestHorizontalPlacement {
            private final IPosition POS_0 = START_POS;
            private final IPosition POS_1 = new Position(START_POS.getRow(), START_POS.getColumn() + 1);
            @Test
            @DisplayName("EAST/WEST: Expande a coluna (column + 1)")
            void testHorizontalExpansion() {
                Caravel eastShip = new Caravel(Compass.EAST, START_POS);
                assertEquals(POS_0, eastShip.getPositions().get(0));
                assertEquals(POS_1, eastShip.getPositions().get(1));

                Caravel westShip = new Caravel(Compass.WEST, START_POS);
                assertEquals(POS_0, westShip.getPositions().get(0));
                assertEquals(POS_1, westShip.getPositions().get(1));
            }
        }
    }

    // --- Secção 3: Gestão de Erros (Ajustada para o comportamento real) ---

    @Nested
    @DisplayName("3. Gestão de Erros e Exceções")
    class TestErrorHandling {

        @Test
        @DisplayName("Exceção real: Null no Bearing lança AssertionError (da Ship)")
        void testRealNullBearingException() {
            // Espera a exceção REAL que é lançada na superclasse (Ship.java:61)
            assertThrows(AssertionError.class, () -> new Caravel(null, START_POS),
                    "A superclasse Ship interceta o 'null' e lança AssertionError.");
        }
    }
}