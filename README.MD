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
    This means you can store documents with different structures in the same collection. 
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
MongoDB stores documents in collections. Collections are analogous to tables in relational databases.

## Document
A record in MongoDB is a document, which is a data structure composed of field and value pairs. MongoDB documents are similar to JSON objects. The values of fields may include other documents, arrays, and arrays of documents.
![documentView](img.png)

Documents are stored in a format called BSON (Binary JSON).

The value of a field can be any of the BSON data types, including other documents, arrays, and arrays of documents. For example, the following document contains values of varying types:
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

The above fields have the following data types:

* _id : holds an [ObjectId](#ObjectId).
* name : holds an embedded document that contains the fields first and last.
* birth : hold values of the Date type.
* skills : holds an array of strings.
* views : holds a value of the NumberLong type.

### ObjectId
ObjectIds are small, likely unique, fast to generate, and ordered.

**_id** : _ObjectId_('6643416132f2740742398b74')

ObjectId values are **12 bytes** in length, consisting of:
1. A 4-byte timestamp, representing the ObjectId's creation, measured in seconds since the Unix epoch.
2. A 5-byte random value generated once per process. This random value is unique to the machine and process.
3. A 3-byte incrementing counter, initialized to a random value.

In MongoDB, each document stored in a collection requires a unique **_id** field that acts as a primary key. 
If an inserted document omits the _id field, the MongoDB driver automatically generates an ObjectId for the 
_id field.

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
By default, when an insert or update operation would result in an invalid document, MongoDB rejects the operation and does not write the document to the collection.

Alternatively, you can configure MongoDB to allow invalid documents and log warnings when schema violations occur.

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
          description: "must be a string and is required"
        },
        age: {
          bsonType: "int",
          minimum: 18,
          description: "must be an integer and is required"
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

* minLength: Specifies the minimum length for a string.
* maxLength: Specifies the maximum length for a string.
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
* minItems: Specifies the minimum number of items in the array.
* maxItems: Specifies the maximum number of items in the array.
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

1.Inserting a Single Document : To insert a single document into a collection, use the insertOne method. If the collection doesn't exist, MongoDB will create it:

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
{ name: "Charlie", age: 35, city: "Chicago" },
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