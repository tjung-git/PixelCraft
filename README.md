**Compile**

`javac -d out *.java converters/*.java `

**Run converters**
```
java -cp "out" PixelCraft Grayscale images/input.png
java -cp "out" PixelCraft Rotate images/input.png
java -cp "out" PixelCraft Blur images/input.png
java -cp "out" PixelCraft GlitchPop images/input.png
java -cp "out" PixelCraft RecursiveFlip images/input.png
java -cp "out" PixelCraft InvertColors images/input.png
java -cp "out" PixelCraft Brighten images/input.png
java -cp "out" PixelCraft ColorSort images/input.png

```
