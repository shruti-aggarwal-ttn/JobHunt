# Unlocking the Power of MongoDB: A Beginner's Guide

## What is MongoDB?

Imagine you have a giant digital notebook where you can jot down anything you like. Sometimes you write a list, other times you jot down a story or perhaps some random thoughts. This notebook is flexible and doesn't force you to organize your notes in a specific way. That's pretty much how MongoDB works.

MongoDB is a type of database known as NoSQL. Unlike traditional databases that store data in neat, structured tables (like spreadsheets), MongoDB stores data in a flexible, semi-structured way. This makes it incredibly versatile and easy to use, especially when dealing with large amounts of diverse data.

>SUMMARY
>- It is a NO SQL database as the data is not stored in the form of tables.
>- Data in MongoDB is stored in the form of [Document](#Document).
>- Being a NO SQL , it offers flexible different structures in the [Collection](#Collection)

## MongoDB VS MySQL
Usually choosing the type of db depends on the requirement of the application. But some benefits of Mongo DB over MySQL is:
 
### 1.Schema Flexibility:

    MongoDB: Being a NoSQL database, MongoDB offers a flexible schema design. 
    This means you can store different structures of documents in the same collection. 
    It is beneficial when dealing with unstructured or semi-structured data, or when the 
    schema might evolve over time.

    MySQL: As a relational database, MySQL enforces a fixed schema. Each row in a table
    must adhere to the predefined schema, which can be restrictive if your data model changes frequently.

### 2.Performance:

    MongoDB: Excels in read and write operations, especially with large volumes of data.
    Its document-oriented storage allows for faster retrieval of nested data structures
    without complex JOIN operations.

    MySQL: Performs well with structured data and complex queries involving multiple 
    tables and relationships. However, JOIN operations can become a bottleneck with very large
    datasets or highly relational data.


## Collection
MongoDB stores documents in collections. Collections in MongoDB are comparable to tables in relational databases.

## Document
A record in MongoDB is a document, consisting of field and value pairs. These documents resemble JSON objects and can contain nested documents, arrays, and arrays of documents.
![documentView](img.png)

Documents are stored in a format called BSON (Binary JSON).

The value of a field in MongoDB can be any BSON data type, including nested documents, arrays, and arrays of documents. For instance, the following document showcases various types of values:
```javascript
var mydoc = {
  _id: ObjectId("5099803df3f4948bd2f98391"),
  name: { 
    first: "Shruti", 
    last: "Agg" 
  },
  birth: new Date('Jun 23, 1999'),
  skills: ["Java", "Spring boot"],
  views: NumberLong(1250000)
}
```

The fields in the above document have the following data types:

* _id : holds an [ObjectId](#ObjectId).
* name: Contains an embedded document with the fields first and last.
* birth: Contains values of the Date type.
* skills: Holds an array of strings.
* views: Contains a value of the NumberLong type.

### ObjectId
ObjectIds are small, ordered , likely unique, and fast to generate .

**_id** : _ObjectId_('6643416132f2740742398b74')

ObjectId values are **12 bytes** in length, consisting of:
1. A 4-byte timestamp, representing the ObjectId's creation, measured in seconds since the Unix epoch.
2. A 5-byte random value generated once per process. This random value is unique to the machine and process.
3. A 3-byte incrementing counter, initialized to a random value.

In MongoDB, every document in a collection must have a unique **_id** field that serves as the primary key. 
If a document is inserted without an _id field, MongoDB will automatically generate an ObjectId for it. 

when an document is fetched , _id is returned in this format.
```javascript
"id": 
{
  "timestamp": 66434161,
  "date": "2024-05-14T10:48:01.000+00:00"
}
```
## Creating Databases and Collections

**Creating a Database** : In MongoDB, you can explicitly create a database using the use command:
>use <my_database>

This command switches to the my_database database. If the database doesn’t exist, MongoDB creates it for you.

**Creating a Collection** : In MongoDB, you want to create an empty collection without inserting any documents using this command:
>db.createCollection("my_collection")

You can also create a collection by [inserting a document](#Insert-A-Document) into the collection using this command (_Will discuss more in further about how to insert document_):
>db.my_collection.insertOne({ name: "shruti" })

## Collection Validation/Constraints
Validation lets you create validation rules for your fields, such as allowed data types and value ranges.
MongoDB offers flexible design , but still if you want to impose some constraints on collection for application requirement like:
* Password should be only string.
* Email should be matching a regex.
* Some field need to be unique, non-empty.

Rules can be applied for insert and update of document while creating Collection. 

**What Happens When a Document Fails Validation** :
By default, MongoDB rejects insert or update operations that would create an invalid document, preventing the document from being written to the collection. 

However, you can configure MongoDB to permit invalid documents and log warnings when schema violations occur.

### Adding Constraints to collection
MongoDB allows you to enforce data integrity and add constraints to your collections using **validators and indexes**.
#### Validators
Validators ensure that documents meet specific criteria before they are inserted or updated. You can define validators when creating a collection using the **createCollection** command. For example, to ensure that documents in the users collection have a non-empty **name** field and an **age** field greater than or equal to 18:
```javascript
db.createCollection("users", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["name", "age"],
      properties: {
        name: {
          bsonType: "string",
          description: "required field and should be string"
        },
        age: {
          bsonType: "int",
          minimum: 18,
          description: "required field and should be integer"
        }
      }
    }
  }
})
```
-- Some Other common types of Validators are:

1.Data Type Validators

* bsonType: Ensures the field is of a specific BSON type. Available types include: string, int, long, double, decimal, bool, date, array, object, etc.

```javascript
bsonType: "string" // Ensures the field is a string
```

2.Required Fields 

* required: Specifies an array of field names that must be present in the document.

```javascript
required: ["name", "age"] // Ensures 'name' and 'age' fields are present
```

3.Numeric Constraints

* minimum: Specifies the minimum value for a numeric field.
* maximum: Specifies the maximum value for a numeric field.

```javascript
age: {
bsonType: "int",
minimum: 18,
maximum: 100
}
```
4.String Constraints

* minLength: Indicates the min length for the string.
* maxLength: Indicates the max length for the string.
* pattern: Ensures the string matches a specified regular expression.

```javascript
email: {
bsonType: "string",
pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
description: "must be a valid email address"
}
```

5.Array Constraints

* items: Specifies the schema for items in an array.
* minItems: Indicates the min no of items in the array.
* maxItems: Indicates the max no of items in the array.
* uniqueItems: Ensures all items in the array are unique.

```javascript
tags: {
bsonType: "array",
items: {
bsonType: "string"
},
minItems: 1,
uniqueItems: true
}
```

6.Object Constraints

* properties: Defines the schema for fields within an object.
* additionalProperties: Controls whether extra fields are allowed.

```javascript
address: {
bsonType: "object",
required: ["street", "city"],
properties: {
street: {
bsonType: "string"
},
city: {
bsonType: "string"
},
zipcode: {
bsonType: "string",
pattern: "^[0-9]{5}$"
}
},
additionalProperties: false // No extra fields allowed
}
```
## Basic CRUD Operations
### Insert a Document

1.Inserting a Single Document : To insert a single document into a collection, use the insertOne method. 
MongoDB creates the collection , if it doesn't exists:

```javascript
db.users.insertOne({
name: "Alice",
age: 25,
city: "New York"
})
```

2.Inserting Multiple Documents : To insert multiple documents at once, use the insertMany method:

```javascript
db.users.insertMany([
{ name: "Bob", age: 30, city: "Los Angeles" },
{ name: "Sanjay", age: 35, city: "Amritsar" },
{ name: "Diana", age: 28, city: "San Francisco" }
])
```
### Reading a Document

1.Retrieving All Documents :To retrieve all documents in a collection, use the find method without any parameters:

```javascript
db.users.find()
```

2.Retrieving Specific Documents : To retrieve documents that match certain criteria, pass a query object to the find method. For example, to find all users named "Alice":

```javascript
db.users.find({ name: "Alice" })
```

3.Retrieving a Single Document :To retrieve a single document that matches a query, use the findOne method:

```javascript
db.users.findOne({ name: "Alice" })
```

### Updating Document

1.Updating a Single Document :To update a single document, use the updateOne method. For example, to update Alice's age:

```javascript
db.users.updateOne(
{ name: "Alice" }, // Filter
{ $set: { age: 26 } } // Update
)
```
The $set operator updates only the specified fields.

2.Updating Multiple Documents :To update multiple documents, use the updateMany method. For example, to increment the age of all users by 1 year:

```javascript
db.users.updateMany(
{}, // Filter (empty object matches all documents)
{ $inc: { age: 1 } } // Update
)
```
The $inc operator increments the specified field by the given value.

3.Replacing a Document :To replace an entire document, use the replaceOne method. This method replaces the entire document except for the _id field:

```javascript
db.users.replaceOne(
{ name: "Alice" }, // Filter
{ name: "Alice", age: 27, city: "New York", job: "Engineer" } // Replacement
)
```

### Delete Documents
1.Deleting a Single Document : To delete a single document, use the deleteOne method. For example, to delete the first document named "Alice":

```javascript
db.users.deleteOne({ name: "Alice" })
```

2.Deleting Multiple Documents : To delete multiple documents, use the deleteMany method. For example, to delete all users from "Los Angeles":

```javascript
db.users.deleteMany({ city: "Los Angeles" })
```

## Indexing
Indexes improve the performance of read operations by reducing the amount of data MongoDB needs to scan. 

##### Use Cases :

You can create an index on fields , which are repeatedly getting queried by an application.

### Create an Index
An index can be created using the **createIndex()** method

##### Types of Indexes
1.Single Field Indexes : A single field index is an index on a single field of a document. It is the common type of index.

```javascript
db.users.createIndex({ name: 1 })
```
Here, the index is created on the name field in ascending order (**1 for ascending, -1 for descending**).

2.Compound Indexes:
A compound index is a multiple fields index. It is useful for queries that match on multiple fields.

```javascript
db.users.createIndex({ name: 1, age: -1 })
```
This creates an index on the name field in ascending order and the age field in descending order.

3.Multikey Indexes :Multikey indexes are used to index fields that hold arrays. Each array value is indexed as a separate value.

```javascript
db.users.createIndex({ tags: 1 })
```
If the tags field contains an array, each element of the array will be indexed.

### Viewing Indexes
To view all indexes on a collection, use the getIndexes method.

```javascript
db.collection.getIndexes()
```
### Dropping an Index
1.To drop an index, use the dropIndex method.

```javascript
db.collection.dropIndex("index_name")
```
2.To drop all indexes on a collection, use the dropIndexes method.

```javascript
db.collection.dropIndexes()
```

## Aggregation Framework
MongoDB's aggregation framework is a powerful tool that allows you to process and transform your data in complex ways, making it possible to perform data analysis directly within the database. Whether you need to filter, group, sort, or transform your data, the aggregation framework provides a flexible and efficient solution.

It works by using a pipeline of stages, where each stage transforms the documents as they pass through. This pipeline model allows for powerful and flexible data manipulations, similar to SQL's GROUP BY and JOIN operations but with more versatility.

### Key Components of the Aggregation Framework
#### Aggregation Pipeline
The aggregation pipeline is a sequence of stages, each performing a specific operation on the input documents. The output of earlier stage is used as the input for the next stage. Some of the commonly used stages include:

- **$match**: Filters documents to pass only those that match the specified conditions.
- **$group**: Aggregates input documents based on a specified identifier expression and uses accumulator expressions to perform operations on the grouped data.
- **$project**: Reshapes each document in the stream, such as including, excluding, or adding new fields.
- **$sort**: Sorts the documents.
- **$limit**: Limits the number of documents passing through the pipeline.
- **$skip**: Skips documents as specified.

#### Expressions
Expressions are used within stages to compute values. They can include field paths, literals, and system variables, among others. MongoDB provides a rich set of expressions for arithmetic, string manipulation, array manipulation, and more.

#### Basic Aggregation Operations
1.The $match Stage : 
The $match stage filters documents by specified criteria. It is similar to the find operation and is typically used as the first stage in the pipeline.

```javascript
db.orders.aggregate([
{ $match: { status: "completed" } }
])
```

2.The $group Stage : 
The $group stage groups documents by a specified identifier and can perform operations such as sum, average, count, min, and max.

```javascript
db.orders.aggregate([
{
$group: {
_id: "$customerId",
totalAmount: { $sum: "$amount" },
averageAmount: { $avg: "$amount" }
}
}
])
```

3.The $project Stage : 
The $project stage reshapes each document, allowing you to include, exclude, or add new fields.

```javascript
db.orders.aggregate([
{
$project: {
customerId: 1,
amount: 1,
discount: { $multiply: ["$amount", 0.1] }
}
}
])
```

4.The $sort Stage : 
The $sort stage sorts the documents by specified fields in ascending or descending order.

```javascript
db.orders.aggregate([
{ $sort: { amount: -1 } }
])
```

5.The $limit and $skip Stages : 
The $limit and $skip stages are used to control the number of documents in the output.
This is like Pagination. "$skip" is for pageNo and "$limit" is for pageSize. 

```javascript
db.orders.aggregate([
{ $sort: { amount: -1 } },
{ $limit: 10 }
])
```

You can create stages separately , add it to a list and then query them together using the **aggregate()** function.

#### Here is an example of Aggregation Pipeline in JAVA.
```java
void workingOnAggregationPipeline() {
    List<Document> stages = new ArrayList<>();
    stages.add(new Document("$search",
            new Document("index", "JobSearch").append("text",
                    new Document("query", text).append("path", new Document(
                            "wildcard", "*")))));
    stages.add(new Document("$sort",
            new Document(input.getSortBy(), Sort.Direction.ASC.equals(input.getSort()) ? 1 : -1)));
    int skip = (input.getPageNo() - 1) * input.getPageSize();
    stages.add(new Document("$skip", skip));
    stages.add(new Document("$limit", input.getPageSize()));
    AggregateIterable<Document> result = collection.aggregate(stages);
}
```
#### In the Above Example :
1. We are creating a list of pipeline stages.
2. First Stage: The first stage is the Search stage. We are creating a document(representing a new json object '{}') for search stage and then inside search stage we have a document which mentions the index to be used 
    and then the text to be searched.
    "**Path**" is used to set the document fields which needs to be scanned for that text , 
    We can mention a list of fields or as mentioned here use a wildcard which means to scan the whole document.
3. Second Stage: the second stage is the Sort stage. Output from First stage will be used in second stage. 
   Here we are creating a document for sort and inside that we have another document which contains a field on which it needs to be sorted and the Direction in which it needs to be sorted.
   **1 means Ascending order and -1 means descending order.**
4. Third Stage : The third stage is the Skip stage. Similar to first two here we create a document of skip and mentioned the pages it need to skip to reach the desired page.
5. Fourth Stage : The fourth stage is limit stage. It will limit the page size.
6. Then all the stage are aggregated together using the aggregate function.

#### The Above example of Aggregation pipeline in Shell form.
```javascript
db.collection.aggregate([
    {
        $search: {
            index: "JobSearch",
            text: {
                query: text,
                path: {
                    wildcard: "*"
                }
            }
        }
    },
    {
        $sort: {
            [input.sortBy]: input.sort === "ASC" ? 1 : -1
        }
    },
    {
        $skip: (input.pageNo - 1) * input.pageSize
    },
    {
        $limit: input.pageSize
    }
]);
```
# Conclusion
MongoDB is a powerful, flexible, and scalable database solution that can handle a variety of applications. Whether you're building a small web app or a large-scale enterprise system, MongoDB’s document-oriented approach and rich set of features make it a great choice.

By now, you should have a good understanding of what MongoDB is, why it’s useful, and how to perform basic operations. Happy coding!