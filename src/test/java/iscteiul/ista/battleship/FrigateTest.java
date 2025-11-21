package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class FrigateTest {
    @Test
    @DisplayName("Fragata: Orientação Horizontal (East/West)")
    void testFrigateHorizontal() {
        IPosition startPos = new Position(0, 0);
        // Testar EAST (cresce nas colunas)
        Frigate fragata = new Frigate(Compass.EAST, startPos);

        List<IPosition> posicoes = fragata.getPositions();

        assertAll("Validar Fragata Horizontal",
                () -> assertTrue(posicoes.contains(new Position(0, 0))),
                () -> assertTrue(posicoes.contains(new Position(0, 1))), // Coluna aumenta
                () -> assertTrue(posicoes.contains(new Position(0, 3))),
                () -> assertFalse(posicoes.contains(new Position(1, 0))) // Linha mantém-se
        );
    }

    @Test
    @DisplayName("Fragata: Orientação Vertical (North/South)")
    void testFrigateVertical() {
        IPosition startPos = new Position(0, 0);
        // Testar SOUTH (cresce nas linhas)
        Frigate fragata = new Frigate(Compass.SOUTH, startPos);

        List<IPosition> posicoes = fragata.getPositions();

        assertAll("Validar Fragata Vertical",
                () -> assertTrue(posicoes.contains(new Position(0, 0))),
                () -> assertTrue(posicoes.contains(new Position(1, 0))), // Linha aumenta
                () -> assertTrue(posicoes.contains(new Position(3, 0))),
                () -> assertFalse(posicoes.contains(new Position(0, 1))) // Coluna mantém-se
        );
    }

    @Test
    @DisplayName("Fragata: getSize deve retornar 4")
    void testGetSize() {
        // Arrange
        IPosition pos = new Position(0, 0);
        Frigate fragata = new Frigate(Compass.NORTH, pos);

        // Act
        Integer size = fragata.getSize();

        // Assert
        // Como Frigate.SIZE é private, comparamos com o valor literal 4
        assertEquals(4, size, "A Fragata deve ter tamanho 4.");
    }
}