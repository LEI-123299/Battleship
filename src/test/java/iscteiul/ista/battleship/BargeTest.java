package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BargeTest {
    @Test
    @DisplayName("Barca: Criação e Propriedades (Tamanho 1)")
    void testBarge() {
        // Arrange
        IPosition pos = new Position(0, 0);
        Compass dir = Compass.NORTH;

        // Act
        Barge ship = new Barge(dir, pos);

        // Assert
        assertAll("Verificar propriedades da Barca",
                () -> assertEquals(1, ship.getSize(), "O tamanho deve ser 1"),
                () -> assertEquals("Barca", ship.getCategory(), "O nome deve ser 'Barca'"), // Verifica o category/name
                () -> assertEquals(dir, ship.getBearing()),
                () -> assertEquals(pos, ship.getPosition())
        );
    }
}