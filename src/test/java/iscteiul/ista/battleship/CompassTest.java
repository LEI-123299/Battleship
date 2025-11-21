package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompassTest {

    @Nested
    @DisplayName("1. Testes de Propriedades e Formatação")
    class PropertyTests {

        @Test
        @DisplayName("getDirection: Deve retornar o char associado a cada direção")
        void getDirection() {
            assertAll("Verificação dos caracteres de direção",
                    () -> assertEquals('n', Compass.NORTH.getDirection(), "NORTH deve ser 'n'"),
                    () -> assertEquals('s', Compass.SOUTH.getDirection(), "SOUTH deve ser 's'"),
                    () -> assertEquals('e', Compass.EAST.getDirection(), "EAST deve ser 'e'"),
                    () -> assertEquals('o', Compass.WEST.getDirection(), "WEST deve ser 'o'"),
                    () -> assertEquals('u', Compass.UNKNOWN.getDirection(), "UNKNOWN deve ser 'u'")
            );
        }

        @Test
        @DisplayName("toString: Deve retornar a representação em String do char")
        void testToString() {
            assertAll("Verificação do toString",
                    () -> assertEquals("n", Compass.NORTH.toString()),
                    () -> assertEquals("s", Compass.SOUTH.toString()),
                    () -> assertEquals("e", Compass.EAST.toString()),
                    () -> assertEquals("o", Compass.WEST.toString()),
                    () -> assertEquals("u", Compass.UNKNOWN.toString())
            );
        }
    }

    @Nested
    @DisplayName("2. Testes de Lógica de Conversão")
    class ConversionTests {

        @Test
        @DisplayName("charToCompass: Deve converter chars ('n','s','e','o') para Enum")
        void charToCompass() {
            assertAll("Conversão de char para Enum",
                    // Casos válidos
                    () -> assertEquals(Compass.NORTH, Compass.charToCompass('n')),
                    () -> assertEquals(Compass.SOUTH, Compass.charToCompass('s')),
                    () -> assertEquals(Compass.EAST, Compass.charToCompass('e')),
                    () -> assertEquals(Compass.WEST, Compass.charToCompass('o')),

                    // Casos inválidos / Default
                    () -> assertEquals(Compass.UNKNOWN, Compass.charToCompass('u')),
                    () -> assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'), "Char inválido deve retornar UNKNOWN"),
                    () -> assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '), "Espaço deve retornar UNKNOWN")
            );
        }
    }

    @Nested
    @DisplayName("3. Testes Padrão de Enum (Values/ValueOf)")
    class StandardEnumTests {

        @Test
        @DisplayName("values: Deve conter todas as constantes na ordem correta")
        void values() {
            Compass[] expected = {Compass.NORTH, Compass.SOUTH, Compass.EAST, Compass.WEST, Compass.UNKNOWN};
            Compass[] actual = Compass.values();

            assertArrayEquals(expected, actual, "O array de values() deve conter todas as constantes na ordem de declaração.");
        }

        @Test
        @DisplayName("valueOf: Deve retornar a constante correta pelo nome")
        void valueOf() {
            assertAll("Recuperação pelo nome (String)",
                    () -> assertEquals(Compass.NORTH, Compass.valueOf("NORTH")),
                    () -> assertEquals(Compass.WEST, Compass.valueOf("WEST")),
                    // Teste de erro para nome inexistente
                    () -> assertThrows(IllegalArgumentException.class, () -> Compass.valueOf("INVALID_NAME"))
            );
        }
    }
}