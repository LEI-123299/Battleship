package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipTest {
    @Test
    @DisplayName("Galeão: Validar forma específica virada a NORTE")
    void testGalleonShapeNorth() {
        // Arrange
        IPosition startPos = new Position(0, 0);
        // Act
        Galleon galeao = new Galleon(Compass.NORTH, startPos);

        // Assert
        // O Galeão a Norte tem 5 posições. Segundo o teu código fillNorth:
        // (0,0), (0,1), (0,2) -> Linha 0
        // (1,1)               -> Linha 1
        // (2,1)               -> Linha 2

        List<IPosition> posicoes = galeao.getPositions();

        assertAll("Validar posições do Galeão a Norte",
                () -> assertEquals(5, posicoes.size()),
                () -> assertTrue(posicoes.contains(new Position(0, 0))),
                () -> assertTrue(posicoes.contains(new Position(0, 1))),
                () -> assertTrue(posicoes.contains(new Position(0, 2))),
                () -> assertTrue(posicoes.contains(new Position(1, 1))),
                () -> assertTrue(posicoes.contains(new Position(2, 1)))
        );
    }

    @Test
    @DisplayName("Galeão: Deve lançar erro se direção for Null")
    void testGalleonNullBearing() {
        IPosition pos = new Position(0, 0);

        // Correção: O código usa 'assert', logo a exceção é AssertionError
        assertThrows(AssertionError.class, () -> {
            new Galleon(null, pos);
        });
    }

    @Test
    @DisplayName("Galeão: Validar forma virada a SUL (fillSouth)")
    void testGalleonShapeSouth() {
        // Arrange: Posição segura (5, 5) para evitar índices negativos
        IPosition startPos = new Position(5, 5);

        // Act: Invoca o construtor com SOUTH -> chama fillSouth()
        Galleon galeao = new Galleon(Compass.SOUTH, startPos);
        List<IPosition> positions = galeao.getPositions();

        // Assert
        // Lógica do fillSouth para (5, 5):
        // Loop 1 (i=0..1): (5+0, 5) -> (5,5)
        //                  (5+1, 5) -> (6,5)
        // Loop 2 (j=2..4):
        //        j=2: (5+2, 5+2-3) -> (7, 4)
        //        j=3: (5+2, 5+3-3) -> (7, 5)
        //        j=4: (5+2, 5+4-3) -> (7, 6)

        assertAll("Validar posições do Galeão a Sul",
                () -> assertEquals(5, positions.size(), "Deve ter 5 posições."),
                () -> assertTrue(positions.contains(new Position(5, 5)), "Topo"),
                () -> assertTrue(positions.contains(new Position(6, 5)), "Meio"),
                () -> assertTrue(positions.contains(new Position(7, 4)), "Base Esquerda"),
                () -> assertTrue(positions.contains(new Position(7, 5)), "Base Centro"),
                () -> assertTrue(positions.contains(new Position(7, 6)), "Base Direita")
        );
    }

    @Test
    @DisplayName("Galeão: Validar forma virada a ESTE (fillEast)")
    void testGalleonShapeEast() {
        // Arrange
        IPosition startPos = new Position(5, 5);

        // Act: Invoca o construtor com EAST -> chama fillEast()
        Galleon galeao = new Galleon(Compass.EAST, startPos);
        List<IPosition> positions = galeao.getPositions();

        // Assert
        // Lógica do fillEast para (5, 5):
        // 1. (5, 5)
        // 2. Loop (i=1..3):
        //        i=1: (5+1, 5+1-3) -> (6, 3)
        //        i=2: (5+1, 5+2-3) -> (6, 4)
        //        i=3: (5+1, 5+3-3) -> (6, 5)
        // 3. (5+2, 5) -> (7, 5)

        assertAll("Validar posições do Galeão a Este",
                () -> assertEquals(5, positions.size(), "Deve ter 5 posições."),
                () -> assertTrue(positions.contains(new Position(5, 5))),
                () -> assertTrue(positions.contains(new Position(6, 3))),
                () -> assertTrue(positions.contains(new Position(6, 4))),
                () -> assertTrue(positions.contains(new Position(6, 5))),
                () -> assertTrue(positions.contains(new Position(7, 5)))
        );
    }

    @Test
    @DisplayName("Galeão: Validar forma virada a OESTE (fillWest)")
    void testGalleonShapeWest() {
        // Arrange
        IPosition startPos = new Position(5, 5);

        // Act: Invoca o construtor com WEST -> chama fillWest()
        Galleon galeao = new Galleon(Compass.WEST, startPos);
        List<IPosition> positions = galeao.getPositions();

        // Assert
        // Lógica do fillWest para (5, 5):
        // 1. (5, 5)
        // 2. Loop (i=1..3):
        //        i=1: (5+1, 5+1-1) -> (6, 5)
        //        i=2: (5+1, 5+2-1) -> (6, 6)
        //        i=3: (5+1, 5+3-1) -> (6, 7)
        // 3. (5+2, 5) -> (7, 5)

        assertAll("Validar posições do Galeão a Oeste",
                () -> assertEquals(5, positions.size(), "Deve ter 5 posições."),
                () -> assertTrue(positions.contains(new Position(5, 5))),
                () -> assertTrue(positions.contains(new Position(6, 5))),
                () -> assertTrue(positions.contains(new Position(6, 6))),
                () -> assertTrue(positions.contains(new Position(6, 7))),
                () -> assertTrue(positions.contains(new Position(7, 5)))
        );
    }

    @Test
    @DisplayName("Galeão: getSize deve retornar o tamanho correto (5)")
    void testGetSize() {
        // Arrange
        IPosition pos = new Position(0, 0);
        // A direção não importa para o getSize, usamos NORTH como exemplo
        Galleon galeao = new Galleon(Compass.NORTH, pos);

        // Act
        Integer size = galeao.getSize();

        // Assert
        // Baseado nos métodos fillSouth/East/West que adicionam 5 posições,
        // o tamanho estático esperado é 5.
        assertEquals(5, size, "O tamanho do Galeão deve ser 5.");
    }
}


