Darkmatter
==========

This game was created for a module on Java and developing software as
a team and this is what we created. It's a bit rough around the edges
but not bad for 9 weeks of effort.

Run the game
------------

You will need [Apache Maven][maven] to build the project. Once you
have it you can build the game like so:

    mvn package

It will run a series of unit tests, and if they all pass it will
package the game as a jar.  You can then run the game with the
following commands.

Single Player: 
       
    java -jar target/darkmatter-final.jar

Multiplayer:

    java -cp target/darkmatter-final.jar com.giantcow.darkmatter.net.Server
    java -jar target/darkmatter-final.jar
    

[maven]: http://maven.apache.org/