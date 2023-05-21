package View;

import java.awt.image.BufferedImage;

public final class ImageData {
	BufferedImage image;
	int width, height, x, y;
	
	// just for passing data.
	public ImageData(BufferedImage image, int x, int y, int width, int height)
	{
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
