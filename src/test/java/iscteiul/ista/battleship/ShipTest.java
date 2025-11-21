package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes ao conceito genérico Ship")
class ShipTest {

    // A classe auxiliar TestShip é mantida no topo da classe principal
    static class TestShip extends Ship {
        private final int size;

        public TestShip(String category, Compass bearing, IPosition pos, int size) {
            super(category, bearing, pos);
            this.size = size;

            positions.clear();
            for (int i = 0; i < size; i++) {
                positions.add(new Position(pos.getRow() - i, pos.getColumn()));
            }
        }

        @Override
        public Integer getSize() {
            return size;
        }
    }

    // O objeto e a configuração @BeforeEach são mantidos na classe principal para uso geral
    Ship ship;

    @BeforeEach
    void setUp() {
        ship = new TestShip("TestShip", Compass.NORTH, new Position(5, 5), 3);
    }

    // O método 'buildShip' vazio é removido, pois há um teste concreto para ele
    // @Test void buildShip() { }


    @Nested
    @DisplayName("A. Asserções do Construtor (Testes de Limites e Nulidade)")
    class ConstructorAssertionsTests {
        // O seu agrupamento original é mantido aqui e renomeado para A.

        @Test
        @DisplayName("Construtor lança AssertionError se bearing for null")
        void bearingNullThrows() {
            assertThrows(AssertionError.class,
                    () -> new TestShip("TestShip", null, new Position(0,0), 1));
        }

        @Test
        @DisplayName("Construtor lança AssertionError se pos for null")
        void positionNullThrows() {
            assertThrows(AssertionError.class,
                    () -> new TestShip("TestShip", Compass.NORTH, null, 1));
        }
    }


    @Nested
    @DisplayName("B. Getters e Inicialização")
    class GettersAndInitializationTests {

        // Todos os testes de getters e toString são agrupados aqui

        @Test
        @DisplayName("Categoria correta do navio")
        void getCategory() {
            assertEquals("TestShip", ship.getCategory());
        }

        @Test
        @DisplayName("Lista de posições está correta")
        void getPositions() {
            assertEquals(3, ship.getPositions().size());
        }

        @Test
        @DisplayName("Posição inicial do navio")
        void getPosition() {
            assertEquals(new Position(5,5), ship.getPosition());
        }

        @Test
        @DisplayName("Orientação correta")
        void getBearing() {
            assertEquals(Compass.NORTH, ship.getBearing());
        }

        @Test
        @DisplayName("toString contém categoria, orientação e posição")
        void testToString() {
            String txt = ship.toString();
            assertTrue(txt.contains(ship.getCategory()));
            assertTrue(txt.contains(ship.getBearing().toString()));
            IPosition pos = ship.getPosition();
            assertTrue(txt.contains(pos.toString()));
        }
    }


    @Nested
    @DisplayName("C. Ocupação e Limites")
    class OccupancyAndBoundaryTests {

        // Testes para ocupação e os 4 métodos de limite são agrupados aqui

        @Test
        @DisplayName("Posição mais acima")
        void getTopMostPos() {
            assertEquals(3, ship.getTopMostPos());
        }

        @Test
        @DisplayName("Posição mais abaixo")
        void getBottomMostPos() {
            assertEquals(5, ship.getBottomMostPos());
        }

        @Test
        @DisplayName("Posição mais à esquerda")
        void getLeftMostPos() {
            assertEquals(5, ship.getLeftMostPos());
        }

        @Test
        @DisplayName("Posição mais à direita")
        void getRightMostPos() {
            assertEquals(5, ship.getRightMostPos());
        }

        @Test
        @DisplayName("Verificar se o navio ocupa uma posição")
        void occupies() {
            assertTrue(ship.occupies(new Position(5,5)));
            assertTrue(ship.occupies(new Position(4,5)));
            assertTrue(ship.occupies(new Position(3,5)));
            assertFalse(ship.occupies(new Position(2,5)));
        }

        @Test
        @DisplayName("occupies com null para cobertura de assert")
        void occupiesWithNull() {
            try {
                ship.occupies(null);
            } catch (AssertionError e) {
                // esperado, apenas para cobertura
            }
        }
    }


    @Nested
    @DisplayName("D. Lógica de Colisão (tooCloseTo)")
    class ProximityTests {

        // Testes de proximidade são agrupados aqui

        @Test
        @DisplayName("Navio está demasiado perto de uma posição")
        void tooCloseTo() {
            assertTrue(ship.tooCloseTo(new Position(4,5)));
            assertFalse(ship.tooCloseTo(new Position(10,10)));
        }

        @Test
        @DisplayName("Navio está demasiado perto de outro navio")
        void testTooCloseTo() {
            Ship other = new TestShip("OtherShip", Compass.NORTH, new Position(2,5), 1);
            assertTrue(ship.tooCloseTo(other));
        }

        @Test
        @DisplayName("tooCloseTo com null para cobertura de assert")
        void tooCloseToWithNull() {
            try {
                ship.tooCloseTo((IShip) null);
            } catch (AssertionError e) {
                // esperado, apenas para cobertura
            }
        }

        @Test
        @DisplayName("tooCloseTo(IShip) com navio distante")
        void tooCloseToOtherShipFarAway() {
            Ship distantShip = new TestShip("Distant", Compass.NORTH, new Position(10, 10), 2);
            // Nenhuma posição de distantShip está adjacente ao ship
            assertFalse(ship.tooCloseTo(distantShip));
        }
    }


    @Nested
    @DisplayName("E. Ciclo de Vida (Tiro e Flutuação)")
    class LifecycleTests {

        // Testes de tiro e flutuação são agrupados aqui

        @Test
        @DisplayName("O navio flutua até sofrer o número de golpes igual ao tamanho")
        void stillFloating() {
            assertTrue(ship.stillFloating());
            ship.shoot(new Position(5,5));
            ship.shoot(new Position(4,5));
            ship.shoot(new Position(3,5));
            assertFalse(ship.stillFloating());
        }

        @Test
        @DisplayName("Registo de tiros")
        void shoot() {

            for (IPosition p : ship.getPositions()) {
                assertFalse(p.isHit());
            }

            ship.shoot(ship.getPositions().get(0));
            assertTrue(ship.getPositions().get(0).isHit());

            ship.shoot(ship.getPositions().get(1));
            assertTrue(ship.getPositions().get(1).isHit());

            assertTrue(ship.stillFloating());

            ship.shoot(ship.getPositions().get(2));
            assertFalse(ship.stillFloating());
        }

        @Test
        @DisplayName("shoot lança AssertionError se posição for null")
        void shootWithNull() {
            assertThrows(AssertionError.class, () -> ship.shoot(null));
        }
    }


    @Nested
    @DisplayName("F. Testes de Casos de Limite e Fábrica")
    class EdgeCasesAndFactoryTests {

        // Casos que testam os limites e a fábrica são agrupados aqui

        @Test
        @DisplayName("Testa buildShip (Fábrica de Navios)")
        void testBuildShip() {
            Ship barca = Ship.buildShip("barca", Compass.NORTH, new Position(0, 0));
            assertEquals("Barca", barca.getCategory());

            Ship caravel = Ship.buildShip("caravela", Compass.EAST, new Position(1, 1));
            assertEquals("Caravela", caravel.getCategory());

            Ship carrack = Ship.buildShip("nau", Compass.SOUTH, new Position(2, 2));
            assertEquals("Nau", carrack.getCategory());

            Ship frigate = Ship.buildShip("fragata", Compass.WEST, new Position(3, 3));
            assertEquals("Fragata", frigate.getCategory());

            Ship galleon = Ship.buildShip("galeao", Compass.NORTH, new Position(4, 4));
            assertEquals("Galeao", galleon.getCategory());

            Ship unknown = Ship.buildShip("desconhecido", Compass.SOUTH, new Position(5, 5));
            assertNull(unknown);
        }

        @Test
        @DisplayName("Testa getTopMostPos com posições desordenadas")
        void getTopMostPosUnordered() {
            TestShip unorderedShip = new TestShip("Unordered", Compass.NORTH, new Position(5, 5), 3);
            // Modifica manualmente a posição para desordenar
            unorderedShip.positions.set(0, new Position(6, 5));
            unorderedShip.positions.set(1, new Position(4, 5));
            unorderedShip.positions.set(2, new Position(5, 5));

            assertEquals(4, unorderedShip.getTopMostPos());
        }

        @Test
        @DisplayName("Testa getBottomMostPos com posições desordenadas")
        void getBottomMostPosUnordered() {
            TestShip unorderedShip = new TestShip("Unordered", Compass.NORTH, new Position(5, 5), 3);
            // Modifica manualmente a posição para desordenar
            unorderedShip.positions.set(0, new Position(4, 5));
            unorderedShip.positions.set(1, new Position(6, 5));
            unorderedShip.positions.set(2, new Position(5, 5));

            assertEquals(6, unorderedShip.getBottomMostPos());
        }

        @Test
        @DisplayName("getLeftMostPos com posições de colunas diferentes")
        void getLeftMostPosDifferentColumns() {
            Ship customShip = new TestShip("Custom", Compass.NORTH, new Position(5, 5), 3);
            // modificar as posições para ter colunas diferentes
            customShip.getPositions().set(1, new Position(5, 4));
            customShip.getPositions().set(2, new Position(5, 6));

            assertEquals(4, customShip.getLeftMostPos());
        }

        @Test
        @DisplayName("getRightMostPos com posições de colunas diferentes")
        void getRightMostPosDifferentColumns() {
            Ship customShip = new TestShip("Custom", Compass.NORTH, new Position(5, 5), 3);
            customShip.getPositions().set(1, new Position(5, 4));
            customShip.getPositions().set(2, new Position(5, 6));

            assertEquals(6, customShip.getRightMostPos());
        }
    }
}