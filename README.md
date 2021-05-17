# StrideUp
## Instruction
The objectives are to develop an API that could
 - Fetch all the Parks from the RemoteAPI
 - Fetch a specific Park identified by 'parkCode' from the RemoteAPI
 - Post to create a new 'parkCode' to the Internal DB
 - Put to update an existing Park to the Internal DB
 - Put does not take any request parameters. the parkCode is provided in the Request Body

## API Spec ( OpenAPI specification )
paths:
  /parks:
    get:
      summary: Fetch all the Parks from both the RemoteAPI and the InternalAPI
      requestBody:
        content:
          None
      responseBody:
        '200':
          description: OK
        content:
          application/json:
            schema :
              type: object
              properties:
                refer to rdm API
    post:
      summary: Create a new Park and add it to the IntetnalDB
      requestBody:
        content:
          application/json:
            schema :
              type: object
              properties:
                refer to rdm API
      responseBody:
        '200':
          description: OK
        content:
          application/json:
            schema :
              type: object
              properties:
                refer to rdm API
    put:
      summary: Update the identified park to the InternalDB
      requestBody:
        content:
          application/json:
            schema :
              type: object
              properties:
                refer to rdm API
      responseBody:
        '200':
          description: OK
        content:
          application/json:
            schema :
              type: object
              properties:
                refer to rdm API
  /parks/{parkCode}:
    get:
      summary: Fetch the identified park from both the RemoteAPI and InrernalDB
      requestBody:
        content:
          None
      responseBody:
        '200':
          description: OK
        content:
          application/json:
            schema :
              type: object
              properties:
                refer to rdm API

## What technologies have been used ?
 - Spring Framework has been used for this exercise.
 - Hibernate has been used for the database management.

## Improvements ?
 - Initial Implementation :
   - The code has been first develpped with multiple tables joined with each other.
   - Those entities can be found in /models_dao/extra_dao
   - This implementation took a long time to implement manually and makes the runtime slow, and the code is not clean as can be seen in /services/extra/WholeDatabase.java
   - No faster way to implement it has been found yet
   - Because of the above, this implementation has been given up
   - This implementation would have made it easy to Expand. Hence, making it Open for expantion from SOLID
 - Current Implementation:
   - The database management is much simpler :
     - Primary Key contains the 'parkCode'
     - The second field contains the whole body of the park.
     - This simple implementation makes it close for extension unfortunately, no other field than parkCode cold be used to search for an element.
     - If the requirements change, the modifications would be expansive.
     - But, sort of fortunately, an unclean , and slow , version of the implementation has been developped to store each subfield in a separate database if needed. Refer to '/services/extra/WholeDatabase.java'

## What to do differently if given enough time
 - In this implementation:
   - Each class tend to be single purpose only.
   - Not open for extension, but it would have been if it has been decided to carry-out the slow approach of '/services/extra/WholeDatabase.java' : Each class would extend from Database Class and implement the interface they want to in order to extract different entity.
