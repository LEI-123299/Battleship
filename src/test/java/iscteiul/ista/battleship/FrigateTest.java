package iscteiul.ista.battleship;


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