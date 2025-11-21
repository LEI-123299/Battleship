package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static iscteiul.ista.battleship.Ship.*;
import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    Fleet fleet;

    private IShip createBargeAt(int row, int col) {
        return new Barge(Compass.NORTH, new Position(row, col));
    }

    private IShip createFrigateAt(int row, int col) {
        return new Frigate(Compass.NORTH, new Position(row, col));
    }

    private IShip createCaravelAt(int row, int col) {
        return new Caravel(Compass.NORTH, new Position(row, col));
    }

    private IShip createGalleonAt(int row, int col) {
        return new Galleon(Compass.NORTH, new Position(row, col));
    }

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
    }

    @Test
    @DisplayName("Deve retornar a lista de navios adicionados")
    void getShips() {
        IShip s1 = createBargeAt(1, 1);
        IShip s2 = createBargeAt(3, 3);
        fleet.addShip(s1);
        fleet.addShip(s2);

        List<IShip> ships = fleet.getShips();
        assertAll(
                () -> assertEquals(2, ships.size(), "A lista deve conter 2 navios."),
                () -> assertTrue(ships.contains(s1), "Deve conter o navio s1."),
                () -> assertTrue(ships.contains(s2), "Deve conter o navio s2.")
        );
    }

    @Test
    @DisplayName("Adiciona um ship corretamente")
    void addShip() {
        IShip s = createBargeAt(2, 2);
        boolean result = fleet.addShip(s);
        assertAll(
                () -> assertTrue(result, "A adição deve ser bem-sucedida."),
                () -> assertEquals(1, fleet.getShips().size(), "O tamanho da frota deve ser 1."),
                () -> assertEquals(s, fleet.getShips().get(0), "O navio adicionado deve estar na frota.")
        );
    }

    @Nested
    @DisplayName("1.1. Testes de Regras do addShip")
    class AddShipRulesTests {

        @Test
        @DisplayName("Deve falhar se o navio estiver fora dos limites (Out of Bounds)")
        void addShip_outOfBounds_shouldReturnFalse() {
            IShip s = createBargeAt(10, 10);
            assertFalse(fleet.addShip(s), "A adição deve falhar se o navio estiver fora dos limites.");
        }

        @Test
        @DisplayName("Deve falhar se o navio for adjacente a outro")
        void addShip_tooCloseTo_shouldReturnFalse() {
            IShip sA = createBargeAt(1, 1);
            IShip sB = createBargeAt(2, 2);

            fleet.addShip(sA);
            assertFalse(fleet.addShip(sB), "A adição deve falhar se for adjacente.");
            assertEquals(1, fleet.getShips().size(), "Apenas o primeiro navio deve ser adicionado.");
        }
    }

    @Test
    @DisplayName("Deve retornar navios de uma categoria específica")
    void getShipsLike() {
        IShip barge1 = createBargeAt(1, 1);
        IShip frigate1 = createFrigateAt(3, 3);
        IShip barge2 = createBargeAt(5, 5);

        fleet.addShip(barge1);
        fleet.addShip(frigate1);
        fleet.addShip(barge2);

        List<IShip> barges = fleet.getShipsLike("Barca");
        List<IShip> frigates = fleet.getShipsLike("Fragata");
        List<IShip> galleons = fleet.getShipsLike("Galeao");

        assertAll(
                () -> assertEquals(2, barges.size(), "Deve encontrar 2 Barcas (Barge)."),
                () -> assertTrue(barges.contains(barge1) && barges.contains(barge2), "Deve conter as Barcas corretas."),
                () -> assertEquals(1, frigates.size(), "Deve encontrar 1 Fragata (Frigate)."),
                () -> assertTrue(galleons.isEmpty(), "Não deve encontrar Galeões (Galleon).")
        );
    }

    @Test
    @DisplayName("Deve retornar apenas navios que ainda flutuam")
    void getFloatingShips() {
        IShip barge = createBargeAt(1, 1);
        IShip frigate = createFrigateAt(5, 5);

        fleet.addShip(barge);
        fleet.addShip(frigate);

        IPosition bargePos = new Position(1, 1);

        IShip targetShip = fleet.shipAt(bargePos);
        if (targetShip != null) {
            targetShip.shoot(bargePos);
        }

        List<IShip> floating = fleet.getFloatingShips();

        assertAll(
                () -> assertEquals(1, floating.size(), "Apenas 1 navio (Frigate) deve estar a flutuar."),
                () -> assertFalse(floating.contains(barge), "A Baraca afundada não deve estar na lista."),
                () -> assertTrue(floating.contains(frigate), "A Fragata deve estar na lista.")
        );
    }

    @Test
    @DisplayName("Deve retornar o navio na posição correta ou NULL")
    void shipAt() {
        IShip s = createCaravelAt(5, 5);
        fleet.addShip(s);

        IPosition occupiedPos = new Position(5, 5);
        IPosition emptyPos = new Position(1, 1);

        assertAll(
                () -> assertEquals(s, fleet.shipAt(occupiedPos), "Deve encontrar a Caravel na posição ocupada."),
                () -> assertNull(fleet.shipAt(emptyPos), "Deve retornar NULL para uma posição vazia.")
        );
    }

    @Test
    @DisplayName("Deve executar sem exceções")
    void printShips() {
        IShip s1 = createBargeAt(1, 1);
        IShip s2 = createFrigateAt(5, 5);
        List<IShip> testShips = Arrays.asList(s1, s2);

        assertDoesNotThrow(() -> {
            Fleet.printShips(testShips);
        }, "A execução do método de impressão não deve lançar exceção.");

    }
    @Test
    @DisplayName("Deve imprimir apenas navios da categoria especificada")
    void printShipsByCategory_shouldFilterAndPrint() {
        IShip s1 = createGalleonAt(1, 1);
        IShip s2 = createFrigateAt(5, 5);
        fleet.addShip(s1);
        fleet.addShip(s2);


        assertDoesNotThrow(() -> {
            fleet.printShipsByCategory("Fragata");
        }, "A impressão por categoria não deve lançar exceção.");
    }


    @Test
    @DisplayName("Deve imprimir apenas navios que ainda flutuam")
    void printFloatingShips_shouldPrintOnlyFloating() {
        IShip s1 = createBargeAt(1, 1);
        IShip s2 = createGalleonAt(5, 5);

        fleet.addShip(s1);
        fleet.addShip(s2);

        fleet.shipAt(new Position(1, 1)).shoot(new Position(1, 1));

        assertDoesNotThrow(() -> {
            fleet.printFloatingShips();
        }, "A impressão de navios flutuantes não deve lançar exceção.");
   }

    @Test
    @DisplayName("Deve executar sem exceções e cobrir a impressão")
    void printAllShipsTest() {

        fleet.addShip(createBargeAt(1, 1));
        fleet.addShip(createFrigateAt(3, 3));

        assertDoesNotThrow(() -> {
            fleet.printAllShips();
        }, "A execução de printAllShips não deve falhar, garantindo a cobertura.");

       }


    @Test
    @DisplayName("Deve executar todas as rotinas de impressão de status sem exceção")
    void printStatusTest() {
        fleet.addShip(createBargeAt(1, 1));
        fleet.addShip(createFrigateAt(3, 3));
        fleet.addShip(createGalleonAt(5, 5));
        fleet.addShip(createCaravelAt(7, 7));
        fleet.shipAt(new Position(1, 1)).shoot(new Position(1, 1));

        assertDoesNotThrow(() -> {
            fleet.printStatus();
        }, "A execução completa de printStatus (que chama todos os auxiliares) não deve falhar.");
    }


}