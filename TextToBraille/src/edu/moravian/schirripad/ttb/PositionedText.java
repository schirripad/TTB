package edu.moravian.schirripad.ttb;

public class PositionedText implements PositionedObject {
	private float x, y;
	private String text;

	public PositionedText(String text, float x, float y) {
		this.x = x;
		this.y = y;
		this.text = text;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	public String getText() {
		return text;
	}

}
