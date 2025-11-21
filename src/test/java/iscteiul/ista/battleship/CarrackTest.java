package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {
    @Test
    @DisplayName("Nau (Carrack): Criação e Propriedades (Tamanho 3)")
    void testCarrack() {
        IPosition pos = new Position(5, 5);
        Compass dir = Compass.SOUTH;

        Carrack ship = new Carrack(dir, pos);

        assertAll("Verificar propriedades da Nau",
                () -> assertEquals(3, ship.getSize(), "O tamanho deve ser 3"),
                () -> assertTrue(ship.getCategory().equalsIgnoreCase("Nau"), "O nome deve ser Nau"),
                () -> assertEquals(dir, ship.getBearing())
        );
    }
}