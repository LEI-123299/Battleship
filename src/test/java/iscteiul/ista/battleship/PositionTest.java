package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PositionTest: Testes da Entidade Posição")
class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        // Inicializa uma posição padrão (5, 5) antes de cada teste
        position = new Position(5, 5);
    }

    @Test
    @DisplayName("getRow: Deve retornar a linha correta")
    void getRow() {
        assertEquals(5, position.getRow(), "A linha deve ser 5.");
    }

    @Test
    @DisplayName("getColumn: Deve retornar a coluna correta")
    void getColumn() {
        assertEquals(5, position.getColumn(), "A coluna deve ser 5.");
    }

    @Test
    @DisplayName("hashCode: Posições iguais devem ter o mesmo hash")
    void testHashCode() {
        Position samePos = new Position(5, 5);
        Position diffPos = new Position(1, 1);

        assertEquals(position.hashCode(), samePos.hashCode(), "HashCodes devem ser iguais.");
        assertNotEquals(position.hashCode(), diffPos.hashCode(), "HashCodes devem ser diferentes.");
    }

    @Test
    @DisplayName("occupy: Deve alterar o estado para ocupado")
    void occupy() {
        assertFalse(position.isOccupied(), "Inicialmente não deve estar ocupada.");
        position.occupy();
        assertTrue(position.isOccupied(), "Após occupy(), deve estar ocupada.");
    }

    @Test
    @DisplayName("shoot: Deve alterar o estado para atingido")
    void shoot() {
        assertFalse(position.isHit(), "Inicialmente não deve estar atingida.");
        position.shoot();
        assertTrue(position.isHit(), "Após shoot(), deve estar atingida.");
    }

    @Test
    @DisplayName("isOccupied: Deve retornar o estado de ocupação")
    void isOccupied() {
        Position emptyPos = new Position(1, 1);
        assertFalse(emptyPos.isOccupied());
        emptyPos.occupy();
        assertTrue(emptyPos.isOccupied());
    }

    @Test
    @DisplayName("isHit: Deve retornar o estado de tiro")
    void isHit() {
        Position cleanPos = new Position(2, 2);
        assertFalse(cleanPos.isHit());
        cleanPos.shoot();
        assertTrue(cleanPos.isHit());
    }

    @Test
    @DisplayName("toString: Deve retornar a string formatada corretamente")
    void testToString() {
        String expected = "Linha = 5 Coluna = 5";
        assertEquals(expected, position.toString(), "O toString deve seguir o formato definido.");
    }

    // --- BLOCO PARA COBERTURA DE RAMIFICAÇÃO (VERDE) ---
    @Nested
    @DisplayName("Cobertura de Ramos (Branch Coverage)")
    class BranchCoverageTests {

        @Test
        @DisplayName("equals: Deve testar todos os ramos lógicos")
        void testEqualsBranches() {
            Position sameCoords = new Position(5, 5);
            Position diffRow = new Position(6, 5);
            Position diffCol = new Position(5, 6);

            assertAll("Equals Short-Circuit",
                    // 1. Mesma referência de memória (if this == other)
                    () -> assertTrue(position.equals(position), "Deve retornar true se for o mesmo objeto (referência)."),

                    // 2. Não é instância de IPosition (else ou instanceof false)
                    () -> assertFalse(position.equals("String"), "Deve retornar false para outros tipos de objeto."),
                    () -> assertFalse(position.equals(null), "Deve retornar false para null."),

                    // 3. Instância correta, mas falha na Linha (1ª condição do &&)
                    () -> assertFalse(position.equals(diffRow), "Deve retornar false se a linha for diferente."),

                    // 4. Instância correta, Linha igual, mas falha na Coluna (2ª condição do &&)
                    () -> assertFalse(position.equals(diffCol), "Deve retornar false se a coluna for diferente."),

                    // 5. Tudo igual
                    () -> assertTrue(position.equals(sameCoords), "Deve retornar true se coordenadas forem iguais.")
            );
        }

        @Test
        @DisplayName("isAdjacentTo: Deve testar todas as condições do &&")
        void testIsAdjacentToBranches() {
            // Base é (5, 5)

            // 1. Falha na Linha (Distância > 1) -> O Java nem testa a coluna
            Position farRow = new Position(7, 5); // Dif = 2
            assertFalse(position.isAdjacentTo(farRow), "Deve falhar na 1ª condição (Linha longe).");

            // 2. Passa na Linha, Falha na Coluna -> O Java testa a coluna e falha
            Position closeRowFarCol = new Position(5, 7); // Linha dif=0, Coluna dif=2
            assertFalse(position.isAdjacentTo(closeRowFarCol), "Deve falhar na 2ª condição (Coluna longe).");

            // 3. Passa em ambos (Sucesso)
            Position neighbor = new Position(5, 6);
            assertTrue(position.isAdjacentTo(neighbor), "Deve passar se ambos estiverem perto.");
        }
    }
}