package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes ao conceito genérico Ship")
class ShipTest {

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

    Ship ship;

    @BeforeEach
    void setUp() {
        ship = new TestShip("TestShip", Compass.NORTH, new Position(5, 5), 3);
    }

    @Test
    void buildShip() {
    }

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
    @DisplayName("O navio flutua até sofrer o número de golpes igual ao tamanho")
    void stillFloating() {
        assertTrue(ship.stillFloating());
        ship.shoot(new Position(5,5));
        ship.shoot(new Position(4,5));
        ship.shoot(new Position(3,5));
        assertFalse(ship.stillFloating());
    }

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
    @DisplayName("toString contém categoria, orientação e posição")
    void testToString() {
        String txt = ship.toString();
        assertTrue(txt.contains(ship.getCategory()));
        assertTrue(txt.contains(ship.getBearing().toString()));
        IPosition pos = ship.getPosition();
        assertTrue(txt.contains(pos.toString()));
    }

    @Test
    @DisplayName("Testa buildShip")
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
}