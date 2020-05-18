ArrayList<Pixel> pixels = new ArrayList<Pixel>(); //<>//

//You can change the size of radius here
final float R = 270;

void setup() {
  size(600, 600);
  //fullScreen();
}

Pixel currentPixel = new Pixel(0, R);
Pixel nextPixel;

void draw() {
  background(0);
  translate(width / 2, height / 2);
  pixels.add(currentPixel);
  float x = currentPixel.getX();
  float y = currentPixel.getY();
  float D = pow(x + 1, 2) + pow(y - 1, 2) - pow(R, 2);
  float d = 0;
  if (D < 0) {
    d = abs(pow(x + 1, 2) + pow(y, 2) - pow(R, 2)) - abs(pow(x + 1, 2) + pow(y - 1, 2) - pow(R, 2));
    if (d <= 0) {
      nextPixel = new Pixel(x + 1, y);
    } else {
      nextPixel = new Pixel(x + 1, y - 1);
    }
  } else if (D > 0) {
    d = abs(pow(x + 1, 2) + pow(y - 1, 2) - pow(R, 2)) - abs(pow(x, 2) + pow(y - 1, 2) - pow(R, 2));
    if (d <= 0) {
      nextPixel = new Pixel(x + 1, y - 1);
    } else {
      nextPixel = new Pixel(x, y - 1);
    }
  } else {
    nextPixel = new Pixel(x + 1, y - 1);
  }
  stroke(255);
  strokeWeight(2);
  for (Pixel pixel : pixels) {
    point(pixel.getX(), pixel.getY());
  }
  stroke(0, 255, 0);
  strokeWeight(4);
  point(nextPixel.getX(), nextPixel.getY());
  currentPixel = new Pixel(nextPixel);
  if (y < 0) {
    print("Done Generating 1/4 of the circle, now continuing with reflecting of this 1/4... \n");
    stroke(255);
    strokeWeight(2);
    for (Pixel pixel : pixels) {
      float x1 = pixel.getX();
      float y1 = pixel.getY();
      point(-x1, y1);
      point(x1, -y1);
      point(-x1, -y1);
    }
    print("Done");
    noLoop();
  }
}
