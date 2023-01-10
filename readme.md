# requirements

Java 11 - just take the latest from https://adoptium.net/temurin/releases/?version=11

# running it

extract this if zipped

open your terminal and cd into the bullet-arena project

execute ```java -jar desktop-1.0.jar```

# building from scratch

you can clone from github:

```git clone https://github.com/simonmdsn/bullet-arena.git```

run the ```dist``` gradle task, if you are using IntelliJ it is on the far right menubar by default.

then move the jar file inside the ```desktop/build/libs``` folder to the root of the project.

or do ``mv ./desktop/build/libs/desktop-1.0.jar ./``

then do ``java -jar desktop-1.0.jar`` to run the program
