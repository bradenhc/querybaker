# QueryBaker
Simple database query builder for CRUD operations written in Java. The purpose of this query builder is to provide an easy way to generate and manipulate String-based database queries that can be handed off to standard library and third party database connection libraries. This builder does not provide support for database connections or container connection drivers.

Ultimately this builder will also include query validation based on a schema imported into the table.

### Brainstorming

```java
// Tables are the backbone
Table t = RelationTable.name(...).column(new Column(...)).column(new Column(...)).build();


Query q = t.select().column(...).where(...).order(...).build();

// There is also
t.insert().columns(...).values(...).build();
t.update().set(..., ...).set(..., ...).where(...).build();
t.delete().where(...).build();

// With support for NoSQL queries in the future

// The resulting query can be turned into a string
Result r = Database.query(q.build()).execute();
```

Conditions

```
// Conditions are most useful for querying
Condition c = where(equal(var, value));

// Or more complex ones
where(not(equal(var,value)))
where(and(not(equal(var, value)), equal(var, value)))

// Perhaps to improve readability
where(not(equal(var,value).and(not(equal(var, value)))
where(equal(var, value).and(equal(var, value)).or(equal(var, value), equal(var, value), equal(var, value)
```