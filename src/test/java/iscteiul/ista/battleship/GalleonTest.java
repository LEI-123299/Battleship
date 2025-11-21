package iscteiul.ista.battleship;


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
    IPosition pos = new Position(0,0);

    // Verifica se lança a exceção NullPointerException como definiste no construtor
    assertThrows(NullPointerException.class, () -> {
        new Galleon(null, pos);
    });
}


