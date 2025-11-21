package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrack (Nau) - Testes Unitários")
class CarrackTest {

    private final IPosition START_POS = new Position(10, 5); // Posição (10, 5)
    private final Integer EXPECTED_SIZE = 3;
    private final String EXPECTED_NAME = "Nau";

    // --- Secção 1: Propriedades e Criação ---
    @Nested
    @DisplayName("1. Propriedades Fixas e Criação")
    class TestFixedProperties {

        @Test
        @DisplayName("Tamanho, Nome e Direção corretos (Happy Path)")
        void testCarrackProperties() {
            Compass dir = Compass.EAST;
            Carrack ship = new Carrack(dir, START_POS);

            assertAll("Verificar propriedades da Nau",
                    // Teu teste original: Tamanho
                    () -> assertEquals(EXPECTED_SIZE, ship.getSize(), "O tamanho deve ser 3"),
                    // Teu teste original: Nome/Categoria
                    () -> assertEquals(EXPECTED_NAME, ship.getCategory(), "A categoria deve ser 'Nau'"),
                    // Teu teste original: Direção
                    () -> assertEquals(dir, ship.getBearing()),
                    // Adicionado: Verifica o número total de posições
                    () -> assertEquals(EXPECTED_SIZE, ship.getPositions().size(), "Deve ter 3 posições por defeito")
            );
        }
    }

    // --- Secção 2: Posicionamento (Validar 3 Células) ---
    @Nested
    @DisplayName("2. Testes de Posicionamento (Tamanho 3)")
    class TestPositioning {

        /**
         * Testes para NORTH e SOUTH: Expande a linha
         */
        @Nested
        @DisplayName("2.1. Orientação Vertical (NORTH/SOUTH)")
        class TestVerticalPlacement {
            private final IPosition POS_0 = START_POS;              // (10, 5)
            private final IPosition POS_1 = new Position(11, 5);     // (10+1, 5)
            private final IPosition POS_2 = new Position(12, 5);     // (10+2, 5)

            @Test
            @DisplayName("NORTH/SOUTH: Expande a linha para 3 posições")
            void testVerticalExpansion() {
                // Teste NORTH
                Carrack northShip = new Carrack(Compass.NORTH, START_POS);
                assertAll("Posições NORTH",
                        () -> assertEquals(POS_0, northShip.getPositions().get(0), "Posição 0 incorreta (NORTH)"),
                        () -> assertEquals(POS_1, northShip.getPositions().get(1), "Posição 1 (+1 Linha) incorreta (NORTH)"),
                        () -> assertEquals(POS_2, northShip.getPositions().get(2), "Posição 2 (+2 Linhas) incorreta (NORTH)")
                );

                // Teste SOUTH
                Carrack southShip = new Carrack(Compass.SOUTH, START_POS);
                assertAll("Posições SOUTH",
                        () -> assertEquals(POS_0, southShip.getPositions().get(0), "Posição 0 incorreta (SOUTH)"),
                        () -> assertEquals(POS_1, southShip.getPositions().get(1), "Posição 1 (+1 Linha) incorreta (SOUTH)"),
                        () -> assertEquals(POS_2, southShip.getPositions().get(2), "Posição 2 (+2 Linhas) incorreta (SOUTH)")
                );
            }
        }

        /**
         * Testes para EAST e WEST: Expande a coluna
         */
        @Nested
        @DisplayName("2.2. Orientação Horizontal (EAST/WEST)")
        class TestHorizontalPlacement {
            private final IPosition POS_0 = START_POS;              // (10, 5)
            private final IPosition POS_1 = new Position(10, 6);     // (10, 5+1)
            private final IPosition POS_2 = new Position(10, 7);     // (10, 5+2)

            @Test
            @DisplayName("EAST/WEST: Expande a coluna para 3 posições")
            void testHorizontalExpansion() {
                // Teste EAST
                Carrack eastShip = new Carrack(Compass.EAST, START_POS);
                assertAll("Posições EAST",
                        () -> assertEquals(POS_0, eastShip.getPositions().get(0), "Posição 0 incorreta (EAST)"),
                        () -> assertEquals(POS_1, eastShip.getPositions().get(1), "Posição 1 (+1 Coluna) incorreta (EAST)"),
                        () -> assertEquals(POS_2, eastShip.getPositions().get(2), "Posição 2 (+2 Colunas) incorreta (EAST)")
                );

                // Teste WEST
                Carrack westShip = new Carrack(Compass.WEST, START_POS);
                assertAll("Posições WEST",
                        () -> assertEquals(POS_0, westShip.getPositions().get(0), "Posição 0 incorreta (WEST)"),
                        () -> assertEquals(POS_1, westShip.getPositions().get(1), "Posição 1 (+1 Coluna) incorreta (WEST)"),
                        () -> assertEquals(POS_2, westShip.getPositions().get(2), "Posição 2 (+2 Colunas) incorreta (WEST)")
                );
            }
        }
    }

    // --- Secção 3: Gestão de Erros ---
    @Nested
    @DisplayName("3. Gestão de Erros (Exceções)")
    class TestErrorHandling {

        @Test
        @DisplayName("Bearing nulo deve lançar AssertionError (da Ship)")
        void testNullBearing() {
            assertThrows(AssertionError.class, () -> new Carrack(null, START_POS),
                    "A superclasse Ship deve lançar AssertionError para bearing nulo.");
        }

    }
}