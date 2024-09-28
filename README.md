# Radar

Radar is a java library which does sport matches management.

## Installation

To use the library you have to include it to your project dependency

```maven
    <dependency>
      <groupId>com.sport.radar</groupId>
      <artifactId>sport-radar</artifactId>
      <version>1.0</version>
    </dependency>
```
```gradle
    implementation 'com.sport.radar.sport-radar:1.0'
```
## Usage

Main class that should be used is com.sport.radar.ScoreBoard which provides method for matches management. 
The main class is thread safe.
```create
  ScoreBoard board = new ScoreBoard()
```
After bean is created you can use board object to
* Start match
* Update match score
* Finish match
* Get list of matches (matches returned in total score descendant order and if total score is the same then most resent started match goes first)
