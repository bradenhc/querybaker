# QueryBaker
Simple database query builder for CRUD operations written in Java. The purpose of this query builder is to provide an easy way to generate and manipulate String-based database queries that can be handed off to standard and third party database connection libraries. This builder does not provide support for database connections or container connection drivers.

Ultimately this builder will also include query validation based on a schema imported into the table. It will also support reading in table schema definitions from a configuration file, allowing a program to easily create, drop, and manipulate tables.

## Maven
```xml
<dependency>
    <groupId>io.github.bradenhc</groupId>
    <artifactId>querybaker</artifactId>
    <version>1.0.0</version>
</dependency>
```

*Note: this installs the latest stable version. To install a different version, change the value of the `<version>` tag*

## Quickstart
Below are some basic examples of queries that, when executed, result in simple CRUD operations on a database. Note that the query builder only provides the query to be able to manipulate the database. It does not actually manipulate the database itself.

```java
// Tables and Columns are the backbone of the baker
Column c1 = new Column("first_column", DataType.INTEGER, 1);
Column c2 = new Column("second_column", DataType.VARCHAR, 255);
Table t = Table.create("table_name").alias("tn").columns(c1, c2);

// Create a query to create the table
String q = t.build();

// Insert into a Table
Insert i = t.insert().values(pair(c1, 24), pair(c2, "Hello World!"));
q = i.build();

// Update table values
Update u = t.update().values(pair(c1, 56), pair(c2, "Goodbye!"));
q = u.build();

// Select on table values
Select s = t.select().columns(c1, c2).where(equal(c1, 56));
q = s.build();

// Delete from a table
Delete d = t.delete().where(equal(c2, "Goodbye!"));
q = d.build();

// Drop a table
q = t.drop().build();

// Truncate a table
q = t.truncate().build();
```

## Conditions
Most database queries do not operate on ALL of the data inside of a table. They act on filtered views of the data based on some condition. QueryBaker provides a simple way to generate a conditional statement by using static methods from the `Condition` class.

```java
import static com.github.bradenhitchcock.querybaker.cond.Condition.*

// A very simple condition that just compares two values. Turning this condition 
// into a string will result in the following: col = value
Condition c = equal(col, value);

// Conditions can be as complex as you want them to be by nesting static calls 
// within each other. 
c = not(equal(col, value));
c = and(not(equal(col, value)), equal(col, value));
c = or(
        and(
            not(lessThan(col, value)), 
            equal(col, value), 
            or(
                greaterThanOrEqual(col, value), 
                equal(col, value)
            )
        ),
        equal(col, value)
    );
```
