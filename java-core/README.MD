# About
A console script program that parses the flow of JSON files of the main entity and generates statistics  in terms of one of its attributes.

## Entity: Player

- **`name`** (String) - name of player
- **`surname`** (String) - surname of player
- **`team`** (String) - player's team
- **`yearOfBirth`** (Integer) - player's year of birth
- **`position`** (List<String>) - position that player plays in team
## Example Input JSON

```json
[
  {
    "name": "John",
    "surname": "Doe",
    "team": "Team A",
    "year_of_birth": "2003",
    "position": "Center, Power Forward"
  },
  {
    "name": "Jane",
    "surname": "Smith",
    "team": "Team B",
    "year_of_birth": "2000",
    "position": "Shooting guard, Point Guard"
  },
  {
    "name": "Mike",
    "surname": "Johnson",
    "team": "Team A",
    "year_of_birth": "2001",
    "position": "Small forward, Point Guard"
  }
]
```
## Example Output XML
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<statistics>
    <item>
        <value>Team A</value>
        <count>2</count>
    </item>
    <item>
        <value>Team B</value>
        <count>1</count>
    </item>
</statistics>

```
## How to run the app
1. cd java-core/ 
2. mvn clean package
3. java -jar target/java-core-1.0-SNAPSHOT.jar $path $parameter

where:
1. $path - path to JSON directory;
2. $parameter - attribute(year_of_birth, position, team);

## Performance
```yaml
Threads | Parsing Time (ms) | Players Parsed
--------------------------------------------
2       | 150              | 1000
4       | 120              | 1000
8       | 100              | 1000
```
In this example, as the number of threads increased, the parsing time decreased, 
indicating improved performance. However, the difference in parsing time between 4 and 8 threads 
was less significant compared to the difference between 2 and 4 threads, suggesting diminishing 
returns beyond a certain thread count. Additionally, the number of players parsed remained consistent 
across all thread counts, indicating stable parsing behavior.