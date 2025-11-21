package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    @Nested
    @DisplayName("1. Testes de Forma e Orientação (Coverage do Switch)")
    class ShapeTests {


        @Test
        @DisplayName("Galeão: Validar forma específica virada a NORTE (Case NORTH)")
        void testGalleonShapeNorth() {
            IPosition startPos = new Position(0, 0);
            Galleon galeao = new Galleon(Compass.NORTH, startPos);
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
        @DisplayName("Galeão: Validar forma virada a SUL (Case SOUTH)")
        void testGalleonShapeSouth() {
            IPosition startPos = new Position(5, 5);
            Galleon galeao = new Galleon(Compass.SOUTH, startPos);
            List<IPosition> positions = galeao.getPositions();

            assertAll("Validar posições do Galeão a Sul",
                    () -> assertEquals(5, positions.size()),
                    () -> assertTrue(positions.contains(new Position(5, 5))),
                    () -> assertTrue(positions.contains(new Position(6, 5))),
                    () -> assertTrue(positions.contains(new Position(7, 4))),
                    () -> assertTrue(positions.contains(new Position(7, 5))),
                    () -> assertTrue(positions.contains(new Position(7, 6)))
            );
        }

        @Test
        @DisplayName("Galeão: Validar forma virada a ESTE (Case EAST)")
        void testGalleonShapeEast() {
            IPosition startPos = new Position(5, 5);
            Galleon galeao = new Galleon(Compass.EAST, startPos);
            List<IPosition> positions = galeao.getPositions();

            assertAll("Validar posições do Galeão a Este",
                    () -> assertEquals(5, positions.size()),
                    () -> assertTrue(positions.contains(new Position(5, 5))),
                    () -> assertTrue(positions.contains(new Position(6, 3))),
                    () -> assertTrue(positions.contains(new Position(6, 4))),
                    () -> assertTrue(positions.contains(new Position(6, 5))),
                    () -> assertTrue(positions.contains(new Position(7, 5)))
            );
        }

        @Test
        @DisplayName("Galeão: Validar forma virada a OESTE (Case WEST)")
        void testGalleonShapeWest() {
            IPosition startPos = new Position(5, 5);
            Galleon galeao = new Galleon(Compass.WEST, startPos);
            List<IPosition> positions = galeao.getPositions();

            assertAll("Validar posições do Galeão a Oeste",
                    () -> assertEquals(5, positions.size()),
                    () -> assertTrue(positions.contains(new Position(5, 5))),
                    () -> assertTrue(positions.contains(new Position(6, 5))),
                    () -> assertTrue(positions.contains(new Position(6, 6))),
                    () -> assertTrue(positions.contains(new Position(6, 7))),
                    () -> assertTrue(positions.contains(new Position(7, 5)))
            );
        }
    }

    @Nested
    @DisplayName("2. Testes de Exceções e Limites")
    class ExceptionTests {

        @Test
        @DisplayName("Construtor: Deve lançar exceção se direção for Null")
        void testGalleonNullBearing() {
            IPosition pos = new Position(0, 0);

            assertThrows(AssertionError.class, () -> {
                new Galleon(null, pos);
            });
        }

        @Test
        @DisplayName("Construtor: Deve lançar IllegalArgumentException no caso Default (UNKNOWN)")
        void testGalleonUnknownBearing() {
            IPosition pos = new Position(0, 0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Galleon(Compass.UNKNOWN, pos);
            }, "Deve lançar IllegalArgumentException ao cair no default do switch.");
        }
    }

    @Nested
    @DisplayName("3. Testes de Propriedades")
    class PropertyTests {
        @Test
        @DisplayName("Galeão: getSize deve retornar o tamanho correto (5)")
        void testGetSize() {
            IPosition pos = new Position(0, 0);
            Galleon galeao = new Galleon(Compass.NORTH, pos);
            assertEquals(5, galeao.getSize(), "O tamanho do Galeão deve ser 5.");
        }
    }
}