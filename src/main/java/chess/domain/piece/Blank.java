package chess.domain.piece;

import chess.domain.position.PositionFactory;

public class Blank extends Piece {
	public static final String BLANK_DEFAULT_POSITION = "a1";
	public static final String BLANK_RESOURCE = ".";

	public Blank() {
		super(PositionFactory.of(BLANK_DEFAULT_POSITION), PieceType.BLANK, Color.NONE);
	}

	@Override
	public String getResource() {
		return BLANK_RESOURCE;
	}
}