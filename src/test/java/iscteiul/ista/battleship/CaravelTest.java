package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {
    @Test
    @DisplayName("Caravela: Criação e Propriedades (Tamanho 2)")
    void testCaravel() {
        IPosition pos = new Position(1, 1);
        Compass dir = Compass.EAST;

        // Instanciar
        Caravel ship = new Caravel(dir, pos);

        assertAll("Verificar propriedades da Caravela",
                () -> assertEquals(2, ship.getSize(), "O tamanho deve ser 2"),
                // Nota: Verifica no ficheiro Caravel.java se o nome é "Caravela" com maiúscula
                () -> assertTrue(ship.getCategory().equalsIgnoreCase("Caravela"), "O nome deve ser Caravela"),
                () -> assertEquals(dir, ship.getBearing())
        );
    }
}