public class Pixel{
  
 private float x;
 private float y;
 
 Pixel(float x, float y){
  this.x = x;
  this.y = y;
 }
 
 Pixel(Pixel newPixel){
  this.x = newPixel.getX();
  this.y = newPixel.getY();
 }
 
 public void setX(float x){
  this.x = x; 
 }
 
 public float getX(){
  return this.x; 
 }
 
 public void setY(float y){
  this.y = y; 
 }
 
 public float getY(){
  return this.y; 
 }
}
