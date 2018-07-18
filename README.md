# Briscola Master AI
This is an Artificial Intelligence that aims to be very good at playing [Briscola](https://en.wikipedia.org/wiki/Briscola), a card game very popular in Italy.

## Players
The game runs with two players, there are multiple player class available:

- [RandomPlayer](https://github.com/devgianlu/BriscolaMasterAI/blob/master/src/com/gianlu/briscolamasterai/Players/RandomPlayer.java), plays random cards (very dumb)
- [ConsolePlayer](https://github.com/devgianlu/BriscolaMasterAI/blob/master/src/com/gianlu/briscolamasterai/Players/ConsolePlayer.java), lets you play from the console against the other player
- [PseudoAiPlayer](https://github.com/devgianlu/BriscolaMasterAI/blob/master/src/com/gianlu/briscolamasterai/Players/PseudoAiPlayer.java), a pseudo-AI based on cards gain and harmfulness (not so strong)
- [MiniMaxPlayer](https://github.com/devgianlu/BriscolaMasterAI/blob/master/src/com/gianlu/briscolamasterai/Players/MiniMaxPlayer.java), a Minimax implementation with controllable maximum depth

### PseudoAiPlayer
This player always tries to make the most points immediately when playing as second and tries to give away less points possible when playing first.
> Example: it would take a two with an ace of trump instead of giving away some points

### MiniMaxPlayer
At depth 8 (3-4 seconds per match) it can win against RandomPlayer, but loses against PseudoAi and Ai. **I hope the implementation is correct!**

## Benchmarking
You can benchmark the game by running multiple instances in series, the [Benchmark class](https://github.com/devgianlu/BriscolaMasterAI/blob/master/src/com/gianlu/briscolamasterai/Benchmark.java) helps you doing so.