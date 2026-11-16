cd "C:\Users\Admin\Desktop\ISEL\Prog ISEL\CanvasLib"

& "C:\Gradle\gradle-8.5\bin\gradle.bat" clean jvmJar

kotlin -cp "build\libs\CanvasLib-jvm-1.0.2.jar" MainKt

#jogo com os arquivos separados#

# Navigate to project root (use backslashes or quote the path)
  cd "C:\Users\Admin\Desktop\ISEL\Prog ISEL\CanvasLib"                                                                                                                                                        
  # Compile - use backslashes and quote paths with spaces                                                                                                                                                  
  kotlinc -d out src\jvmMain\kotlin\pt\isel\canvas\*.kt src\jvmMain\kotlin\*.kt

  # Run - Windows classpath uses semicolons, not colons
  # First find your Kotlin lib path:
  kotlin -version
  # Then use that path, e.g.:
  java -cp "out;C:\path\to\kotlin\lib\*" pt.isel.canvas.MainKt