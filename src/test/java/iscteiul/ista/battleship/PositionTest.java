package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("equals: Deve verificar igualdade baseada em coordenadas")
    void testEquals() {
        Position samePos = new Position(5, 5);
        Position diffPos = new Position(6, 6);
        String notAPosition = "Não sou uma posição";

        assertAll("Testes de Igualdade",
                () -> assertEquals(position, samePos, "Deve ser igual a (5, 5)."),
                () -> assertNotEquals(position, diffPos, "Não deve ser igual a (6, 6)."),
                () -> assertNotEquals(position, null, "Não deve ser igual a null."),
                () -> assertNotEquals(position, notAPosition, "Não deve ser igual a objetos de outro tipo.")
        );
    }

    @Test
    @DisplayName("isAdjacentTo: Deve detetar posições vizinhas (incluindo diagonais)")
    void isAdjacentTo() {
        // Posição base é (5, 5)
        Position horizontal = new Position(5, 6);
        Position vertical = new Position(4, 5);
        Position diagonal = new Position(6, 6);
        Position farAway = new Position(1, 1);

        assertAll("Verificação de Adjacência",
                () -> assertTrue(position.isAdjacentTo(horizontal), "Deve ser adjacente na horizontal."),
                () -> assertTrue(position.isAdjacentTo(vertical), "Deve ser adjacente na vertical."),
                () -> assertTrue(position.isAdjacentTo(diagonal), "Deve ser adjacente na diagonal."),
                () -> assertTrue(position.isAdjacentTo(position), "Uma posição é tecnicamente adjacente a si mesma (distância <= 1)."),
                () -> assertFalse(position.isAdjacentTo(farAway), "Não deve ser adjacente a (1, 1).")
        );
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
        // Teste redundante com o occupy(), mas foca no getter
        Position emptyPos = new Position(1, 1);
        assertFalse(emptyPos.isOccupied());

        emptyPos.occupy();
        assertTrue(emptyPos.isOccupied());
    }

    @Test
    @DisplayName("isHit: Deve retornar o estado de tiro")
    void isHit() {
        // Teste redundante com o shoot(), mas foca no getter
        Position cleanPos = new Position(2, 2);
        assertFalse(cleanPos.isHit());

        cleanPos.shoot();
        assertTrue(cleanPos.isHit());
    }

    @Test
    @DisplayName("toString: Deve retornar a string formatada corretamente")
    void testToString() {
        // Baseado no código que me deste: return ("Linha = " + row + " Coluna = " + column);
        String expected = "Linha = 5 Coluna = 5";
        assertEquals(expected, position.toString(), "O toString deve seguir o formato definido.");
    }
}