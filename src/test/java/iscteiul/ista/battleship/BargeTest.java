package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Unidade para a Barca (Tamanho 1)")
class BargeTest {

    // Configuração para uso comum (embora possa ser feita dentro do @Nested)
    private final IPosition START_POS = new Position(5, 5);
    private final Compass DIRECTION = Compass.NORTH;


    @Nested
    @DisplayName("A. Propriedades e Inicialização")
    class InitializationTests {

        @Test
        @DisplayName("Barca: Criação e Propriedades (Tamanho 1)")
        void testBargeProperties() {
            // Arrange
            IPosition pos = START_POS;
            Compass dir = DIRECTION;

            // Act
            Barge ship = new Barge(dir, pos);

            // Assert
            assertAll("Verificar propriedades da Barca",
                    () -> assertEquals(1, ship.getSize(), "O tamanho deve ser 1"),
                    () -> assertEquals("Barca", ship.getCategory(), "O nome deve ser 'Barca'"),
                    () -> assertEquals(dir, ship.getBearing()),
                    () -> assertEquals(pos, ship.getPosition())
            );
        }
    }


    @Nested
    @DisplayName("B. Ciclo de Vida (Afundamento)")
    class LifecycleTests {

        @Test
        @DisplayName("Deve afundar imediatamente com um único tiro")
        void barge_sinks_on_first_shot() {
            // Arrange
            Barge barge = new Barge(DIRECTION, START_POS);
            IPosition hitPos = barge.getPositions().get(0);

            assertTrue(barge.stillFloating(), "A Barca deve começar a flutuar");

            barge.shoot(hitPos);

            assertFalse(barge.stillFloating(), "A Barca (Tamanho 1) deve afundar após 1 tiro.");
            assertTrue(hitPos.isHit(), "A posição deve estar marcada como atingida.");
        }

        @Test
        @DisplayName("Não deve afundar se o tiro falhar a posição")
        void does_not_sink_if_shot_misses() {

            Barge barge = new Barge(DIRECTION, START_POS);
            IPosition missPos = new Position(10, 10);

            barge.shoot(missPos);

            assertTrue(barge.stillFloating(), "A Barca deve continuar a flutuar se o tiro falhar.");
        }
    }
}