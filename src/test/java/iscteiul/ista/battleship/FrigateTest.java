package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    @Nested
    @DisplayName("1. Testes de Orientação e Posicionamento (Coverage)")
    class OrientationTests {

        @Test
        @DisplayName("Orientação Horizontal: ESTE (Cresce para a direita)")
        void testFrigateEast() {
            IPosition startPos = new Position(0, 0);
            Frigate fragata = new Frigate(Compass.EAST, startPos);
            List<IPosition> posicoes = fragata.getPositions();

            assertAll("Validar EAST",
                    () -> assertTrue(posicoes.contains(new Position(0, 0))),
                    () -> assertTrue(posicoes.contains(new Position(0, 1))),
                    () -> assertTrue(posicoes.contains(new Position(0, 3)))
            );
        }

        @Test
        @DisplayName("Orientação Horizontal: OESTE (Cresce para a direita tb - lógica do código)")
        void testFrigateWest() {
            IPosition startPos = new Position(0, 0);
            Frigate fragata = new Frigate(Compass.WEST, startPos);
            List<IPosition> posicoes = fragata.getPositions();

            assertAll("Validar WEST",
                    () -> assertEquals(4, posicoes.size()),
                    () -> assertTrue(posicoes.contains(new Position(0, 1)))
            );
        }

        @Test
        @DisplayName("Orientação Vertical: SUL (Cresce para baixo)")
        void testFrigateSouth() {
            IPosition startPos = new Position(0, 0);
            Frigate fragata = new Frigate(Compass.SOUTH, startPos);
            List<IPosition> posicoes = fragata.getPositions();

            assertAll("Validar SOUTH",
                    () -> assertTrue(posicoes.contains(new Position(0, 0))),
                    () -> assertTrue(posicoes.contains(new Position(1, 0))),
                    () -> assertTrue(posicoes.contains(new Position(3, 0)))
            );
        }

        @Test
        @DisplayName("Orientação Vertical: NORTE (Cresce para baixo tb - lógica do código)")
        void testFrigateNorth() {
            IPosition startPos = new Position(0, 0);
            Frigate fragata = new Frigate(Compass.NORTH, startPos);
            List<IPosition> posicoes = fragata.getPositions();

            assertAll("Validar NORTH",
                    () -> assertEquals(4, posicoes.size()),
                    () -> assertTrue(posicoes.contains(new Position(1, 0)))
            );
        }
    }

    @Nested
    @DisplayName("2. Testes de Propriedades")
    class PropertyTests {
        @Test
        @DisplayName("getSize: Deve retornar 4")
        void testGetSize() {
            Frigate fragata = new Frigate(Compass.NORTH, new Position(0, 0));
            assertEquals(4, fragata.getSize());
        }
    }

    @Nested
    @DisplayName("3. Testes de Exceções")
    class ExceptionTests {
        @Test
        @DisplayName("Construtor: Deve lançar exceção no caso default (UNKNOWN)")
        void testInvalidBearing() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Frigate(Compass.UNKNOWN, new Position(0, 0));
            });
        }
    }
}