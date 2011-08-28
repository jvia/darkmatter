/**
 * Hold all the networking code for the game.
 * <p>
 * It is split of between the {@link com.giantcow.darkmatter.net.Client} and
 * {@link com.giantcow.darkmatter.net.Server}. The {@code Server} runs as a single instance and
 * spawns a new thread for each {@code Client} that tries to connect. The {@code Server} can handle
 * a theoretically unlimited numbers of players. The only constraints are on the game's particular
 * level and the system resources.
 * <p>
 * When enough clients have connected to the server for the particular game to start, one of the
 * Client handlers on the server will spawn the GameLoop thread which will being the game. The
 * game runs on the server. The clients essentially get snapshots of the game state at any given
 * time and pass it to the player's painting code.
 * <p>
 * The player interacts with others by using the client to send click messages. This minimizes
 * the amount of data being sent on the network because clients are sending small objects which
 * the server can easily deal with.
 * <p>
 *
 * {@textdiagram overview
 *
 *                             +---------------------+
 *                             | cRED                |
 *                             |      DarkMatter     |
 *                             |                     |
 *                             +-----------+---------+
 *                                         |
 *                                         |
 *                                         |
 *                 +-----------------------+--------------------+
 *                 | cRED                                       |
 *                 |                   Client                   |
 *                 |                                            |
 *                 | o Client asks for state of the world       |
 *                 | o Client sends any clicks                  |
 *                 +--------------------------------------------+
 *                                        ^
 *                                        :
 *                                        :
 *                                        :
 *                                        V
 *                 +--------------------------------------------+
 *                 | cBLU                                       |
 *                 |                  Handler                   |
 *                 |                                            |
 *                 | o Deals with user messages                 |
 *                 | o Sends back response if necessary         |
 *                 | o Handler is unique to each client         |
 *                 +---------------------+---------+------------+
 *                                       |         |
 *                               +-------+         +--------------------+
 *                               |                                      |
 *                 +-------------+------------+         +---------------+-------------+
 *                 | cBLU                     |         | cBLU                        |
 *                 |     Server               |         |       GameLoop              |
 *                 |                          |         |                             |
 *                 | o Maintains global state +---------+ o Runs all the game logic   |
 *                 | o Handler for each client|         | o Only one may exists       |
 *                 +--------------------------+         +-----------------------------+
 *}
 */
package com.giantcow.darkmatter.net;