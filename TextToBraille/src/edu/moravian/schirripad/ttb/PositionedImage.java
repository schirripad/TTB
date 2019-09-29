package edu.moravian.schirripad.ttb;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class PositionedImage extends Image {
	private float x, y;
	private Image img;

	public PositionedImage(float x, float y, Image img) {
		this.x = x;
		this.y = x;
		this.img = img;
	}

	public Image getImg() {
		return img;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return img.getGraphics();
	}

	@Override
	public int getHeight(ImageObserver arg0) {
		// TODO Auto-generated method stub
		return img.getHeight(arg0);
	}

	@Override
	public Object getProperty(String arg0, ImageObserver arg1) {
		// TODO Auto-generated method stub
		return img.getProperty(arg0, arg1);
	}

	@Override
	public ImageProducer getSource() {
		// TODO Auto-generated method stub
		return img.getSource();
	}

	@Override
	public int getWidth(ImageObserver arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
